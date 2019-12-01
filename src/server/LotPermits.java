package server;

import java.util.concurrent.ConcurrentHashMap;

public class LotPermits {
	public ConcurrentHashMap<String,Lot> lots = new ConcurrentHashMap<>();
}
