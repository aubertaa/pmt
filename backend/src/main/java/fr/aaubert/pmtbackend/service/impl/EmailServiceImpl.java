package fr.aaubert.pmtbackend.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import fr.aaubert.pmtbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class EmailServiceImpl implements EmailService {

    private final SendGrid sendGridClient;

    public EmailServiceImpl(@Value("${sendgrid.api.key}") String apiKey) {
        this.sendGridClient = new SendGrid(apiKey);
    }

    @Override
    public String sendEmail(String toEmail, String subject, String body) {
        Email from = new Email("aaubert_visiplus@macaveamoi.fr");
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGridClient.api(request);
            return "Email sent with status code: " + response.getStatusCode();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error sending email: " + ex.getMessage();
        }
    }
}
