package org.ubs;

final class ValidatorConstants {
	public static final int SUDOKU_SIZE = 9;
	public static final int SUBBOX_SIZE = 3;
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 9;
	
	public static final String INFO_INVALID_SUDOKU_PREFIX = "INVALID: ";
	public static final String INFO_VALID_SUDOKU = "O (VALID)";
	public static final String ERROR_MISSING_NAME = "Missing file name";
	public static final String ERROR_WRONG_SIZE = "Wrong sudoku size";
	public static final String ERROR_NON_NUMERIC_VALUE = "Invalid value - non numeric";
	public static final String ERROR_VALUE_OUT_OF_RANGE = "Invalid value - non numeric";
	
	public static final String INVALID_VALUE_IN_ROW = "Invalid value in row";
	public static final String INVALID_VALUE_IN_COLUMN = "Invalid value in column";
	public static final String INVALID_VALUE_IN_SUB_BOX = "Invalid value in sub-box";
	
	private ValidatorConstants() {
	}
}
