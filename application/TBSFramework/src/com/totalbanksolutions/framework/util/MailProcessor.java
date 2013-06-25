package com.totalbanksolutions.framework.util;

import java.io.*;
import java.util.Properties;
import java.util.Date;

import javax.mail.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* Monitors given mailbox for new mail */

public class MailProcessor
{
    protected final static Log log = LogFactory.getLog(MailProcessor.class);
	final static int frequencyMilliseconds = 30000;

	public static void main(String[] argv) 
	{
		try
		{
			boolean isContinue = true;
			MailProcessor monitor = new MailProcessor();
			while(isContinue)
			{
				monitor.processMail();
				Thread.sleep(frequencyMilliseconds);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void processMail()
	{
		//boolean debug = false;	
	
		//Exchange
		/*
		String protocol = "imap"; 
		String port = "";
		String user = "vcatrini";
		String password = "Password0";
		String mailhost = "tbshk-exch01.intranet.totalbanksolutions.com";
		*/
		
		//Gmail
		String protocol = "imaps"; 
		//String port = "587";
		String user = "vcatrini";
		String password = "******";
		String mailhost = "imap.gmail.com";

		String incomingFolderName = "TBSIncoming"; //"inbox";
		String processedFolderName = "TBSProcessed";
		
		try
		{
		    Properties props = System.getProperties();
	
			// Get a Session object
			Session session = Session.getInstance(props, null);
			session.setDebug(true);
	
			// Get a Store object
			Store store = session.getStore(protocol);
			// Connect
			store.connect(mailhost, user, password);

			// Open a Folder
			Folder sourceFolder = store.getFolder(incomingFolderName);
			if (sourceFolder == null || !sourceFolder.exists()) 
			{
				System.out.println("Invalid source folder");
				System.exit(1);
			}

			sourceFolder.open(Folder.READ_WRITE);
			
			int count = sourceFolder.getMessageCount();
			if (count == 0) 
			{ // No messages in the source folder
				System.out.println(sourceFolder.getName() + " is empty");
				// Close folder, store and return
				sourceFolder.close(false);
				store.close();
				return;
			}

		    // Open destination folder, create if reqd
		    Folder destFolder = store.getFolder(processedFolderName);
			if (sourceFolder == null || !sourceFolder.exists()) 
			{
				System.out.println("Invalid destination folder");
				System.exit(1);
			}
			
			Message[] msgs = sourceFolder.getMessages();
			log.debug("Found " + msgs.length + " messages");

			if(msgs.length != 0)
			{
				int processedCount = 0;
				boolean isProcess = true;
				for(Message msg : msgs)
				{
					log.debug("PROCESSING MESSAGE #: " + processedCount++);
					dumpEnvelope(msg);
					MailSender mailSender = new MailSender();
					String subject = msg.getSubject();
					String from = msg.getFrom()[0].toString();
					if(from.equalsIgnoreCase("vcatrini@gmail.com"))
					{
						isProcess = false;
					}
					if(isProcess)
					{
						String refNumber = getReferenceNumber(subject);
						String bankCode = getBankCode(msg);
						mailSender.SendMail(from, "RE: " + subject, refNumber, bankCode);
					}
				}
				if(isProcess)
				{
					sourceFolder.copyMessages(msgs, destFolder);
				}
				sourceFolder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);
			}

			// Close folders and store
		    sourceFolder.close(true); // expunge deleted messages
		    store.close();
			
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	public static String getReferenceNumber(String input)
	{
		String ref = "";
		String[] list = StringUtils.split(input);
		if(list != null && list.length > 0)
		{
			for(String part : list)
			{
				if(StringUtils.startsWithIgnoreCase(part, "TBSReference#"))
				{
					ref = StringUtils.removeStartIgnoreCase(part, "TBSReference#");
					break;
				}
			}
		}
		return ref;
	}

	public static String getBankCode(Message msg)
	{
		String ref = "";
		try
		{
			String input = getMessageString(msg);
			String[] list = StringUtils.split(input);
			if(list != null && list.length > 0)
			{
				for(String part : list)
				{
					if(StringUtils.startsWithIgnoreCase(part, "BankCode:"))
					{
						ref = StringUtils.removeStartIgnoreCase(part, "BankCode:");
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ref;
	}
	
	public static String getMessageString(Part p) throws Exception 
	{
		String output = "";
		Object o = p.getContent();
		if (o instanceof String) 
		{
			output = (String)o;
		} 
		else if (o instanceof Multipart) 
		{
			Multipart mp = (Multipart)o;
			int count = mp.getCount();
			for (int i = 0; i < count; i++)
			{
				output += getMessageString(mp.getBodyPart(i));
			}
			
		} 
		else if (o instanceof Message)
		{
			//Nested Message
			output = getMessageString((Part)o);
		} 
		else if (o instanceof InputStream) 
		{
			InputStream is = (InputStream)o;
			int c;
			while ((c = is.read()) != -1)
			{
				output += c;
			}
		}
		return output;
	}
	
	public static void dumpPart(Part p) throws Exception 
	{
		if (p instanceof Message) dumpEnvelope((Message)p);
	
			/* Dump input stream
	InputStream is = new BufferedInputStream(p.getInputStream());
	int c;
	while ((c = is.read()) != -1)
	    System.out.write(c);
	*/
	
	System.out.println("CONTENT-TYPE: " + p.getContentType());
	
	Object o = p.getContent();
	if (o instanceof String) {
	    System.out.println("This is a String");
	System.out.println("---------------------------");
	    System.out.println((String)o);
	} else if (o instanceof Multipart) {
	    System.out.println("This is a Multipart");
	System.out.println("---------------------------");
	    Multipart mp = (Multipart)o;
	    int count = mp.getCount();
	    for (int i = 0; i < count; i++)
		dumpPart(mp.getBodyPart(i));
	} else if (o instanceof Message) {
	    System.out.println("This is a Nested Message");
	System.out.println("---------------------------");
	    dumpPart((Part)o);
	} else if (o instanceof InputStream) {
	    System.out.println("This is just an input stream");
	System.out.println("---------------------------");
	    InputStream is = (InputStream)o;
	    int c;
	    while ((c = is.read()) != -1)
		System.out.write(c);
	}
	}	

    public static void dumpEnvelope(Message m) throws Exception 
    {
    	log.debug("This is the message envelope");
    	log.debug("---------------------------");
    	Address[] a;
    	// FROM 
    	if ((a = m.getFrom()) != null) {
    	    for (int j = 0; j < a.length; j++)
    	    	log.debug("FROM: " + a[j].toString());
    	}

    	// TO
    	if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
    	    for (int j = 0; j < a.length; j++)
    	    	log.debug("TO: " + a[j].toString());
    	}

    	// SUBJECT
    	log.debug("SUBJECT: " + m.getSubject());

    	// DATE
    	Date d = m.getSentDate();
    	log.debug("SendDate: " +
    	    (d != null ? d.toString() : "UNKNOWN"));

    	// SIZE
    	log.debug("Size: " + m.getSize());

    	// FLAGS:
    	Flags flags = m.getFlags();
    	StringBuffer sb = new StringBuffer();
    	Flags.Flag[] sf = flags.getSystemFlags(); // get the system flags

    	boolean first = true;
    	for (int i = 0; i < sf.length; i++) {
    	    String s;
    	    Flags.Flag f = sf[i];
    	    if (f == Flags.Flag.ANSWERED)
    		s = "\\Answered";
    	    else if (f == Flags.Flag.DELETED)
    		s = "\\Deleted";
    	    else if (f == Flags.Flag.DRAFT)
    		s = "\\Draft";
    	    else if (f == Flags.Flag.FLAGGED)
    		s = "\\Flagged";
    	    else if (f == Flags.Flag.RECENT)
    		s = "\\Recent";
    	    else if (f == Flags.Flag.SEEN)
    		s = "\\Seen";
    	    else
    		continue;	// skip it
    	    if (first)
    		first = false;
    	    else
    		sb.append(' ');
    	    sb.append(s);
    	}

    	String[] uf = flags.getUserFlags(); // get the user flag strings
    	for (int i = 0; i < uf.length; i++) {
    	    if (first)
    		first = false;
    	    else
    		sb.append(' ');
    	    sb.append(uf[i]);
    	}
    	log.debug("FLAGS = " + sb.toString());

    	// X-MAILER
    	//String[] hdrs = m.getHeader("X-Mailer");
    	//if (hdrs != null)
    	//    System.out.println("X-Mailer: " + hdrs[0]);
    	//else
    	//    System.out.println("X-Mailer NOT available");
		log.debug("---------------------------");
	}
}
