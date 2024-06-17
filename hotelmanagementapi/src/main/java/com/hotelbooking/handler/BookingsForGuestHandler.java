package com.hotelbooking.handler;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.hotelbooking.model.Booking;
import com.hotelbooking.service.BookingManager;
import com.hotelbooking.utility.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles HTTP requests to retrieve bookings for a specific guest.
 */
public class BookingsForGuestHandler implements HttpHandler {

	private static final Logger logger = Logger.getLogger(BookingsForGuestHandler.class.getName());
	private final BookingManager bookingManager;

	public BookingsForGuestHandler(BookingManager bookingManager) {
		this.bookingManager = bookingManager;
	}

	/**
	 * Handles GET requests to retrieve bookings for a specific guest. Responds with
	 * a list of bookings formatted as strings.
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
				// Extract guestName parameter from query string
				String query = exchange.getRequestURI().getQuery();
				String guestName = Utils.getValue(query.split("&"), "guestName");

				// Retrieve bookings for the guest from BookingManager
				List<Booking> bookings = bookingManager.findBookingsByGuest(guestName);

				// Format bookings as strings and join them with newline
				String response = bookings.stream().map(
						b -> String.format("%s booked room %d on %s", b.getGuestName(), b.getRoomNumber(), b.getDate()))
						.collect(Collectors.joining("\n"));

				// Send HTTP response with the list of bookings
				Utils.sendResponse(exchange, 200, response);
			} else {
				// Respond with 405 Method Not Allowed if request method is not GET
				Utils.sendResponse(exchange, 405, "Method Not Allowed");
			}
		} catch (Exception e) {
			// Log and respond with 500 Internal Server Error for any unexpected exceptions
			logger.log(Level.SEVERE, "Error handling find bookings by guest request", e);
			Utils.sendResponse(exchange, 500, "Internal Server Error");
		}
	}
}
