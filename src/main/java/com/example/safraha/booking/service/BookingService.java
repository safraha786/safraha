package com.example.safraha.booking.service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.safraha.booking.entity.Booking;
import com.example.safraha.booking.repository.BookingRepository;
import com.example.safraha.hotel.entity.Hotel;
import com.example.safraha.hotel.repository.HotelRepository;
import com.example.safraha.membership.entity.Membership;
import com.example.safraha.membership.entity.NightWallet;
import com.example.safraha.membership.repository.MembershipRepository;
import com.example.safraha.region.entity.Region;
import com.example.safraha.user.entity.User;
import com.example.safraha.user.repository.UserRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          HotelRepository hotelRepository,
                          MembershipRepository membershipRepository,
                          UserRepository userRepository) {

        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createBooking(Long userId,
                              Long hotelId,
                              LocalDate checkIn,
                              LocalDate checkOut) {

        // 1️⃣ Validate date range
        if (!checkIn.isBefore(checkOut)) {
            throw new RuntimeException("Invalid date range");
        }

        // 2️⃣ Validate weekdays
        validateWeekdays(checkIn, checkOut);

        // 3️⃣ Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 4️⃣ Fetch hotel
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // 5️⃣ Get all active memberships of user
        List<Membership> activeMemberships =
                membershipRepository.findByUser_IdAndActiveTrue(userId);

        if (activeMemberships.isEmpty()) {
            throw new RuntimeException("No active memberships found");
        }

        // 6️⃣ Match membership region with hotel's regions
        Membership matchedMembership = findMatchingMembership(activeMemberships, hotel);

        // 7️⃣ Calculate nights
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        // 8️⃣ Validate wallet
        NightWallet wallet = matchedMembership.getWallet();

        if (wallet.getRemainingNights() < nights) {
            throw new RuntimeException("Not enough nights available");
        }

        // 9️⃣ TODO: Inventory validation will go here

        // 🔟 Deduct wallet
        wallet.setUsedNights(wallet.getUsedNights() + (int) nights);
        wallet.setRemainingNights(wallet.getRemainingNights() - (int) nights);

        // 1️⃣1️⃣ Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setMembership(matchedMembership);
        booking.setHotel(hotel);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        booking.setTotalNights((int) nights);
        booking.setStatus("CONFIRMED");
        booking.setCreatedAt(LocalDateTime.now());

        bookingRepository.save(booking);
    }

    private Membership findMatchingMembership(List<Membership> memberships, Hotel hotel) {

        Set<Region> hotelRegions = hotel.getRegions();

        return memberships.stream()
                .filter(m ->
                        hotelRegions.stream()
                                .anyMatch(r ->
                                        r.getId().equals(m.getRegionId())
                                )
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "No active membership valid for this hotel"));
    }

    private void validateWeekdays(LocalDate checkIn, LocalDate checkOut) {

        LocalDate date = checkIn;

        while (date.isBefore(checkOut)) {

            DayOfWeek day = date.getDayOfWeek();

            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                throw new RuntimeException(
                        "Bookings allowed only on weekdays");
            }

            date = date.plusDays(1);
        }
    }
}


