package com.lepa.portal;


import com.google.common.collect.ImmutableList;
import com.lepa.portal.enums.Role;
import com.lepa.portal.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userServiceImpl;
    private static final String API ="/api/**";

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public WebSecurity(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
        provider.setUserDetailsService(userServiceImpl);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/user/ResetPasswordsMail").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/user/Register").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/user/ResetPassword").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/login").hasAnyRole(Role.ADMIN.nameRole, Role.MODERATOR.nameRole, Role.USER.nameRole)
                .antMatchers(HttpMethod.POST, API).hasAnyRole(Role.ADMIN.nameRole, Role.MODERATOR.nameRole, Role.USER.nameRole)
                .antMatchers(HttpMethod.DELETE, API).hasAnyRole(Role.ADMIN.nameRole, Role.MODERATOR.nameRole, Role.USER.nameRole)
                .antMatchers(HttpMethod.PUT, API).hasAnyRole(Role.ADMIN.nameRole, Role.MODERATOR.nameRole, Role.USER.nameRole)
                .and().formLogin()
                .and().httpBasic()
                .and().csrf()
                .disable();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));  //set access from all domains
        configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
