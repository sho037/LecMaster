package shok.lecmaster.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {
  /**
   * 認証・認可に関する設定を行う
   *
   * @param http
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain fileterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/"))
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).authenticated())
        .csrf(csrf -> csrf
            .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/*")))
        .headers(headers -> headers
            .frameOptions(frameOptions -> frameOptions
                .sameOrigin()));
    return http.build();
  }

  /**
   * 認証処理に関する設定（誰がどのようなロールでログインできるか）
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    // $ sshrun htpasswd -nbBC 10 USERNAME PASSWORD

    UserDetails[] users = {
        User.withUsername("teacher1").password("{bcrypt}$2y$10$PTIElp2sSt4AGXmM/MSMdeactS214M5/DLzjVMzTHMdUB.SZnLnYW")
            .roles("TEACHER").build(),
        User.withUsername("teacher2").password("{bcrypt}$2y$10$C7hKg.ULqTg8qK7udpLSCOHxHbruUz0yf8gmC9FURcvB1c8drVEfa")
            .roles("TEACHER").build(),
        User.withUsername("student1").password("{bcrypt}$2y$10$T3jSu12fw9Gmm1p591CpbuGLfI/ECh88lCpdXUGQcjr4zcDMO1nkC")
            .roles("STUDENT").build(),
        User.withUsername("student2").password("{bcrypt}$2y$10$IM00QILoPNl3N.3X3oJhMOByxa2HNeUgV6GQ9v3Hl1OtoEsjNIZa.")
            .roles("STUDENT").build(),
        User.withUsername("student3").password("{bcrypt}$2y$10$C.xpyPCaxuVSTITaVRRN5.bby1//m256EwKHujSdObAjTpLjMPzEy")
            .roles("STUDENT").build(),
    };

    // 生成したユーザをImMemoryUserDetailsManagerに渡す（いくつでも良い）
    return new InMemoryUserDetailsManager(users);
  }
}
