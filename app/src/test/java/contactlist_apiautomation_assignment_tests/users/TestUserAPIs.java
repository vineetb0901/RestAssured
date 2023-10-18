package contactlist_apiautomation_assignment_tests.users;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.APIResources;
import resources.Utils;
import resources.responsebody.user.CreateUserResponse;
import resources.responsebody.user.User;
import resources.testdata.user.TestDataBuilderForUser;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestUserAPIs {
    public static String token;
    String loginToken;
    TestDataBuilderForUser testDataBuilderUser;
    String email;
    String password;
    String firstName;
    String lastName;
    @BeforeClass
    public TestUserAPIs setUp(){
        testDataBuilderUser = new TestDataBuilderForUser();
        firstName = Utils.generateFirstName();
        lastName = Utils.generateLastName();
        email = Utils.generateEmail();
        password = Utils.generatePassword();
        return this;
    }


    @Test(priority = 1)
    public void shouldTestCreateUser() throws IOException {
        //Arrange
        String resource = APIResources.CreateUserAPI.getResource();
        //Act
        CreateUserResponse createUserResponse = given().spec(Utils.requestSpecificationBuilder())
                .body(testDataBuilderUser.createUserPayload(firstName, lastName, email, password))
                .when().post(resource)
                .then().spec(Utils.responseSpecificationBuilder())
                .extract().response().as(CreateUserResponse.class);
        token = createUserResponse.getToken();

        //Assert
        Assert.assertEquals(createUserResponse.getUser().getFirstName(), firstName);
    }

    @Test(priority = 2)
    public void shouldTestGetUserProfile() throws IOException {
        //Arrange
        String resource = APIResources.GetUserProfileAPI.getResource();
        //Act
        User user = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .when().get(resource)
                .then().spec(Utils.responseSpecificationBuilder())
                .assertThat().statusCode(200)
                .extract().response().as(User.class);
        //Assert
        Assert.assertEquals(user.getEmail(), email);
    }

    @Test(priority = 3)
    public void shouldTestUpdateUser() throws IOException {
        //Arrange
        String resource = APIResources.UpdateUserAPI.getResource();
        String updatedFirstName = Utils.generateFirstName();
        String updatedLastName = Utils.generateLastName();
        //Act
        User updatedUser = given().spec(Utils.requestSpecificationBuilder())
                .body(testDataBuilderUser.createUserPayload(updatedFirstName, updatedLastName, email, password))
                .header("Authorization", "Bearer " + token)
                .when().patch(resource)
                .then().spec(Utils.responseSpecificationBuilder()).assertThat().statusCode(200)
                .extract().response().as(User.class);
        //Assert
        Assert.assertEquals(updatedUser.getFirstName(), updatedFirstName);
    }

    @Test(priority = 4)
    public void shouldTestLogInUser() throws IOException {
        //Arrange
        String resource = APIResources.LogInUserAPI.getResource();
        //Act
        CreateUserResponse createUserResponse = given().spec(Utils.requestSpecificationBuilder())
                .body(testDataBuilderUser.createLoginPayload(email, password))
                .when().post(resource)
                .then().spec(Utils.responseSpecificationBuilder())
                .assertThat().statusCode(200)
                .extract().response().as(CreateUserResponse.class);
        loginToken = createUserResponse.getToken();
        //Assert
        Assert.assertEquals(createUserResponse.getUser().getEmail(),email);
    }
    @Test(priority = 5)
    public void shouldTestLogOutUser() throws IOException {
        //Arrange
        String resource = APIResources.LogOutUserAPI.getResource();
        //Act
        Response response = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + loginToken)
                .when().post(resource)
                .then().extract().response();
        //Assert
        Assert.assertEquals(response.statusCode(),200);
    }
    @Test(priority = 6)
    public void shouldTestDeleteUser() throws IOException {
        //Arrange
        this.shouldTestLogInUser();
        String resource = APIResources.DeleteUserAPI.getResource();
        //Act
        Response response = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + loginToken)
                .header("Cookie","token=" + loginToken)
                .when().delete(resource)
                .then().extract().response();
        //Assert
        Assert.assertEquals(response.getStatusCode(),200);
    }
}
