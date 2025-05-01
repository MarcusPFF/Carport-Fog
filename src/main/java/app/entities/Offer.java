package app.entities;
import app.entities.CustomerInformation;
import java.util.Date;


public class Offer {
    private int offerId;
    private float totalExpensePrice;
    private float totalRetailPrice;
    private int sellerId;
    private CustomerInformation customerInformation;
    private Date expirationDate;

    public Offer(int offerId, float totalExpensePrice, float totalRetailPrice, int sellerId, CustomerInformation customerInformation, Date expirationDate) {
        this.offerId = offerId;
        this.totalExpensePrice = totalExpensePrice;
        this.totalRetailPrice = totalRetailPrice;
        this.sellerId = sellerId;
        this.customerInformation = customerInformation;
        this.expirationDate = expirationDate;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public float getTotalExpensePrice() {
        return totalExpensePrice;
    }

    public void setTotalExpensePrice(float totalExpensePrice) {
        this.totalExpensePrice = totalExpensePrice;
    }

    public float getTotalRetailPrice() {
        return totalRetailPrice;
    }

    public void setTotalRetailPrice(float totalRetailPrice) {
        this.totalRetailPrice = totalRetailPrice;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public CustomerInformation getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(CustomerInformation customerInformation) {
        this.customerInformation = customerInformation;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offerId=" + offerId +
                ", totalExpensePrice=" + totalExpensePrice +
                ", totalRetailPrice=" + totalRetailPrice +
                ", sellerId=" + sellerId +
                ", customerInformation=" + customerInformation +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
