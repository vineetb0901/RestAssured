package resources.responsebody.contacts;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetContactListResponse {
    private List<AddContactResponse> contactList;
}
