//package com.StockCharting.APIGateway.service;
//
//import com.StockCharting.APIGateway.dto.MyUserDetails;
//import com.StockCharting.APIGateway.dto.UserDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        UserDTO userDTO = null;
//        try {
//            userDTO = restTemplate.getForObject("http://localhost:8085/users/searchByName="+s,UserDTO.class);
//        } catch (HttpClientErrorException e){
//            throw new UsernameNotFoundException("User "+s+" Not Available");
//        }
//
//        assert userDTO != null;
//        return new MyUserDetails(userDTO);
//    }
//}
