package idb2camp.b2campjufrin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountryDto {
    private Integer countryId;
    private String countryName;
    private String countryCode;
    private Integer regionId;
    private List<LocationDto> location;

}
