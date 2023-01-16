package org.ubs;

import mockit.Expectations;
import mockit.Mocked;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.Test;
import static org.ubs.ValidatorConstants.ERROR_NON_NUMERIC_VALUE;
import static org.ubs.ValidatorConstants.ERROR_VALUE_OUT_OF_RANGE;
import static org.ubs.ValidatorConstants.ERROR_WRONG_SIZE;
import static org.ubs.ValidatorConstants.SUDOKU_SIZE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SudokuFileReaderTest {
	
	@Mocked
	CSVParser parser;
	
	@Mocked
	CSVRecord record;
	
	@Test
	public void shouldThrowExceptionIfAmountOfRowsIsTooSmall() {
		List<CSVRecord> rows = List.of();
		
		assertThatThrownBy(() -> SudokuFileReader.parseRows(rows))
				.isInstanceOf(SudokuReaderException.class)
				.hasMessageContaining(ERROR_WRONG_SIZE);
	}
	
	@Test
	public void shouldThrowExceptionIfAmountOfColumnsIsTooSmall() {
		List<CSVRecord> rows = prepareMocks();
		
		new Expectations() {{
			record.size();
			result = 5;
		}};
		
		assertThatThrownBy(() -> SudokuFileReader.parseRows(rows))
				.isInstanceOf(SudokuReaderException.class)
				.hasMessageContaining(ERROR_WRONG_SIZE);
	}
	
	@Test
	public void shouldConvertRowToSudokuObject() throws SudokuReaderException {
		List<CSVRecord> rows = prepareMocks();
		
		new Expectations() {{
			record.size();
			result = SUDOKU_SIZE;
			
			record.get(anyInt);
			result = "1";
		}};
		
		Map<SudokuElement, Integer> sudoku = SudokuFileReader.parseRows(rows);
		Assertions.assertThat(sudoku.size()).isEqualTo(SUDOKU_SIZE * SUDOKU_SIZE);
	}
	
	
	@Test
	public void shouldAcceptEmptyAndBlankValues() throws SudokuReaderException {
		List<CSVRecord> rows = prepareMocks();
		
		new Expectations() {{
			record.size();
			result = SUDOKU_SIZE;
			
			record.get(anyInt);
			result = "";
		}};
		
		Map<SudokuElement, Integer> sudoku = SudokuFileReader.parseRows(rows);
		Assertions.assertThat(sudoku).isEmpty();
	}
	
	@Test
	public void shouldThrowExceptionIfNonNumericValueFound() {
		List<CSVRecord> rows = prepareMocks();
		
		new Expectations() {{
			record.size();
			result = SUDOKU_SIZE;
			
			record.get(anyInt);
			result = "k";
		}};
		
		assertThatThrownBy(() -> SudokuFileReader.parseRows(rows))
				.isInstanceOf(SudokuReaderException.class)
				.hasMessageContaining(ERROR_NON_NUMERIC_VALUE);
	}
	
	@Test
	public void shouldThrowExceptionIfIncorrectValueFound() {
		List<CSVRecord> rows = prepareMocks();
		
		new Expectations() {{
			record.size();
			result = SUDOKU_SIZE;
			
			record.get(anyInt);
			result = "99";
		}};
		
		assertThatThrownBy(() -> SudokuFileReader.parseRows(rows))
				.isInstanceOf(SudokuReaderException.class)
				.hasMessageContaining(ERROR_VALUE_OUT_OF_RANGE);
	}
	
	private List<CSVRecord> prepareMocks() {
		List<CSVRecord> rows = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			rows.add(record);
		}
		return rows;
	}
	
}
