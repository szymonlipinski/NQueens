package com.simononsoftware.nqueens;

import java.util.Objects;

/**
 * One board field.
 * <p>
 * I use here the standard chess notation:
 * File is a column.
 * Rank is a row.
 */
public class Field implements Comparable<Field> {
    private final int file;
    private final int rank;

    public Field(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public int getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return rank == field.rank &&
                file == field.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, file);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", file, rank);
    }

    @Override
    public int compareTo(Field o) {
        if (this.file == o.file) return Integer.compare(this.rank, o.rank);
        else return Integer.compare(this.file, o.file);
    }
}
