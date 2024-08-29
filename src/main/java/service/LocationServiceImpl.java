package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.constant.GlobalMessage;
import idb2camp.b2campjufrin.dto.request.CountryDto;
import idb2camp.b2campjufrin.dto.request.LocationDto;
import idb2camp.b2campjufrin.expection.BusinessException;
import idb2camp.b2campjufrin.model.Country;
import idb2camp.b2campjufrin.model.Location;
import idb2camp.b2campjufrin.repository.CountryRepository;
import idb2camp.b2campjufrin.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepo;
    private final CountryRepository countryRepo;
    private final ModelMapper modelMapper;

    @Override
    public LocationDto createLocation(LocationDto newLocationDto) {
        validateLocationDto(newLocationDto);

        Location newLocationEntity = modelMapper.map(newLocationDto, Location.class);
        Country country = countryRepo.findById(newLocationDto.getCountryId())
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));
        newLocationEntity.setCountry(country);

        Location savedLocation = locationRepo.save(newLocationEntity);
        return modelMapper.map(savedLocation, LocationDto.class);
    }

    @Override
    public CountryDto getCountryById(Integer countryId) {
        Country country = countryRepo.findById(countryId)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));

        List<LocationDto> locationDtos = country.getLocation().stream()
                .map(location -> modelMapper.map(location, LocationDto.class))
                .collect(Collectors.toList());

        CountryDto countryDto = modelMapper.map(country, CountryDto.class);
        countryDto.setLocation(locationDtos);

        return countryDto;
    }

    private void validateLocationDto(LocationDto newLocationDto) {
    }
}
