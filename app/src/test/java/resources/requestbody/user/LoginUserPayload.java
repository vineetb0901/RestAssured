package resources.requestbody.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserPayload {
    private String email;
    private String password;
}
