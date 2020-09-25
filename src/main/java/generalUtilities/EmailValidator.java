package generalUtilities;

import static restAssuredUtilities.Reporting.logStep;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class EmailValidator {

	/**
	 * Validates if email account from parameters has received email with expected subject by checking the inbox
	 * every 3 seconds.
	 *
	 * @param strHost 			Host
	 * @param strStoreType 		Store Type
	 * @param strUser 			User
	 * @param strPassword 		Password
	 * @param strEmailSubject 	Email Subject
	 * @return Returns true if email received has the same subject. Otherwise, false.
	 */
	public static boolean ValidatingEmail(String strHost, String strStoreType, String strUser, String strPassword,
			String strEmailSubject)  {
		logStep("Validating if email account from parameters has received email with expected subject by checking the inbox\n"
				+ "every 3 seconds");
		boolean result = false;
		try {
			Properties properties = new Properties();

			properties.put("mail.pop3.host", strHost);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			Store store = emailSession.getStore("pop3s");
			store.connect(strHost, strUser, strPassword);

			Folder emailFolder = store.getFolder("INBOX");

			int count = 0;
			while (count <= 30 && !result) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				emailFolder.open(Folder.READ_ONLY);
				Message[] messages = emailFolder.getMessages();
				for (Message message : messages) {
					String strCurrentSubject = message.getSubject();
					if (strCurrentSubject.equals(strEmailSubject)) {
						result = true;
					}
				}
				count++;
				emailFolder.close();
			}
			store.close();

			// TODO: anything to add in catch blocks?
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
