package com.example.Apesech.Hotel.Restaurant.service.interfaces;

import com.example.Apesech.Hotel.Restaurant.dta.Response;
import com.example.Apesech.Hotel.Restaurant.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}
