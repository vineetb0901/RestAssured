package contactlist_apiautomation_assignment.users;

import org.testng.Assert;
import org.testng.annotations.Test;
import resources.APIResources;
import resources.Utils;
import resources.responsebody.user.CreateUserResponse;
import resources.testdata.user.TestDataBuild;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestCreateUser {
   @Test
   public void shouldTestCreateUser() throws IOException {
       //Arrange
      TestDataBuild testDataBuild = new TestDataBuild();

      //Act
      CreateUserResponse createUserResponse = given().spec(Utils.requestSpecificationBuilder())
              .body(testDataBuild.createUserPayload("Baganna","K","baganna1278@test.com","bag#321"))
              .when().post(APIResources.CreateUserAPI.getResource())
              .then().spec(Utils.responseSpecificationBuilder())
              .extract().response().as(CreateUserResponse.class);
      String token = createUserResponse.getToken();
      System.out.println(token);
      //Assert
      Assert.assertEquals(createUserResponse.getUser().getFirstName(),"Baganna");
   }
}
