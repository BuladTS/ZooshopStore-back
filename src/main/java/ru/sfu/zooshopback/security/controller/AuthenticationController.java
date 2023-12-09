package ru.sfu.zooshopback.security.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sfu.zooshopback.DTO.auth.AuthenticationRequest;
import ru.sfu.zooshopback.DTO.auth.AuthenticationResponse;
import ru.sfu.zooshopback.DTO.auth.RegisterRequest;
import ru.sfu.zooshopback.security.service.AuthenticationService;

@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("Authentication " + request);
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/check-token")
    public ResponseEntity<?> checkToken() {
        return ResponseEntity.ok("Token is valid");
    }
}
