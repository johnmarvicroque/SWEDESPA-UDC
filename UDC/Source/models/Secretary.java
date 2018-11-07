package models;

public class Secretary extends Users
{
	private int secretaryID;
	
	public Secretary(int userID, int secretaryID, String name, String username)
	{
		this.userID = userID;
		this.secretaryID = secretaryID;
		this.name = name;
		this.username = username;
		this.userType = getClass().toString().split("\\.")[1];
	}
	
	public void setSecretaryID(int secretaryID)
	{
		this.secretaryID = secretaryID;
	}
	
	public int getSecretaryID()
	{
		return secretaryID;
	}
}
