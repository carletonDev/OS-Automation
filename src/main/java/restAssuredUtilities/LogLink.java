package restAssuredUtilities;


import com.aventstack.extentreports.markuputils.Markup;

public class LogLink implements Markup{

	private String logFilepath;
	
	public LogLink(String logFilepath) {
		this.logFilepath = logFilepath;
	}
		
	public String getMarkup() {
		
		 final String htmlTag = "<a href='"+logFilepath+"' target='_new'style = 'color:blue;'><u>Logs</u></a>";
		return htmlTag;
	}
	
}
