package factories;

import java.time.LocalDate;
import java.time.LocalTime;

import models.Doctor;
import models.Slots;
import models.TimeSlot;

public class TimeSlotFactory implements SlotFactory
{
	public Slots createSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date)
	{
		return new TimeSlot(timeStart, timeEnd, date, null, false);
	}
	
	public TimeSlot createTimeSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, Doctor doctor, boolean isReserved)
	{
		TimeSlot slot = (TimeSlot) createSlot(timeStart, timeEnd, date);
		slot.setDoctor(doctor);
		slot.setReserved(isReserved);
		
		return slot;
	}
}
