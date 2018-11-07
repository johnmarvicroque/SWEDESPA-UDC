package factories;

import models.Secretary;
import models.Users;

public class SecretaryFactory implements UserFactory
{
	public Users createUser(int userID, String name, String username) 
	{
		return new Secretary(userID, 0, name, username);
	}
	
	public Secretary createSecretary(int userID, int secretaryID, String name, String username)
	{
		Secretary secretary = (Secretary) createUser(userID, name, username);
		secretary.setSecretaryID(secretaryID);
		
		return secretary;
	}
}
