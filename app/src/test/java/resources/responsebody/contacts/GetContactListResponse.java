package resources.responsebody.contacts;

import lombok.Getter;
import resources.requestbody.contacts.AddContactPayload;

import java.util.List;

@Getter
public class GetContactListResponse {
    private List<AddContactResponse> contactList;
}
