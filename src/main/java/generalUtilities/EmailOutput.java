package generalUtilities;

import static restAssuredUtilities.Reporting.logStep;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPTransport;

public class EmailOutput {

	/**
	 * Send report via e-mail. Sets up server and manage subject, cc, body and attachment of the e-mail
	 */
	public static void sendEmailableReport(String from, String password, String to, String cc, String reportPath) {
		//	logStep("Sending Report via email to "+ to+ " from "+ from);
		// Recipient's email ID needs to be mentioned.
		//String to = "james.mccullough@onshoreoutsourcing.com";

		// Sender's email ID needs to be mentioned
		//we used mail account to send out reports, these are the required settings for gmail
		String host = "smtp.gmail.com";
		String port = "465";

		// Get system properties
		Properties properties = new Properties();

		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");
		// Get the default Session object.
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};

		Session session = Session.getInstance(properties, auth);
				//Session.getDefaultInstance(properties, auth);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			// Set To: header field of the header.
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));

			// Set Subject: header field
			message.setSubject("Java Selenium Automation Test Execution Report" + " " + generalUtilities.DateUtilities.getCurrentDate());

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText("To view the report, open the attached html document.");

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = generalUtilities.FileManagement.getEmailableReport(reportPath);
			DataSource source = null;

			if (filename != null) {
				source = new FileDataSource(filename);
			}
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			SMTPTransport transport = (SMTPTransport)session.getTransport("smtp");
			//transport.connect(host, Integer.parseInt(port), from, password);
			transport.connect(from, password);
			//transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			logStep("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
