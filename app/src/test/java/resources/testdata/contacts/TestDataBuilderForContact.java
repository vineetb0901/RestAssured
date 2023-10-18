package resources.testdata.contacts;

import resources.requestbody.contacts.AddContactPayload;
import resources.requestbody.contacts.UpdateEmailAndPhoneNumberPayload;

public class TestDataBuilderForContact {
       public AddContactPayload addContactPayload = new AddContactPayload();
    public AddContactPayload createAddContactPayload(String firstName, String lastName, String email, String phoneNumber) {
        addContactPayload.setFirstName(firstName);
        addContactPayload.setLastName(lastName);
        addContactPayload.setBirthdate("1970-01-01");
        addContactPayload.setEmail(email);
        addContactPayload.setPhoneNumber(phoneNumber);
        addContactPayload.setStreet1("Apartment A");
        addContactPayload.setStreet2("2 Main St.");
        addContactPayload.setCity("Bengaluru");
        addContactPayload.setStateProvince("Karnataka");
        addContactPayload.setPostalCode("560064");
        addContactPayload.setCountry("India");
        return addContactPayload;
    }

    public UpdateEmailAndPhoneNumberPayload createPayloadForUpdateEmailAnd(String email, String phoneNumber){
        UpdateEmailAndPhoneNumberPayload updateEmailAndPhoneNumberPayload = new UpdateEmailAndPhoneNumberPayload();
        updateEmailAndPhoneNumberPayload.setEmail(email);
        updateEmailAndPhoneNumberPayload.setPhone(phoneNumber);
        return updateEmailAndPhoneNumberPayload;
    }
}
