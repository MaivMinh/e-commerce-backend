package com.minh.user_service.mapper;

import com.minh.user_service.DTOs.AddressDTO;
import com.minh.user_service.entity.Address;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressMapper {
  public static void mapToAddressDTO(Address address, AddressDTO addressDTO) {
    if (address == null) {
      log.warn("Address is null, cannot map to AddressDTO");
      return;
    }
    if (addressDTO == null) {
      log.warn("AddressDTO is null, creating a new instance");
      addressDTO = new AddressDTO();
    }

    addressDTO.setId(address.getId());
    addressDTO.setFullName(address.getFullName());
    addressDTO.setPhone(address.getPhone());
    addressDTO.setAddress(address.getAddress());
    addressDTO.setWard(address.getWard());
    addressDTO.setDistrict(address.getDistrict());
    addressDTO.setCity(address.getCity());
    addressDTO.setIsDefault(address.getIsDefault());
  }
}
