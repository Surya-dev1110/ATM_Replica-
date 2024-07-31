package ATM_PROJECT;

import java.util.Properties;

import javax.activation.*;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class Javasendmail {
    public static void sendMail(String recipientEmail,String Subject,String text) 
    {
        //Gmail ID & Password
        String senderEmail = "jdbcbank@gmail.com";
        String senderPassword = "xiefrtnssiczopem";
        //Sending mail using SMTP protocol Simple Mail Transfer Protocol
        


        //Setup properties for the mail server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");//authentication via username 
        properties.put("mail.smtp.starttls.enable", "true");//TLS protocol for security
        properties.put("mail.smtp.host", "smtp.gmail.com");//gmail host
        properties.put("mail.smtp.port", "587");


       //Creating session object
        Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(senderEmail, senderPassword);
        }
        });

        try {
                //Message object
                Message message = new MimeMessage(session);

                
                message.setFrom(new InternetAddress(senderEmail));

                // Set To: header field of the header
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

                // Set Subject: header field
                message.setSubject(Subject);

                // Now set the actual message body
                message.setText(text);


                // Send message
                Transport.send(message);

                System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
    }

        public static void sendMailAttach(String recipientEmail, String Subject, String text, String fileName) {
                // Gmail ID & password
                String senderEmail = "jdbcbank@gmail.com";
                String senderPassword = "xiefrtnssiczopem";

                // Setup properties for the mail server
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(senderEmail, senderPassword);
                        }
                });

                try {

                        Message message = new MimeMessage(session);

                        message.setFrom(new InternetAddress(senderEmail));

                        // Set To: header field of the header
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

                        // Set Subject: header field
                        message.setSubject(Subject);

                        // Attachment section

                        BodyPart messagebody = new MimeBodyPart();
                        messagebody.setText(text);
                        Multipart multipart = new MimeMultipart();
                        // text mgs
                        multipart.addBodyPart(messagebody);
                        messagebody = new MimeBodyPart();

                        String filename = fileName;// Attachment File name
                        DataSource source = new FileDataSource(filename);
                        messagebody.setDataHandler(new DataHandler(source));
                        messagebody.setFileName(filename);
                        multipart.addBodyPart(messagebody);
                        message.setContent(multipart);
                        // Send message
                        Transport.send(message);

                        System.out.println("Email sent successfully!");

                } catch (MessagingException e) {
                        throw new RuntimeException(e);
                }
        }
}
