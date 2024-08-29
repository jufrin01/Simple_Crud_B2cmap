package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.dto.request.CountryDto;
import idb2camp.b2campjufrin.dto.response.BaseResponse;

import java.util.List;

public interface CountryService {
    BaseResponse<CountryDto> createCountry(CountryDto newCountryDto);

    BaseResponse<CountryDto> getCountryById(Integer countryId);

    List<CountryDto> getAllCountries();
}
