package factories;

import java.time.LocalDate;
import java.time.LocalTime;

import models.Slots;

public interface SlotFactory 
{
	public Slots createSlot(LocalTime timeStart, LocalTime timeEnd, LocalDate date);
}
