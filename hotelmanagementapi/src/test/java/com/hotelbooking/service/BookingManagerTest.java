package com.hotelbooking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hotelbooking.model.Booking;

public class BookingManagerTest {
    private BookingManager bookingManager;

    @BeforeEach
    public void setUp() {
        bookingManager = new BookingManager(10);
    }

    @Test
    public void testStoreBooking() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-14");
        boolean result = bookingManager.storeBooking("John Doe", 1, date);
        assertTrue(result, "Booking should be stored successfully");

        // Attempt to book the same room on the same date
        boolean duplicateResult = bookingManager.storeBooking("Jane Doe", 1, date);
        assertFalse(duplicateResult, "Booking should not be stored, room already booked");
    }

    @Test
    public void testFindAvailableRooms() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-14");
        bookingManager.storeBooking("John Doe", 1, date);
        bookingManager.storeBooking("Jane Doe", 2, date);

        List<Integer> availableRooms = bookingManager.findAvailableRooms(date);
        assertFalse(availableRooms.contains(1), "Room 1 should not be available");
        assertFalse(availableRooms.contains(2), "Room 2 should not be available");
        assertEquals(8, availableRooms.size(), "There should be 8 available rooms");
    }

    @Test
    public void testFindBookingsByGuest() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-14");
        bookingManager.storeBooking("John Doe", 1, date);
        bookingManager.storeBooking("John Doe", 2, date);

        List<Booking> bookings = bookingManager.findBookingsByGuest("John Doe");
        assertEquals(2, bookings.size(), "John Doe should have 2 bookings");
    }
}

