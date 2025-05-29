package com.minh.auth_service.repository;

import com.minh.auth_service.model.AccountAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountAddressRepository extends JpaRepository<AccountAddress, String> {
}
