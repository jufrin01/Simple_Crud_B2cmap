package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.constant.GlobalMessage;
import idb2camp.b2campjufrin.dto.request.CountryDto;
import idb2camp.b2campjufrin.dto.request.LocationDto;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.expection.BusinessException;
import idb2camp.b2campjufrin.model.Country;
import idb2camp.b2campjufrin.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepo;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse<CountryDto> createCountry(CountryDto newCountryDto) {
        validateCountryDto(newCountryDto);

        Country newCountryEntity = modelMapper.map(newCountryDto, Country.class);
        Country savedCountry = countryRepo.save(newCountryEntity);

        CountryDto countryDto = modelMapper.map(savedCountry, CountryDto.class);

        return BaseResponse.<CountryDto>builder()
                .message("Country created successfully")
                .data(countryDto)
                .build();
    }

    @Override
    public BaseResponse<CountryDto> getCountryById(Integer countryId) {
        Country country = countryRepo.findById(countryId)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));

        CountryDto countryDto = modelMapper.map(country, CountryDto.class);

        return BaseResponse.<CountryDto>builder()
                .message("Country retrieved successfully")
                .data(countryDto)
                .build();
    }

    @Override
    public List<CountryDto> getAllCountries() {
        List<Country> countries = countryRepo.findAll();
        List<CountryDto> countryDtos = countries.stream()
                .map(country -> {
                    CountryDto countryDto = modelMapper.map(country, CountryDto.class);

                    if (country.getLocation() != null) {
                        List<LocationDto> locationDtos = country.getLocation().stream()
                                .filter(Objects::nonNull)
                                .map(location -> modelMapper.map(location, LocationDto.class))
                                .collect(Collectors.toList());
                        countryDto.setLocation(locationDtos);
                    }
                    return countryDto;
                })
                .collect(Collectors.toList());

        return countryDtos;
    }

    private void validateCountryDto(CountryDto newCountryDto) {
    }
}
