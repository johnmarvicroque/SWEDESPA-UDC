package models;

public class Client extends Users
{
	private int clientID;
	private boolean isTemp;
	
	public Client(int userID, int clientID, String name, String username, boolean isTemp)
	{
		this.userID = userID;
		this.clientID = clientID;
		this.name = name;
		this.username = username;
		this.userType = getClass().toString().split("\\.")[1];
		this.isTemp = isTemp;
	}
	
	public void setClientID(int clientID)
	{
		this.clientID = clientID;
	}
	
	public void setTemporary(boolean isTemp)
	{
		this.isTemp = isTemp;
	}
	
	public int getClientID()
	{
		return clientID;
	}
	
	public boolean isTemporary()
	{
		return isTemp;
	}
}
