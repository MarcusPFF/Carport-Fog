package app.entities;

import java.util.Date;
import java.util.UUID;

public class Order {
    private int orderId;
    private int offerId;
    private UUID trackingNumber;
    private Date purchaseDate;
    private String status;

    public Order(int orderId, int offerId, UUID trackingNumber , Date purchaseDate, String status) {
        this.orderId = orderId;
        this.offerId = offerId;
        this.trackingNumber = trackingNumber;
        this.purchaseDate = purchaseDate;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public UUID getTrackingNumber() {
        return trackingNumber;
    }
    public void setTrackingNumber(UUID trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", offerId=" + offerId +
                ", trackingNumber=" + trackingNumber +
                ", purchaseDate=" + purchaseDate +
                ", status='" + status + '\'' +
                '}';
    }
}
