package resources.responsebody.user;


import lombok.Getter;

@Getter
public class CreateUserResponse {
   private User user;
   private String token;

}
