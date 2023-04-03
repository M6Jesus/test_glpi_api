package org.test.api;

public class Ticket {

    public String titre;
    public String description;

    public int status;

    public int urgency;

    public Ticket(String titre, String description, int status, int urgency) {
        this.titre = titre;
        this.description = description;
        this.status = status;
        this.urgency = urgency;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }
}
