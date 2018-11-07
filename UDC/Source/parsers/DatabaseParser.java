package parsers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Client;
import models.Doctor;
import models.ReservedSlot;
import models.TimeSlot;

public class DatabaseParser extends DataParsers
{
	/***********************************
	 * Database connection
	 */
	private static Connection getConnection() throws Exception 
	{
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/udc?autoReconnect=true&useSSL=false";
			String username = "root";
			String password = "4654";
			Class.forName(driver);
				
			Connection connection = DriverManager.getConnection(url,username,password);
			return connection;
		} 
		catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	/***********************************
	 * User Readers:
	 */
	/*
	 ***********************************
	 * @return string = "usertype,name,username,userid,(type)id"
	 ***********************************
	 */
	@Override
	public String getUser(String username, String password) 
	{
		/* 
		 * - Receives a user-name and password.
		 * - Searches the database using the data received
		 *   to match a user.
		 * - If the specific user is found, return the string of data
		 *   of that specific user from the database.
		 * - If no specific user was found, return null for nothing.
		 */
		 String userType = null;
		 int userID = 0;
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT user_Id, userType FROM users "
													+ "WHERE username = ? AND password = ?");
			statement.setString(1, username);
			statement.setString(2, password);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next())
			{
				userType = result.getString("userType");
				userID = result.getInt("user_Id");
				statement = connection.prepareStatement("SELECT * FROM " + userType + " WHERE Users_user_Id = ?");
				statement.setInt(1, userID);
				
				result = statement.executeQuery();
				if(result.next())
				{
					String Name = result.getString("Name");
					int id = result.getInt(userType.toLowerCase() + "_Id");
					connection.close();
					return userType + "," + Name + "," + username +"," + userID + "," + id;
				}
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 ***********************************
	 * @return string = "usertype,name,username,userid,(type)id"
	 ***********************************
	 */
	public String getUser(int userID)
	{
		/* 
		 * - Receives a user's ID.
		 * - Searches the database using the data received
		 *   to match a user.
		 * - If the specific user is found, return the string of data
		 *   of that specific user from the database.
		 * - If no specific user was found, return null for nothing.
		 */
		String userType = null;
		String username = null;
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM users WHERE user_Id = ?");
			statement.setInt(1, userID);
			
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				userType = result.getString("userType");
				username = result.getString("username");
				
				statement = connection.prepareStatement("SELECT * FROM " + userType + " WHERE Users_user_Id = ?");
				statement.setInt(1, userID);
				
				result = statement.executeQuery();
				if(result.next())
				{
					String Name = result.getString("Name");
					int id = result.getInt(userType.toLowerCase() + "_Id");
					connection.close();
					return userType + "," + Name + "," + username +"," + userID + "," + id;
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 ***********************************
	 * @return string = "usertype,name,username,userid,(type)id"
	 ***********************************
	 */
	public String getDoctor(int doctorID)
	{
		/* 
		 * - Receives a doctor's ID.
		 * - Searches the database using the data received
		 *   to match a user.
		 * - If the specific user is found, return the string of data
		 *   of that specific user from the database.
		 * - If no specific user was found, return null for nothing.
		 */
		String Name = null;
		int userID = 0;
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM doctor WHERE doctor_Id = ?");
			statement.setInt(1, doctorID);
			
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				Name = result.getString("Name");
				userID = result.getInt("Users_user_Id");
				
				statement = connection.prepareStatement("SELECT * FROM users WHERE user_Id = ?");               
				statement.setInt(1, userID);
				
				result = statement.executeQuery();
				if(result.next())
				{
					String userType = result.getString("userType");
					String username = result.getString("username");
					connection.close();
					return userType + "," + Name + "," + username +"," + userID + "," + doctorID;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 ***********************************
	 * @return string = "usertype,name,username,userid,(type)id"
	 ***********************************
	 */
	public String getClient(int clientID)
	{
		/* 
		 * - Receives a client's ID.
		 * - Searches the database using the data received
		 *   to match a user.
		 * - If the specific user is found, return the string of data
		 *   of that specific user from the database.
		 * - If no specific user was found, return null for nothing.
		 */
		String Name = null;
		int userID = 0;
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM client "
													+ "WHERE client_Id = ?");
			statement.setInt(1, clientID);
			
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				Name = result.getString("Name");
				userID = result.getInt("Users_user_Id");
				
				statement = connection.prepareStatement("SELECT * FROM users" 
									                     + " WHERE user_Id = ?");
				statement.setInt(1, userID);
				
				result = statement.executeQuery();
				if(result.next())
				{
					String userType = result.getString("userType");
					String username = result.getString("username");
					connection.close();
					return userType + "," + Name + "," + username +"," + userID + "," + clientID;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 ***********************************
	 * @return string = "usertype,name,username,userid,(type)id"
	 ***********************************
	 */
	public String getSecretary(int secretaryID)
	{
		/* 
		 * - Receives a secretary's ID
		 * - Searches the database using the data received
		 *   to match a user.
		 * - If the specific user is found, return the string of data
		 *   of that specific user from the database.
		 * - If no specific user was found, return null for nothing.
		 */
		String Name = null;
		String userType = null;
		String username = null;
		int userID = 0;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM secretary "
													+ "WHERE secretary_Id = ?");
			statement.setInt(1, secretaryID);
			
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				Name = result.getString("Name");
				userID = result.getInt("Users_user_Id");
				
				statement = connection.prepareStatement("SELECT * FROM users WHERE user_Id = ?");		                   
				statement.setInt(1, userID);
				
				result = statement.executeQuery();
				if(result.next())
				{
					userType = result.getString("userType");
					username = result.getString("username");
					connection.close();
					return userType + "," + Name + "," + username +"," + userID + "," + secretaryID;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * ***********************************
	 * @return ArrayList of string = "usertype,name,username,userid,(type)id"
	 * ***********************************
	 */
	@Override
	public ArrayList<String> getDoctors() 
	{
		/* 
		 * - Searches the database of existing users that are doctors.
		 * - Returns a collection of strings that contain data from the database.
		 * - Each string contains data for a specific doctor.
		 */
		ArrayList<String> list = new ArrayList<String>();
		String name = null;
		int doctorID = 0;
		int userID = 0;
		String username = null;
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM doctor d INNER JOIN users u on d.Users_user_Id = u.user_Id");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{			
				doctorID = result.getInt("doctor_Id");
				name = result.getString("Name");
				userID = result.getInt("Users_user_Id");
				username = result.getString("username");
				
				list.add("doctor," + name + "," + username + "," + userID + "," + doctorID);
			}
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * ***********************************
	 * @return ArrayList of string = "usertype,name,userid,(type)id"
	 * ***********************************
	 */
	@Override
	public ArrayList<String> getTemporaryClients() 
	{
		/* 
		 * - Searches the database of existing users that are clients that are temporary.
		 * - Returns a collection of strings that contain data from the database.
		 * - Each string contains data for a specific temporary client.
		 */
		ArrayList<String> list = new ArrayList<String>();
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM client WHERE isTemp = 1");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{	
				int clientID = result.getInt("client_Id");
				String name = result.getString("Name");
				int userID = result.getInt("Users_user_Id");
				
				list.add("client," + name + "," + userID + "," + clientID);
			}
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/***********************************
	 * Slot Readers:
	 */
	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,doctorID"
	 * ***********************************
	 */
	@Override
	public ArrayList<String> getTimeSlots(Doctor doctor) 
	{
		/* 
		 * - Receives a doctor object.
		 * - Searches the database of time slots made by a specific doctor.
		 * - The database contains 30 minute interval time slots, this method
		 *   must compress those 30 minute interval time slots into a time slot
		 *   whose time start and time end are time ranges. 
		 * - Returns a collection of strings that contain data.
		 * - Each string contains data for a specific time slot.
		 */
		ArrayList<String> list = new ArrayList<String>();
		int slotID = 0;
		String timeStart = null;
		String timeEnd = null;
		String date = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			statement = connection.prepareStatement("SELECT DISTINCT app_ID FROM timeslots WHERE doctor_ID = ?");
			statement.setInt(1, doctor.getDoctorID());
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				statement = connection.prepareStatement("select * from timeslots where app_ID = ?");
				statement.setInt(1, result.getInt("app_ID"));
				ResultSet result2 = statement.executeQuery();
				slotID = 0;
				while(result2.next())
				{
					if(slotID == 0)
					{
						slotID = result2.getInt("slot_Id");
						timeStart = result2.getTime("timeStart").toString();
						timeEnd = result2.getTime("timeEnd").toString();
						date = result2.getDate("date").toString();
					}
					else if(slotID < result2.getInt("slot_ID"))
					{
						slotID = result2.getInt("slot_ID");
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result2.getTime("timeStart").toString();
					}
				}
				
				list.add(timeStart + "," + timeEnd + "," + date + "," + doctor.getDoctorID());
			}
			
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,doctorID"
	 * ***********************************
	 */
	public ArrayList<String> getTimeSlots(LocalDate date)
	{
		/* 
		 * - Receives a date.
		 * - Searches the database of time slots made by a specific doctor.
		 * - The database contains 30 minute interval time slots, this method
		 *   must compress those 30 minute interval time slots into a time slot
		 *   whose time start and time end are time ranges. 
		 * - Returns a collection of strings that contain data.
		 * - Each string contains data for a specific time slot.
		 */
		ArrayList<String> list = new ArrayList<String>();
		int slotID = 0;
		String timeStart = null;
		String timeEnd = null;
		int doctorID = 0;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			statement = connection.prepareStatement("SELECT DISTINCT app_ID FROM timeslots WHERE date = ?");
			statement.setDate(1, Date.valueOf(date));
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				statement = connection.prepareStatement("select * from timeslots where app_ID = ?");
				statement.setInt(1, result.getInt("app_ID"));
				ResultSet result2 = statement.executeQuery();
				slotID = 0;
				
				while(result2.next())
				{
					if(slotID == 0)
					{
						doctorID = result2.getInt("doctor_Id");
						slotID = result2.getInt("slot_Id");
						timeStart = result2.getTime("timeStart").toString();
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(slotID < result2.getInt("slot_ID"))
					{
						slotID = result2.getInt("slot_ID");
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result2.getTime("timeStart").toString();
					}
				}
				
				list.add(timeStart + "," + timeEnd + "," + date.toString() + "," + doctorID);
			}
			
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,doctorID"
	 * ***********************************
	 */
	@Override
	public ArrayList<String> getTimeSlots(Doctor doctor, LocalDate date) 
	{
		/* 
		 * - Receives a doctor object and a date.
		 * - Searches the database of time slots made by a specific doctor 
		 *   at a specific date.
		 * - The database contains 30 minute interval time slots, this method
		 *   must compress those 30 minute interval time slots into a time slot
		 *   whose time start and time end are time ranges. 
		 * - Returns a collection of strings that contain data.
		 * - Each string contains data for a specific time slot.
		 */

		ArrayList<String> list = new ArrayList<String>();
		int slotID = 0;
		String timeStart = null;
		String timeEnd = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			statement = connection.prepareStatement("SELECT DISTINCT app_ID FROM timeslots WHERE doctor_ID = ? AND date = ?");
			statement.setInt(1, doctor.getDoctorID());
			statement.setDate(2, Date.valueOf(date));
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				statement = connection.prepareStatement("select * from timeslots where app_ID = ?");
				statement.setInt(1, result.getInt("app_ID"));
				ResultSet result2 = statement.executeQuery();
				slotID = 0;
				while(result2.next())
				{
					if(slotID == 0)
					{
						slotID = result2.getInt("slot_Id");
						timeStart = result2.getTime("timeStart").toString();
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(LocalTime.parse(timeEnd).isBefore(LocalTime.parse(result2.getTime("timeEnd").toString())))
					{
						slotID = result2.getInt("slot_ID");
						timeEnd = result2.getTime("timeEnd").toString();
					}
					else if(LocalTime.parse(timeStart).isAfter(LocalTime.parse(result2.getTime("timeStart").toString())))
					{
						timeStart = result2.getTime("timeStart").toString();
					}
				}
				
				list.add(timeStart + "," + timeEnd + "," + date.toString() + "," + doctor.getDoctorID());
			}
			
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	 * ***********************************
	 */
	@Override
	public ArrayList<String> getReservations(Client client) 
	{
		/* 
		 * - Receives a client object.
		 * - Searches the database of reservations by a specific client.
		 * - The database contains 30 minute interval reservations, this method
		 *   must compress those 30 minute interval reservations into a reservation
		 *   whose time start and time end are time ranges. 
		 * - Returns a collection of strings that contain data.
		 * - Each string contains data for a specific time slot.
		 */
		int appID = 0;
		int slotID = 0;
		int doctorID = 0;
		String timeStart = null;
		String timeEnd = null;
		String date = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM reservations r INNER JOIN timeslots t ON r.Timeslots_slot_Id = t.slot_Id AND r.client_Id = ?");
			statement.setInt(1, client.getClientID());
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> list = new ArrayList<String>();
			
			while(result.next())
			{	
				if(appID == 0) 
				{
					appID = result.getInt("app_Id");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					date = result.getDate("date").toString();
					doctorID = result.getInt("doctor_Id");
				}
				else if(appID == result.getInt("app_ID")) 
				{
					if(slotID < result.getInt("slot_Id"))
					{
						slotID = result.getInt("slot_Id");
						timeEnd = result.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result.getTime("timeStart").toString();
					}
				}
				else {
					list.add(timeStart + "," + timeEnd + "," + date + "," + client.getClientID() + "," + doctorID);
					appID = result.getInt("app_ID");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					doctorID = result.getInt("doctor_Id");
				}
			}
			
			if(appID != 0) 
			{
				list.add(timeStart + "," + timeEnd + "," + date + "," + client.getClientID() + "," + doctorID);
			}
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	 * ***********************************
	 */
	@Override
	public ArrayList<String> getReservations(LocalDate date) 
	{
		/* 
		 * - Receives a date.
		 * - Searches the database of reservations at a specific date.
		 * - The database contains 30 minute interval reservations, this method
		 *   must compress those 30 minute interval reservations into a reservation
		 *   whose time start and time end are time ranges. 
		 * - Returns a collection of strings that contain data.
		 * - Each string contains data for a specific time slot.
		 */
		int slotID = 0;
		int doctorID = 0;
		int clientID = 0;
		String timeStart = null;
		String timeEnd = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT t.*, r.client_Id FROM reservations r INNER JOIN timeslots t ON date = ? AND r.Timeslots_slot_Id = t.slot_Id");
			statement.setDate(1, Date.valueOf(date));
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> list = new ArrayList<String>();
			
			while(result.next())
			{
				if(clientID == 0) 
				{
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					clientID = result.getInt("client_Id");
					doctorID = result.getInt("doctor_Id");	
				}
				else if(clientID == result.getInt("client_Id")) 
				{
					if(slotID < result.getInt("slot_Id"))
					{
						slotID = result.getInt("slot_Id");
						timeEnd = result.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result.getTime("timeStart").toString();
					}
				}
				else 
				{
					list.add(timeStart + "," + timeEnd + "," + date.toString() + "," + clientID + "," + doctorID);
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					clientID = result.getInt("client_Id");
					doctorID = result.getInt("doctor_Id");
				}
			}
			if(clientID != 0) 
			{
				list.add(timeStart + "," + timeEnd + "," + date.toString() + "," + clientID + "," + doctorID);
			}	
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	 * ***********************************
	 */
	@Override
	public ArrayList<String> getReservations(Doctor doctor) 
	{
		/* 
		 * - Receives a doctor object.
		 * - Searches the database of reservations with a specific doctor.
		 * - The database contains 30 minute interval reservations, this method
		 *   must compress those 30 minute interval reservations into a reservation
		 *   whose time start and time end are time ranges. 
		 * - Returns a collection of strings that contain data.
		 * - Each string contains data for a specific time slot.
		 */
		int slotID = 0;
		int clientID = 0;
		String timeStart = null;
		String timeEnd = null;
		String date = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT t.*, r.client_Id FROM reservations r INNER JOIN timeslots t ON r.Timeslots_slot_Id = t.slot_Id AND r.doctor_Id = ?");
			statement.setInt(1, doctor.getDoctorID());
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> list = new ArrayList<String>();
			
			while(result.next())
			{
				if(clientID == 0) 
				{
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					date = result.getDate("date").toString();
					clientID = result.getInt("client_Id");
				}
				else if (clientID == result.getInt("client_Id")) 
				{
					if(slotID < result.getInt("slot_Id"))
					{
						slotID = result.getInt("slot_Id");
						timeEnd = result.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result.getTime("timeStart").toString();
					}
				}
				else 
				{
					list.add(timeStart + "," + timeEnd + "," + date + "," + clientID + "," + doctor.getDoctorID());
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					date = result.getDate("date").toString();
					clientID = result.getInt("client_Id");
				}
			}
			if(clientID != 0) 
			{
				list.add(timeStart + "," + timeEnd + "," + date + "," + clientID + "," + doctor.getDoctorID());
			}
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	 * ***********************************
	 */
	@Override
	public ArrayList<String> getReservations(Client client, LocalDate date) 
	{
		/* 
		 * - Receives a client object and a date.
		 * - Searches the database of reservations by a specific client at a specific date.
		 * - The database contains 30 minute interval reservations, this method
		 *   must compress those 30 minute interval reservations into a reservation
		 *   whose time start and time end are time ranges. 
		 * - Returns a collection of strings that contain data.
		 * - Each string contains data for a specific time slot.
		 */
		int appID = 0;
		int slotID = 0;
		int doctorID = 0;
		String timeStart = null;
		String timeEnd = null; 
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT t.*, r.client_Id FROM reservations r INNER JOIN timeslots t ON r.Timeslots_slot_Id = t.slot_Id AND r.client_Id = ? AND t.date = ?");
			statement.setInt(1, client.getClientID());
			statement.setDate(2, Date.valueOf(date));
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> list = new ArrayList<String>();
			
			while(result.next())
			{
				if(appID == 0) {
					appID = result.getInt("app_Id");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					doctorID = result.getInt("doctor_Id");
				}
				else if(appID == result.getInt("app_Id")) {
					if(slotID < result.getInt("slot_Id"))
					{
						slotID = result.getInt("slot_Id");
						timeEnd = result.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result.getTime("timeStart").toString();
					}
				}
				else {
					list.add(timeStart + "," + timeEnd + "," + date.toString() + "," + client.getClientID() + "," + doctorID);
					appID = result.getInt("app_Id");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					doctorID = result.getInt("doctor_Id");
				}
			}
			
			if(appID != 0) {
				list.add(timeStart + "," + timeEnd + "," + date.toString() + "," + client.getClientID() + "," + doctorID);
			}
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	 * ***********************************
	 */
	@Override
	public ArrayList<String> getReservations(Doctor doctor, LocalDate date){
		/* 
		 * - Receives a doctor object and a date.
		 * - Searches the database of reservations with a specific doctor at a specific date.
		 * - The database contains 30 minute interval reservations, this method
		 *   must compress those 30 minute interval reservations into a reservation
		 *   whose time start and time end are time ranges. 
		 * - Returns a collection of strings that contain data.
		 * - Each string contains data for a specific time slot.
		 */
		int slotID = 0;
		int clientID = 0;
		String timeStart = null;
		String timeEnd = null; 
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT t.*, r.client_Id FROM reservations r INNER JOIN timeslots t ON r.Timeslots_slot_Id = t.slot_Id AND r.doctor_Id = ? AND t.date = ?");
			statement.setInt(1, doctor.getDoctorID());
			statement.setDate(2, Date.valueOf(date));
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> list = new ArrayList<String>();
			
			while(result.next())
			{
				if(clientID == 0) 
				{
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					clientID = result.getInt("client_Id");
				}
				else if (clientID == result.getInt("client_Id")) 
				{
					if(slotID < result.getInt("slot_Id"))
					{
						slotID = result.getInt("slot_Id");
						timeEnd = result.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result.getTime("timeStart").toString();
					}
				}
				else 
				{
					list.add(timeStart + "," + timeEnd + "," + date.toString() + "," + clientID + "," + doctor.getDoctorID());
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					clientID = result.getInt("client_Id");
				}
			}
			
			if(clientID != 0) 
			{
				list.add(timeStart + "," + timeEnd + "," + date.toString() + "," + clientID + "," + doctor.getDoctorID());
			}
			connection.close();
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	 * ***********************************
	 */
	public ArrayList<String> getAllReservedSlots(){
		/*  TODO
		 * - Get all the reserved slots in the database.
		 * - SELECT * FROM reservations
		 */
		ArrayList<String> list = new ArrayList<String>();
		int appID = 0;
		int slotID = 0;
		int clientID = 0;
		int doctorID = 0;
		String timeStart = null;
		String timeEnd = null; 
		String date = null;
		
		try 
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT t.*, r.client_Id FROM reservations r INNER JOIN timeslots t ON r.Timeslots_slot_Id = t.slot_Id");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				if(appID == 0) 
				{
					appID = result.getInt("app_Id");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					date = result.getDate("date").toString();
					clientID = result.getInt("client_Id");
					doctorID = result.getInt("doctor_Id");
				}
				else if (appID == result.getInt("app_ID")) 
				{
					if(slotID < result.getInt("slot_Id") && clientID == result.getInt("client_Id"))
					{
						slotID = result.getInt("slot_Id");
						timeEnd = result.getTime("timeEnd").toString();
					}
					else
					{
						list.add(timeStart + "," + timeEnd + "," + date + "," + clientID + "," + doctorID);
						slotID = result.getInt("slot_Id");
						timeStart = result.getTime("timeStart").toString();
						timeEnd = result.getTime("timeEnd").toString();
						date = result.getDate("date").toString();
						clientID = result.getInt("client_Id");
						doctorID = result.getInt("doctor_Id");
					}
				}	
				else 
				{
					list.add(timeStart + "," + timeEnd + "," + date + "," + clientID + "," + doctorID);
					appID = result.getInt("app_Id");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					date = result.getDate("date").toString();
					clientID = result.getInt("client_Id");
					doctorID = result.getInt("doctor_Id");
				}
			}
			
			if(appID != 0)
			{
				list.add(timeStart + "," + timeEnd + "," + date + "," + clientID + "," + doctorID);
			}
			connection.close();
			return list;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * ***********************************
	 * @return ArrayList of string = "timeStart,timeEnd,date,doctorID"
	 * ***********************************
	 */
	public ArrayList<String> getAllTimeSlots()
	{
		/* 
		 * - Get all the time slots in the database.
		 * - SELECT * FROM timeslots
		 */
		
		ArrayList<String> list = new ArrayList<String>();
		int appID = 0;
		int slotID = 0;
		int doctorID = 0;
		String timeStart = null;
		String timeEnd = null; 
		String date = null;
		
		try
		{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM timeslots");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				if(appID == 0) 
				{
					appID = result.getInt("app_Id");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					date = result.getDate("date").toString();
					doctorID = result.getInt("doctor_Id");
				}
				else if (appID == result.getInt("app_Id")) 
				{
					if(slotID < result.getInt("slot_Id"))
					{
						slotID = result.getInt("slot_Id");
						timeEnd = result.getTime("timeEnd").toString();
					}
					else
					{
						timeStart = result.getTime("timeStart").toString();
					}
				}
				else 
				{
					list.add(timeStart + "," + timeEnd + "," + date + "," + doctorID);
					appID = result.getInt("app_Id");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					date = result.getDate("date").toString();
					doctorID = result.getInt("doctor_Id");
				}
			}
			
			
			if(appID != 0) 
			{
				list.add(timeStart + "," + timeEnd + "," + date + "," + doctorID);
			}
			connection.close();
			return list;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/*
	 * 
	 * Get Free Slots for Specific doctor
	 * 
	 */
	public ArrayList<String> getFreeSlots(Doctor doctor) 
	{
		int appID = 0;
		int slotID = 0;
		String timeStart = null;
		String timeEnd = null;
		String date = null;
		
		try{
			Connection connection = getConnection();
			PreparedStatement statement;
			
			statement = connection.prepareStatement("SELECT * FROM timeslots t LEFT JOIN reservations r ON t.slot_Id = r.Timeslots_slot_Id WHERE reserve_Id IS NULL AND t.doctor_Id = ?");
			statement.setInt(1, doctor.getDoctorID()); 	
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> list = new ArrayList<String>();
			
			while(result.next())
			{
				if(appID == 0) {
					appID = result.getInt("app_Id");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					date = result.getDate("date").toString();
				}
				
				else if (appID == result.getInt("app_Id")) {
					if(slotID+1 == result.getInt("slot_Id")) {
						slotID = result.getInt("slot_Id");
						timeEnd = result.getTime("timeEnd").toString();
					}
					else {
						list.add(timeStart + "," + timeEnd + "," + date + "," + doctor.getDoctorID());
						slotID = result.getInt("slot_Id");
						timeStart = result.getTime("timeStart").toString();
						timeEnd = result.getTime("timeEnd").toString();
						date = result.getDate("date").toString();
					}
				}
				else {
					list.add(timeStart + "," + timeEnd + "," + date + "," + doctor.getDoctorID());
					appID = result.getInt("app_Id");
					slotID = result.getInt("slot_Id");
					timeStart = result.getTime("timeStart").toString();
					timeEnd = result.getTime("timeEnd").toString();
					date = result.getDate("date").toString();
				}
			}
			if(appID != 0) {
				list.add(timeStart + "," + timeEnd + "," + date + "," + doctor.getDoctorID());
			}	
			connection.close();
			return list;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/***********************************
	 * User Writers:
	 */
	@Override
	public void saveTemporaryClient(Client client) 
	{
		/*
		 * - Receives a client object.
		 * - It is assumed that the client object is not yet an entry.
		 * - Create a special ID for the client.
		 * - Saves into the database.
		 */
		
		//Creating client_Id
		int ctr = 1;
		try 
		{
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM client");
			ResultSet result = statement.executeQuery();

			while(result.next())
			{
				 ctr = result.getInt(1);
			}
			ctr++;
			
			statement = connection.prepareStatement("INSERT INTO client (client_Id, Name, isTemp, Users_user_Id) " + "VALUES(?,?,?,?)");
			statement.setInt(1,ctr);
			statement.setString(2, client.getName());
			statement.setBoolean(3, true);
			statement.setInt(4, getSecretaryID());
			statement.executeUpdate();
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/***********************************
	 * Slot Writers:
	 */
	@Override
	public void saveTimeSlot(TimeSlot slot) 
	{
		/*
		 * - Receives a time slot object.
		 * - It is assumed that the time slot object has no conflicts.
		 * - Create a special ID for the time slot.
		 * - The time slot is divided into 30 minute interval entries.
		 * - Saves into the database.
		 */
		
		int appointment = 0;
		int ctr = 1;
		
		//TimeSlot ID creation
		try 
		{
			Connection connection = getConnection();
			PreparedStatement statement;
					
			statement = connection.prepareStatement("SELECT * FROM timeslots");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				ctr++;
				
				if(result.getInt("app_Id") != appointment)
				{
					 appointment = result.getInt("app_Id");
				}
			}
			appointment++;
			
			String sql ="INSERT INTO timeslots(slot_Id, app_ID, doctor_Id, date, timeStart, timeEnd)" + "VALUES(?,?,?,?,?,?)";
			
			String[] sTime = slot.getTimeStart().toString().split(":");
			String[] eTime = slot.getTimeEnd().toString().split(":");
			int SH = Integer.parseInt(sTime[0]);
			int SM = Integer.parseInt(sTime[1]);
			int EH = Integer.parseInt(eTime[0]);
			int EM = Integer.parseInt(eTime[1]);
			
			statement = connection.prepareStatement(sql);
			do
			{
				statement.setString(1, String.valueOf(ctr));
				statement.setString(2, String.valueOf(appointment));
				statement.setString(3, String.valueOf(slot.getDoctor().getDoctorID()));
				statement.setString(4, slot.getDate().toString());
				if(SM == 30)
				{
					statement.setString(5, SH + ":" + SM + ":00");
					//statement.addBatch();
					SH = SH + 1;
					SM = 00;
					statement.setString(6, SH + ":0" + SM + ":00");
				}
				else if(SM != 30)
				{
					statement.setString(5, SH + ":0" + SM + ":00");
					//statement.addBatch();
					SM = 30;
					statement.setString(6, SH + ":" + SM + ":00");
				}
				statement.addBatch();
				ctr++;
				
			}while(SH < EH || SM < EM);
			
			statement.executeBatch();	
			connection.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void saveReservation(ReservedSlot slot) 
	{
		/*
		 * - Receives a reserved slot object.
		 * - It is assumed that the reserved slot object has no conflicts.
		 * - Create a special ID for the reserved slot.
		 * - Matches the reserved slot with the time slots.
		 * - It is assumed that the reserved slot is within a specific time slot range.
		 * - The reserved slot is divided into 30 minute interval entries.
		 * - Saves into the database.
		 */
		
		int appointment = -1;
		ArrayList<Integer> slots = new ArrayList<Integer>();
		int ctr=1;
		
		//ReservedSlotID creation
		try 
		{
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations");
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				ctr = ctr+1;
			}
			appointment = appointment +1;
			
			statement = connection.prepareStatement("SELECT * FROM timeslots");
			ResultSet subResult = statement.executeQuery();
			
			while(subResult.next())
			{
				if(subResult.getString("timeStart").equals(slot.getTimeStart().toString() + ":00") && subResult.getInt("doctor_Id") == slot.getDoctor().getDoctorID() && subResult.getString("date").equals(slot.getDate().toString()))
				{
					slots.add(subResult.getInt("slot_Id"));
				}
			}
			
			String sql ="INSERT INTO reservations(reserve_Id, doctor_Id, client_Id, Timeslots_slot_Id)" + "VALUES(?,?,?,?)";
			
			statement = connection.prepareStatement(sql);
			for(int i = 0; i<slots.size(); i++)
			{
				statement.setString(1, String.valueOf(ctr));
				statement.setString(2, String.valueOf(slot.getDoctor().getDoctorID()));
				statement.setString(3, String.valueOf(slot.getClient().getClientID()));
				statement.setString(4, String.valueOf(slots.get(i)));
				statement.addBatch();
				ctr++;
			}
			statement.executeBatch();
			connection.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/***********************************
	 * Slot Modifiers:
	 */
	@Override
	public void modifyTimeSlot(TimeSlot slot, LocalTime newTimeStart, LocalTime newTimeEnd) 
	{
		/* 
		 * - Receives a time slot object with a new time start and a new time end.
		 * - It is assumed that the time slot object has no conflicts.
		 * - Searches the database of that specific time slot.
		 * - Modify the times.
		 * - The time slot is divided into 30 minute interval entries.
		 * - Saves into the database.
		 */
		
		int App_ID=-1;
		String Date = slot.getDate().toString();
		String OStime = slot.getTimeStart().toString() + ":00";
		String OEtime = slot.getTimeEnd().toString() + ":00";
		String NStime = newTimeStart.toString() + ":00";
		String NEtime = newTimeEnd.toString() + ":00";
//		String[] AOStime = OStime.split(":");
//		String[] AOEtime = OEtime.split(":");
//		String[] ANStime = NStime.split(":");
//		String[] ANEtime = NEtime.split(":");
		
		
		boolean changeStart = false;
		boolean changeEnd = false;
		boolean changeStartDel = false;
		boolean changeEndDel = false;
		
		
		try{
			Connection conn = getConnection();
			String sql = "SELECT * FROM timeslots";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			if(slot.getTimeStart().isAfter(newTimeStart)){
				changeStart = true;
				changeStartDel = false;
				System.out.println("Add S");
			}
			else if(slot.getTimeStart().isBefore(newTimeStart)){
				changeStart = false;
				changeStartDel = true;
				System.out.println("Del S");
			}
			else{
				changeStart = false;
				changeStartDel = false;
				
			}
			
			if(slot.getTimeEnd().isAfter(newTimeEnd)){
				changeEnd = false;
				changeEndDel = true;
				System.out.println("Del E");
			}
			else if(slot.getTimeEnd().isBefore(newTimeEnd)){
				changeEnd = true;
				changeEndDel = false;
				System.out.println("Add E");
			}
			else{
				changeEnd = false;
				changeEndDel = false;
				
			}
			
			while(result.next()){
				if(result.getString("timeStart").equals(OStime)){
					App_ID = result.getInt("app_Id");
				}
			}
			System.out.println(App_ID);
			
			
			if(changeStart && changeEnd){
				//add Both Sides huhuhu
				System.out.println("Change Both");
				addModifiedSlots(NStime, OStime, App_ID, slot.getDoctor().getDoctorID(), Date);
				addModifiedSlots(OEtime, NEtime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			else if(changeStart && !changeEnd){
				System.out.println("Extend Start");
				addModifiedSlots(NStime, OStime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			else if(!changeStart && changeEnd){
				//Add at End
				System.out.println("Extend End");
				addModifiedSlots(OEtime, NEtime, App_ID, slot.getDoctor().getDoctorID(), Date);
				
			}
			
			if(changeStartDel && !changeEndDel){
				System.out.println("Shorten Start");
				System.out.println(OStime + " " + NStime);
				deleteModifiedSlots(OStime, NStime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			else if(!changeStartDel && changeEndDel){
				System.out.println("Shorten End");
				deleteModifiedSlots(NEtime, OEtime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			else if(changeStartDel && changeEndDel){
				System.out.println("Shorten Both");
				deleteModifiedSlots(OStime, NStime, App_ID, slot.getDoctor().getDoctorID(), Date);
				deleteModifiedSlots(NEtime, OEtime, App_ID, slot.getDoctor().getDoctorID(), Date);
			}
			
			if(!changeStart && !changeEnd && !changeStartDel && !changeEndDel){
				System.out.println("Change Nothing");
			}
			conn.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void addModifiedSlots(String timeStart, String timeEnd, int app_Id, int doctor_Id, String date){
		try {
			int ctr=1;
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT slot_Id FROM timeslots");
			ResultSet results = statement.executeQuery();
			while(results.next()){
				if(ctr < results.getInt("slot_Id"))
					ctr = results.getInt("slot_Id");
			}
			ctr++;
			
			String sql ="INSERT INTO timeslots(slot_Id, app_ID, doctor_Id, date, timeStart, timeEnd)" + "VALUES(?,?,?,?,?,?)";
			
			String[] sTime = timeStart.toString().split(":");
			String[] eTime = timeEnd.toString().split(":");
			int SH = Integer.parseInt(sTime[0]);
			int SM = Integer.parseInt(sTime[1]);
			int EH = Integer.parseInt(eTime[0]);
			int EM = Integer.parseInt(eTime[1]);
			
			statement = conn.prepareStatement(sql);
			do
			{
				statement.setString(1, String.valueOf(ctr));
				statement.setString(2, String.valueOf(app_Id));
				statement.setString(3, String.valueOf(doctor_Id));
				statement.setString(4, date);
				if(SM == 30)
				{
					statement.setString(5, SH + ":" + SM + ":00");
					//statement.addBatch();
					SH = SH + 1;
					SM = 00;
					statement.setString(6, SH + ":0" + SM + ":00");
				}
				else if(SM != 30)
				{
					statement.setString(5, SH + ":0" + SM + ":00");
					//statement.addBatch();
					SM = 30;
					statement.setString(6, SH + ":" + SM + ":00");
				}
				statement.addBatch();
				ctr++;
				
			}while(SH < EH || SM < EM);
			
			statement.executeBatch();	
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void deleteModifiedSlots(String timeStart, String timeEnd, int app_Id, int doctor_Id, String date){
		
		System.out.println(timeStart + " " + timeEnd);
		ArrayList<Integer> delete = new ArrayList<Integer>();
		try {
			boolean trace = false;
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM timeslots");
			ResultSet results = statement.executeQuery();
			while(results.next()){
				if((results.getString("timeEnd").equals(timeEnd)) && (results.getString("timeStart").equals(timeStart)) && !trace){
					delete.add(results.getInt("slot_Id"));
					trace = false;
					break;
				}
				else if((results.getString("timeStart").equals(timeStart)) && !trace){
					trace = true;
					delete.add(results.getInt("slot_Id"));
				}
				else if(!results.getString("timeEnd").equals(timeEnd) && trace){
					delete.add(results.getInt("slot_Id"));
				}
				else if(results.getString("timeEnd").equals(timeEnd) && trace){
					delete.add(results.getInt("slot_Id"));
					trace = false;
					break;
				}	
			}
			
			String sql = "DELETE FROM timeslots WHERE slot_Id = ?";
			statement = conn.prepareStatement(sql);
			
			
			for(int i = 0; i<delete.size(); i++)
			{
				statement.setInt(1, delete.get(i));
				statement.addBatch();
			}
			System.out.println(delete);
			statement.executeBatch();	
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void modifyTimeSlot(TimeSlot slot, LocalDate newDate) 
	{
		/* 
		 * - Receives a time slot object with a new date.
		 * - It is assumed that the time slot object has no conflicts.
		 * - Searches the database of that specific time slot.
		 * - Simply change the date of the time slot entries.
		 */
		int appID = -1;
		
		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT app_Id FROM timeslots WHERE date = ? AND timeStart =?");
			statement.setString(1, slot.getDate().toString());
			statement.setString(2, slot.getTimeStart().toString());
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				appID = result.getInt("app_Id");
			}
			
			statement = connection.prepareStatement("UPDATE timeslots SET date = ? WHERE app_Id = ?");
			statement.setString(1, newDate.toString());
			statement.setInt(2, appID);
			statement.execute();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifyTimeSlot(TimeSlot slot, LocalDate newDate, LocalTime newTimeStart, LocalTime newTimeEnd) {
		/*
		 * - Receives a time slot object with a new time start, a new time end and a new date.
		 * - It is assumed that the time slot object has no conflicts.
		 * - Searches the database of that specific time slot.
		 * - Modify the times.
		 * - Change the date.
		 * - Split the times if necessary (create new ID and change the old IDs and and save new entries).
		 * - Unaffected entries will retain the old ID.
		 * - The time slot is divided into 30 minute interval entries.
		 * - Saves into the database.
		 */
		
		this.modifyTimeSlot(slot, newDate);
		slot.setDate(newDate);
		this.modifyTimeSlot(slot, newTimeStart, newTimeEnd);
	}

	@Override
	public void removeTimeSlot(TimeSlot slot) 
	{
		/* 
		 * - Receives a time slot object.
		 * - Searches the database of that specific time slot object.
		 * - Removes the entries related to that time slot object.
		 */
		
		ArrayList<Integer> slots = new ArrayList<Integer>();
		ArrayList<Integer> change = new ArrayList<Integer>();
		int LappID = -1;
		int appslot = 0;
		
		try {
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM timeslots");
			ResultSet result = statement.executeQuery();
			
			
			boolean tof = false;
			
			while(result.next())
			{
				if(!tof && result.getString("timeStart").equals(slot.getTimeStart().toString() + ":00") 
						&& result.getInt("doctor_Id") == slot.getDoctor().getDoctorID() 
						&& result.getString("date").equals(slot.getDate().toString()))
				{
					appslot = result.getInt("app_Id");
					slots.add(result.getInt("slot_Id"));
					tof = true;
				}
				else if(tof && !result.getString("timeEnd").equals(slot.getTimeEnd().toString() + ":00") 
						&& result.getInt("app_Id") == appslot)
				{
					slots.add(result.getInt("slot_Id"));
				}
				else if(tof && result.getString("timeEnd").equals(slot.getTimeEnd().toString() + ":00") 
						&& result.getInt("app_Id") == appslot)
				{
					slots.add(result.getInt("slot_Id"));
					tof = false;
				}
				else if(result.getInt("app_Id") == appslot){
					change.add(result.getInt("slot_Id"));
				}
			}
			conn.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		try
		{
			Connection conn = getConnection();
			String sql = "DELETE FROM timeslots WHERE slot_Id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			
			for(int i = 0; i<slots.size(); i++)
			{
				statement.setInt(1, slots.get(i));
				statement.addBatch();
			}
			
			statement.executeBatch();
			conn.close();
		}  
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//Finds last appID for segmented timeslots
		try {
			Connection conn = getConnection();
			String sql = "SELECT * FROM timeslots";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			
			while(results.next()){
				if(LappID == -1){
					LappID = results.getInt("app_Id");
				}
				else if(LappID != results.getInt("app_Id")){
					LappID = results.getInt("app_Id");
				}
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			Connection conn = getConnection();
			String sql = "UPDATE timeslots SET app_Id = ? WHERE slot_Id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			LappID = LappID + 1;
			
			for(int i =0; i<change.size(); i++){
				statement.setInt(1, LappID);
				statement.setInt(2, change.get(i));
				statement.addBatch();
			}
			statement.executeBatch();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void removeReservedSlot(ReservedSlot slot) 
	{
		/* 
		 * - Receives a reserved slot object.
		 * - Searches the database of that specific reserved slot object.
		 * - Removes the entries related to that reserved slot object.
		 */
		
		ArrayList<Integer> slots = new ArrayList<Integer>();
		
		try 
		{
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM reservations r INNER JOIN timeslots t ON t.doctor_Id = r.doctor_Id AND r.Timeslots_Slot_Id = t.slot_Id");
			ResultSet result = statement.executeQuery();
			
			boolean tof = false;
			System.out.println(slot.getTimeStart().toString());
			while(result.next())
			{
				if(!tof && result.getString("timeStart").equals(slot.getTimeStart().toString() + ":00") 
					&& result.getString("date").equals(slot.getDate().toString()))
				{
					System.out.println("IN");
					slots.add(result.getInt("slot_Id"));
					tof = true;
				}
				else if(tof && !result.getString("timeEnd").equals(slot.getTimeEnd().toString()))
				{
					slots.add(result.getInt("slot_Id"));
				}
				else if(tof && result.getString("timeEnd").equals(slot.getTimeEnd().toString()))
				{
					break;
				}
				System.out.println(slots);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//Deletes
		try
		{
			Connection conn = getConnection();
			String sql = "DELETE FROM reservations WHERE Timeslots_slot_Id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			for(int i = 0; i<slots.size(); i++)
			{
				statement.setInt(1, slots.get(i));
				statement.addBatch();
			}
			statement.executeBatch();
		}  
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/***********************************
	 * Class Methods:
	 */
	/*
	 * - Used by the temp client methods.
	 */
	private int getSecretaryID()
	{
		int ctr = -1;
		try 
		{
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM users");
			ResultSet result = statement.executeQuery();

			while(result.next())
			{
				 if(result.getString("userType").equals("Secretary"))
				 {
					 ctr = result.getInt("user_Id");
					 break;
				 }
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return ctr;
	}
	
}