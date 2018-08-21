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
package uk.ac.sussex.gdsc.test.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Class to run timing tasks.
 */
public class TimingService {
    /**
     * The new line used in the report. Equal to
     * {@code System.getProperty("line.separator")}.
     */
    public static final String newLine;
    static {
        newLine = System.getProperty("line.separator");
    }

    private int runs = 0;

    private final ArrayList<TimingResult> results = new ArrayList<>();

    /**
     * @return The number of timing results
     */
    public int getSize() {
        return results.size();
    }

    /**
     * Clear the results.
     */
    public void clearResults() {
        results.clear();
    }

    /**
     * Get the timing result.
     * <p>
     * If the index is negative then count back from the end of the results list.
     *
     * @param index The index
     * @return the timing result
     */
    public TimingResult get(int index) {
        if (index < 0)
            return results.get(results.size() + index);
        return results.get(index);
    }

    /**
     * Instantiates a new timing service.
     */
    public TimingService() {
        this(5);
    }

    /**
     * Instantiates a new timing service.
     *
     * @param runs the number of timing runs
     */
    public TimingService(int runs) {
        setRuns(runs);
    }

    /**
     * Sets the runs.
     *
     * @param runs the number of timing runs
     */
    public void setRuns(int runs) {
        this.runs = runs;
    }

    /**
     * Gets the number of timing runs.
     *
     * @return the runs
     */
    public int getRuns() {
        return runs;
    }

    /**
     * Execute the timing task.
     *
     * @param task the task
     * @return the timing result
     */
    public TimingResult execute(TimingTask task) {
        return execute(task, false);
    }

    /**
     * Execute the timing task.
     *
     * @param task  the task
     * @param check Set to true to validate result with the check method
     * @return the timing result
     */
    public TimingResult execute(TimingTask task, boolean check) {
        final int size = task.getSize();
        final long[] times = new long[runs];

        // Store the result
        final Object[] result = new Object[size];
        for (int run = 0; run < runs; run++) {
            final Object[] data = new Object[size];
            for (int i = 0; i < size; i++) {
                data[i] = task.getData(i);
            }
            final long start = System.nanoTime();
            for (int i = 0; i < size; i++) {
                result[i] = task.run(data[i]);
            }
            times[run] = System.nanoTime() - start;
        }
        if (check) {
            for (int i = 0; i < size; i++) {
                task.check(i, result[i]);
            }
        }
        final TimingResult r = new TimingResult(task, times);
        results.add(r);
        return r;
    }

    /**
     * Report the timimg results to the output.
     *
     * @param out the output
     */
    public void report(PrintStream out) {
        report(out, results.toArray(new TimingResult[results.size()]));
    }

    /**
     * Report the last n timimg results to the output.
     *
     * @param out the output
     * @param n   the n
     */
    public void report(PrintStream out, int n) {
        if (n < 1 || results.isEmpty())
            return;
        final int to = results.size();
        final int from = to - n;
        if (from <= 0) {
            // Report all results
            report(out);
            return;
        }
        final TimingResult[] r = new TimingResult[to - from];
        for (int i = 0, j = from; j < to; i++, j++) {
            r[i] = results.get(j);
        }
        report(out, r);
    }

    /**
     * Get a report with the timing results.
     * <p>
     * A leading new line is prefixed to the report and no final trailing new line
     * is used.
     *
     * @return the report
     */
    public String getReport() {
        return getReport(Integer.MAX_VALUE, true);
    }

    /**
     * Get a report with the timing results.
     * <p>
     * A leading new line is optionally prefixed to the report and no final trailing
     * new line is used.
     *
     * @param leadingNewLine Set to true to add a leading new line
     * @return the report
     */
    public String getReport(boolean leadingNewLine) {
        return getReport(Integer.MAX_VALUE, leadingNewLine);
    }

    /**
     * Get a report with the last n timing results.
     * <p>
     * A leading new line is prefixed to the report and no final trailing new line
     * is used.
     *
     * @param n the n
     * @return the report
     */
    public String getReport(int n) {
        return getReport(n, true);
    }

    /**
     * Get a report with the last n timing results.
     * <p>
     * A leading new line is optionally prefixed to the report and no final trailing
     * new line is used.
     *
     * @param n              the n
     * @param leadingNewLine Set to true to add a leading new line
     * @return the report
     */
    public String getReport(int n, boolean leadingNewLine) {
        if (n < 1)
            return "";
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(baos, true)) {
            if (leadingNewLine)
                ps.println();
            report(ps, n);
            ps.close();
            return getReport(baos);
        }
    }

    private static String getReport(ByteArrayOutputStream baos) {
        final String text = new String(baos.toByteArray());
        // Remove the new line at the end
        final int i = text.lastIndexOf(newLine);
        // assert i == text.length() - newLine.length() : "New-line not at the end of
        // the string";
        return (i < 0) ? text : text.substring(0, i);
    }

    /**
     * Report the timimg results to the output.
     *
     * @param out     the output
     * @param results the results
     */
    public static void report(PrintStream out, TimingResult... results) {
        if (results == null || results.length == 0)
            return;

        final double[] avs = new double[results.length];
        final long[] mins = new long[results.length];

        int width = 0;
        for (int i = 0; i < results.length; i++) {
            final int l = results[i].getTask().getName().length();
            if (width < l) {
                width = l;
            }

            mins[i] = results[i].getMin();
            avs[i] = results[i].getMean();
        }
        final String format = String.format("%%-%ds : %%15d (%%8.3f)%%c: %%15f (%%8.3f)%%c" + newLine, width);

        // Find the fastest
        final long min = min(mins);
        final double av = min(avs);

        for (int i = 0; i < results.length; i++) {
            // Results relative to the first result
            // Mark the fastest
            final char mc = (mins[i] == min) ? '*' : ' ';
            final char ac = (avs[i] == av) ? '*' : ' ';
            out.printf(format, results[i].getTask().getName(), mins[i], (double) mins[i] / mins[0], mc, avs[i],
                    avs[i] / avs[0], ac);
        }
    }

    private static long min(long[] mins) {
        long min = mins[0];
        for (int i = 1; i < mins.length; i++)
            if (min > mins[i]) {
                min = mins[i];
            }
        return min;
    }

    private static double min(double[] mins) {
        double min = mins[0];
        for (int i = 1; i < mins.length; i++)
            if (min > mins[i]) {
                min = mins[i];
            }
        return min;
    }

    /**
     * Repeat all the tasks.
     *
     * @return the number repeated
     */
    public int repeat() {
        return repeat(results.size());
    }

    /**
     * Repeat all the tasks up to the given size.
     *
     * @param size the size
     * @return the number repeated
     */
    public int repeat(int size) {
        for (int i = 0; i < size; i++) {
            execute(results.get(i).getTask());
        }
        return size;
    }

    /**
     * Run each task and call the check method
     */
    public void check() {
        final int size = results.size();
        for (int i = 0; i < size; i++) {
            check(results.get(i).getTask());
        }
    }

    /**
     * Run the task and call the check method on the results
     *
     * @param task the task
     */
    public static void check(TimingTask task) {
        final int size = task.getSize();
        for (int i = 0; i < size; i++) {
            final Object result = task.run(task.getData(i));
            task.check(i, result);
        }
    }
}