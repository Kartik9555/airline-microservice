package com.learning.ancillary.service.service;

import com.learning.common.payload.request.AncillaryRequest;
import com.learning.common.payload.response.AncillaryResponse;

import java.util.List;

public interface AncillaryService {
    AncillaryResponse getById(Long id) throws Exception;
    List<AncillaryResponse> getByAirlineId(Long userId) throws Exception;
    AncillaryResponse createAncillary(Long userId, AncillaryRequest request);
    AncillaryResponse updateAncillary(Long id, AncillaryRequest request) throws Exception;
    void deleteAncillary(Long id) throws Exception;
}
