package com.minh.user_service.repository;

import com.minh.user_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
  List<Address> findAllByUserId(String id);

  @Query(value = "select address from addresses where user_id = :id", nativeQuery = true)
  String findShippingAddressByUserId(String id);
}
