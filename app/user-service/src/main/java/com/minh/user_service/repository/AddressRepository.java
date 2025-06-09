package com.minh.user_service.repository;

import com.minh.user_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
  List<Address> findAllByUserId(String id);

  Optional<Address> findAddressById(String id);
}
