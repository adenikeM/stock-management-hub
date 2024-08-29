package com.springboot.stockmanagementhub.service;

import com.springboot.stockmanagementhub.model.Location;
import com.springboot.stockmanagementhub.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }

    public Page<Location> getAllLocation2(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return locationRepository.findAll(pageable);
    }
    public Page<Location> getAllLocation3(Pageable pageable){
        return locationRepository.findAll(pageable);
    }
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Optional<Location> updateLocation(Location location) {
        locationRepository.findById(location.getId());
        if (location.getId() == null) {
            throw new IllegalArgumentException("Location  id must not be null");
        }
        return Optional.of(locationRepository.save(location));
    }

    public void deleteLocation(Long id) {
        locationRepository.findById(id);
    }

}
