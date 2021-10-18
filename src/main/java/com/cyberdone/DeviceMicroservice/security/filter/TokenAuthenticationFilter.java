package com.cyberdone.DeviceMicroservice.security.filter;

import com.cyberdone.DeviceMicroservice.security.CyberdoneUserDetails;
import com.cyberdone.DeviceMicroservice.security.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenAuthenticationFilter extends AuthenticationFilter {

    public TokenAuthenticationFilter(JwtService jwtService) {
        super(jwtService);
    }

    @Override
    public void authenticate(HttpServletRequest request, String username) {
        var userDetails = new CyberdoneUserDetails(roles);
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
