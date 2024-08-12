package com.example.Apesech.Hotel.Restaurant.service.interfaces;

import com.example.Apesech.Hotel.Restaurant.dta.LoginRequest;
import com.example.Apesech.Hotel.Restaurant.dta.Response;
import com.example.Apesech.Hotel.Restaurant.entity.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
}
