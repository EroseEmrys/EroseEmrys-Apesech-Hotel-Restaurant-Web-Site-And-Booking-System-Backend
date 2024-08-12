package com.example.Apesech.Hotel.Restaurant.service.impl;

import com.example.Apesech.Hotel.Restaurant.dta.BookingDTO;
import com.example.Apesech.Hotel.Restaurant.dta.Response;
import com.example.Apesech.Hotel.Restaurant.entity.Booking;
import com.example.Apesech.Hotel.Restaurant.entity.Room;
import com.example.Apesech.Hotel.Restaurant.entity.User;
import com.example.Apesech.Hotel.Restaurant.exception.OurException;
import com.example.Apesech.Hotel.Restaurant.repo.BookingRepository;
import com.example.Apesech.Hotel.Restaurant.repo.RoomRepository;
import com.example.Apesech.Hotel.Restaurant.repo.UserRepository;
import com.example.Apesech.Hotel.Restaurant.service.interfaces.IBookingService;
import com.example.Apesech.Hotel.Restaurant.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response = new Response();

        try {
            // Validate date range
            if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
                throw new IllegalArgumentException("Check-in date must come before check-out date");
            }

            // Find room and user
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new OurException("Room Not Found"));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new OurException("User Not Found"));

            // Check room availability
            List<Booking> existingBookings = room.getBookings();
            if (!roomIsAvailable(bookingRequest, existingBookings)) {
                throw new OurException("Room not available for selected date range");
            }

            // Calculate and validate total number of guests
            int totalNumOfGuest = bookingRequest.getNumOfAdults() + bookingRequest.getNumOfChildren();
            if (totalNumOfGuest < 1) {
                throw new IllegalArgumentException("Total number of guests must be at least 1");
            }
            bookingRequest.setTotalNumOfGuest(totalNumOfGuest);

            // Log values before saving
            System.out.println("Check-in date: " + bookingRequest.getCheckInDate());
            System.out.println("Check-out date: " + bookingRequest.getCheckOutDate());
            System.out.println("Number of adults: " + bookingRequest.getNumOfAdults());
            System.out.println("Number of children: " + bookingRequest.getNumOfChildren());
            System.out.println("Total number of guests: " + bookingRequest.getTotalNumOfGuest());
            System.out.println("Booking confirmation code: " + bookingRequest.getBookingConfirmationCode());

            // Set booking details
            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);

            // Save booking
            bookingRepository.save(bookingRequest);

            // Set response
            response.setStatusCode(200);
            response.setMessage("Booking successful");
            response.setBookingConfirmationCode(bookingConfirmationCode);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving booking: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new OurException("Booking Not Found"));
            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, true);
            response.setStatusCode(200);
            response.setMessage("Booking found successfully");
            response.setBooking(bookingDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error finding booking: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
            response.setStatusCode(200);
            response.setMessage("Bookings retrieved successfully");
            response.setBookingList(bookingDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving bookings: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new OurException("Booking Does Not Exist"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Booking cancelled successfully");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error cancelling booking: " + e.getMessage());
        }
        return response;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()) &&
                                bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate()))
                );
    }
}
