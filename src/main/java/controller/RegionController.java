package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.dto.request.RegionDto;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@AllArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping
    public BaseResponse<RegionDto> createRegion(@RequestBody RegionDto newRegionDto) {
        return regionService.createRegion(newRegionDto);
    }

    @GetMapping("/{Id}")
    public BaseResponse<RegionDto> getRegionById(@PathVariable Integer regionId) {
        return regionService.getRegionById(regionId);

    }

    @GetMapping
    public BaseResponse<List<RegionDto>> getAllRegions() {
        return regionService.getAllRegions();
    }
}
//    { exemple : create region JSON
////        "regionName": "BREBES",
////            "countries": []
////    }