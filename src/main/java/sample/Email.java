package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    @FXML
    public TextField adre;
    @FXML
    private TextField komu;
    @FXML
    private TextField tema;
    @FXML
    private Button otp;
    @FXML
    void initialize() {
        final String from = "alexa172jera@gmail.com";
        final String username = "alexa172jera@gmail.com";
        final String password = "mzsnipbumgwzqdng";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "username");
        props.put("mail.smtp.password", "password");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        otp.setOnAction(event -> {
            DropShadow shadow = new DropShadow();
            otp.setEffect(shadow);
            String text = komu.getText();
            String text1 = tema.getText();
            String text2 = adre.getText();
            try {
// Create a default MimeMessage object :
                Message message = new MimeMessage(session);

// Set From: header field of the header :
                message.setFrom(new InternetAddress(from));
// Set To: header field of the header :
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(text));

// Set Subject: header field :
                message.setSubject(text1);

                message.setText(text2);
                Transport.send(message);
                System.out.println("Sent message successfully.");
            } catch (MessagingException e) {
                System.out.println("Sent message failed.");
                e.printStackTrace();
            }

        });
}
}
