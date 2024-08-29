package idb2camp.b2campjufrin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegionDto {
    private Integer regionId;
    private String regionName;
    private List<CountryDto> country;

}
