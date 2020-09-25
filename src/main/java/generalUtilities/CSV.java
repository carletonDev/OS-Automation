package generalUtilities;

import static restAssuredUtilities.Reporting.logStep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSV {

	/**
	 * Gets csv data and store in list of string arrays
	 *
	 * @param fileName file name with csv data
	 * @return list of string arrays with data
	 */
	public static List<String[]> get(String fileName){
		List<String[]> data = new ArrayList<String[]>();
		String testRow;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			while ((testRow = br.readLine()) != null) {
				String[] line = testRow.split(",");
				data.add(line);
			}
		} catch (FileNotFoundException e) {
			logStep("ERROR: File not found " + fileName);
		} catch (IOException e) {
			logStep("ERROR: Could not read " + fileName);
		}

		return data;
	}
}