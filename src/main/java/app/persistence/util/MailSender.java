package app.persistence.util;

import app.entities.CustomerInformation;
import app.exceptions.DatabaseException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;

public class MailSender {
    private int offerId = 0;

    public void sendFirstMail(String to, String name, float salesPrice, int offerId, String searchForOfferLink) throws IOException {
        Email from = new Email("no-reply@marcuspff.com");
        from.setName("!Johannes Fog - Team");

        Mail mail = new Mail();
        mail.setFrom(from);

        String API_KEY = System.getenv("SENDGRID_API_KEY");

        Personalization personalization = new Personalization();

        //Her skal man indsætte værdier som man kan bruge i mailen. F.eks.
        //Hej {name} !
        //Her skal vi hente sessionattributes ind, så vi kan bruge dem som en værdi
        personalization.addTo(new Email(to));
        personalization.addDynamicTemplateData("name", name);
        personalization.addDynamicTemplateData("salesPrice", salesPrice);
        personalization.addDynamicTemplateData("offerId", offerId);
        personalization.addDynamicTemplateData("searchForOfferLink", searchForOfferLink);
        mail.addPersonalization(personalization);
        mail.addCategory("carportapp");

        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            mail.templateId = "d-c1ab968df32b4e1dabf28de9e5d863e3";
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Error sending mail 1");
            throw ex;
        }
    }

    public void sendSecondMail(CustomerInformation customerInformation, String acceptOfferTempLink) throws IOException {
        Email from = new Email("no-reply@marcuspff.com");
        from.setName("!Johannes Fog - Team");

        Mail mail = new Mail();
        mail.setFrom(from);

        String API_KEY = System.getenv("SENDGRID_API_KEY");

        String to = customerInformation.getCustomerMail();
        String name = customerInformation.getFirstName();
        try {
            offerId = app.persistence.controller.RoutingController.getOfferId();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        Personalization personalization = new Personalization();
        personalization.addTo(new Email(to));
        personalization.addDynamicTemplateData("name", name);
        personalization.addDynamicTemplateData("offerId", offerId);
        personalization.addDynamicTemplateData("acceptOfferLink", acceptOfferTempLink);

        mail.addPersonalization(personalization);

        mail.addCategory("carportapp");

        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            mail.templateId = "d-897ddae32453452092087be0ac5aee9e";
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Error sending mail 2");
            throw ex;
        }
    }

    public void sendSellerMailContact(String to, CustomerInformation customerInformation) throws IOException {
        Email from = new Email("no-reply@marcuspff.com");
        from.setName("!Johannes Fog - Team");
        Mail mail = new Mail();
        mail.setFrom(from);
        String API_KEY = System.getenv("SENDGRID_API_KEY");
        Personalization personalization = new Personalization();

        String customerEmail = customerInformation.getCustomerMail();
        String firstName = customerInformation.getFirstName();
        String lastName = customerInformation.getLastName();
        String streetName = customerInformation.getStreetName();
        int houseNumber = customerInformation.getHouseNumber();
        int zipCode = customerInformation.getZipCode();
        String city = customerInformation.getCity();
        int phoneNumber = customerInformation.getPhoneNumber();
        int sellerCode = app.persistence.controller.RoutingController.getSellerCode();
        try {
            offerId = app.persistence.controller.RoutingController.getOfferId();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        personalization.addTo(new Email(to));
        mail.addPersonalization(personalization);
        personalization.addDynamicTemplateData("customerEmail", customerEmail);
        personalization.addDynamicTemplateData("firstName", firstName);
        personalization.addDynamicTemplateData("lastName", lastName);
        personalization.addDynamicTemplateData("streetName", streetName);
        personalization.addDynamicTemplateData("houseNumber", houseNumber);
        personalization.addDynamicTemplateData("zipCode", zipCode);
        personalization.addDynamicTemplateData("city", city);
        personalization.addDynamicTemplateData("phoneNumber", phoneNumber);
        personalization.addDynamicTemplateData("sellerCode", sellerCode);
        personalization.addDynamicTemplateData("offerId", offerId);

        mail.addCategory("carportapp");
        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            mail.templateId = "d-fd3f5e09f2804d1a8c911d6eb8ab2c25";
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Error sending mail 3");
            throw ex;
        }
    }

    public void sendSellerMailAccept(String to, CustomerInformation customerInformation) throws IOException {
        Email from = new Email("no-reply@marcuspff.com");
        from.setName("!Johannes Fog - Team");
        Mail mail = new Mail();
        mail.setFrom(from);
        String API_KEY = System.getenv("SENDGRID_API_KEY");

        String customerEmail = customerInformation.getCustomerMail();
        String firstName = customerInformation.getFirstName();
        int sellerCode = app.persistence.controller.RoutingController.getSellerCode();
        try {
            offerId = app.persistence.controller.RoutingController.getOfferId();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        Personalization personalization = new Personalization();
        personalization.addTo(new Email(to));
        mail.addPersonalization(personalization);
        personalization.addDynamicTemplateData("customerEmail", customerEmail);
        personalization.addDynamicTemplateData("firstName", firstName);
        personalization.addDynamicTemplateData("sellerCode", sellerCode);
        personalization.addDynamicTemplateData("offerId", offerId);


        mail.addCategory("carportapp");
        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            mail.templateId = "6c551c3b0c3d406f9d53dd37e1a954a9";
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Error sending mail 3");
            throw ex;
        }
    }
}
