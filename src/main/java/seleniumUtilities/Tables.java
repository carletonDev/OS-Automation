package seleniumUtilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Tables {

	/**
	 * Check if columns have data
	 *
	 * @param table	Table element
	 * @param pagination Pagination element
	 * @param columns Columns
	 * @return True if all columns have text. Otherwise, false
	 */
	public static boolean tableColumnsHaveText(WebElement table, WebElement pagination, String[] columns) {
		boolean boolResult = true;
		boolean boolLastColumn = false;
		try {
			int intRowIndex = 0;

			do {

				WebElement tbody = table.findElement(By.tagName("tbody"));
				List<WebElement> listTableRows = tbody.findElements(By.tagName("tr"));
				//int tableSize = tableRows.size();
				for (WebElement row : listTableRows) {
					intRowIndex++;
					List<WebElement> listTableData = row.findElements(By.tagName("td"));
					for (String index : columns) {
						int column = -1;
						column = Integer.parseInt(index);

						if(listTableData.size()<column) {
							break;
						}

						if (column >= 0) {
							WebElement cell = listTableData.get(column);
							String strValue = cell.getText();
							if (strValue.isEmpty()) {
								throw new Exception("Row " + intRowIndex + ", Cell " +(column+1)+ " does not contain data");
							}
						}
					}
				}
				try {
					pagination.click();
				}
				catch(Exception e) {
					boolLastColumn = true;
					//result = false;
				}
			} while (!boolLastColumn);

		} catch (NumberFormatException numberFormat) {
			boolResult = false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			boolResult = false;
		}
		return boolResult;
	}
}