//package com.StockCharting.APIGateway.jwt;
//
//import com.StockCharting.APIGateway.exception.NotLoggedInException;
//import com.StockCharting.APIGateway.service.MyUserDetailsService;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//    @Autowired
//    private MyUserDetailsService myUserDetailsService;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @SneakyThrows
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//
//        String username = null,jwt;
//
//        if (authHeader!=null && authHeader.startsWith("Bearer")){
//            jwt = authHeader.substring(7);
//            username = jwtService.extractUsername(jwt);
//
//            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
//                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
//                if(jwtService.validateToken(jwt,userDetails)){
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
//                            UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken
//                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//
//                }
//            }
//        }
//        else
//            throw new NotLoggedInException("You are not Logged In");
//
//        filterChain.doFilter(request,response);
//
//    }
//}
