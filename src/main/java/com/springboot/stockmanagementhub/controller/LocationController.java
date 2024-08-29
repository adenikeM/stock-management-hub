package com.springboot.stockmanagementhub.controller;

import com.springboot.stockmanagementhub.model.Location;
import com.springboot.stockmanagementhub.model.dto.ErrorResponse;
import com.springboot.stockmanagementhub.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
@Controller
@Slf4j
@RequestMapping("api")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocation() {
        return ResponseEntity.ok().body(locationService.getAllLocation());
    }

    @GetMapping("/v2/locations")
    public ResponseEntity<List<Location>> getAllLocation2(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "0") int pageSize){

        Page<Location> locations = locationService.getAllLocation2(pageNo, pageSize);
        return ResponseEntity.ok(locations.getContent());
    }
    @GetMapping("/v3/locations")
    public ResponseEntity<List<Location>> getAllLocation3(Pageable pageable){
        Page<Location> locations = locationService.getAllLocation3(pageable);
        return ResponseEntity.ok(locations.getContent());
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<?> getLocationByID(@PathVariable Long id) {
        log.info("Get Location id by " + id);
        if (id < 1) {
            ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "Location id cannot be less than 1",
                            "Invalid ID",
                            List.of("ID must be greater than 0")));
        }
        return locationService.getLocationById(id)
                              .map(location -> ResponseEntity.ok().body(location))
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/locations")
    public ResponseEntity<?> createLocation(@RequestBody Location location) {
        log.info("Request to create location => {}", location);
        if (location.getId() != null) {
            log.info("product location => {}", location);
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID should be null",
                            "Invalid ID",
                            List.of("ID should be null when creating a new location")));
        }
        return ResponseEntity.ok().body(locationService.createLocation(location));
    }

    @PutMapping("/locations")
    public ResponseEntity<?> updateLocation(@RequestBody Location location) {
        if (location.getId() == null) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID cannot be null",
                            "Invalid ID",
                            List.of("ID must be provided for updates"))
            );
        }
        Optional<Location> updatedLocation = locationService.updateLocation(location);
        if (updatedLocation.isPresent()) {
            return ResponseEntity.ok(updatedLocation);
        } else {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "Location not found",
                            "Update failed",
                            List.of("Location with the given ID doesn't exist")));
        }
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

}
