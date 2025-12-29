package com.codegym.email_configuration.service;

import com.codegym.email_configuration.entity.Email;
import com.codegym.email_configuration.repository.EmailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService implements IEmailService{
    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public List<Email> getAllEmails() {
        return emailRepository.getAllEmail();
    }

    @Override
    public Email getEmailById(int id) {
        return emailRepository.getEmailById(id);
    }

    @Override
    public void updateEmail(int id, Email email) {
        emailRepository.updateEmail(id, email);
    }
}
