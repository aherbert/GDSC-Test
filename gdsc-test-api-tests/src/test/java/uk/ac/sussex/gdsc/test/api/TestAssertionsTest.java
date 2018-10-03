/*-
 * #%L
 * Genome Damage and Stability Centre Test Utilities
 *
 * Contains utilities for use with test frameworks.
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
package uk.ac.sussex.gdsc.test.api;

import uk.ac.sussex.gdsc.test.api.equality.IntIntEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public class TestAssertionsTest {
  @Test
  public void test() {
    Assertions.assertThrows(AssertionError.class, () -> {
      TestAssertions.assertTest(1, 2, new IntIntEquals());
    });
    Assertions.assertThrows(AssertionError.class, () -> {
      TestAssertions.assertArrayTest(new float[1][1], new double[1][1], new IntIntEquals());
    });
  }
}