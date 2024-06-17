package com.hotelbooking.handler;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hotelbooking.service.BookingManager;
import com.hotelbooking.utility.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles HTTP requests to store a booking for a guest in a hotel room.
 */
public class BookingHandler implements HttpHandler {
	private static final Logger logger = Logger.getLogger(BookingHandler.class.getName());

	private final BookingManager bookingManager;

	public BookingHandler(BookingManager bookingManager) {
		this.bookingManager = bookingManager;
	}

	/**
	 * Handles POST requests to store a booking for a guest in a hotel room.
	 * Responds with success or failure message.
	 *
	 * @param exchange The HttpExchange object representing the HTTP request and
	 *                 response.
	 * @throws IOException If an I/O error occurs during handling of the HTTP
	 *                     request.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			if ("POST".equals(exchange.getRequestMethod())) {
				// Read request body to get booking parameters
				String query = new String(exchange.getRequestBody().readAllBytes());
				String[] params = query.split("&");

				// Extract parameters: guestName, roomNumber, date
				String guestName = Utils.getValue(params, "guestName");
				int roomNumber = Integer.parseInt(Utils.getValue(params, "roomNumber"));
				Date date = Utils.parseDate(Utils.getValue(params, "date"));

				// Store booking and generate response
				boolean success = bookingManager.storeBooking(guestName, roomNumber, date);
				String response = success
						? "Room Booked successfully for " + guestName + " with room number:" + roomNumber
						: "Room is already booked";

				// Send HTTP response with appropriate status code
				Utils.sendResponse(exchange, success ? 200 : 400, response);
			} else {
				// Respond with 405 Method Not Allowed if request method is not POST
				Utils.sendResponse(exchange, 405, "Method Not Allowed");
			}
		} catch (Exception e) {
			// Log and respond with 500 Internal Server Error for any unexpected exceptions
			logger.log(Level.SEVERE, "Error handling store booking request", e);
			Utils.sendResponse(exchange, 500, "Internal Server Error");
		}
	}
}
