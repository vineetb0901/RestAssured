package resources.testdata.user;

import resources.requestbody.user.CreateUserPayload;

public class TestDataBuild {
    public CreateUserPayload createUserPayload(String firstName, String lastName, String email, String password) {
        CreateUserPayload createUserPayload = new CreateUserPayload();
        createUserPayload.setFirstName(firstName);
        createUserPayload.setLastName(lastName);
        createUserPayload.setEmail(email);
        createUserPayload.setPassword(password);
        return createUserPayload;
    }
}
