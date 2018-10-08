package com.jwtAuthentication.jwtAuthentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwtAuthentication.jwtAuthentication.model.EmployeeLogin;
import com.jwtAuthentication.jwtAuthentication.model.User;
import com.jwtAuthentication.jwtAuthentication.repository.EmpLoginRepository;
import com.jwtAuthentication.jwtAuthentication.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
	EmpLoginRepository empLoginRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
		System.out.println("Load user name");
		
		EmployeeLogin user = empLoginRepository.findByUsername(usernameOrEmail).orElseThrow(() -> 
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)    
        );
		
//		User user = userRepository.findByUsername(usernameOrEmail).orElseThrow(() -> 
//        	new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)    
//		);
		
		System.out.println("user name found");

        return UserPrincipal.create2(user);
	}
	
	// This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }

}
