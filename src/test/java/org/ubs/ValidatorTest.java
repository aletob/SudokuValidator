package org.ubs;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import static org.ubs.ValidatorConstants.INVALID_VALUE_IN_COLUMN;
import static org.ubs.ValidatorConstants.INVALID_VALUE_IN_ROW;
import static org.ubs.ValidatorConstants.INVALID_VALUE_IN_SUB_BOX;
import static org.ubs.ValidatorConstants.SUDOKU_SIZE;

import java.util.HashMap;
import java.util.Map;

public class ValidatorTest {
	
	private Map<SudokuElement, Integer> sudoku;
	
	@Before
	public void prepareMock() {
		mockValidSudoku();
	}
	
	@Test
	public void shouldReturnValidForFullFilledSudoku() {
		SudokuValidator validator = new SudokuValidator(sudoku);
		
		boolean isValid = validator.isValid();
		
		SoftAssertions.assertSoftly(soft -> {
			soft.assertThat(isValid).isTrue();
			soft.assertThat(validator.getError()).isNull();
		});
	}
	
	@Test
	public void shouldReturnValidForPartlyFilledSudoku() {
		removeSomeSudokuValues();
		SudokuValidator validator = new SudokuValidator(sudoku);
		
		boolean isValid = validator.isValid();
		
		SoftAssertions.assertSoftly(soft -> {
			soft.assertThat(sudoku.size() < SUDOKU_SIZE * SUDOKU_SIZE).isTrue();
			soft.assertThat(isValid).isTrue();
			soft.assertThat(validator.getError()).isNull();
		});
	}
	
	@Test
	public void shouldReturnValidForEmptySudoku() {
		mockEmptySudoku();
		SudokuValidator validator = new SudokuValidator(sudoku);
		
		boolean isValid = validator.isValid();
		
		SoftAssertions.assertSoftly(soft -> {
			soft.assertThat(sudoku.size()).isZero();
			soft.assertThat(isValid).isTrue();
			soft.assertThat(validator.getError()).isNull();
		});
	}
	
	
	@Test
	public void shouldReturnInvalidIfValueInRowIsDuplicated() {
		mockSudokuWithRepeatedValueInRow();
		SudokuValidator validator = new SudokuValidator(sudoku);
		
		boolean result = validator.isValid();
		
		SoftAssertions.assertSoftly(soft -> {
			soft.assertThat(result).isFalse();
			soft.assertThat(validator.getError()).isEqualTo(INVALID_VALUE_IN_ROW);
		});
	}
	
	@Test
	public void shouldReturnInvalidIfValueInColumnIsDuplicated() {
		mockSudokuWithRepeatedValueInColumn();
		SudokuValidator validator = new SudokuValidator(sudoku);
		
		boolean result = validator.isValid();
		
		SoftAssertions.assertSoftly(soft -> {
			soft.assertThat(result).isFalse();
			soft.assertThat(validator.getError()).isEqualTo(INVALID_VALUE_IN_COLUMN);
		});
	}
	
	@Test
	public void shouldReturnInvalidIfValueInSmallCubeIsDuplicated() {
		mockSudokuWithRepeatedValueInSmallCube();
		SudokuValidator validator = new SudokuValidator(sudoku);
		
		boolean result = validator.isValid();
		
		SoftAssertions.assertSoftly(soft -> {
			soft.assertThat(result).isFalse();
			soft.assertThat(validator.getError()).isEqualTo(INVALID_VALUE_IN_SUB_BOX);
		});
	}
	
	private void mockValidSudoku() {
		sudoku = new HashMap<>();
		int[][] validExample = {
				{1, 2, 3, 4, 5, 6, 7, 8, 9},
				{4, 5, 6, 7, 8, 9, 1, 2, 3},
				{7, 8, 9, 1, 2, 3, 4, 5, 6},
				{2, 3, 4, 5, 6, 7, 8, 9, 1},
				{5, 6, 7, 8, 9, 1, 2, 3, 4},
				{8, 9, 1, 2, 3, 4, 5, 6, 7},
				{3, 4, 5, 6, 7, 8, 9, 1, 2},
				{6, 7, 8, 9, 1, 2, 3, 4, 5},
				{9, 1, 2, 3, 4, 5, 6, 7, 8}};
		
		for (int i = 0; i < ValidatorConstants.SUDOKU_SIZE; i++) {
			for (int j = 0; j < ValidatorConstants.SUDOKU_SIZE; j++) {
				int value = validExample[i][j];
				sudoku.put(new SudokuElement(i, j), value);
			}
		}
	}
	
	private void removeSomeSudokuValues() {
		sudoku.remove(new SudokuElement(1, 6));
		sudoku.remove(new SudokuElement(2, 2));
		sudoku.remove(new SudokuElement(4, 1));
		sudoku.remove(new SudokuElement(7, 0));
		sudoku.remove(new SudokuElement(8, 5));
	}
	
	private void mockEmptySudoku() {
		sudoku = new HashMap<>();
		
	}
	
	private void mockSudokuWithRepeatedValueInRow() {
		SudokuElement valueToBeReplaced = new SudokuElement(0, 2);
		SudokuElement valueToBeCopied = new SudokuElement(0, 1);
		sudoku.put(valueToBeReplaced, sudoku.get(valueToBeCopied));
	}
	
	private void mockSudokuWithRepeatedValueInColumn() {
		SudokuElement valueToBeReplaced = new SudokuElement(1, 0);
		SudokuElement valueToBeCopied = new SudokuElement(0, 0);
		sudoku.put(valueToBeReplaced, sudoku.get(valueToBeCopied));
		sudoku.remove(new SudokuElement(1, 6));
	}
	
	private void mockSudokuWithRepeatedValueInSmallCube() {
		sudoku = new HashMap<>();
		sudoku.put(new SudokuElement(0, 0), 3);
		sudoku.put(new SudokuElement(1, 1), 3);
	}
	
	
}
