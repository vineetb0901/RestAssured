package resources.responsebody.contacts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddContactResponse {
    private String _id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String email;
    private String phone;
    private String street1;
    private String street2;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
    private String owner;
    private int __v;
}
