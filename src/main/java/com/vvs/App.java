package com.vvs;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class App
{
    public static void main( String[] args )
    {
        // recipient's email address
        String to = "victorsmirnov67@gmail.com";
        // sender's email address
        String from = "victorsmirnov@me.com";
        // assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";
        // Get system properties
        Properties  properties = System.getProperties();
        // setup email server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // get the sesstion object and pass username/password (password to application!)
        Session session = Session.getInstance(properties, new Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("${MAIL_LOGIN}", "${MAIL_PASSWORD}");
           }
        });

        // used to debug SMTP issues
        session.setDebug(true);

        try {
            // create a default mime message object
            MimeMessage message = new MimeMessage(session);
            // set from: header field of header
            message.setFrom(new InternetAddress(from));
            // set to: header field of header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // set subject: header field
            message.setSubject("This is the Subject line.");
            // set the actual text message
            // message.setText("This is the actual message...");
            // or set the actual HTML message
            message.setContent("<h2>This is actual message embded in HTML tags...</h2>", "text/html");

            System.out.println("sending...");
            // send message
            Transport.send(message);
            System.out.println("Sent message successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
