package resources.requestbody.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserPayload {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
