package resources.requestbody.contacts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmailAndPhoneNumberPayload {
    private String email;
    private String phone;
}
