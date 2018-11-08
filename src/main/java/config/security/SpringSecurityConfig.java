package config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/registration", "/login", "/universities").permitAll()
                .antMatchers("/registerTutor", "/registerUniver").access("hasRole(\"" + UserRole.ADMIN.toString() + "\")")
                .antMatchers("/editor", "/delete", "/userList", "/stat", "/registerGroups").access("hasRole(\"" + UserRole.TEACHER.toString() + "\")")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").usernameParameter("user").passwordParameter("password")
                .and()
                .logout().invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedHandler((req, resp, ex) -> req.getRequestDispatcher("forbidden.jsp").forward(req, resp));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
