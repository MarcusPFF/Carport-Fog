package app.entities;

import java.util.Date;

public class Order {
    private int orderId;
    private int offerId;
    private Date date;
    private String status;
    private float salesPrice;

    public Order(int orderId, int offerId, Date date, String status, float salesPrice) {
        this.orderId = orderId;
        this.offerId = offerId;
        this.date = date;
        this.status = status;
        this.salesPrice = salesPrice;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(float salesPrice) {
        this.salesPrice = salesPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", offerId=" + offerId +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", salesPrice=" + salesPrice +
                '}';
    }
}
