package com.codegym.email_configuration.repository;

import com.codegym.email_configuration.entity.Email;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmailRepository {
    private final List<Email> emails;

    public EmailRepository() {
        emails = new ArrayList<>();
    }

    public Email getEmailById(int id) {
        return emails.get(id);
    }

    public void updateEmail(int id, Email email) {
        int index = emails.indexOf(getEmailById(id));
        emails.set(index, email);
    }

    public List<Email> getAllEmail() {
        return emails;
    }
}
