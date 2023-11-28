package shok.lecmaster.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LecMasterController {

  @GetMapping("/")
  public String index(@AuthenticationPrincipal UserDetails user) {
    if (user == null) {
      return "redirect:/login";
    } else if (user.getAuthorities().toString().equals("[ROLE_STUDENT]")) {
      return "redirect:/student";
    } else if (user.getAuthorities().toString().equals("[ROLE_TEACHER]")) {
      return "redirect:/teacher";
    }

    return "index.html";
  }


}
