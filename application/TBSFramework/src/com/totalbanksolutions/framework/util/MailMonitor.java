package com.totalbanksolutions.framework.util;

import java.io.*;
import java.util.Properties;
import java.util.Date;

import javax.mail.*;
//import javax.activation.*;
//import javax.mail.internet.*;
//import javax.mail.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.sun.mail.smtp.SMTPTransport;

//import java.util.*;
import javax.mail.event.*;

import com.sun.mail.imap.*;

/* Monitors given mailbox for new mail */

public class MailMonitor
{

    protected final Log log = LogFactory.getLog(getClass());

	public static void main(String[] argv) 
	{
		MailMonitor monitor = new MailMonitor();
		monitor.start();
	}

	public void start()
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

		String folderName = "TBSTest"; //"inbox";
		int frequencyMilliseconds = 10000;
		
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
			Folder folder = store.getFolder(folderName);
			if (folder == null || !folder.exists()) 
			{
				System.out.println("Invalid folder");
				System.exit(1);
			}

			folder.open(Folder.READ_ONLY);

			// Add messageCountListener to listen for new messages
			folder.addMessageCountListener(new MessageCountAdapter() 
			{
				public void messagesAdded(MessageCountEvent ev) {
					Message[] msgs = ev.getMessages();
					System.out.println("Got " + msgs.length + " new messages");
					// Just dump out the new messages
					for (int i = 0; i < msgs.length; i++) 
					{
						try 
						{
							log.debug("SUBJECT: " + msgs[i].getSubject());
							log.debug("FROM: " + msgs[i].getFrom()[0]);
							log.debug("SENT DATE: " + msgs[i].getSentDate());
							log.debug("CONTENT TYPE: " + msgs[i].getContentType());
							log.debug("CONTENTS: " + msgs[i].getContent());
							
							dumpPart(msgs[i]);
							
							
							//System.out.println("-----");
							//System.out.println("Message " + msgs[i].getMessageNumber() + ":");
							//msgs[i].writeTo(System.out);
						} 
						catch (IOException ioex) 
						{ 
							ioex.printStackTrace();	
						} 
						catch (MessagingException mex) 
						{
							mex.printStackTrace();
						}
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				}
			});
			
			boolean supportsIdle = false;
			try 
			{
				if (folder instanceof IMAPFolder) 
				{
					IMAPFolder f = (IMAPFolder)folder;
					f.idle();
					supportsIdle = true;
				}
			} 
			catch (FolderClosedException fex) 
			{
				throw fex;
			} 
			catch (MessagingException mex) 
			{
				supportsIdle = false;
			}
			
			for (;;) 
			{
				if (supportsIdle && folder instanceof IMAPFolder) 
				{
					IMAPFolder f = (IMAPFolder)folder;
					f.idle();
					System.out.println("IDLE done");
				} 
				else 
				{
					Thread.sleep(frequencyMilliseconds);
					// This is to force the IMAP server to send us EXISTS notifications. 
					int count = folder.getMessageCount();
					System.out.println("MESSAGE COUNT = " + count);
				}
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
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

    public static void dumpEnvelope(Message m) throws Exception {
    	System.out.println("This is the message envelope");
    	System.out.println("---------------------------");
    	Address[] a;
    	// FROM 
    	if ((a = m.getFrom()) != null) {
    	    for (int j = 0; j < a.length; j++)
    		System.out.println("FROM: " + a[j].toString());
    	}

    	// TO
    	if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
    	    for (int j = 0; j < a.length; j++)
    		System.out.println("TO: " + a[j].toString());
    	}

    	// SUBJECT
    	System.out.println("SUBJECT: " + m.getSubject());

    	// DATE
    	Date d = m.getSentDate();
    	System.out.println("SendDate: " +
    	    (d != null ? d.toString() : "UNKNOWN"));

    	// SIZE
    	System.out.println("Size: " + m.getSize());

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
    	System.out.println("FLAGS = " + sb.toString());

    	// X-MAILER
    	String[] hdrs = m.getHeader("X-Mailer");
    	if (hdrs != null)
    	    System.out.println("X-Mailer: " + hdrs[0]);
    	else
    	    System.out.println("X-Mailer NOT available");
        }
	
}
