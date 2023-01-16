package org.ubs;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.Test;
import static org.ubs.ValidatorConstants.ERROR_MISSING_NAME;

/**
 * Unit test for Sudoku validator {@link Application}
 */
public class ApplicationTest {
	
	@Test
	public void readFileTest() {
		String[] emptyArray = new String[0];
		
		assertThatThrownBy(() -> Application.readFile(emptyArray))
				.isInstanceOf(SudokuReaderException.class)
				.hasMessageContaining(ERROR_MISSING_NAME);
	}
	
}
