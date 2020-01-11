package com.simononsoftware.nqueens;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A simple board implementation, specialized to solve the NQueens problem.
 * <p>
 * The board is stored in a bitboard.
 * <p>
 * *
 * The fields are numbered like in the below example for a board of size 3 is:
 * <p>
 * <pre>
 *   2 | 6 7 8
 *   1 | 3 4 5
 *   0 | 0 1 2
 *   --+-------
 *     | 0 1 2
 * </pre>
 */

public class Board {

    /**
     * Size of the board.
     */
    private final int size;

    /**
     * The set field.
     */
    private Field field;

    /**
     * The bitboard with the one field set and, depending on the functions called, also all the controlled fields.
     */
    private final BitSet bitboard;

    Board(int size) {
        this.size = size;
        bitboard = new BitSet(size);
    }

    /**
     * Combines the bitboards from all the boards into one bitboard which has set all the fields the boards have.
     *
     * @param boards Boards to combine the bitboards to.
     * @return Bitboard with set fields from all the bitboards.
     */
    static BitSet combineBitboards(List<Board> boards) {
        BitSet result = new BitSet(boards.get(0).size);
        boards.stream().map(board -> board.bitboard).forEach(result::or);
        return result;
    }

    /**
     * Returns list of fields from all the boards.
     *
     * @param boards Boards to get the fields for.
     * @return List of fields for all the boards.
     */
    static List<Field> getSetFields(List<Board> boards) {
        return boards.stream().map(board -> board.field).collect(Collectors.toList());
    }

    /**
     * Converts the field to a bitboard index.
     *
     * @param field Field to get the index for.
     * @return Index of the bitboard for the given field.
     */
    public int fieldToBitboardIndex(Field field) {
        return field.getRank() * size + field.getFile();
    }

    /**
     * Converts the bitboard index to the field.
     *
     * @param index Bitboard index to convert.
     * @return Field
     */
    public Field bitboardIndexToField(int index) {
        return new Field(index % size, index / size);
    }

    /**
     * Set all the fields controlled by the given field.
     *
     * @param field Field to set.
     */
    void setControlledFields(Field field) {
        setRanks(field);
        setFiles(field);
        setDiagonals(field);
    }

    /**
     * Validates if the field with the given coordinates fits in the board.
     *
     * @param field Field to set.
     */
    private void validateField(Field field) {
        int index = fieldToBitboardIndex(field);
        if (field.getRank() >= size || field.getRank() < 0 ||
                field.getFile() >= size || field.getFile() < 0) {
            throw new BadFieldException(this, field);
        }
    }

    /**
     * Sets the field. There can be just one.
     *
     * @param field Field to set.
     */
    void setField(Field field) {
        if (this.field != null) {
            throw new BadFieldException("There is already set the field.");
        }
        validateField(field);

        this.field = field;
        bitboard.set(fieldToBitboardIndex(field));
    }

    void setFieldWithControlledFields(Field field) {
        bitboard.set(fieldToBitboardIndex(field));
        setField(field);
        setControlledFields(field);
    }

    /**
     * Sets a bit in the bitboard without changing the `field` value.
     *
     * @param field Field to set.
     */
    private void markFieldSet(Field field) {
        bitboard.set(fieldToBitboardIndex(field));
    }

    /**
     * Sets all bits for the same rank as the field.
     *
     * @param field Field to set the ranks for.
     */
    private void setRanks(Field field) {
        for (int i = 0; i < size; i++) {
            markFieldSet(new Field(i, field.getRank()));
        }
    }

    /**
     * Sets all bits for the same file as the field.
     *
     * @param field Field to set the files for.
     */
    private void setFiles(Field field) {
        for (int i = 0; i < size; i++) {
            markFieldSet(new Field(field.getFile(), i));
        }
    }

    /**
     * Sets the diagonal fields for the field.
     * <p>
     * The diagonal lines are going through the given field and the angle is ±45°,
     * which gives the coefficient a in the line formula y=ax+b equal to ±1.
     * So the coefficient b is:
     * [a = +1; b = rank - file]
     * [a = -1; b = rank + file]
     *
     * @param field The field to set the diagonal fields for the field.
     */
    private void setDiagonals(Field field) {

        Line lineIncreasing = new Line(+1, field.getRank() - field.getFile());
        Line lineDecreasing = new Line(-1, field.getRank() + field.getFile());

        for (int i = 0; i < size; i++) {

            Field[] fields = {
                    new Field(lineDecreasing.calculateValue(i), i),
                    new Field(i, lineIncreasing.calculateValue(i))
            };

            for (Field markField : fields) {
                try {
                    validateField(markField);
                    markFieldSet(markField);
                } catch (BadFieldException e) {
                    // here we just silently do nothing, it means the field is outside the board,
                    // so setting it will not work
                }
            }
        }
    }

    /**
     * Returns the size of the board.
     *
     * @return size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the bit for the field is set.
     *
     * @param field Field to check.
     * @return True if the field is set, false otherwise.
     */
    private boolean isSet(Field field) {
        validateField(field);
        return bitboard.get(fieldToBitboardIndex(field));
    }

    /**
     * Returns a unicode version of the board. Somethings like this: TODO
     */
    @Override
    public String toString() {

        String setField = "*";
        String emptyField = "·";

        int maxFieldNumberSize = Integer.toString(size).length();
        String filesNumberingFormat = String.format("%%-%ds | ", maxFieldNumberSize + 1);
        String ranksNumberingFormat = String.format("%%-%ds", maxFieldNumberSize + 1);

        StringBuilder output = new StringBuilder();

        for (int rank = size - 1; rank >= 0; rank--) {
            output.append(String.format(filesNumberingFormat, rank));
            for (int file = 0; file < size; file++) {
                String piece;
                if (isSet(new Field(file, rank))) {
                    piece = setField;
                } else {
                    piece = emptyField;
                }
                output.append(String.format(ranksNumberingFormat, piece));

            }
            output.append("\n");
        }
        output.append(String.format(filesNumberingFormat, ""));
        for (int rank = 0; rank < size; rank++) {
            output.append(String.format(ranksNumberingFormat, rank));
        }
        output.append("\n");

        return output.toString();
    }
}
