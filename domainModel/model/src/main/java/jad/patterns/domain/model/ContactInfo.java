package jad.patterns.domain.model;

import java.util.List;

public class ContactInfo extends DomainModel{
    private List<String> phoneNumbers;
    private List<String> emails;

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}