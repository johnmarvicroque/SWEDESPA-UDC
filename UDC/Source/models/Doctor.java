package models;

public class Doctor extends Users
{
	private int doctorID;
	
	public Doctor(int userID, int doctorID, String name, String username)
	{
		this.userID = userID;
		this.doctorID = doctorID;
		this.name = name;
		this.username = username;
		this.userType = getClass().toString().split("\\.")[1];
	}
	
	public void setDoctorID(int doctorID)
	{
		this.doctorID = doctorID;
	}
	
	public int getDoctorID()
	{
		return doctorID;
	}
}
