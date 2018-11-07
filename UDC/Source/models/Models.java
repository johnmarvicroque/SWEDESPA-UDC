package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import factories.ClientFactory;
import factories.DoctorFactory;
import factories.ReservedSlotFactory;
import factories.SecretaryFactory;
import factories.SlotFactory;
import factories.TimeSlotFactory;
import factories.UserFactory;
import parsers.DataParsers;
import parsers.DatabaseParser;
import views.Hub;

public class Models 
{
	private Hub main;
	private DataParsers parser;
	private UserFactory userFactory;
	private SlotFactory slotFactory;
	
	/***********************************
	 * Class Startup:
	 */
	public Models()
	{
		parser = new DatabaseParser();
	}
	
	public void setHub(Hub main)
	{
		this.main = main;
	}
	
	/***********************************
	 * User Related:
	 */
	public Users getUser(String username, String password)
	{
		/*
		 * - Create a user based on the username and password.
		 * - It is already assumed that the username and password is
		 *   in the database.
		 */
		
		String temp[];
		
		temp = parser.getUser(username, password).split(",");
		
		switch(temp[0].toUpperCase())
		{
			case "DOCTOR": 
				userFactory = new DoctorFactory();
				Doctor doctor = (Doctor) userFactory.createUser(Integer.parseInt(temp[3]), temp[1], temp[2]);
				doctor.setDoctorID(Integer.parseInt(temp[4]));
				return doctor;
			case "CLIENT": 
				userFactory = new ClientFactory();
				Client client = (Client) userFactory.createUser(Integer.parseInt(temp[3]), temp[1], temp[2]);
				client.setClientID(Integer.parseInt(temp[4]));
				return client;				
			case "SECRETARY": 
				userFactory = new SecretaryFactory();
				Secretary secretary = (Secretary) userFactory.createUser(Integer.parseInt(temp[3]), temp[1], temp[2]);
				secretary.setSecretaryID(Integer.parseInt(temp[4]));
				return secretary;
		}
		
		return null;
	}
	
	public Users getUser(int userID)
	{
		/*
		 * - Create a user based on a userID.
		 * - It is already assumed that the userID is in the database.
		 */
		
		String temp[];
		
		temp = parser.getUser(userID).split(",");
		
		switch(temp[0].toUpperCase())
		{
			case "DOCTOR": 
				userFactory = new DoctorFactory();
				Doctor doctor = (Doctor) userFactory.createUser(userID, temp[1], temp[2]);
				doctor.setDoctorID(Integer.parseInt(temp[4]));
				return doctor;
			case "CLIENT": 
				userFactory = new ClientFactory();
				Client client = (Client) userFactory.createUser(userID, temp[1], temp[2]);
				client.setClientID(Integer.parseInt(temp[4]));
				return client;				
			case "SECRETARY": 
				userFactory = new SecretaryFactory();
				Secretary secretary = (Secretary) userFactory.createUser(userID, temp[1], temp[2]);
				secretary.setSecretaryID(Integer.parseInt(temp[4]));
		}
		
		return null;
	}
	
	public Client getClient(int clientID)
	{
		String temp[];
		userFactory = new ClientFactory();
		temp = parser.getClient(clientID).split(",");
		
		
		Client client = (Client) userFactory.createUser(Integer.parseInt(temp[3]), temp[1], temp[2]);
		client.setClientID(clientID);
		
		return client;
	}
	
	public Doctor getDoctor(int doctorID)
	{
		String temp[];
		userFactory = new DoctorFactory();
		temp = parser.getDoctor(doctorID).split(",");
		
		Doctor doctor = (Doctor) userFactory.createUser(Integer.parseInt(temp[3]), temp[1], temp[2]);
		doctor.setDoctorID(doctorID);
		
		return doctor;
	}
	
	public ArrayList<Doctor> getDoctors()
	{
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		ArrayList<String> data;
		userFactory = new DoctorFactory();
		
		data = parser.getDoctors();
		
		for(int i = 0; i < data.size(); i++)
		{
			String temp[] = data.get(i).split(",");
			
			Doctor doctor = (Doctor) userFactory.createUser(Integer.parseInt(temp[3]), temp[1], temp[2]);
			doctor.setDoctorID(Integer.parseInt(temp[3]));
			doctors.add(doctor);
		}
		
		return doctors;
	}
	
	public boolean checkUser(String username, String password)
	{
		/*
		 * - Searches a user based on the username and password.
		 * - Returns true if the user is in the database, otherwise false.
		 */
		
		String data = null;
		
		data = parser.getUser(username, password);
		if(data != null)
		{
			return true;
		}
		
		return false;
	}
	
	/***********************************
	 * Slot Related:
	 */
	public boolean checkTimeSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date)
	{
		/*
		 * - Searches the database based on a start and end time at a specific date.
		 * - Compares if it overlaps/conflicts another time slot.
		 * - Returns true if there are conflicts, otherwise false.
		 */
		
		ArrayList<Slots> list;
		LocalTime start;
		LocalTime end;
		
		
		list = getTimeSlots(date);
		
		for(int i = 0; i < list.size(); i++)
		{
			start = list.get(i).getTimeStart();
			end = list.get(i).getTimeEnd();
			
			if(start.equals(timeStart) || end.equals(timeEnd))
				return true;
			else if(start.isBefore(timeStart) && end.isAfter(timeStart))
				return true;
			else if(start.isBefore(timeEnd) && end.isAfter(timeEnd))
				return true;
			else if(start.isBefore(timeStart) && end.equals(timeEnd))
				return true;
			else if(start.equals(timeStart) && end.isAfter(timeEnd))
				return true;
		}
		
		return false;
	}
	
	public boolean checkTimeSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, TimeSlot slot)
	{
		/*
		 * - Searches the database based on a start and end time at a specific date.
		 * - Compares if it overlaps/conflicts another time slot.
		 * - Returns true if there are conflicts, otherwise false.
		 */
		
		ArrayList<Slots> list;
		LocalTime start;
		LocalTime end;
		
		
		list = getTimeSlots(date, slot.getDoctor());
		
		for(int i = 0; i < list.size(); i++)
		{
			start = list.get(i).getTimeStart();
			end = list.get(i).getTimeEnd();
			
			if(!(start.equals(slot.getTimeStart()) && end.equals(slot.getTimeEnd()) && slot.getDate().equals(date))) 
			{
				if(start.equals(timeStart) || end.equals(timeEnd))
				{
					System.out.println("1"); 
					return true;
				}
				else if(start.isBefore(timeStart) && end.isAfter(timeStart))
				{
					System.out.println("2"); 
					return true;
				}
				else if(start.isBefore(timeEnd) && end.isAfter(timeEnd))
				{
					System.out.println("3"); 
					return true;
				}
				else if(start.isBefore(timeStart) && end.equals(timeEnd))
				{
					System.out.println("4"); 
					return true;
				}
				else if(start.equals(timeStart) && end.isAfter(timeEnd))
				{
					System.out.println("5"); 
					return true;
				}
			}
			else
			{
				System.out.println(start + " " + end);
				if(start.equals(timeStart) || end.equals(timeEnd))
				{
					System.out.println("6"); 
					return true;
				}
				else if(start.isBefore(timeStart) && end.isAfter(timeStart))
				{
					System.out.println("7"); 
					return true;
				}
				else if(start.isBefore(timeEnd) && end.isAfter(timeEnd))
				{
					System.out.println("8"); 
					return true;
				}
				else if(start.isBefore(timeStart) && end.equals(timeEnd))
				{
					System.out.println("9"); 
					return true;
				}
				else if(start.equals(timeStart) && end.isAfter(timeEnd))
				{
					System.out.println("10"); 
					return true;
				}
			}
		}
		
		return false;
	}
	
	public ArrayList<Slots> getTimeSlots(LocalDate date, Doctor doctor)
	{
		/*
		 * - Creates a compilation of time slots.
		 * - Searches the database using a specific date and a specific doctor.
		 * - Returns the list if entries are found.
		 */
		
		ArrayList<Slots> list = new ArrayList<Slots>();
		ArrayList<Slots> reserved = getReservedSlots(date, doctor);
		ArrayList<String> data;
		slotFactory = new TimeSlotFactory();
		LocalTime timeStart;
		LocalTime timeEnd;
		TimeSlot slotA;
		TimeSlot slotB;
		TimeSlot slotC;
		
		data = parser.getTimeSlots(doctor, date);
		for(int i = 0; i < data.size(); i++)
		{
			String temp[] = data.get(i).split(",");
			
			slotA = (TimeSlot) slotFactory.createSlot(LocalTime.parse(temp[0]), LocalTime.parse(temp[1]), date);
			slotA.setDoctor(doctor);
			
			list.add(slotA);
		}
		
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = 0; j < reserved.size(); j++)
			{
				slotA = (TimeSlot) list.get(i);
				
				if(slotA.getTimeStart().equals(reserved.get(j).getTimeStart()) && slotA.getTimeEnd().equals(reserved.get(j).getTimeEnd()))
					slotA.setReserved(true);
				else if(slotA.getTimeStart().equals(reserved.get(j).getTimeStart()) && slotA.getTimeEnd().isAfter(reserved.get(j).getTimeEnd()))
				{
					//TS=RS && RE<TE
					timeEnd = slotA.getTimeEnd();
					
					slotA.setTimeEnd(reserved.get(j).getTimeEnd());
					slotA.setReserved(true);
					
					slotB = (TimeSlot) slotFactory.createSlot(slotA.getTimeEnd(), timeEnd, date);
					slotB.setDoctor(doctor);
					
					list.add(slotB);
				}
				else if(slotA.getTimeStart().isBefore(reserved.get(j).getTimeStart()) && slotA.getTimeEnd().equals(reserved.get(j).getTimeEnd()))
				{
					//TS<RS && RE=TE
					timeStart = slotA.getTimeStart();
					
					slotA.setReserved(true);
					slotA.setTimeStart(reserved.get(j).getTimeStart());
					
					slotB = (TimeSlot) slotFactory.createSlot(timeStart, slotA.getTimeStart(), date);
					slotB.setDoctor(doctor);
					
					list.add(slotB);
				}
				else if(slotA.getTimeStart().isBefore(reserved.get(j).getTimeStart()) && slotA.getTimeEnd().isAfter(reserved.get(j).getTimeEnd()))
				{
					//TS<RS && RE<TE
					timeEnd = slotA.getTimeEnd();
					timeStart = slotA.getTimeStart();
					
					slotA.setReserved(true);
					slotA.setTimeStart(reserved.get(j).getTimeStart());
					slotA.setTimeEnd(reserved.get(j).getTimeEnd());
					
					slotB = (TimeSlot) slotFactory.createSlot(timeStart, reserved.get(j).getTimeStart(), date);
					slotB.setDoctor(doctor);
					
					list.add(slotB);
					
					slotC = (TimeSlot) slotFactory.createSlot(reserved.get(j).getTimeEnd(), timeEnd, date);
					slotC.setDoctor(doctor);
					
					list.add(slotC);
				}
			}
		}
		
		return list;
	}
	
	public ArrayList<Slots> getTimeSlots(LocalDate date)
	{
		/*
		 * - Creates a compilation of time slots.
		 * - Searches the database using a specific date.
		 * - Returns the list if entries are found.
		 */
		
		ArrayList<Slots> list = new ArrayList<Slots>();
		ArrayList<String> data;
		slotFactory = new TimeSlotFactory();
		userFactory = new DoctorFactory();
		
		data = parser.getTimeSlots(date);
		for(int i = 0; i < data.size(); i++)
		{
			String temp[] = data.get(i).split(",");
			String temp2[] = parser.getDoctor(Integer.parseInt(temp[3])).split(",");
			
			Doctor doctor = (Doctor) userFactory.createUser(Integer.parseInt(temp2[3]), temp2[1], temp2[2]);
			doctor.setDoctorID(Integer.parseInt(temp2[3]));
			
			TimeSlot slot = (TimeSlot) slotFactory.createSlot(LocalTime.parse(temp[0]), LocalTime.parse(temp[1]), date);
			slot.setDoctor(doctor);
			list.add(slot);
		}
		
		return list;
	}
	
	public ArrayList<Slots> getTimeSlots()
	{
		/*
		 * - Creates a compilation of time slots.
		 * - Gets all of the time slots in the database.
		 * - Returns the list if entries are found.
		 */
		
		ArrayList<Slots> list = new ArrayList<Slots>();
		ArrayList<String> data;
		slotFactory = new TimeSlotFactory();
		userFactory = new DoctorFactory();
		
		data = parser.getAllTimeSlots();
		
		for(int i = 0; i < data.size(); i++)
		{
			String temp[] = data.get(i).split(",");
			String temp2[] = parser.getDoctor(Integer.parseInt(temp[3])).split(",");
			
			Doctor doctor = (Doctor) userFactory.createUser(Integer.parseInt(temp2[3]), temp2[1], temp2[2]);
			doctor.setDoctorID(Integer.parseInt(temp2[3]));
			
			TimeSlot slot = (TimeSlot) slotFactory.createSlot(LocalTime.parse(temp[0]), LocalTime.parse(temp[1]), LocalDate.parse(temp[2]));
			slot.setDoctor(doctor);
			list.add(slot);
		}
		
		return list;
	}
	
	public ArrayList<Slots> getReservedSlots(LocalDate date, Doctor doctor)
	{
		/* 
		 * - Creates a compilation of reserved slots.
		 * - Searches the database using a specific date and a specific doctor.
		 * - Returns the list if entries are found.
		 */
		
		ArrayList<Slots> list = new ArrayList<Slots>();
		ArrayList<String> data;
		slotFactory = new ReservedSlotFactory();
		userFactory = new ClientFactory();
		
		//@return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
		data = parser.getReservations(doctor, date);
		
		for(int i = 0; i < data.size(); i++)
		{
			String temp[] = data.get(i).split(",");
			
			System.out.println(temp[3]);
			Client client = getClient(Integer.parseInt(temp[3]));
			
			ReservedSlot slot = (ReservedSlot) slotFactory.createSlot(LocalTime.parse(temp[0]), LocalTime.parse(temp[1]), LocalDate.parse(temp[2]));
			slot.setDoctor(doctor);
			slot.setClient(client);
			list.add(slot);
		}
		
		return list;
		//return null;
	}
	
	public ArrayList<Slots> getReservedSlots(LocalDate date, Client client)
	{
		/* TODO
		 * - Creates a compilation of reserved slots.
		 * - Searches the database using a specific date and a specific client.
		 * - Returns the list if entries are found.
		 */
		
		
		
		
		return null;
	}
	
	public ArrayList<Slots> getReservedSlots()
	{
		/* TODO
		 * - Creates a compilation of reserved slots.
		 * - Gets all of the reserved slots in the database.
		 * - Returns the list if entries are found.
		 */
		
		ArrayList<Slots> list = new ArrayList<Slots>();
		ArrayList<String> data;
		slotFactory = new ReservedSlotFactory();
		
		data = parser.getAllReservedSlots();
		
		for(int i = 0; i < data.size(); i++)
		{
			String temp[] = data.get(i).split(",");
			
			Doctor doctor = getDoctor(Integer.parseInt(temp[4]));
			Client client = getClient(Integer.parseInt(temp[3]));
					
			ReservedSlot slot = (ReservedSlot) slotFactory.createSlot(LocalTime.parse(temp[0]), LocalTime.parse(temp[1]), LocalDate.parse(temp[2]));
			slot.setDoctor(doctor);
			slot.setClient(client);
			list.add(slot);
		}
		
		//TODO
		
		return list;
	}
	
	public void addTimeSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, Users user)
	{
		/*
		 * - Saves a new time slot entry into the database.
		 * - Creates a time slot object first.
		 * - Pass the object to the data parser.
		 * - Save the object in the database.
		 */
		
		
		TimeSlot timeSlot =  (TimeSlot) slotFactory.createSlot(timeStart, timeEnd, date);
		timeSlot.setDoctor((Doctor) user);
		
		this.parser.saveTimeSlot(timeSlot);
		this.updateAll();
	}
	
	public void updateTimeSlot(TimeSlot slot, LocalTime timeStart, LocalTime timeEnd, LocalDate date)
	{
		/* TODO
		 * - Updates the time slot entry in the database.
		 * - Data parser will automatically split them.
		 * - Save the object in the database.
		 */
		
		parser.modifyTimeSlot(slot,  date,  timeStart, timeEnd);
	}
	
	public void updateTimeSlot(TimeSlot slot, LocalDate date)
	{
		parser.modifyTimeSlot(slot, date);
	}
	
	public void removeTimeSlot(TimeSlot slot)
	{
		parser.removeTimeSlot(slot);
	}
	
	/***********************************
	 * Class Methods:
	 */
	public void updateAll()
	{
		main.updateAll();
	}
	
	
}
