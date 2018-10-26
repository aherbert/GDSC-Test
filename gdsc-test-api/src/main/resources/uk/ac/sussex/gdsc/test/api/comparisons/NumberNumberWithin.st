<package>

import uk.ac.sussex.gdsc.test.api.function.<Number><Number>BiPredicate;

import java.io.Serializable;
<if(import1)>import <import1>;<\n><endif>import java.util.function.Supplier;

/**
 * Tests {@code <number>} equality using an absolute error tolerance.
 *
 * @see <Number><Number>BiPredicate#test(<number>, <number>)
 * @see EqualityUtils#areWithin(<number>, <number>, <errorType>)
 */
public class <Number><Number><Within>
    implements <Number><Number>BiPredicate, Supplier\<String>, Serializable {

  /** The serial version ID. */
  private static final long serialVersionUID = 1L;

  /** The absolute error. */
  private final <errorType> absoluteError;

  /**
   * Create a predicate to test two {@code <number>} values are equal within an absolute error.
   *
   * @param absoluteError The maximum absolute error between \<code>value1\</code>
   *                      and \<code>value2\</code> for which both numbers are still
   *                      considered equal.
   * @throws IllegalArgumentException If the absolute error is not positive or below the maximum
   *         measurable difference between <number> values
   */
  public <Number><Number><Within>(<errorType> absoluteError) {
    EqualityUtils.<number>sValidateAbsoluteError(absoluteError);
    this.absoluteError = absoluteError;
  }

  /**
   * {@inheritDoc}
   *
   * \<p>Tests that two <number>s are equal within an absolute error tolerance.
   *
   * @return true if equal within error
   */
  @Override
  public boolean test(<number> value1, <number> value2) {
    // Errors have been checked so use the package level method
    return EqualityUtils.<number>sTestWithin(value1, value2, absoluteError);
  }

  /**
   * Get a description of this predicate.
   *
   * @return The description
   */
  @Override
  public String get() {
    return EqualityUtils.<number>sGetDescriptionWithin(absoluteError);
  }
}