package fr.aaubert.pmtbackend.service;

public interface EmailService {
    String sendEmail(String toEmail, String subject, String body);
}
