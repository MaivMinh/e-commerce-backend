package com.minh.user_service.service;

import com.minh.user_service.DTOs.AddressDTO;
import com.minh.user_service.DTOs.UserCreateDTO;
import com.minh.user_service.DTOs.UserDTO;
import com.minh.user_service.entity.Address;
import com.minh.user_service.entity.Gender;
import com.minh.user_service.entity.User;
import com.minh.user_service.mapper.AddressMapper;
import com.minh.user_service.mapper.UserMapper;
import com.minh.user_service.repository.AddressRepository;
import com.minh.user_service.repository.UserRepository;
import com.minh.user_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final AddressRepository addressRepository;

  public ResponseData getProfile(String accountId) {
    User user = userRepository.findUserByAccountId(accountId).orElseThrow(() -> new RuntimeException("User not found"));
    List<Address> addresses = addressRepository.findAllByUserId(user.getId());

    List<AddressDTO> addressDTOs = addresses.stream()
            .map(address -> {
              AddressDTO addressDTO = new AddressDTO();
              AddressMapper.mapToAddressDTO(address, addressDTO);
              return addressDTO;
            }).toList();

    UserDTO userDTO = new UserDTO();
    UserMapper.mapToUserDTO(user, userDTO);
    userDTO.setAddressDTOs(addressDTOs);

    return ResponseData.builder()
            .status(200)
            .message("Success")
            .data(userDTO)
            .build();
  }

  public ResponseData createUser(UserCreateDTO userCreateDTO) {
    User user = new User();
    user.setId(UUID.randomUUID().toString());
    user.setAccountId(userCreateDTO.getAccountId());
    user.setFullName(userCreateDTO.getFullName());
    user.setUsername(userCreateDTO.getUsername());
    user.setAvatar(userCreateDTO.getAvatar());
    user.setGender(Gender.valueOf(userCreateDTO.getGender()));
    user.setBirthDate(userCreateDTO.getBirthDate());
    userRepository.save(user);
    return ResponseData.builder()
            .status(201)
            .message("User created successfully")
            .data(null)
            .build();
  }

  public ResponseData getUserById(String userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    UserDTO userDTO = new UserDTO();
    UserMapper.mapToUserDTO(user, userDTO);

    List<Address> addresses = addressRepository.findAllByUserId(user.getId());
    List<AddressDTO> addressDTOs = addresses.stream()
            .map(address -> {
              AddressDTO addressDTO = new AddressDTO();
              AddressMapper.mapToAddressDTO(address, addressDTO);
              return addressDTO;
            }).toList();

    userDTO.setAddressDTOs(addressDTOs);

    return ResponseData.builder()
            .status(200)
            .message("Success")
            .data(userDTO)
            .build();
  }

  public ResponseData findAllUsers(int page, int size, String sort) {
    Pageable pageable = null;
    if (StringUtils.hasText(sort)) {
      /// sort = createdAt:desc,name:asc
      List<Sort.Order> orders = new ArrayList<>();
      String[] sortFields = sort.split(",");
      for (String field : sortFields) {
        orders.add(new Sort.Order(Sort.Direction.fromString(field.split(":")[1].toUpperCase()), field.split(":")[0]));
      }
      pageable = PageRequest.of(page, size, Sort.by(orders));
    } else pageable = PageRequest.of(page, size);

    Page<User> users = userRepository.findAll(pageable);
    List<UserDTO> userDTOs = users.stream()
            .map(user -> {
              UserDTO userDTO = new UserDTO();
              UserMapper.mapToUserDTO(user, userDTO);
              return userDTO;
            }).toList();

    return ResponseData.builder()
            .status(200)
            .message("Success")
            .data(Map.of("users", userDTOs,
                    "size", users.getSize(),
                    "page", users.getNumber() + 1,
                    "totalPages", users.getTotalPages(),
                    "totalElements", users.getTotalElements()))
            .build();
  }

  public ResponseData updateUser(UserDTO userDTO) {
    User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("User not found"));
    user.setFullName(userDTO.getFullName());
    user.setAvatar(userDTO.getAvatar());
    user.setGender(Gender.valueOf(userDTO.getGender()));
    user.setBirthDate(userDTO.getBirthDate());
    userRepository.save(user);
    return ResponseData.builder()
            .status(200)
            .message("User updated successfully")
            .data(null)
            .build();
  }
}
