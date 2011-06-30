package org.ncibi.commons.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * An e-mail notification utility. Can be created once in and used multiple times. 
 * 
 * @author Terry E. Weymouth
 * 
 * @version 1.0 - Nov 2010
 * 
 * Copyright 2010 The Regents of the University of Michigan, www.umich.edu
 * Developed by NCIBI, www.ncibi.org, with support from National Institutes of Health Grant #U54DA021519
 */
public class EmailGateway {

	private final String from;
	private final String password;
	private final String username;
	private final String hostname;
	private String header = "";
	private String footer = "";

	/**
	 * Set up an E-Mail Gateway. The hostname is an STMP server that you have access to, e.g. stmp.mail.umich.edu;
	 * the username and password are for that server.
	 * 
	 * @param hostname - String - address of an STMP host
	 * @param username - String - for access to the STMP at hostname
	 * @param password - String - for access to the STMP at hostname
	 * @param from - String - to appear as the sender; most likely checked as a valid e-mail address by the STMP host
	 */
	public EmailGateway(String hostname, String username, String password, String from){
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.from = from;
	}

	/**
	 * Set the header for all notifications from this EmailGateway
	 * 
	 * @param header - String - set to null or blank to disable (default is disabled)
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * Set the footer for all notifications from this EmailGateway
	 * 
	 * @param footer - String - set to null of blank to disable (default is disabled)
	 */
	public void setFooter(String footer) {
		this.footer = footer;
	}

	/**
	 * Send a notification message using this gateway. 
	 * 
	 * @param to - String - the email address of the recipient
	 * @param subject - String - the subject of the message
	 * @param message - String - the body of the message
	 * @throws EmailException
	 */
	public void sendMail(String to, String subject, String message) throws EmailException{
		Email email = new SimpleEmail();
		email.setHostName(hostname);
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(username, password));
		email.setTLS(true);
		email.setFrom(from);
		email.setSubject(subject);
		if ((header != null) && (header.trim().length() > 0))
			message = header + "\n\n" + message;
		if ((footer != null) && (footer.trim().length() > 0))
			message += "\n\n" + footer;
		email.setMsg(message);
		email.addTo(to);
		email.send();
	}
	
}
