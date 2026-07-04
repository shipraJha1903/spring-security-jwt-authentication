package com.shipracoding.introduction.filters;

import com.shipracoding.introduction.entities.UserEntity;
import com.shipracoding.introduction.services.JwtService;
import com.shipracoding.introduction.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")){
              filterChain.doFilter(request,response);
              return;
        }

        String token = requestTokenHeader.split("Bearer ")[1];
        Long userId = jwtService.getUserIdFromToken(token);

        if(userId !=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserEntity user = userService.getUserById(userId);
            // Now putting this user in spring security context holder.

            // for that we are creating an authentication taki usko andar daal sake.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user,null,null);
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request) // to deal with ip address
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response); // we call the next filter in the filter chain , kyunki ye hamara
        // custom filter hain & we want ke control ab aage wale filters pe chale jae toh ye likhna prega.



    }
}
