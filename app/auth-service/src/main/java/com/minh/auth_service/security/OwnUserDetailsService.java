package com.minh.auth_service.security;

import com.minh.auth_service.model.Account;
import com.minh.auth_service.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OwnUserDetailsService implements UserDetailsService {
  private final AccountService accountService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountService.findAccountByUsername(username);
    if (account != null ) {
      List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole().toString()));
      return new User(username, account.getPassword(), roles );
    }
    throw new UsernameNotFoundException("Failed to load user by username");
  }
}
