package com.learning.airline.core.service.service;

import com.learning.common.enums.AirlineStatus;
import com.learning.common.payload.request.AirlineRequest;
import com.learning.common.payload.response.AirlineDropdownItem;
import com.learning.common.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {
    AirlineResponse createAirline(AirlineRequest request, Long ownerId) throws Exception;
    AirlineResponse getAirlineByOwner(Long ownerId) throws Exception;
    AirlineResponse getAirlineById(Long airlineId) throws Exception;
    Page<AirlineResponse> getAllAirlines(Pageable pageable);
    AirlineResponse updateAirline(Long airlineId, AirlineRequest request, Long ownerId) throws Exception;
    void deleteAirline(Long airlineId, Long ownerId) throws Exception;
    AirlineResponse changeStatus(Long airlineId, AirlineStatus status) throws Exception;
    List<AirlineDropdownItem> getAllAirlineDropdownItems();
}
