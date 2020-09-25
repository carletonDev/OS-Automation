package generalUtilities;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManagement {
	/**
	 * Deletes files in the folder and the folder.
	 *
	 * @param folderPath folder path to be deleted
	 */
	public static void deleteFolder(String folderPath) {
		File fileFolder = new File(folderPath);
		if(fileFolder.isDirectory()) {
			File[] fileEntries = fileFolder.listFiles();
			if (fileEntries != null) {
				for(File file: fileEntries) {
					if(!file.isDirectory()) {
						file.delete();
					}

				}
			}
			fileFolder.delete();
		}
	}


	/**
	 * Gets absolute path of current directory
	 *
	 * @return absolute path of current directory
	 */
	public static String getCurrentDirectory() {
		Path root = Paths.get(".").normalize().toAbsolutePath();
		return root.toString();
	}


	/**
	 * Gets path of emailable report
	 *
	 * @return path of report if exists. Otherwise, null.
	 */
	static String getEmailableReport(String reportPath) {
		File report = new File(getCurrentDirectory()+reportPath);
		if(report.exists()) {
			return report.getPath();
		}
		else {
			return null;
		}
	}

	/**
	 * Gets path of image
	 *
	 * @param imgName name of image
	 * @return path of image if exists. Otherwise, null
	 */
	public static String getProjectImage(String imgName) {
		File image = new File(getCurrentDirectory()+ "\\src\\main\\resources\\Images\\"+imgName);
		if(image.exists()) {
			return image.getPath();
		}
		else {
			return StringUtils.EMPTY;
		}
	}
}