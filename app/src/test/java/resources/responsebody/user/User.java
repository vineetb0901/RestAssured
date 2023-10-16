package resources.responsebody.user;

import lombok.Getter;

@Getter
public class User {
    private String _id;
    private String firstName;
    private String lastName;
    private String email;
    private String __v;

    public String get_id() {
        return _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String get__v() {
        return __v;
    }
}
