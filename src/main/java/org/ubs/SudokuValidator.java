package org.ubs;

import static org.ubs.ValidatorConstants.INVALID_VALUE_IN_COLUMN;
import static org.ubs.ValidatorConstants.INVALID_VALUE_IN_ROW;
import static org.ubs.ValidatorConstants.INVALID_VALUE_IN_SUB_BOX;
import static org.ubs.ValidatorConstants.SUBBOX_SIZE;
import static org.ubs.ValidatorConstants.SUDOKU_SIZE;

import java.util.List;
import java.util.Map;

final class SudokuValidator {
	
	private String error;
	private final Map<SudokuElement, Integer> sudoku;
	
	SudokuValidator(Map<SudokuElement, Integer> sudoku) {
		this.sudoku = sudoku;
	}
	
	boolean isValid() {
		return hasValidRows() && hasValidColumns() && hasValidSubBoxes();
	}
	
	String getError() {
		return error;
	}
	
	private boolean hasValidRows() {
		for (int rowIndex = 0; rowIndex < SUDOKU_SIZE; rowIndex++) {
			List<Integer> valuesInRow = getValuesFromGivenRow(rowIndex);
			if (foundRepeatedValues(valuesInRow)) {
				error = INVALID_VALUE_IN_ROW;
				return false;
			}
		}
		return true;
	}
	
	private List<Integer> getValuesFromGivenRow(int rowIndex) {
		return sudoku.entrySet().stream()
				.filter(it -> it.getKey().x().equals(rowIndex))
				.map(Map.Entry::getValue)
				.toList();
	}
	
	private boolean foundRepeatedValues(List<Integer> strings) {
		return strings.size() != strings.stream().distinct().count();
	}
	
	private boolean hasValidColumns() {
		for (int columnIndex = 0; columnIndex < SUDOKU_SIZE; columnIndex++) {
			List<Integer> valuesInColumn = getValuesFromGivenColumn(columnIndex);
			
			if (foundRepeatedValues(valuesInColumn)) {
				error = INVALID_VALUE_IN_COLUMN;
				return false;
			}
		}
		return true;
	}
	
	private List<Integer> getValuesFromGivenColumn(int columnIndex) {
		return sudoku.entrySet().stream()
				.filter(it -> it.getKey().y().equals(columnIndex))
				.map(Map.Entry::getValue)
				.toList();
	}
	
	private boolean hasValidSubBoxes() {
		for (int row = 0; row < SUBBOX_SIZE; row++) {
			for (int column = 0; column < SUBBOX_SIZE; column++) {
				List<Integer> sudokuValues = getValuesFromGivenSubBox(row, column);
				if (foundRepeatedValues(sudokuValues)) {
					error = INVALID_VALUE_IN_SUB_BOX;
					return false;
				}
			}
			
		}
		return true;
	}
	
	private List<Integer> getValuesFromGivenSubBox(int maxRowIndex, int maxColumnIndex) {
		return sudoku.entrySet().stream()
				.filter(it -> it.getKey().y() / SUBBOX_SIZE == maxRowIndex)
				.filter(it -> it.getKey().x() / SUBBOX_SIZE == maxColumnIndex)
				.map(Map.Entry::getValue)
				.toList();
		
	}
}
