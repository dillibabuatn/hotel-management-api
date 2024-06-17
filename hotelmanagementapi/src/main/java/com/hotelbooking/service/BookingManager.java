package com.hotelbooking.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.hotelbooking.model.Booking;

/**
 * Manages hotel room bookings, ensuring no double bookings and providing
 * functionalities to store bookings, find available rooms, and retrieve bookings by guest.
 */
public class BookingManager {
    private final int totalRooms;
    private final Map<Date, Set<Integer>> bookingsByDate; // Map to store room bookings by date
    private final Map<String, List<Booking>> bookingsByGuest; // Map to store bookings by guest name

    /**
     * Constructs a BookingManager instance with a specified total number of rooms.
     *
     * @param totalRooms The total number of rooms in the hotel.
     */
    public BookingManager(int totalRooms) {
        this.totalRooms = totalRooms;
        this.bookingsByDate = new ConcurrentHashMap<>(); // Concurrent map for thread safety
        this.bookingsByGuest = new ConcurrentHashMap<>(); // Concurrent map for thread safety
    }

    /**
     * Stores a booking for a guest on a specified date.
     *
     * @param guestName  The name of the guest making the booking.
     * @param roomNumber The room number to be booked.
     * @param date       The date of the booking.
     * @return true if the booking was successfully stored, false if the room is already booked.
     */
	public synchronized boolean storeBooking(String guestName, int roomNumber, Date date) {
		validateRoomNumber(roomNumber);

		// Ensure thread-safe access to bookingsByDate
		bookingsByDate.putIfAbsent(date, Collections.synchronizedSet(new HashSet<>()));

		Set<Integer> bookedRooms = bookingsByDate.get(date);
		if (bookedRooms.contains(roomNumber)) {
			return false; // Room is already booked for the specified date
		} else {
			bookedRooms.add(roomNumber); // Mark the room as booked
			Booking booking = new Booking(guestName, roomNumber, date);

			// Ensure thread-safe access to bookingsByGuest
			bookingsByGuest.putIfAbsent(guestName, new CopyOnWriteArrayList<>());
			bookingsByGuest.get(guestName).add(booking); // Add booking to guest's list

			return true; // Booking successfully stored
		}
	}

    /**
     * Finds and returns a list of room numbers that are available on the specified date.
     *
     * @param date The date for which available rooms are to be found.
     * @return A list of available room numbers.
     */
    public List<Integer> findAvailableRooms(Date date) {
       Set<Integer> bookedRooms = bookingsByDate.getOrDefault(date, Collections.emptySet());
        List<Integer> availableRooms = new ArrayList<>();
        for (int i = 1; i <= totalRooms; i++) {
            if (!bookedRooms.contains(i)) {
                availableRooms.add(i);
            }
        }
        return availableRooms;
    }

    /**
     * Finds and returns a list of bookings made by a specific guest.
     *`
     * @param guestName The name of the guest for whom bookings are to be retrieved.
     * @return A list of bookings made by the guest.
     */
    public List<Booking> findBookingsByGuest(String guestName) {
        return new ArrayList<>(bookingsByGuest.getOrDefault(guestName, Collections.emptyList()));
    }

    /**
     * Validates if the provided room number is within valid range (1 to totalRooms).
     *
     * @param roomNumber The room number to validate.
     * @throws IllegalArgumentException if the room number is invalid.
     */
    private void validateRoomNumber(int roomNumber) {
        if (roomNumber < 1 || roomNumber > totalRooms) {
            throw new IllegalArgumentException("Invalid room number");
        }
    }
}
