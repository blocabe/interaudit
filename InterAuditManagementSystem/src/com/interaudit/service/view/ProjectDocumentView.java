package com.interaudit.service.view;

import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.Mission;


public class ProjectDocumentView {
    private Mission project;
    private Document document;
    private boolean currentUserTeamMember;

    public Mission getProject() {
        return project;
    }

    public void setProject(Mission project) {
        this.project = project;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
    public boolean isCurrentUserTeamMember() {
        return currentUserTeamMember;
    }
    public void setCurrentUserTeamMember(boolean currentUserTeamMember) {
        this.currentUserTeamMember = currentUserTeamMember;
    }
}
