package com.udea.edyl.EDyL.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.udea.edyl.EDyL.data.entity.Address;
import com.udea.edyl.EDyL.web.dto.AddressDto;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address addressDtoToAddress(AddressDto addressDto);

    AddressDto addressToAddressDto(Address address);

    List<Address> addressDtosToAddresses(List <AddressDto> addressDtos);
    
    List<AddressDto> addressesToAddressDtos(List<Address> addresses);
}
