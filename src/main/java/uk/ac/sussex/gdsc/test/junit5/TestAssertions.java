/*-
 * #%L
 * Genome Damage and Stability Centre Test Package
 *
 * The GDSC Test package contains code for use with the JUnit test framework.
 * %%
 * Copyright (C) 2018 Alex Herbert
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
package uk.ac.sussex.gdsc.test.junit5;

import java.util.function.Supplier;
import org.opentest4j.AssertionFailedError;

/**
 * Adds additional helper assert functions to those provided by {@link org.junit.jupiter.api.Assertions}.
 * <p>
 * Assert functions are provided to compare float values using relative error, e.g.
 *
 * <pre>
 * &#64;Test
 * public void myTest()
 * {
 * 	TestAssertions.assertEqualsRelative(999.9, 1000.0, 1e-2); // passes
 * }
 * </pre>
 *
 * @see org.opentest4j.AssertionFailedError
 */
public class TestAssertions
{
	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} are equal within the given {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and
	 * {@link Double#compare(double, double)}.
	 * </p>
	 *
	 * @param expected
	 *            The expected value.
	 * @param actual
	 *            The value to check against <code>expected</code>.
	 * @param relativeError
	 *            the maximum relative error between <code>expected</code> and
	 *            <code>actual</code> for which both numbers are still
	 *            considered equal.
	 */
	public static void assertEqualsRelative(double expected, double actual, double relativeError)
	{
		ExtraAssertionUtils.assertEqualsRelative(expected, actual, relativeError, (String) null);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} are equal within the given {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and
	 * {@link Double#compare(double, double)}.
	 * </p>
	 * <p>
	 * Fails with the supplied failure {@code message}.
	 * <p>
	 *
	 * @param expected
	 *            The expected value.
	 * @param actual
	 *            The value to check against <code>expected</code>.
	 * @param relativeError
	 *            the maximum relative error between <code>expected</code> and
	 *            <code>actual</code> for which both numbers are still
	 *            considered equal.
	 * @param message
	 *            The message.
	 */
	public static void assertEqualsRelative(double expected, double actual, double relativeError, String message)
	{
		ExtraAssertionUtils.assertEqualsRelative(expected, actual, relativeError, message);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} are equal within the given {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and
	 * {@link Double#compare(double, double)}.
	 * </p>
	 * <p>
	 * If necessary, the failure message will be retrieved lazily from the supplied {@code messageSupplier}.
	 * </p>
	 *
	 * @param expected
	 *            The expected value.
	 * @param actual
	 *            The value to check against <code>expected</code>.
	 * @param relativeError
	 *            the maximum relative error between <code>expected</code> and
	 *            <code>actual</code> for which both numbers are still
	 *            considered equal.
	 * @param messageSupplier
	 *            The message supplier.
	 */
	public static void assertEqualsRelative(double expected, double actual, double relativeError,
			Supplier<String> messageSupplier)
	{
		ExtraAssertionUtils.assertEqualsRelative(expected, actual, relativeError, messageSupplier);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} are equal within the given {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and
	 * {@link Double#compare(double, double)}.
	 * </p>
	 *
	 * @param expected
	 *            The expected value.
	 * @param actual
	 *            The value to check against <code>expected</code>.
	 * @param relativeError
	 *            the maximum relative error between <code>expected</code> and
	 *            <code>actual</code> for which both numbers are still
	 *            considered equal.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The arguments.
	 */
	public static void assertEqualsRelative(double expected, double actual, double relativeError, String format,
			Object... args)
	{
		ExtraAssertionUtils.assertEqualsRelative(expected, actual, relativeError, () -> {
			return String.format(format, args);
		});
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} are equal within the given {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 *
	 * @param expected
	 *            The expected value.
	 * @param actual
	 *            The value to check against <code>expected</code>.
	 * @param relativeError
	 *            the maximum relative error between <code>expected</code> and
	 *            <code>actual</code> for which both numbers are still
	 *            considered equal.
	 */
	public static void assertEqualsRelative(float expected, float actual, double relativeError)
	{
		ExtraAssertionUtils.assertEqualsRelative(expected, actual, relativeError, (String) null);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} are equal within the given {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * Fails with the supplied failure {@code message}.
	 * <p>
	 *
	 * @param expected
	 *            The expected value.
	 * @param actual
	 *            The value to check against <code>expected</code>.
	 * @param relativeError
	 *            the maximum relative error between <code>expected</code> and
	 *            <code>actual</code> for which both numbers are still
	 *            considered equal.
	 * @param message
	 *            The message.
	 */
	public static void assertEqualsRelative(float expected, float actual, double relativeError, String message)
	{
		ExtraAssertionUtils.assertEqualsRelative(expected, actual, relativeError, message);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} are equal within the given {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * If necessary, the failure message will be retrieved lazily from the supplied {@code messageSupplier}.
	 * </p>
	 *
	 * @param expected
	 *            The expected value.
	 * @param actual
	 *            The value to check against <code>expected</code>.
	 * @param relativeError
	 *            the maximum relative error between <code>expected</code> and
	 *            <code>actual</code> for which both numbers are still
	 *            considered equal.
	 * @param messageSupplier
	 *            The message supplier.
	 */
	public static void assertEqualsRelative(float expected, float actual, double relativeError,
			Supplier<String> messageSupplier)
	{
		ExtraAssertionUtils.assertEqualsRelative(expected, actual, relativeError, messageSupplier);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} are equal within the given {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * If necessary, the failure message will be retrieved lazily from the supplied {@code format} and {@code args}.
	 * </p>
	 *
	 * @param expected
	 *            The expected value.
	 * @param actual
	 *            The value to check against <code>expected</code>.
	 * @param relativeError
	 *            the maximum relative error between <code>expected</code> and
	 *            <code>actual</code> for which both numbers are still
	 *            considered equal.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The arguments.
	 */
	public static void assertEqualsRelative(float expected, float actual, double relativeError, String format,
			Object... args)
	{
		ExtraAssertionUtils.assertEqualsRelative(expected, actual, relativeError, () -> {
			return String.format(format, args);
		});
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} double arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and
	 * {@link Double#compare(double, double)}.
	 * </p>
	 *
	 * @param expected
	 *            The double array with expected values.
	 * @param actual
	 *            The double array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 */
	public static void assertArrayEqualsRelative(double[] expected, double[] actual, double relativeError)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError, null, null);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} double arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and
	 * {@link Double#compare(double, double)}.
	 * </p>
	 *
	 * @param expected
	 *            The double array with expected values.
	 * @param actual
	 *            The double array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 * @param message
	 *            the identifying message for the {@link AssertionFailedError} (<code>null</code>
	 *            okay)
	 */
	public static void assertArrayEqualsRelative(double[] expected, double[] actual, double relativeError,
			String message)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError, null, message);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} double arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and
	 * {@link Double#compare(double, double)}.
	 * </p>
	 * <p>
	 * If necessary, the failure message will be retrieved lazily from the supplied {@code messageSupplier}.
	 * </p>
	 *
	 * @param expected
	 *            The double array with expected values.
	 * @param actual
	 *            The double array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 * @param messageSupplier
	 *            The message supplier.
	 */
	public static void assertArrayEqualsRelative(double[] expected, double[] actual, double relativeError,
			Supplier<String> messageSupplier)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError, null, messageSupplier);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} double arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and
	 * {@link Double#compare(double, double)}.
	 * </p>
	 * <p>
	 * If necessary, the failure message will be retrieved lazily from the supplied {@code format} and {@code args}.
	 * </p>
	 *
	 * @param expected
	 *            The double array with expected values.
	 * @param actual
	 *            The double array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The arguments.
	 */
	public static void assertArrayEqualsRelative(double[] expected, double[] actual, double relativeError,
			String format, Object... args)
	{
		assertArrayEqualsRelative(expected, actual, relativeError, () -> {
			return String.format(format, args);
		});
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} float arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 *
	 * @param expected
	 *            The float array with expected values.
	 * @param actual
	 *            The float array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 */
	public static void assertArrayEqualsRelative(float[] expected, float[] actual, double relativeError)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError, null, null);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} float arrays are equal within the given
	 * {@code relativeError}.
	 * If they are not, an {@link AssertionFailedError} is thrown with the supplied failure {@code message}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * Fails with the supplied failure {@code message}.
	 * <p>
	 *
	 * @param expected
	 *            The float array with expected values.
	 * @param actual
	 *            The float array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 * @param message
	 *            the identifying message for the {@link AssertionFailedError} (<code>null</code>
	 *            okay)
	 */
	public static void assertArrayEqualsRelative(float[] expected, float[] actual, double relativeError, String message)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError, null, message);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} float arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * If necessary, the failure message will be retrieved lazily from the supplied {@code messageSupplier}.
	 * </p>
	 *
	 * @param expected
	 *            The float array with expected values.
	 * @param actual
	 *            The float array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 * @param messageSupplier
	 *            The message supplier.
	 */
	public static void assertArrayEqualsRelative(float[] expected, float[] actual, double relativeError,
			Supplier<String> messageSupplier)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError, null, messageSupplier);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} float arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * If necessary, the failure message will be retrieved lazily from the supplied {@code messageSupplier}.
	 * </p>
	 *
	 * @param expected
	 *            The float array with expected values.
	 * @param actual
	 *            The float array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The arguments.
	 */
	public static void assertArrayEqualsRelative(float[] expected, float[] actual, double relativeError, String format,
			Object... args)
	{
		assertArrayEqualsRelative(expected, actual, relativeError, () -> {
			return String.format(format, args);
		});
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} float/double arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and,
	 * {@link Double#compare(double, double)} or {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * This supports nested arrays, e.g. {@code double[][]}.
	 * </p>
	 *
	 * @param expected
	 *            The float/double array with expected values.
	 * @param actual
	 *            The float/double array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 */
	public static void assertArrayEqualsRelative(Object[] expected, Object[] actual, double relativeError)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} float/double arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and,
	 * {@link Double#compare(double, double)} or {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * Fails with the supplied failure {@code message}.
	 * </p>
	 * <p>
	 * This supports nested arrays, e.g. {@code double[][]}.
	 * </p>
	 *
	 * @param expected
	 *            The float/double array with expected values.
	 * @param actual
	 *            The float/double array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 * @param message
	 *            The message.
	 */
	public static void assertArrayEqualsRelative(Object[] expected, Object[] actual, double relativeError,
			String message)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError, message);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} float/double arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and,
	 * {@link Double#compare(double, double)} or {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * If necessary, the failure message will be retrieved lazily from the supplied {@code messageSupplier}.
	 * </p>
	 * <p>
	 * This supports nested arrays, e.g. {@code double[][]}.
	 * </p>
	 *
	 * @param expected
	 *            The float/double array with expected values.
	 * @param actual
	 *            The float/double array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 * @param messageSupplier
	 *            The message supplier.
	 */
	public static void assertArrayEqualsRelative(Object[] expected, Object[] actual, double relativeError,
			Supplier<String> messageSupplier)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError, messageSupplier);
	}

	/**
	 * <em>Asserts</em> that {@code expected} and {@code actual} float/double arrays are equal within the given
	 * {@code relativeError}.
	 * <p>
	 * Equality imposed by this method is consistent with {@link Double#equals(Object)} and,
	 * {@link Double#compare(double, double)} or {@link Float#equals(Object)} and
	 * {@link Float#compare(float, float)}.
	 * </p>
	 * <p>
	 * If necessary, the failure message will be retrieved lazily from the supplied {@code messageSupplier}.
	 * </p>
	 * <p>
	 * This supports nested arrays, e.g. {@code double[][]}.
	 * </p>
	 *
	 * @param expected
	 *            The float/double array with expected values.
	 * @param actual
	 *            The float/double array with actual values.
	 * @param relativeError
	 *            The maximum relative error between <code>expected[i]</code> and
	 *            <code>actual[i]</code> for which both numbers are still
	 *            considered equal.
	 * @param format
	 *            The message format.
	 * @param args
	 *            The arguments.
	 */
	public static void assertArrayEqualsRelative(Object[] expected, Object[] actual, double relativeError, String format,
			Object... args)
	{
		ExtraAssertArrayEquals.assertArrayEqualsRelative(expected, actual, relativeError, () -> {
			return String.format(format, args);
		});
	}

	////////////////////////////////////////////////////////////////////////////
	// Formatted methods.
	// These wrap the JUnit assert methods with an
	// assertion error containing a formatted message.
	// No checks are made that the format or arguments are not null.
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Wraps an assertion error with a new error that has a formatted message appended to
	 * the input error's message.
	 *
	 * @param error
	 *            The error.
	 * @param format
	 *            The format
	 * @param args
	 *            The arguments.
	 * @throws AssertionError
	 *             The wrapped assertion error.
	 */
	public static void wrapAssertionFailedErrorAppend(AssertionError error, String format, Object... args)
			throws AssertionError
	{
		final String msg = error.getMessage();
		if (msg == null || msg.length() == 0)
			throw new AssertionError(String.format(format, args), error);
		throw new AssertionError(msg + " " + String.format(format, args), error);
	}

	/**
	 * Wraps an assertion error with a new error that has a formatted message appended to
	 * the input error's message.
	 *
	 * @param error
	 *            The error.
	 * @param messageSupplier
	 *            The message supplier.
	 * @throws AssertionError
	 *             The wrapped assertion error.
	 */
	public static void wrapAssertionFailedErrorAppend(AssertionError error, Supplier<String> messageSupplier)
			throws AssertionError
	{
		final String msg = error.getMessage();
		if (msg == null || msg.length() == 0)
			throw new AssertionError(messageSupplier.get(), error);
		throw new AssertionError(msg + " " + messageSupplier.get(), error);
	}

	/**
	 * Wraps an assertion error with a new error that has a formatted message prepended to
	 * the input error's message.
	 *
	 * @param error
	 *            The error.
	 * @param format
	 *            The format
	 * @param args
	 *            The arguments.
	 * @throws AssertionError
	 *             The wrapped assertion error.
	 */
	public static void wrapAssertionFailedError(AssertionError error, String format, Object... args)
			throws AssertionError
	{
		final String msg = error.getMessage();
		if (msg == null || msg.length() == 0)
			throw new AssertionError(String.format(format, args), error);
		throw new AssertionError(String.format(format, args) + " " + msg, error);
	}

	/**
	 * Wraps an assertion error with a new error that has a formatted message prepended to
	 * the input error's message.
	 *
	 * @param error
	 *            The error.
	 * @param messageSupplier
	 *            The message supplier.
	 * @throws AssertionError
	 *             The wrapped assertion error.
	 */
	public static void wrapAssertionFailedError(AssertionError error, Supplier<String> messageSupplier)
			throws AssertionError
	{
		final String msg = error.getMessage();
		if (msg == null || msg.length() == 0)
			throw new AssertionError(messageSupplier.get(), error);
		throw new AssertionError(messageSupplier.get() + " " + msg, error);
	}
}
