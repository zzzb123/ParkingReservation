package server;

public interface Lot {
	TimeSlot getTimeSlot(String timekey);
	void cullSlots(String currentTime);
}
