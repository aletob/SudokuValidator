package org.ubs;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import static org.ubs.ValidatorConstants.ERROR_NON_NUMERIC_VALUE;
import static org.ubs.ValidatorConstants.ERROR_VALUE_OUT_OF_RANGE;
import static org.ubs.ValidatorConstants.ERROR_WRONG_SIZE;
import static org.ubs.ValidatorConstants.MAX_VALUE;
import static org.ubs.ValidatorConstants.MIN_VALUE;
import static org.ubs.ValidatorConstants.SUDOKU_SIZE;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/***
 * Class responsible for reading and parsing file with sudoku
 */
final class SudokuFileReader {
	
	private SudokuFileReader() {
	}
	
	static Map<SudokuElement, Integer> readInput(String fileName) throws SudokuReaderException, IOException {
		try (FileReader reader = new FileReader(fileName)) {
			CSVParser parser = CSVFormat.DEFAULT.parse(reader);
			List<CSVRecord> rows = parser.getRecords();
			return parseRows(rows);
		}
	}
	
	static Map<SudokuElement, Integer> parseRows(List<CSVRecord> rows) throws SudokuReaderException {
		Map<SudokuElement, Integer> elementsWithValues = new HashMap<>();
		checkSize(rows.size());
		for (int rowIndex = 0; rowIndex < SUDOKU_SIZE; rowIndex++) {
			checkSize(rows.get(rowIndex).size());
			for (int columnIndex = 0; columnIndex < SUDOKU_SIZE; columnIndex++) {
				SudokuElement item = new SudokuElement(rowIndex, columnIndex);
				getDigit(rows.get(rowIndex).get(columnIndex))
						.ifPresent(value -> elementsWithValues.put(item, value));
			}
		}
		return elementsWithValues;
	}
	
	private static void checkSize(int size) throws SudokuReaderException {
		if (size != SUDOKU_SIZE) {
			throw new SudokuReaderException(ERROR_WRONG_SIZE);
		}
	}
	
	private static Optional<Integer> getDigit(String cellValue) throws SudokuReaderException {
		return cellValue.isBlank() ? Optional.empty() : Optional.of(parseToDigit(cellValue.trim()));
	}
	
	private static Integer parseToDigit(String cellValue) throws SudokuReaderException {
		try {
			int intValue = Integer.parseInt(cellValue);
			if (intValue > MAX_VALUE || intValue < MIN_VALUE) {
				throw new SudokuReaderException(ERROR_VALUE_OUT_OF_RANGE);
			}
			return intValue;
		} catch (NumberFormatException e) {
			throw new SudokuReaderException(ERROR_NON_NUMERIC_VALUE);
		}
	}
	
}
