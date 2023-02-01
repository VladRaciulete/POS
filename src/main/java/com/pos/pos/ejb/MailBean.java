package com.pos.pos.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


@Stateless
public class MailBean {
    private static final Logger LOG = Logger.getLogger(PasswordBean.class.getName());
    Session newSession = null;
    MimeMessage mimeMessage = null;

    public void sendMailForNewCashierAdded(List<String> emails){
        try {
            LOG.info("sendMailForNewCashierAdded");
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");

            newSession = Session.getDefaultInstance(properties, null);

            String emailSubject = "New cashier added";
            String emailBody = "body text";
            String link = "http://localhost:8080/POS-1.0-SNAPSHOT/Cashiers";
            String bodyText = "Hi! There is a new cashier waiting to be validated.\n" +
                    "Click the link below to access the validation page:\n" +
                    link;

            InternetAddress[] recipients = new InternetAddress[emails.size()];
            int contor = 0;

            for(String email : emails){
                recipients[contor] = new InternetAddress(email);
                contor += 1;
            }


            mimeMessage = new MimeMessage(newSession);

            mimeMessage.addRecipients(Message.RecipientType.TO, recipients);
            mimeMessage.setSubject(emailSubject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(emailBody, "html/text");
            mimeBodyPart.setText(bodyText);


            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(mimeBodyPart);

            mimeMessage.setContent(mimeMultipart);

            String emailHost = "smtp.gmail.com";
            String fromUser = "pointofsalesmail@gmail.com";
            String fromUserPassword = "svmyrelzvcbudedy";

            Transport transport = newSession.getTransport("smtp");
            transport.connect(emailHost, fromUser, fromUserPassword);

            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

            transport.close();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
