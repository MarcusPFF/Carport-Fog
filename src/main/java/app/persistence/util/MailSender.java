package app.persistence.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;

public class MailSender {
    public boolean sendFirstMail(String to, String name, String email) throws IOException {
        Email from = new Email("no-reply@marcuspff.com");
        from.setName("!Johannes Fog - Team");

        Mail mail = new Mail();
        mail.setFrom(from);

        String API_KEY = System.getenv("SENDGRID_API_KEY");

        Personalization personalization = new Personalization();


        //Her skal man indsætte værdier som man kan bruge i mailen. F.eks.
        //Hej {name} !
        //Her skal vi hente sessionattributes ind, så vi kan bruge dem som en værdi
        personalization.addTo(new Email("marcus.pff03@gmail.com"));
        personalization.addDynamicTemplateData("name", "Marcus");
        personalization.addDynamicTemplateData("email", "marcus.pff03@gmail.com");

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
            System.out.println("Error sending mail");
            throw ex;
        }
        return true;
    }

    public boolean sendSecondMail(String to, String name, String email) throws IOException {
        Email from = new Email("no-reply@marcuspff.com");
        from.setName("!Johannes Fog - Team");

        Mail mail = new Mail();
        mail.setFrom(from);

        String API_KEY = System.getenv("SENDGRID_API_KEY");

        Personalization personalization = new Personalization();


        //Her skal man indsætte værdier som man kan bruge i mailen. F.eks.
        //Hej {name} !
        //Her skal vi hente sessionattributes ind, så vi kan bruge dem som en værdi
        personalization.addTo(new Email("marcus.pff03@gmail.com"));
        personalization.addDynamicTemplateData("name", "Marcus");
        personalization.addDynamicTemplateData("email", "marcus.pff03@gmail.com");
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
            System.out.println("Error sending mail");
            throw ex;
        }
        return true;
    }

    public boolean sendSellerMail(String to, String customerName, String customerEmail, String customerTelephoneNumber, String customerOfferId) throws IOException {
        Email from = new Email("no-reply@marcuspff.com");
        from.setName("!Johannes Fog - Team");

        Mail mail = new Mail();
        mail.setFrom(from);

        String API_KEY = System.getenv("SENDGRID_API_KEY");

        Personalization personalization = new Personalization();


        //Her skal man indsætte værdier som man kan bruge i mailen. F.eks.
        //Hej {name} !
        //Her skal vi hente sessionattributes ind, så vi kan bruge dem som en værdi
        personalization.addTo(new Email("sellersatjohannesfog@gmail.com"));
        mail.addPersonalization(personalization);
        personalization.addDynamicTemplateData("customerName", customerName);
        personalization.addDynamicTemplateData("customerEmail", customerEmail);
        personalization.addDynamicTemplateData("customerTelephoneNumber", customerTelephoneNumber);
        personalization.addDynamicTemplateData("customerOfferid", customerOfferId);

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
            System.out.println("Error sending mail");
            throw ex;
        }
        return true;
    }
}
