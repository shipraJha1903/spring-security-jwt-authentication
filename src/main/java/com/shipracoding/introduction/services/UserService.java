package com.shipracoding.introduction.services;

import com.shipracoding.introduction.dto.SignUpDto;
import com.shipracoding.introduction.dto.UserDto;
import com.shipracoding.introduction.entities.UserEntity;
import com.shipracoding.introduction.exceptions.ResourceNotFoundException;
import com.shipracoding.introduction.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new BadCredentialsException("User with email"+username +" not found"));
    }

    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User with this id not found" + userId));
    }


    public UserDto signUp(SignUpDto signUpDto) {
        Optional<UserEntity> user =  userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email"+ signUpDto.getEmail()+" already exists");
        }
        UserEntity toBeCreatedUser = modelMapper.map(signUpDto,UserEntity.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        UserEntity savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser,UserDto.class);
    }
}
