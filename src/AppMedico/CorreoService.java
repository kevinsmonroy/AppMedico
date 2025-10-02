package AppMedico;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.Serializable;
import java.util.Properties;

public class CorreoService implements Serializable {
    private final String remitente = "tu_correo@gmail.com";  // tu Gmail
    private final String clave = "TU_CONTRASEÑA_DE_APP";     // contraseña de aplicación

    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);
            System.out.println("Correo enviado al paciente: " + destinatario);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
