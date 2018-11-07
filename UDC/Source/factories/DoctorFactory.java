package factories;

import models.Doctor;
import models.Users;

public class DoctorFactory implements UserFactory
{
	public Users createUser(int userID, String name, String username) 
	{
		return new Doctor(userID, 0, name, username);
	}
	
	public Doctor createDoctor(int userID, int doctorID, String name, String username)
	{
		Doctor doctor = (Doctor) createUser(userID, name, username);
		doctor.setDoctorID(doctorID);
		
		return doctor; 
	}

}
