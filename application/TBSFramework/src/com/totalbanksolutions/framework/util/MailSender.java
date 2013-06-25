package com.totalbanksolutions.framework.util;

import java.io.*;
import java.util.Properties;
import java.util.Date;

//import javax.mail.Message.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
import javax.mail.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.mail.smtp.SMTPTransport;

/**
 * Demo app that shows how to construct and send a single part html
 * message.  Note that the same basic technique can be used to send
 * data of any type.
 */

/*
 ******************** SMS to Email addresses ***********************
T-Mobile: 		phonenumber@tmomail.net 
Virgin Mobile: 	phonenumber@vmobl.com 
Cingular: 		phonenumber@cingularme.com 
Sprint: 		phonenumber@messaging.sprintpcs.com
Verizon: 		phonenumber@vtext.com
Nextel: 		phonenumber@messaging.nextel.com

 (phonenumber is 10 digit)
 */


public class MailSender 
{

    protected final Log log = LogFactory.getLog(getClass());

	public static void main(String[] argv) 
	{
		MailSender mailsender = new MailSender();
		String to = "vcatrini@totalbanksolutions.com"; //, vcatrini@gmail.com"; //, Systems@totalbanksolutions.com";
		String subject = "Java API email testing";
		String refNumber = "10001";
		String bankCode = "ABCD";
		mailsender.SendMail(to, subject, refNumber, bankCode);
	}

	public void SendMail(String to, String subject, String heading, String messageText)
	{
		String from = "DMS.Notifications@totalbanksolutions.com";
		String cc = null;
		String bcc = null;
		//String url = null;
		String mailer = "MailUtility";
		//String record = null;	// name of folder in which to record mail
		boolean debug = false;

		
		//Gmail
		String protocol = "smtp"; 
		String port = "25";
		String user = "DMS.Notifications";
		String password = "Password1";
		String mailhost = "10.240.2.13"; //internal TBS server
		
		try 
		{
			log.debug("To: " + to);
			log.debug("Subject: " + subject);

			Properties props = System.getProperties();
			// XXX - could use Session.getTransport() and Transport.connect()
			// XXX - assume we're using SMTP
			//if (mailhost != null)
			//{
				//props.put("mail.smtp.host", mailhost);
			//}
			//props.put("mail.user", user);
			//props.put("mail.password", password);
			props.put("mail.host", mailhost);
			props.put("mail.smtp.host", mailhost);
			props.put("mail.smtp.user", user);
			if(port != null) props.put("mail.smtp.port", port);
			props.put("mail.debug", true);
			
			Session session = Session.getInstance(props);
			if (debug)
			{
				session.setDebug(true);
			}
			
			SMTPTransport t = (SMTPTransport)session.getTransport(protocol);
			t.connect(mailhost, user, password);

			Message msg = new MimeMessage(session);
			if (from != null)
			{
				msg.setFrom(new InternetAddress(from));
			}
			else
			{
				msg.setFrom();
			}

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
			
			if (cc != null)
			{
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
			}

			if (bcc != null)
			{
				msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false));
			}

			msg.setSubject(subject);
			
			collect(msg, heading, messageText);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new Date());

			String msgText = heading + messageText;
			msg.setText(msgText);

			// simple send
			//Transport.send(msg);

			try 
			{
				t.sendMessage(msg, msg.getAllRecipients());
			} 
			finally 
			{
				log.debug("Response: " + t.getLastServerResponse());
				t.close();
			}
			
			log.debug("\nMail was sent successfully.");
			System.out.println("Mail was sent successfully.");

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void collect(Message msg, String refNumber, String bankCode) throws MessagingException, IOException 
	{
		String subject = msg.getSubject();
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<TITLE>\n");
		sb.append(subject + "\n");
		sb.append("</TITLE>\n");
		sb.append("</HEAD>\n");
		sb.append("<BODY>\n");
		//sb.append("<H3> Proof of Concept: Java email Reader/Sender </H3>" + "\n");

		sb.append("<font color='blue'>Thank you. Your email has been received and processed.</font>\n");
		sb.append("<br><br>");

		sb.append("<table width='300px' border='1' cellspacing='1' cellpadding='1'>\n");
		sb.append("<tbody>\n");
		sb.append("<tr>\n");
		sb.append("<td width='150px'>TBSReference#:</td>\n");
		sb.append("<td width='150px'>" + refNumber + "</td>\n");		
		sb.append("</tr>\n");
		sb.append("<tr>\n");
		sb.append("<td>Bank Code:</td>\n");
		sb.append("<td>" + bankCode + "</td>\n");		
		sb.append("</tr>\n");
		sb.append("</tbody>\n");
		sb.append("</table>\n");

		sb.append("</BODY>\n");
		sb.append("</HTML>\n");

		msg.setDataHandler(new DataHandler(new ByteArrayDataSource(sb.toString(), "text/html")));
	}

}
