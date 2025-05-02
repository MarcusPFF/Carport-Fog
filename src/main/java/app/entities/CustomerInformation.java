package app.entities;

public class CustomerInformation {
    private String customerMail;
    private String firstName;
    private String lastName;
    private Address address;

    public CustomerInformation(String customerMail, String firstName, String lastName, Address address) {
        this.customerMail = customerMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerInformation{" +
                "customerMail='" + customerMail + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
