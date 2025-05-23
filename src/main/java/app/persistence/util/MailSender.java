package app.persistence.util;

import app.entities.CustomerInformation;
import app.persistence.controller.RoutingController;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class MailSender {
    private static RoutingController routingController = new RoutingController();

    public void sendFirstMail(String to, String name, float salesPrice, int offerId, String searchForOfferLink) throws IOException {
        Email from = new Email("no-reply@marcuspff.com");
        from.setName("!Johannes Fog - Team");

        Mail mail = new Mail();
        mail.setFrom(from);

        String API_KEY = System.getenv("SENDGRID_API_KEY");

        Personalization personalization = new Personalization();

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
        int offerId = routingController.getOfferId();

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
        int offerId = routingController.getOfferId();

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
        int orderId = routingController.getOrderId();

        Personalization personalization = new Personalization();
        personalization.addTo(new Email(to));
        mail.addPersonalization(personalization);
        personalization.addDynamicTemplateData("customerEmail", customerEmail);
        personalization.addDynamicTemplateData("firstName", firstName);
        personalization.addDynamicTemplateData("sellerCode", sellerCode);
        personalization.addDynamicTemplateData("orderId", orderId);


        mail.addCategory("carportapp");
        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            mail.templateId = "d-6c551c3b0c3d406f9d53dd37e1a954a9";
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

    public void sendLastAcceptMailAndMaterialList(String to, int orderId, CustomerInformation customerInformation, byte[] pdfBytes, UUID trackingNumber) throws IOException {
        Email from = new Email("no-reply@marcuspff.com");
        from.setName("!Johannes Fog - Team");
        Mail mail = new Mail();
        mail.setFrom(from);
        String API_KEY = System.getenv("SENDGRID_API_KEY");

        String firstName = customerInformation.getFirstName();
        String trackingNumberString = trackingNumber.toString();

        Personalization personalization = new Personalization();
        personalization.addTo(new Email(to));
        mail.addPersonalization(personalization);
        personalization.addDynamicTemplateData("firstName", firstName);
        personalization.addDynamicTemplateData("orderId", orderId);
        personalization.addDynamicTemplateData("trackingNumber", trackingNumberString);

        mail.addCategory("carportapp");

        Attachments attachments = new Attachments();
        attachments.setContent(Base64.getEncoder().encodeToString(pdfBytes));
        attachments.setType("application/pdf");
        attachments.setFilename("materialeliste.pdf");
        attachments.setDisposition("attachment");
        mail.addAttachments(attachments);

        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            mail.templateId = "d-082b519b4de144c6aa41463133ea8939";
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());

            if (response.getStatusCode() != 202) {
                throw new IOException("Email sending failed: "
                        + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (IOException ex) {
            System.out.println("Error sending final acceptance mail");
            throw new IOException("Kunne ikke sende acceptmail: " + ex.getMessage(), ex);
        }
    }
}
