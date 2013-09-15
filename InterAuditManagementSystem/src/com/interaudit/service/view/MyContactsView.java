package com.interaudit.service.view;

import java.util.List;

import com.interaudit.domain.model.Contact;

public class MyContactsView {
    private List<Contact> contacts;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
