package factories;

import models.Users;

public interface UserFactory 
{
	public Users createUser(int userID, String name, String username);
}
