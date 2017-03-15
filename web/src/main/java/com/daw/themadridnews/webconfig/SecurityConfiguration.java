package com.daw.themadridnews.webconfig;
import com.daw.themadridnews.user.UserRepositoryAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserRepositoryAuthenticationProvider authenticationProvider;

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	// Public pages
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/portada").permitAll();
        http.authorizeRequests().antMatchers("/portada/**").permitAll();

        http.authorizeRequests().antMatchers("/imagen/**").permitAll();
        
        http.authorizeRequests().antMatchers("/buscar").permitAll();

        http.authorizeRequests().antMatchers("/registro").permitAll();
        http.authorizeRequests().antMatchers("/registro/**").permitAll();
        http.authorizeRequests().antMatchers("/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();

        http.authorizeRequests().antMatchers("/categoria/**").permitAll();
        http.authorizeRequests().antMatchers("/anuncio/{id}").permitAll();
        http.authorizeRequests().antMatchers("/privacidad").permitAll();
        http.authorizeRequests().antMatchers("/terminos-de-uso").permitAll();

        http.authorizeRequests().antMatchers("/ajustes").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/ajustes/**").hasAnyRole("USER");
        
        http.authorizeRequests().antMatchers("/editor/**").hasAnyRole( "EDITOR", "ADMIN");
        http.authorizeRequests().antMatchers("/publicista/**").hasAnyRole( "ADVERTISING", "ADMIN");
        
        // Login form
        http.formLogin().loginPage("/portada").and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/portada");
        http.formLogin().usernameParameter("email");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/portada");
        http.formLogin().failureUrl("/loginError");

        // Logout
        //http.logout().logoutUrl("/logout");
        //http.logout().logoutSuccessUrl("/portada");
        
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // Database authentication provider
        auth.authenticationProvider(authenticationProvider);
    }
}
