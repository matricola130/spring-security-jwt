package io.springsecurity.springsecurityjwt;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.springsecurity.springsecurityjwt.models.AuthenticationRequest;
import io.springsecurity.springsecurityjwt.models.AuthenticationResponse;
import io.springsecurity.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping( "/hello")
    public String hello(Authentication auth){
        String principal = auth.getPrincipal().toString();
        System.out.println(principal);
        return "Hello World";
    }

    @RequestMapping("/roleFIRST")
    @PreAuthorize("hasAuthority('FIRST')")
    public String roleFirst(){
        return "Role First!";
    }

    @RequestMapping("/roleSECOND")
    @PreAuthorize("hasAuthority('SECOND')")
    public String roleSecond(){
        return "Role Second!";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("User o password errati!", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


}
