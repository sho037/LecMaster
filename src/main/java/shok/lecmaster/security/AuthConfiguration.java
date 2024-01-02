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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
    // TEACHERロールのユーザ取得
    ArrayList<UserInfo> teachers = GetUserInfos("src/main/java/shok/lecmaster/security/teacher.csv");

    // STUDENTロールのユーザ取得
    ArrayList<UserInfo> students = GetUserInfos("src/main/java/shok/lecmaster/security/student.csv");

    // 生成したユーザを格納する
    UserDetails[] users = new UserDetails[teachers.size() + students.size()];
    for (int i = 0; i < teachers.size(); i++) {
      users[i] = User.withUsername(teachers.get(i).getUsername())
          .password("{bcrypt}" + teachers.get(i).getPassword())
          .roles("TEACHER").build();
    }
    for (int i = 0; i < students.size(); i++) {
      users[teachers.size() + i] = User.withUsername(students.get(i).getUsername())
          .password("{bcrypt}" + students.get(i).getPassword())
          .roles("STUDENT").build();
    }

    // 生成したユーザをImMemoryUserDetailsManagerに渡す（いくつでも良い）
    return new InMemoryUserDetailsManager(users);
  }

  /**
   * ファイルからユーザー情報を取得する
   *
   * @return
   */
  private ArrayList<UserInfo> GetUserInfos(String path) {
    ArrayList<UserInfo> users = new ArrayList<UserInfo>();

    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        users.add(new UserInfo(values[0], values[1]));
      }
      br.close();
    } catch (IOException e) {
      System.out.println(e);
    }

    return users;
  }
}

class UserInfo {
  private String username;
  private String password;

  public UserInfo(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }
}
