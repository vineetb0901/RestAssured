package contactlist_apiautomation_assignment.users;

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
    TestDataBuild testDataBuild = new TestDataBuild();
    String email = Utils.generateEmail();

    @Test(priority = 1)
    public void shouldTestCreateUser() throws IOException {
        //Arrange
        //Act
        CreateUserResponse createUserResponse = given().spec(Utils.requestSpecificationBuilder())
                .body(testDataBuild.createUserPayload("Baganna", "K", email, "bag#321"))
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
   
}
