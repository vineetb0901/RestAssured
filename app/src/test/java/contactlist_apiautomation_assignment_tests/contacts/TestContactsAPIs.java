package contactlist_apiautomation_assignment_tests.contacts;

import contactlist_apiautomation_assignment_tests.users.TestUserAPIs;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.APIResources;
import resources.Utils;
import resources.requestbody.contacts.AddContactPayload;
import resources.requestbody.contacts.UpdateEmailAndPhoneNumberPayload;
import resources.responsebody.contacts.AddContactResponse;
import resources.testdata.contacts.TestDataBuilder_contact;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestContactsAPIs {
    private TestDataBuilder_contact testDataBuilderContact;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String token;
    private String _id;

    @BeforeClass
    public void setUp() throws IOException {
        testDataBuilderContact = new TestDataBuilder_contact();
        firstName = Utils.generateFirstName();
        lastName = Utils.generateLastName();
        email = Utils.generateEmail();
        phoneNumber = Utils.generatePhoneNumber();
        new TestUserAPIs().shouldTestCreateUser();
        token = TestUserAPIs.token;
    }

    @Test(priority = 1)
    public void shouldTestAddContact() throws IOException {
        //Arrange
        String resource = APIResources.AddContactAPI.getResource();
        AddContactPayload addContactPayload = testDataBuilderContact.createAddContactPayload(firstName, lastName, email, phoneNumber);
        //Act
        AddContactResponse addContactResponse = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .body(addContactPayload)
                .when().post(resource)
                .then().spec(Utils.responseSpecificationBuilder()).assertThat().statusCode(201)
                .extract().response().as(AddContactResponse.class);
        _id = addContactResponse.get_id();
        //Assert
        Assert.assertEquals(addContactResponse.getFirstName(), firstName);
    }

    @Test(priority = 2)
    public void shouldTestGetContactList() throws IOException {
        //Arrange
        String resources = APIResources.GetContactList.getResource();
        //Act
        Response response = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .when().get(resources)
                .then().spec(Utils.responseSpecificationBuilder())
                .assertThat().statusCode(200)
                .extract().response();
        String firstNameRes = Utils.getJsonPath(response, "[0].firstName");
        //Assert
        Assert.assertEquals(firstNameRes, firstName);
    }

    @Test(priority = 3)
    public void shouldTestGetContact() throws IOException {
        //Arrange
        String resource = APIResources.GetContact.getResource();
        //Act
        AddContactResponse addContactResponse = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .when().get(resource+_id)
                .then().spec(Utils.responseSpecificationBuilder())
                .assertThat().statusCode(200)
                .extract().response().as(AddContactResponse.class);
        //Assert
        Assert.assertEquals(addContactResponse.get_id(),_id);
    }
    @Test(priority = 4)
    public void shouldTestUpdateContact() throws IOException {
        //Arrange
        String resource = APIResources.UpdateContact.getResource();
        String updatedFirstName = Utils.generateFirstName();
        String updatedLastName = Utils.generateLastName();
        String updatedEmail = Utils.generateEmail();
        String updatedPhoneNumber = Utils.generatePhoneNumber();
        AddContactPayload updatedContactPayload = testDataBuilderContact.createAddContactPayload(updatedFirstName, updatedLastName, updatedEmail, updatedPhoneNumber);
        //Act
        AddContactResponse updatedContactResponse = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .body(updatedContactPayload)
                .when().put(resource+_id)
                .then().spec(Utils.responseSpecificationBuilder())
                .extract().response().as(AddContactResponse.class);
        String updatedContactPayloadFirstName = updatedContactPayload.getFirstName();
        String updatedContactResponseFirstName = updatedContactResponse.getFirstName();
        //Assert
        Assert.assertEquals(updatedContactPayloadFirstName,updatedContactResponseFirstName);
    }
    @Test(priority = 5)
    public void shouldTestUpdatePhoneNumberAndEmail() throws IOException {
        //Arrange
        String resource = APIResources.UpdateContact.getResource();
        String updatedEmail = Utils.generateEmail();
        String updatedPhoneNumber = Utils.generatePhoneNumber();
        UpdateEmailAndPhoneNumberPayload payloadForUpdateEmailAndPhone = testDataBuilderContact.createPayloadForUpdateEmailAnd(updatedEmail, updatedPhoneNumber);
        //Act
        AddContactResponse addContactResponse = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .body(payloadForUpdateEmailAndPhone)
                .when().patch(resource + _id)
                .then().spec(Utils.responseSpecificationBuilder())
                .extract().response().as(AddContactResponse.class);
        String email1 = addContactResponse.getEmail();
        String email2 = payloadForUpdateEmailAndPhone.getEmail();
        //Assert
        Assert.assertEquals(email1,email2);
    }
}
