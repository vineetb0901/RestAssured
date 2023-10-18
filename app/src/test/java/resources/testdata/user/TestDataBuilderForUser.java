package resources.testdata.user;

import resources.requestbody.user.CreateUserPayload;
import resources.requestbody.user.LoginUserPayload;

public class TestDataBuilderForUser {
    public CreateUserPayload createUserPayload(String firstName, String lastName, String email, String password) {
        CreateUserPayload createUserPayload = new CreateUserPayload();
        createUserPayload.setFirstName(firstName);
        createUserPayload.setLastName(lastName);
        createUserPayload.setEmail(email);
        createUserPayload.setPassword(password);
        return createUserPayload;
    }

    public LoginUserPayload createLoginPayload(String email, String password){
        LoginUserPayload loginUserPayload = new LoginUserPayload();
        loginUserPayload.setEmail(email);
        loginUserPayload.setPassword(password);
        return loginUserPayload;
    }
}
