package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.dto.request.CountryDto;
import idb2camp.b2campjufrin.dto.request.LocationDto;

public interface LocationService {
    LocationDto createLocation(LocationDto newLocationDto);

    CountryDto getCountryById(Integer countryId);
}
