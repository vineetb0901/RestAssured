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
import resources.testdata.contacts.TestDataBuilderForContact;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestContactsAPIs {
    private TestDataBuilderForContact testDataBuilderContact;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String token;
    private String id;

    @BeforeClass(groups = "run-all")
    public void setUp() throws IOException {
        testDataBuilderContact = new TestDataBuilderForContact();
        firstName = Utils.generateFirstName();
        lastName = Utils.generateLastName();
        email = Utils.generateEmail();
        phoneNumber = Utils.generatePhoneNumber();

        //calling shouldTestCreateUser for getting token
        new TestUserAPIs().setUp().shouldTestCreateUser();

        token = TestUserAPIs.token;
    }
//TCA01
    @Test(priority = 1, groups = "run-all")
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
        id = addContactResponse.get_id();
        //Assert
        Assert.assertEquals(addContactResponse.getFirstName(), firstName);
    }

//TCA02
    @Test(priority = 2, groups = "run-all")
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
        String firstNameInResponse = Utils.getJsonPath(response, "[0].firstName");
        //Assert
        Assert.assertEquals(firstNameInResponse, firstName);
    }

//TCA03
    @Test(priority = 3, groups = "run-all")
    public void shouldTestGetContact() throws IOException {
        //Arrange
        String resource = APIResources.GetContact.getResource();
        //Act
        AddContactResponse addContactResponse = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .when().get(resource+ id)
                .then().spec(Utils.responseSpecificationBuilder())
                .assertThat().statusCode(200)
                .extract().response().as(AddContactResponse.class);
        //Assert
        Assert.assertEquals(addContactResponse.get_id(), id);
    }

//TCA04
    @Test(priority = 4, groups = "run-all")
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
                .when().put(resource+ id)
                .then().spec(Utils.responseSpecificationBuilder())
                .extract().response().as(AddContactResponse.class);
        String updatedContactPayloadFirstName = updatedContactPayload.getFirstName();
        String updatedContactResponseFirstName = updatedContactResponse.getFirstName();
        //Assert
        Assert.assertEquals(updatedContactPayloadFirstName,updatedContactResponseFirstName);
    }

//TCA05
    @Test(priority = 5, groups = "run-all")
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
                .when().patch(resource + id)
                .then().spec(Utils.responseSpecificationBuilder())
                .extract().response().as(AddContactResponse.class);
        String requestedEmail = addContactResponse.getEmail();
        String  emailInResponse= payloadForUpdateEmailAndPhone.getEmail();
        //Assert
        Assert.assertEquals(requestedEmail,emailInResponse);
    }

//TCA06
    @Test(priority = 6, groups = "run-all")
    public void shouldTestDeleteContact() throws IOException {
        //Arrange
        String resource = APIResources.DeleteContact.getResource();
        //Act
        String deleteContactResponse = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .when().delete(resource + id)
                .then().extract().response().asString();
        //Assert
        Assert.assertEquals(deleteContactResponse,"Contact deleted");
    }
}
