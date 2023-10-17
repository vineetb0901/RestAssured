package resources.responsebody.user;


public class CreateUserResponse {
   private User user;

   private String token;
   public String getToken() {
      return token;
   }

   public User getUser() {
      return user;
   }

}
