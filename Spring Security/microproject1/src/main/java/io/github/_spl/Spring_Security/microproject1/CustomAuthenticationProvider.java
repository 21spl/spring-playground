package io.github._spl.Spring_Security.microproject1;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate (Authentication authentication) throws AuthenticationException {
        // authentication logic here

        // we get username from Authentication object
        // Authentication class implements Principal interface
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        // we define "wrik" as the only authenticated user
        if("wrik".equals(username) && "12345".equals(password)){
            return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());
        }
        else{
            throw new AuthenticationCredentialsNotFoundException("Error!");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType)
    {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authenticationType);
    }
}
