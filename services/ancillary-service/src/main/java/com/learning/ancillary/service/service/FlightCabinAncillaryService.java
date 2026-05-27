package com.learning.ancillary.service.service;

import com.learning.common.enums.AncillaryType;
import com.learning.common.payload.request.FlightCabinAncillaryRequest;
import com.learning.common.payload.response.FlightCabinAncillaryResponse;

import java.util.List;

public interface FlightCabinAncillaryService {
    FlightCabinAncillaryResponse getFlightCabinAncillaryById(Long id) throws Exception;
    List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flight, Long CabinClassId);
    List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids);
    List<FlightCabinAncillaryResponse> getAllByFlightIdAndCabinClassIdAndAncillaryType(Long flightId, Long cabinClassId, AncillaryType ancillaryType);
    FlightCabinAncillaryResponse getByFlightIdAndCabinClassIdAndAncillaryType(Long flightId, Long cabinClassId, AncillaryType ancillaryType) throws Exception;
    FlightCabinAncillaryResponse createFlightCabinAncillary(FlightCabinAncillaryRequest request) throws Exception;
    FlightCabinAncillaryResponse updateFlightCabinAncillary(Long id, FlightCabinAncillaryRequest request) throws Exception;
    void deleteFlightCabinAncillary(Long id) throws Exception;
    Double calculateAncillaryPrice(List<Long> ids);
}
