package server;

import java.io.Serializable;
import java.util.ArrayList;
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

	public synchronized void registerUserNormal(User u, String  time) throws NoOpenSpotsException{
		UnmappedTimeSlot t = (UnmappedTimeSlot)timeSlots.get(time);
		ArrayList<User> registrations = t.getNormalRegistrations();
		if(registrations.size() >= normalSpots)
			throw new NoOpenSpotsException();
		registrations.add(u);
	}
	public synchronized void registerUserHandicap(User u, String  time) throws NoOpenSpotsException{
		UnmappedTimeSlot t = (UnmappedTimeSlot)timeSlots.get(time);
		ArrayList<User> registrations = t.getHandicapRegistrations();
		if(registrations.size() >= normalSpots)
			throw new NoOpenSpotsException();
		registrations.add(u);
	}
}
