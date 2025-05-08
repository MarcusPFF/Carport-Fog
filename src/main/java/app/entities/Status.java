package app.entities;

public class Status {
    private int statusId;
    private String statusDescription;
    private String messageForMail;

    public Status(int statusId, String statusDescription, String messageForMail) {
        this.statusId = statusId;
        this.statusDescription = statusDescription;
        this.messageForMail = messageForMail;
    }


    public int getStatusId() {
        return statusId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public String getMessageForMail() {
        return messageForMail;
    }


    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public void setMessageForMail(String messageForMail) {
        this.messageForMail = messageForMail;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusId=" + statusId +
                ", statusDescription='" + statusDescription + '\'' +
                ", messageForMail='" + messageForMail + '\'' +
                '}';
    }
}
