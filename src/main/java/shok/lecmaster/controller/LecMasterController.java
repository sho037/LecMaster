package shok.lecmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.ModelMap;

import shok.lecmaster.model.Lecture;
import shok.lecmaster.model.LectureMpper;

import java.util.ArrayList;

@Controller
public class LecMasterController {
  @Autowired
  LectureMpper lectureMpper;

  @GetMapping("/teacher")
  public String teacher(@AuthenticationPrincipal UserDetails user, ModelMap model) {
    ArrayList<Lecture> lectures = lectureMpper.getLectures();

    if (user == null) {
      return "redirect:/login";
    } else if (user.getAuthorities().toString().equals("[ROLE_STUDENT]")) {
      return "redirect:/student";
    }

    model.addAttribute("lectures", lectures);

    return "teacher.html";
  }

  @GetMapping("/student")
  public String student() {
    return "student.html";
  }

}
