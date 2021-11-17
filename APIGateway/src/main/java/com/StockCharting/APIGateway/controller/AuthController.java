//package com.StockCharting.APIGateway.controller;
//
//import com.StockCharting.APIGateway.dto.AuthRequest;
//import com.StockCharting.APIGateway.dto.AuthResponse;
//import com.StockCharting.APIGateway.exception.NotLoggedInException;
//import com.StockCharting.APIGateway.jwt.JwtService;
//import com.StockCharting.APIGateway.service.MyUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AuthController {
//
//    @Autowired
//    private MyUserDetailsService myUserDetailsService;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws NotLoggedInException {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
//        }catch (BadCredentialsException e){
//            throw new NotLoggedInException("User Not Available");
//        }
//
//        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUsername());
//
//        String jwt = jwtService.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthResponse(jwt));
//    }
//}
