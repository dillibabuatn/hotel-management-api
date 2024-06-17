package com.hotelbooking.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import com.hotelbooking.handler.AvailableRoomsHandler;
import com.hotelbooking.handler.BookingHandler;
import com.hotelbooking.handler.BookingsForGuestHandler;
import com.hotelbooking.service.BookingManager;
import com.sun.net.httpserver.HttpServer;

public class HotelBookingManagerController {
	private static final int PORT = 8080;
	private static final Logger logger = Logger.getLogger(HotelBookingManagerController.class.getName());
	private static final BookingManager bookingManager = new BookingManager(10);

	/**
	 * Main method to start the HTTP server for hotel booking management.
	 */
	public static void main(String[] args) {
		try {
			// Create a new HTTP server instance
			HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

			// Context mappings for different endpoints
			server.createContext("/bookingRoom", new BookingHandler(bookingManager));
			server.createContext("/findAvailablerooms", new AvailableRoomsHandler(bookingManager));
			server.createContext("/findBookingByGuest", new BookingsForGuestHandler(bookingManager));

			// Use default executor
			server.setExecutor(null);

			// Start the server
			server.start();
			logger.info("Server started on port " + PORT);
		} catch (IOException e) {
			// Handle server startup errors
			e.printStackTrace();
		}
	}
}
