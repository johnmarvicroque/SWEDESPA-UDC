package factories;

import models.Client;
import models.Users;

public class ClientFactory implements UserFactory
{
	public Users createUser(int userID, String name, String username) 
	{
		return new Client(userID, 0, name, username, false);
	}
	
	public Client createClient(int userID, int clientID, String name, String username, boolean isTemp)
	{
		Client client = (Client) createUser(userID, name, username);
		client.setClientID(clientID);
		client.setTemporary(isTemp);
		
		return client; 
	}
}
