package com.minh.user_service.service;

import com.minh.user_service.DTOs.AddressCreateDTO;
import com.minh.user_service.DTOs.AddressDTO;
import com.minh.user_service.entity.Address;
import com.minh.user_service.repository.AddressRepository;
import com.minh.user_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
  private final AddressRepository addressRepository;

  public ResponseData createAddressForUser(AddressCreateDTO addressCreateDTO) {
    Address address = new Address();
    address.setId(UUID.randomUUID().toString());
    address.setAddress(addressCreateDTO.getAddress());
    address.setFullName(addressCreateDTO.getFullName());
    address.setPhone(addressCreateDTO.getPhone());
    address.setWard(addressCreateDTO.getWard());
    address.setDistrict(addressCreateDTO.getDistrict());
    address.setCity(addressCreateDTO.getCity());
    address.setUserId(addressCreateDTO.getUserId());
    if (addressCreateDTO.getIsDefault()) {
      ///  replace address isDefault status equal true.
      addressRepository.findAllByUserId(addressCreateDTO.getUserId()).forEach(existingAddress -> {
        existingAddress.setIsDefault(false);
        addressRepository.save(existingAddress);
      });
    }
    address.setIsDefault(addressCreateDTO.getIsDefault());
    addressRepository.save(address);

    AddressDTO addressDTO = new AddressDTO();
    addressDTO.setId(address.getId());
    addressDTO.setAddress(address.getAddress());
    addressDTO.setFullName(address.getFullName());
    addressDTO.setPhone(address.getPhone());
    addressDTO.setWard(address.getWard());
    addressDTO.setDistrict(address.getDistrict());
    addressDTO.setCity(address.getCity());
    addressDTO.setIsDefault(address.getIsDefault());

    return ResponseData.builder()
            .status(201)
            .message("Address created successfully")
            .data(addressDTO)
            .build();
  }

  public ResponseData findAllAddressesByUserId(String userId) {
    List<Address> addresses = addressRepository.findAllByUserId(userId);
    List<AddressDTO> addressDTOs = addresses.stream()
            .map(address -> {
              AddressDTO addressDTO = new AddressDTO();
              addressDTO.setId(address.getId());
              addressDTO.setAddress(address.getAddress());
              addressDTO.setFullName(address.getFullName());
              addressDTO.setPhone(address.getPhone());
              addressDTO.setWard(address.getWard());
              addressDTO.setDistrict(address.getDistrict());
              addressDTO.setCity(address.getCity());
              addressDTO.setIsDefault(address.getIsDefault());
              return addressDTO;
            }).toList();

    return ResponseData.builder()
            .status(200)
            .message("Addresses retrieved successfully")
            .data(addressDTOs)
            .build();
  }

  public ResponseData updateAddress(@Valid AddressDTO addressDTO) {
    Address address = addressRepository.findById(addressDTO.getId())
            .orElseThrow(() -> new RuntimeException("Address not found"));

    address.setAddress(addressDTO.getAddress());
    address.setFullName(addressDTO.getFullName());
    address.setPhone(addressDTO.getPhone());
    address.setWard(addressDTO.getWard());
    address.setDistrict(addressDTO.getDistrict());
    address.setCity(addressDTO.getCity());

    if (addressDTO.getIsDefault()) {
      // Replace address isDefault status equal true.
      addressRepository.findAllByUserId(address.getUserId()).forEach(existingAddress -> {
        existingAddress.setIsDefault(false);
        addressRepository.save(existingAddress);
      });
    }
    address.setIsDefault(addressDTO.getIsDefault());

    addressRepository.save(address);

    return ResponseData.builder()
            .status(200)
            .message("Address updated successfully")
            .data(null)
            .build();
  }

  public ResponseData deleteAddress(String addressId) {
    Address address = addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found"));
    addressRepository.delete(address);
    return ResponseData.builder()
            .status(200)
            .message("Address deleted successfully")
            .data(null)
            .build();
  }
}
