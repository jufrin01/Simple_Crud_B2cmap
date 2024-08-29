package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.dto.request.RegionDto;
import idb2camp.b2campjufrin.dto.response.BaseResponse;

import java.util.List;

public interface RegionService {

    BaseResponse<RegionDto> createRegion(RegionDto newRegionDto);

    BaseResponse<RegionDto> getRegionById(Integer regionId);

    BaseResponse<List<RegionDto>> getAllRegions();
}
