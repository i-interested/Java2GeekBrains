package Lesson2;

class MyArrayDataException extends Exception {
    private int row;
    private int column;

    public MyArrayDataException(int row, int column) {
        super("incorrect data column: " + column + ", row: " + row);
        this.column = column;
        this.row = row;
    }
}
