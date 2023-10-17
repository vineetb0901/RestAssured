package resources;

import lombok.Getter;


public enum APIResources {
    CreateUserAPI("/users"),
    GetUserProfileAPI("/users/me"),
    UpdateUserAPI("/users/me"),
    LogOutUserAPI("/users/logout"),
    LogInUserAPI("/users/login"),
    DeleteUserAPI("/users/me"),
    AddContactAPI("/contacts"),
    GetContactList("/contacts"),
    GetContact("/contacts/"),
    UpdateContact("/contacts/"),
    DeleteContact("/contacts/");


    private final String resource;

    APIResources(String resource) {
        this.resource = resource;
    }
    public String getResource() {
        return resource;
    }
}
