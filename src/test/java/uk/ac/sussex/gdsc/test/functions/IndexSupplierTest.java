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
package uk.ac.sussex.gdsc.test.functions;

import org.apache.commons.rng.UniformRandomProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import uk.ac.sussex.gdsc.test.TestSettings;
import uk.ac.sussex.gdsc.test.junit5.RandomSeed;
import uk.ac.sussex.gdsc.test.junit5.SeededTest;

@SuppressWarnings("javadoc")
public class IndexSupplierTest {
    private final String messagePrefix = "prefix ";
    private final String messageSuffix = " suffix";

    @Test
    public void testConstructer() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            IndexSupplier s = new IndexSupplier(0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            IndexSupplier s = new IndexSupplier(-1);
        });
        for (int dim = 1; dim <= 3; dim++) {
            IndexSupplier s = new IndexSupplier(dim);
            Assertions.assertNotNull(s);
            Assertions.assertEquals(dim, s.getDimensions());

            s = new IndexSupplier(dim, messagePrefix, messageSuffix);
            Assertions.assertNotNull(s);
            Assertions.assertEquals(dim, s.getDimensions());
            Assertions.assertEquals(messagePrefix, s.getMessagePrefix());
            Assertions.assertEquals(messageSuffix, s.getMessageSuffix());
        }
    }

    @Test
    public void testSetters() {
        IndexSupplier s = new IndexSupplier(1);
        Assertions.assertEquals("[", s.getPrefix());
        Assertions.assertEquals("]", s.getSuffix());
        Assertions.assertEquals("][", s.getDelimiter());
        Assertions.assertNull(s.getMessagePrefix());
        Assertions.assertNull(s.getMessageSuffix());

        s.setFormat("<", ">");
        Assertions.assertEquals("<", s.getPrefix());
        Assertions.assertEquals(">", s.getSuffix());
        Assertions.assertEquals("><", s.getDelimiter());

        s.setMessagePrefix(null);
        Assertions.assertNull(s.getMessagePrefix());
        s.setMessagePrefix("");
        Assertions.assertEquals("", s.getMessagePrefix());
        s.setMessagePrefix(messagePrefix);
        Assertions.assertEquals(messagePrefix, s.getMessagePrefix());

        s.setMessageSuffix(null);
        Assertions.assertNull(s.getMessageSuffix());
        s.setMessageSuffix("");
        Assertions.assertEquals("", s.getMessageSuffix());
        s.setMessageSuffix(messageSuffix);
        Assertions.assertEquals(messageSuffix, s.getMessageSuffix());
    }

    @SeededTest
    public void test1DMessage(RandomSeed seed) {
        IndexSupplier s = new IndexSupplier(1);
        Assertions.assertEquals("[0]", s.get());
        UniformRandomProvider rng = TestSettings.getRandomGenerator(seed.getSeed());
        for (int i = 0; i < 5; i++) {
            int next = rng.nextInt(10);
            s.set(0, next);
            Assertions.assertEquals(next, s.get(0));
            Assertions.assertEquals("[" + next + "]", s.get());
        }
        s.setFormat("<", ">");
        // Try with empty message pre/suffix
        s.setMessagePrefix("");
        s.setMessageSuffix("");
        for (int i = 0; i < 5; i++) {
            int next = rng.nextInt(10);
            s.set(0, next);
            Assertions.assertEquals("<" + next + ">", s.get());
        }
        s.setFormat("(", ")");
        // Try with empty message pre/suffix
        s.setMessagePrefix(messagePrefix);
        s.setMessageSuffix(messageSuffix);
        for (int i = 0; i < 5; i++) {
            int next = rng.nextInt(10);
            s.set(0, next);
            Assertions.assertEquals(messagePrefix + "(" + next + ")" + messageSuffix, s.get());
        }

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            s.set(1, 0);
        });
    }

    @SeededTest
    public void test2DMessage(RandomSeed seed) {
        IndexSupplier s = new IndexSupplier(2);
        Assertions.assertEquals("[0][0]", s.get());
        UniformRandomProvider rng = TestSettings.getRandomGenerator(seed.getSeed());
        for (int i = 0; i < 3; i++) {
            int nexti = rng.nextInt(10);
            s.set(0, nexti);
            Assertions.assertEquals(nexti, s.get(0));
            for (int j = 0; j < 3; j++) {
                int nextj = rng.nextInt(10);
                s.set(1, nextj);
                Assertions.assertEquals(nextj, s.get(1));
                Assertions.assertEquals("[" + nexti + "][" + nextj + "]", s.get());
            }
        }

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            s.set(2, 0);
        });
    }
}
