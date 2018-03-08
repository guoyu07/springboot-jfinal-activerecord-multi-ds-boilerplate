package com.gaols.study.studyboot.utils;

/**
 * @author gaols
 */
public class SqlKit {
    private StringBuilder builder;
    private static final String SEP = " ";

    public SqlKit() {
        builder = new StringBuilder();
    }

    public SqlKit(int initialSize) {
        builder = new StringBuilder(initialSize);
    }

    /**
     * Append a sql line to ensure it has a trailing whitespace.
     *
     * @param line The sql line to be appended
     * @return The sql line with a trailing whitespace
     */
    public SqlKit append(String line) {
        builder.append(line);
        builder.append(SEP);
        return this;
    }

    public SqlKit append(int line) {
        builder.append(line);
        builder.append(SEP);
        return this;
    }

    /**
     * Append a sql line without a trailing space, normally, this is
     * the last sql line you'd like to be appended.
     *
     * @param line The last sql line to be appended without trailing a whitespace
     * @return The builder itself.
     */
    public SqlKit last(String line) {
        builder.append(line);
        return this;
    }

    /**
     * Concatenate all appended sql line to build the last sql.
     *
     * @return Concatenated sql
     */
    public String build() {
        return builder.toString();
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
