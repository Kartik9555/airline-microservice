package com.learning.booking.service.repository;

import com.learning.booking.service.model.Booking;
import com.learning.common.enums.BookingStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUserId(Long userId);
    long countByFlightInstanceId(Long flightInstanceId);

    @Query("""
                select b from Booking b left join b.passengers p
                where b.airlineId = :airlineId
                    and (:search is null or LOWER(b.bookingReference) like LOWER(CONCAT('%', :search, '%'))
                        or LOWER(p.firstName) like LOWER(CONCAT('%', :search, '%'))
                        or LOWER(p.lastName) like LOWER(CONCAT('%', :search, '%'))
                        or LOWER(p.email) like LOWER(CONCAT('%', :search, '%'))
                        or LOWER(b.contactInfo.email) like LOWER(CONCAT('%', :search, '%'))
                        or LOWER(b.contactInfo.phone) like LOWER(CONCAT('%', :search, '%'))
                    )
                    and (:status is null or b.status = :status)
                    and (:flightInstanceId is null or b.flightInstanceId = :flightInstanceId)
            """)
    List<Booking> findByAirlineWithFilter(@Param("airlineId") Long airlineId,
                                          @Param("search") String search,
                                          @Param("status") BookingStatus status,
                                          @Param("flightInstanceId") Long flightInstanceId,
                                          Sort sort);

    boolean existsByBookingReference(String bookingReference);
}
