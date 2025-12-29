package com.codegym.email_configuration.service;

import com.codegym.email_configuration.entity.Email;

import java.util.List;

public interface IEmailService {
    List<Email> getAllEmails();
    Email getEmailById(int id);
    void updateEmail(int id, Email email);
}
