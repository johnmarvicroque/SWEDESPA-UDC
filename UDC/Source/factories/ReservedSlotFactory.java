package factories;

import java.time.LocalDate;
import java.time.LocalTime;

import models.Client;
import models.Doctor;
import models.ReservedSlot;
import models.Slots;

public class ReservedSlotFactory implements SlotFactory
{
	public Slots createSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date)
	{
		return new ReservedSlot(timeStart, timeEnd, date, null, null);
	}
	
	public ReservedSlot createReservedSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date, Client client, Doctor doctor)
	{
		ReservedSlot slot = (ReservedSlot) createSlot(timeStart, timeEnd, date);
		slot.setClient(client);
		slot.setDoctor(doctor);
		
		return slot;
	}
}
