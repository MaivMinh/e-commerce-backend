package com.minh.user_service.controller;

import com.minh.user_service.DTOs.AddressCreateDTO;
import com.minh.user_service.DTOs.AddressDTO;
import com.minh.user_service.response.ResponseData;
import com.minh.user_service.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/users/addresses")
public class AddressController {
  private final AddressService addressService;

  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createAddressForUser(@RequestBody @Valid AddressCreateDTO addressCreateDTO) {
    ResponseData response = addressService.createAddressForUser(addressCreateDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(value = "/{userId}")
  public ResponseEntity<ResponseData> findAllAddressesByUserId(@PathVariable String userId) {
    ResponseData response = addressService.findAllAddressesByUserId(userId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @PutMapping(value = "")
  public ResponseEntity<ResponseData> updateAddress(@RequestBody @Valid AddressDTO addressDTO) {
    ResponseData response = addressService.updateAddress(addressDTO);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @DeleteMapping(value = "/{addressId}")
  public ResponseEntity<ResponseData> deleteAddress(@PathVariable String addressId) {
    ResponseData response = addressService.deleteAddress(addressId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}
