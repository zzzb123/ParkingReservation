package server;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class UnmappedLot implements Lot, Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int normalSpots;
	private int handicapSpots;
	private ConcurrentHashMap<String, TimeSlot> timeSlots = new ConcurrentHashMap<>();
	
	public UnmappedLot(String name, int nspots, int hspots) {
		this.name = name;
		normalSpots = nspots;
		handicapSpots = hspots;
	}
	
	@Override
	public TimeSlot getTimeSlot(String timekey) {
		if(timeSlots.contains(timekey))
			return timeSlots.get(timekey);
		TimeSlot t = new UnmappedTimeSlot();
		timeSlots.put(timekey, t);
		return t;
	}
	@Override
	public void cullSlots(String currentTime) {
		for(String s : timeSlots.keySet()) {
			if(s.compareTo(currentTime) < 0)
				timeSlots.remove(s);
		}
	}
}
