package com.learning.seat.service.service.impl;

import com.learning.common.enums.CabinClassType;
import com.learning.common.payload.request.CabinClassRequest;
import com.learning.common.payload.response.CabinClassResponse;
import com.learning.seat.service.mapper.CabinClassMapper;
import com.learning.seat.service.model.CabinClass;
import com.learning.seat.service.repository.CabinClassRepository;
import com.learning.seat.service.service.CabinClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CabinClassServiceImpl implements CabinClassService {

    private final CabinClassRepository cabinClassRepository;

    @Override
    public CabinClassResponse getCabinClassById(Long id) throws Exception {
        final CabinClass cabinClass = cabinClassRepository.findById(id)
                .orElseThrow(() -> new Exception("Cabin Class not found for id " + id));
        return CabinClassMapper.toCabinClass(cabinClass);
    }

    @Override
    public List<CabinClassResponse> getCabinClassByAircraftId(Long aircraftId) throws Exception {
        return cabinClassRepository.getByAircraftId(aircraftId)
                .stream()
                .map(CabinClassMapper::toCabinClass)
                .toList();
    }

    @Override
    public CabinClassResponse getByAircraftIdAndCabinClass(Long aircraftId, CabinClassType name) throws Exception {
        final CabinClass cabinClass = cabinClassRepository.findByAircraftIdAndName(aircraftId, name)
                .orElseThrow(() -> new Exception("Cabin Class not found for Aircraft id " + aircraftId));
        return CabinClassMapper.toCabinClass(cabinClass);
    }

    @Override
    public CabinClassResponse createCabinClass(CabinClassRequest request) throws Exception {
        if(cabinClassRepository.existsByAircraftIdAndCode(request.getAircraftId(), request.getCode())) {
            throw new Exception("Cabin Class already exists");
        }
        final CabinClass cabinClass = CabinClassMapper.toCabinClass(request);
        return CabinClassMapper.toCabinClass(cabinClassRepository.save(cabinClass));
    }

    @Override
    public CabinClassResponse updateCabinClass(Long id, CabinClassRequest request) throws Exception {
        final CabinClass cabinClass = cabinClassRepository.findById(id)
                .orElseThrow(() -> new Exception("Cabin Class not found for id " + id));
        if(cabinClassRepository.existsByAircraftIdAndCodeAndIdNot(request.getAircraftId(), request.getCode(), cabinClass.getId())) {
            throw new Exception("Cabin Class with the code " + request.getCode() + " already exists");
        }
        CabinClassMapper.toCabinClass(request, cabinClass);
        return CabinClassMapper.toCabinClass(cabinClassRepository.save(cabinClass));
    }

    @Override
    public void deleteCabinClassById(Long id) throws Exception {
        final CabinClass cabinClass = cabinClassRepository.findById(id)
                .orElseThrow(() -> new Exception("Cabin Class not found for id " + id));
        cabinClassRepository.delete(cabinClass);
    }
}
