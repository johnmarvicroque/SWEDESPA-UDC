package parsers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Client;
import models.Doctor;
import models.ReservedSlot;
import models.TimeSlot;

public abstract class DataParsers 
{
	/***********************************
	 * User Readers:
	 */
	//@return string = "usertype,name,username,userid,(type)id"
	public abstract String getUser(String username, String password);
	
	//@return string = "usertype,name,username,userid,(type)id"
	public abstract String getUser(int userID);
	
	//@return string = "usertype,name,username,userid,(type)id"
	public abstract String getDoctor(int doctorID);
	
	//@return string = "usertype,name,username,userid,(type)id"
	public abstract String getClient(int clientID);
	
	//@return string = "usertype,name,username,userid,(type)id"
	public abstract String getSecretary(int secretaryID);
	
	//@return ArrayList of string = "usertype,name,username,userid,(type)id"
	public abstract ArrayList<String> getDoctors();
	
	//@return ArrayList of string = "usertype,name,userid,(type)id"
	public abstract ArrayList<String> getTemporaryClients();
	
	/***********************************
	 * Slot Readers:
	 */
	//@return ArrayList of string = "timeStart,timeEnd,date,doctorID"
	public abstract ArrayList<String> getTimeSlots(Doctor doctor);
	
	//@return ArrayList of string = "timeStart,timeEnd,date,doctorID"
	public abstract ArrayList<String> getTimeSlots(LocalDate date);
	
	//@return ArrayList of string = "timeStart,timeEnd,date,doctorID"
	public abstract ArrayList<String> getTimeSlots(Doctor doctor, LocalDate date);
	
	//@return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	public abstract ArrayList<String> getReservations(Client client);
	
	//@return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	public abstract ArrayList<String> getReservations(LocalDate date);
	
	//@return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	public abstract ArrayList<String> getReservations(Doctor doctor);
	
	// @return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	public abstract ArrayList<String> getReservations(Client client, LocalDate date);
	
	//@return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	public abstract ArrayList<String> getReservations(Doctor doctor, LocalDate date);
	
	//@return ArrayList of string = "timeStart,timeEnd,date,clientID,doctorID"
	public abstract ArrayList<String> getAllTimeSlots();
	
	//@return ArrayList of string = "timeStart,timeEnd,date,doctorID"
	public abstract ArrayList<String> getAllReservedSlots();
	
	/***********************************
	 * User Writers:
	 */
	public abstract void saveTemporaryClient(Client client);
	
	/***********************************
	 * Slot Writers:
	 */
	public abstract void saveTimeSlot(TimeSlot slot);
	public abstract void saveReservation(ReservedSlot slot);
	
	/***********************************
	 * Slot Modifiers:
	 */
	public abstract void modifyTimeSlot(TimeSlot slot, LocalTime newTimeStart, LocalTime newTimeEnd);
	public abstract void modifyTimeSlot(TimeSlot slot, LocalDate newDate);
	public abstract void modifyTimeSlot(TimeSlot slot, LocalDate newDate, LocalTime newTimeStart, LocalTime newTimeEnd);
	public abstract void removeTimeSlot(TimeSlot slot);
	public abstract void removeReservedSlot(ReservedSlot slot);
}
