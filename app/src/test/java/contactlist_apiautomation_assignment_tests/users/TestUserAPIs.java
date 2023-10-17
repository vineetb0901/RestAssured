package contactlist_apiautomation_assignment_tests.users;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import resources.APIResources;
import resources.Utils;
import resources.responsebody.user.CreateUserResponse;
import resources.responsebody.user.User;
import resources.testdata.user.TestDataBuild;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestUserAPIs {
    String token;
    String loginToken;
    TestDataBuild testDataBuild = new TestDataBuild();
    String email = Utils.generateEmail();
    String password = Utils.generatePassword();

    @Test(priority = 1)
    public void shouldTestCreateUser() throws IOException {
        //Arrange
        //Act
        CreateUserResponse createUserResponse = given().spec(Utils.requestSpecificationBuilder())
                .body(testDataBuild.createUserPayload("Baganna", "K", email, password))
                .when().post(APIResources.CreateUserAPI.getResource())
                .then().spec(Utils.responseSpecificationBuilder())
                .extract().response().as(CreateUserResponse.class);
        token = createUserResponse.getToken();

        //Assert
        Assert.assertEquals(createUserResponse.getUser().getFirstName(), "Baganna");
    }

    @Test(priority = 2)
    public void shouldTestGetUserProfile() throws IOException {
        //Arrange

        //Act
        User user = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + token)
                .when().get(APIResources.GetUserProfileAPI.getResource())
                .then().spec(Utils.responseSpecificationBuilder())
                .assertThat().statusCode(200)
                .extract().response().as(User.class);
        //Assert
        Assert.assertEquals(user.getEmail(), email);
    }

    @Test(priority = 3)
    public void shouldTestUpdateUser() throws IOException {
        //Arrange

        //Act
        User user = given().spec(Utils.requestSpecificationBuilder())
                .body(testDataBuild.createUserPayload("UpdatedFName", "updatedLName", email, password))
                .header("Authorization", "Bearer " + token)
                .when().patch(APIResources.UpdateUserAPI.getResource())
                .then().spec(Utils.responseSpecificationBuilder()).assertThat().statusCode(200)
                .extract().response().as(User.class);
        //Assert
        Assert.assertEquals(user.getFirstName(), "UpdatedFName");
    }

    @Test(priority = 4)
    public void shouldTestLogInUser() throws IOException {
        //Arrange

        //Act
        CreateUserResponse createUserResponse = given().spec(Utils.requestSpecificationBuilder())
                .body(testDataBuild.createLoginPayload(email, password))
                .when().post(APIResources.LogInUserAPI.getResource())
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

        //Act
        Response response = given().spec(Utils.requestSpecificationBuilder())
                .header("Authorization", "Bearer " + loginToken)
                .when().post(APIResources.LogOutUserAPI.getResource())
                .then().extract().response();
        //Assert
        Assert.assertEquals(response.statusCode(),200);
    }

}
