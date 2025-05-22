package app.entities;
import java.util.Date;


public class Offer {
    private int offerId;
    private float totalExpensePrice;
    private float totalRetailPrice;
    private int sellerId;
    private customerInformation customerInformation;
    private Date expirationDate;
    private int carportLength;
    private int carportWidth;
    private int shedLength;
    private int shedWidth;

    public Offer(int offerId, float totalExpensePrice, float totalRetailPrice, int sellerId, customerInformation customerInformation, Date expirationDate, int carportLength, int carportWidth, int shedLength, int shedWidth) {
        this.offerId = offerId;
        this.totalExpensePrice = totalExpensePrice;
        this.totalRetailPrice = totalRetailPrice;
        this.sellerId = sellerId;
        this.customerInformation = customerInformation;
        this.expirationDate = expirationDate;
        this.carportLength = carportLength;
        this.carportWidth = carportWidth;
        this.shedLength = shedLength;
        this.shedWidth = shedWidth;
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

    public customerInformation getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(customerInformation customerInformation) {
        this.customerInformation = customerInformation;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCarportLength() {
        return carportLength;
    }

    public void setCarportLength(int carportLength) {
        this.carportLength = carportLength;
    }

    public int getCarportWidth() {
        return carportWidth;
    }

    public void setCarportWidth(int carportWidth) {
        this.carportWidth = carportWidth;
    }

    public int getShedLength() {
        return shedLength;
    }

    public void setShedLength(int shedLength) {
        this.shedLength = shedLength;
    }

    public int getShedWidth() {
        return shedWidth;
    }

    public void setShedWidth(int shedWidth) {
        this.shedWidth = shedWidth;
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
                ", carportLength=" + carportLength +
                ", carportWidth=" + carportWidth +
                ", shedLength=" + shedLength +
                ", shedWidth=" + shedWidth +
                '}';
    }
}
