package com.hotelbooking.model;

import java.util.Date;

/**
 * Represents a booking made by a guest for a hotel room.
 */
public class Booking {
	private String guestName;
	private int roomNumber;
	private Date date; // Use java.util.Date if compatibility with legacy code is needed

	/**
	 * Constructs a Booking object with guest name, room number, and date
	 * (LocalDate).
	 *
	 * @param guestName  The name of the guest making the booking.
	 * @param roomNumber The number of the room booked.
	 * @param date       The date of the booking (LocalDate).
	 */
	public Booking(String guestName, int roomNumber, Date date) {
		this.guestName = guestName;
		this.roomNumber = roomNumber;
		this.date = date;
	}

	// Getters and Setters

	/**
	 * Returns the name of the guest.
	 *
	 * @return The guest's name.
	 */
	public String getGuestName() {
		return guestName;
	}

	/**
	 * Sets the name of the guest.
	 *
	 * @param guestName The guest's name to set.
	 */
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	/**
	 * Returns the room number.
	 *
	 * @return The room number.
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Sets the room number.
	 *
	 * @param roomNumber The room number to set.
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * Returns the date of the booking (java.util.Date).
	 *
	 * @return The booking date (java.util.Date).
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date of the booking (java.util.Date).
	 *
	 * @param date1 The booking date (java.util.Date) to set.
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
