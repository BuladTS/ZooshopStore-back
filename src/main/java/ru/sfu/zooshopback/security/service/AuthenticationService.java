package ru.sfu.zooshopback.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sfu.zooshopback.DTO.auth.AuthenticationRequest;
import ru.sfu.zooshopback.DTO.auth.AuthenticationResponse;
import ru.sfu.zooshopback.DTO.auth.RegisterRequest;
import ru.sfu.zooshopback.model.User;
import ru.sfu.zooshopback.model.enums.UserRole;
import ru.sfu.zooshopback.repository.UserRepository;
import ru.sfu.zooshopback.security.service.exception.UserAlreadyExistException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isPresent())
            throw new UserAlreadyExistException("User with username " + optionalUser.get().getUsername() + " already exists");

        UserRole role = UserRole.USER;
        if (request.getRole() != null && request.getRole().equals("admin"))
            role = UserRole.ADMIN;
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isEmpty())
            throw new UsernameNotFoundException("User with username " + request.getUsername() + " not found");
        String jwtToken = jwtService.generateToken(user.get());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}