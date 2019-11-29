package server;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

public class UnmappedLot implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int normalSpots;
	private int handicapSpots;
	private LinkedList<User> normalReservations = (LinkedList<User>) Collections.synchronizedList(new LinkedList<User>());
	private LinkedList<User> handicapReservations = (LinkedList<User>) Collections.synchronizedList(new LinkedList<User>());
	
	public UnmappedLot(String name, int normalSpots, int handicapSpots) {
		this.name = name;
		this.normalSpots = normalSpots;
		this.handicapSpots = handicapSpots;
	}
	
	public int normalSpotsOpen() {
		return normalSpots - normalReservations.size();
	}
	public int handicapSpotsOpen() {
		return handicapSpots - handicapReservations.size();
	}
}
