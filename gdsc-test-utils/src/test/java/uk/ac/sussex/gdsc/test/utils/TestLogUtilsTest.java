/*-
 * #%L
 * Genome Damage and Stability Centre Test Utilities
 *
 * Contains utilities for use with test frameworks.
 * %%
 * Copyright (C) 2018 - 2020 Alex Herbert
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package uk.ac.sussex.gdsc.test.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.ac.sussex.gdsc.test.utils.TestLogUtils.TestLevel;

@SuppressWarnings("javadoc")
class TestLogUtilsTest {
  private static Logger logger;

  @BeforeAll
  public static void beforeAll() {
    logger = Logger.getLogger(TestLogUtilsTest.class.getName());
  }

  @AfterAll
  public static void afterAll() {
    logger = null;
  }

  @Test
  void canGetStaceTraceElement() {
    final StackTraceElement[] e = new Throwable().getStackTrace();
    final StackTraceElement o = TestLogUtils.getStackTraceElement();
    final StackTraceElement o2 = TestLogUtils.getStackTraceElement(1);
    Assertions.assertNotNull(e);
    Assertions.assertNotNull(o);
    Assertions.assertEquals(e[0].getClassName(), o.getClassName());
    Assertions.assertEquals(e[0].getMethodName(), o.getMethodName());
    Assertions.assertEquals(e[0].getLineNumber() + 1, o.getLineNumber());
    logger.log(TestLevel.TEST_INFO,
        () -> String.format("%s:%s:%d", o.getClassName(), o.getMethodName(), o.getLineNumber()));

    Assertions.assertNotNull(o2);
    Assertions.assertEquals(e[1].getClassName(), o2.getClassName());
    Assertions.assertEquals(e[1].getMethodName(), o2.getMethodName());
    Assertions.assertEquals(e[1].getLineNumber(), o2.getLineNumber());
  }

  @Test
  void getStaceTraceElementIsNullWhenNotLocated() {
    final int size = new Throwable().getStackTrace().length;
    final StackTraceElement o = TestLogUtils.getStackTraceElement(size);
    Assertions.assertNull(o);
  }

  @Test
  void getStaceTraceElementThrowsWhenCountIsNegative() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      TestLogUtils.getStackTraceElement(-1);
    });
  }

  @Test
  void canGetCodePoint() {
    final StackTraceElement e = new Throwable().getStackTrace()[0];
    final String codePoint = TestLogUtils.getCodePoint();
    Assertions.assertNotNull(e);
    Assertions.assertNotNull(codePoint);
    final String[] o = codePoint.split(":");
    Assertions.assertEquals(e.getClassName(), o[0]);
    Assertions.assertEquals(e.getMethodName(), o[1]);
    Assertions.assertEquals(e.getLineNumber() + 1, Integer.parseInt(o[2]));
    logger.log(TestLevel.TEST_INFO, codePoint);
    Assertions.assertEquals("", TestLogUtils.getCodePoint(null));
  }

  @Test
  void canGetRecord() {
    final StringBuilder sb = new StringBuilder("A StringBuilder passed as an object");
    for (final Level l : new Level[] {TestLevel.TEST_INFO, TestLevel.TEST_DEBUG}) {
      //@formatter:off
      logger.log(check(l, TestLogUtils.getRecord(l, "This is a record"), "This is a record"));
      logger.log(check(l, TestLogUtils.getRecord(l, "This is a formatted record: %d", 1), String.format("This is a formatted record: %d", 1)));
      logger.log(check(l, TestLogUtils.getRecord(l, () -> String.format("This is a supplier record: %d", 1)), String.format("This is a supplier record: %d", 1)));
      logger.log(check(l, TestLogUtils.getRecord(l, sb), sb.toString()));
      //@formatter:on
    }
  }

  private static LogRecord check(Level level, LogRecord record, String expected) {
    Assertions.assertEquals(level, record.getLevel());
    Assertions.assertEquals(expected, record.getMessage());
    return record;
  }

  private static LogRecord check(Level level, LogRecord record, String... contains) {
    Assertions.assertEquals(level, record.getLevel());
    final String msg = record.getMessage();
    for (final String expected : contains) {
      Assertions.assertTrue(msg.contains(expected), expected);
    }
    return record;
  }

  @Test
  void canSerializeRecord() throws IOException, ClassNotFoundException {
    final String format = "This is a supplier record: %d";
    final Object[] args = new Object[] {1};
    final String expected = String.format(format, args);
    for (final Level l : new Level[] {TestLevel.TEST_INFO, TestLevel.TEST_DEBUG}) {
      checkSerialize(l, TestLogUtils.getRecord(l, expected), expected);
      checkSerialize(l, TestLogUtils.getRecord(l, format, args), expected);
      checkSerialize(l, TestLogUtils.getRecord(l, () -> String.format(format, args)), expected);
    }
  }

  private static void checkSerialize(Level level, LogRecord record, String expected)
      throws IOException, ClassNotFoundException {

    final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
      out.writeObject(record);
    }

    final ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
    try (ObjectInputStream in = new ObjectInputStream(bis)) {
      final LogRecord deserializedRecord = (LogRecord) in.readObject();
      Assertions.assertEquals(level, deserializedRecord.getLevel());
      Assertions.assertEquals(expected, deserializedRecord.getMessage());
    }
  }

  @Test
  void canGetFailRecord() {
    //@formatter:off
    logger.log(check(TestLevel.TEST_FAILURE, TestLogUtils.getFailRecord("This is a failed record"), "This is a failed record"));
    logger.log(check(TestLevel.TEST_FAILURE, TestLogUtils.getFailRecord("This is a failed formatted record: %d", 1), String.format("This is a failed formatted record: %d", 1)));
    logger.log(check(TestLevel.TEST_FAILURE, TestLogUtils.getFailRecord(() -> String.format("This is a failed supplier record: %d", 1)), String.format("This is a failed supplier record: %d", 1)));
    //@formatter:on

    // Since the stack trace looks bad in the output create a dummy one
    final Throwable t = new Throwable("This is a failed record with thrown");
    StackTraceElement[] trace = t.getStackTrace();
    trace = Arrays.copyOf(trace, 2);
    final String className = this.getClass().getSimpleName();
    trace[1] = new StackTraceElement("stack.trace.truncated.for." + className, "itWouldBeLonger",
        "DummyClass.java", 10);
    t.setStackTrace(trace);
    logger.log(check(TestLevel.TEST_FAILURE, TestLogUtils.getFailRecord(t),
        "This is a failed record with thrown"));
  }

  @Test
  void canGetTimingRecord() {
    //@formatter:off
    // Default levels
    logger.log(check(TestLevel.TEST_INFO, TestLogUtils.getTimingRecord("slow", 123, "fast", 21), "slow", "fast", "123", "21"));
    logger.log(check(TestLevel.TEST_FAILURE, TestLogUtils.getTimingRecord("slow", 7, "fast", 21), "slow", "fast", "7", "21"));
    logger.log(check(TestLevel.TEST_INFO, TestLogUtils.getStageTimingRecord("slow", 123, "fast", 21), "slow", "fast", "123", "21"));
    logger.log(check(TestLevel.TEST_WARNING, TestLogUtils.getStageTimingRecord("slow", 7, "fast", 21), "slow", "fast", "7", "21"));

    // Explicit levels
    // long version
    logger.log(check(Level.FINER, TestLogUtils.getTimingRecord("slow", 123, "fast", 21, Level.FINER, Level.WARNING), "slow", "fast", "123", "21"));
    logger.log(check(Level.WARNING, TestLogUtils.getTimingRecord("slow", 7, "fast", 21, Level.FINER, Level.WARNING), "slow", "fast", "7", "21"));
    // double version
    logger.log(check(Level.FINER, TestLogUtils.getTimingRecord("slow", 123., "fast", 21., Level.FINER, Level.WARNING), "slow", "fast", "123.", "21."));
    logger.log(check(Level.WARNING, TestLogUtils.getTimingRecord("slow", 7., "fast", 21., Level.FINER, Level.WARNING), "slow", "fast", "7.", "21."));

    // Using the TimingResult
    final TimingResult fast = new TimingResult("Fast", new long[] { 100 });
    final TimingResult slow = new TimingResult("Slow", new long[] { 1000 });
    final TimingResult slowFast = new TimingResult("SlowFast", new long[] { 100, 10000 });

    logger.log(check(TestLevel.TEST_INFO, TestLogUtils.getTimingRecord(slow, fast)));
    logger.log(check(TestLevel.TEST_INFO, TestLogUtils.getTimingRecord(slow, slowFast, true)));
    logger.log(check(TestLevel.TEST_FAILURE, TestLogUtils.getTimingRecord(slow, slowFast, false)));
    logger.log(check(TestLevel.TEST_INFO, TestLogUtils.getStageTimingRecord(slow, fast)));
    logger.log(check(TestLevel.TEST_INFO, TestLogUtils.getStageTimingRecord(slow, slowFast, true)));
    logger.log(check(TestLevel.TEST_WARNING, TestLogUtils.getStageTimingRecord(slow, slowFast, false)));
    //@formatter:on
  }

  private static class MessageSupplier implements Supplier<String> {
    int count = 0;

    @Override
    public String get() {
      count++;
      return "Lazy message";
    }
  }

  @Test
  void canGetResultRecord() {
    //@formatter:off
    logger.log(TestLogUtils.getResultRecord(true, "This is a test passed record"));
    logger.log(TestLogUtils.getResultRecord(true, "This is a test passed formatted record: %d", 1));
    logger.log(TestLogUtils.getStageResultRecord(true, "This is a test stage passed record"));
    logger.log(TestLogUtils.getStageResultRecord(true, "This is a test stage passed formatted record: %d", 1));
    logger.log(TestLogUtils.getResultRecord(false, "This is a test failure record"));
    logger.log(TestLogUtils.getResultRecord(false, "This is a test failure formatted record: %d", 1));
    logger.log(TestLogUtils.getStageResultRecord(false, "This is a test stage failure record"));
    logger.log(TestLogUtils.getStageResultRecord(false, "This is a test stage failure formatted record: %d", 1));

    final MessageSupplier message = new MessageSupplier();
    Assertions.assertEquals(TestLevel.TEST_FAILURE.intValue(), TestLogUtils.getResultRecord(false, "").getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_FAILURE.intValue(), TestLogUtils.getResultRecord(false, "%d", 1).getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_FAILURE.intValue(), TestLogUtils.getResultRecord(false, message).getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_INFO.intValue(), TestLogUtils.getResultRecord(true, "").getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_INFO.intValue(), TestLogUtils.getResultRecord(true, "%d", 1).getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_INFO.intValue(), TestLogUtils.getResultRecord(true, message).getLevel().intValue());

    Assertions.assertEquals(TestLevel.TEST_WARNING.intValue(), TestLogUtils.getStageResultRecord(false, "").getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_WARNING.intValue(), TestLogUtils.getStageResultRecord(false, "%d", 1).getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_WARNING.intValue(), TestLogUtils.getStageResultRecord(false, message).getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_INFO.intValue(), TestLogUtils.getStageResultRecord(true, "").getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_INFO.intValue(), TestLogUtils.getStageResultRecord(true, "%d", 1).getLevel().intValue());
    Assertions.assertEquals(TestLevel.TEST_INFO.intValue(), TestLogUtils.getStageResultRecord(true, message).getLevel().intValue());
    //@formatter:on
  }

  @Test
  void canLazyLoadRecordMessage() {
    MessageSupplier message = new MessageSupplier();

    if (logger.isLoggable(TestLevel.TEST_FAILURE)) {
      message = new MessageSupplier();
      logger.log(TestLogUtils.getResultRecord(false, message));
      Assertions.assertEquals(1, message.count);
    }

    if (!logger.isLoggable(TestLevel.TEST_INFO)) {
      message = new MessageSupplier();
      logger.log(TestLogUtils.getResultRecord(true, message));
      // Should not try to create the message
      Assertions.assertEquals(0, message.count);
    }
  }

  @Test
  void canParseTestLevel() {
    Assertions.assertEquals(TestLevel.TEST_FAILURE, Level.parse("TEST FAILURE"));
    Assertions.assertEquals(TestLevel.TEST_WARNING, Level.parse("TEST WARNING"));
    Assertions.assertEquals(TestLevel.TEST_INFO, Level.parse("TEST INFO"));
    Assertions.assertEquals(TestLevel.TEST_DEBUG, Level.parse("TEST DEBUG"));
  }
}
