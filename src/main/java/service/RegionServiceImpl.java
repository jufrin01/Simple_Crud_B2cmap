package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.constant.GlobalMessage;
import idb2camp.b2campjufrin.dto.request.RegionDto;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.expection.BusinessException;
import idb2camp.b2campjufrin.model.Region;
import idb2camp.b2campjufrin.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepo;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse<RegionDto> createRegion(RegionDto newRegionDto) {
        validateRegionDto(newRegionDto);

        Region newRegionEntity = modelMapper.map(newRegionDto, Region.class);
        Region savedRegion = regionRepo.save(newRegionEntity);

        RegionDto regionDto = modelMapper.map(savedRegion, RegionDto.class);

        return BaseResponse.<RegionDto>builder()
                .message("Region created successfully")
                .data(regionDto)
                .build();
    }

    @Override
    public BaseResponse<RegionDto> getRegionById(Integer regionId) {
        Region region = regionRepo.findById(regionId)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));

        RegionDto regionDto = modelMapper.map(region, RegionDto.class);

        return BaseResponse.<RegionDto>builder()
                .message("Region retrieved successfully")
                .data(regionDto)
                .build();
    }

    @Override
    public BaseResponse<List<RegionDto>> getAllRegions() {
        List<Region> regions = regionRepo.findAll();
        List<RegionDto> regionDtos = regions.stream()
                .map(region -> modelMapper.map(region, RegionDto.class))
                .collect(Collectors.toList());

        return BaseResponse.<List<RegionDto>>builder()
                .message("All regions retrieved successfully")
                .data(regionDtos)
                .build();
    }

    private void validateRegionDto(RegionDto newRegionDto) {
    }
}
