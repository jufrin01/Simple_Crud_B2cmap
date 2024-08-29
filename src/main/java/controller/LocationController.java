package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.dto.request.CountryDto;
import idb2camp.b2campjufrin.dto.request.LocationDto;
import idb2camp.b2campjufrin.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
@AllArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto newLocationDto) {
        LocationDto createdLocation = locationService.createLocation(newLocationDto);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @GetMapping("{countryId}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Integer countryId) {
        CountryDto countryDto = locationService.getCountryById(countryId);
        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }
}

//{ JSON create contoh+
//    "streetAddress": "???",
//    "postalCode": "???",
//    "city": "?",
//    "stateProvince": "??",
//    "countryId": 1
//    }

