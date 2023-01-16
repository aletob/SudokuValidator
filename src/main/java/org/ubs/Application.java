package org.ubs;

import static org.ubs.ValidatorConstants.ERROR_MISSING_NAME;
import static org.ubs.ValidatorConstants.INFO_INVALID_SUDOKU_PREFIX;
import static org.ubs.ValidatorConstants.INFO_VALID_SUDOKU;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Program for validating a standard 9x9 Sudoku puzzle
 */
public class Application {
	
	public static void main(String[] args) throws InterruptedException {
		try {
			Map<SudokuElement, Integer> sudoku = readFile(args);
			String message = getValidationResult(sudoku);
			printResult(message);
		} catch (SudokuReaderException | IOException e) {
			printResult(INFO_INVALID_SUDOKU_PREFIX + e.getMessage());
		}
	}
	
	static Map<SudokuElement, Integer> readFile(String[] args) throws SudokuReaderException, IOException {
		String fileName = getFilename(args);
		return SudokuFileReader.readInput(fileName);
	}
	
	private static String getFilename(String[] args) throws SudokuReaderException {
		if (args.length == 0) {
			throw new SudokuReaderException(ERROR_MISSING_NAME);
		}
		return args[0];
	}
	
	private static String getValidationResult(Map<SudokuElement, Integer> sudoku) {
		SudokuValidator validator = new SudokuValidator(sudoku);
		return validator.isValid() ? INFO_VALID_SUDOKU : validator.getError();
	}
	
	private static void printResult(String message) throws InterruptedException {
		System.out.println(message);
		TimeUnit.SECONDS.sleep(5);
	}
	
}
