package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.dto.request.CountryDto;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
@AllArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @PostMapping
    public BaseResponse<CountryDto> createCountry(@RequestBody CountryDto newCountryDto) {
        return countryService.createCountry(newCountryDto);

    }

    @GetMapping("/{countryId}")
    public BaseResponse<CountryDto> getCountryById(@PathVariable Integer countryId) {
        return countryService.getCountryById(countryId);
    }

    @GetMapping
    public List<CountryDto> getAllCountries() {
        return countryService.getAllCountries();
    }
}
//    { contoh josn creted
//    "countryName": "suappp",
//    "countryCode": "kokolip",
//    "regionId": 7,
//    "regionId": 7,
//    "location": []
//    }
