package com.mani.theatre.beans;

public class Seat {

	private String id;
	private int screenId;
	private boolean availablity;
	private int seatId;

	public Seat(String id, int screenId, boolean availablity, int seatId) {
		super();
		this.setId(id);
		this.screenId = screenId;
		this.availablity = availablity;
		this.seatId = seatId;
	}

	public Seat() {

	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public boolean isAvailable() {
		return availablity;
	}

	public void setAvailablity(boolean availablity) {
		this.availablity = availablity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	@Override
	public String toString()
	{
		if(isAvailable()) return "[ ] "+id;
		else
			return "[ X ]"+id;
	}
}