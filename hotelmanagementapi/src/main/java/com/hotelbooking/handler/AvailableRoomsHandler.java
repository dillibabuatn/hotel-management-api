package com.hotelbooking.handler;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.hotelbooking.service.BookingManager;
import com.hotelbooking.utility.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles HTTP requests to find available rooms based on a specified date.
 */
public class AvailableRoomsHandler implements HttpHandler {

	private static final Logger logger = Logger.getLogger(AvailableRoomsHandler.class.getName());
	private final BookingManager bookingManager;

	public AvailableRoomsHandler(BookingManager bookingManager) {
		this.bookingManager = bookingManager;
	}

	/**
	 * Handles GET requests to find available rooms for a given date. Responds with
	 * a comma-separated list of room numbers.
	 *
	 * @param exchange The HttpExchange object representing the HTTP request and
	 *                 response.
	 * @throws IOException If an I/O error occurs during handling of the HTTP
	 *                     request.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			if ("GET".equals(exchange.getRequestMethod())) {
				// Extract and parse the date parameter from the query string
				String query = exchange.getRequestURI().getQuery();
				Date date = Utils.parseDate(Utils.getValue(query.split("&"), "date"));

				// Retrieve available rooms from the booking manager
				List<Integer> availableRooms = bookingManager.findAvailableRooms(date);

				// Convert the list of room numbers to a comma-separated string
				String response = availableRooms.stream().map(String::valueOf).collect(Collectors.joining(","));

				// Send HTTP response with the list of available rooms
				Utils.sendResponse(exchange, 200, response);
			} else {
				// Respond with 405 Method Not Allowed if request method is not GET
				Utils.sendResponse(exchange, 405, "Method Not Allowed");
			}
		} catch (Exception e) {
			// Log and respond with 500 Internal Server Error for any unexpected exceptions
			logger.log(Level.SEVERE, "Error handling find available rooms request", e);
			Utils.sendResponse(exchange, 500, "Internal Server Error");
		}
	}
}
