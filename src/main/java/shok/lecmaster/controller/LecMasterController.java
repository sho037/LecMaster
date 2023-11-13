package shok.lecmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import shok.lecmaster.model.Lecture;
import shok.lecmaster.model.LectureMpper;

import java.util.ArrayList;

@Controller
public class LecMasterController {
  @Autowired
  LectureMpper lectureMpper;

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

  @GetMapping("/setting")
  public String setting(@RequestParam int id, ModelMap model) {

    String name = lectureMpper.getName(id);
    String password = lectureMpper.getPassword(id);

    model.addAttribute("id", id);
    model.addAttribute("name", name);
    model.addAttribute("password", password);

    return "setting.html";
  }

  @PostMapping("/setting")
  public String updatePassword(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    String password = request.getParameter("password");

    lectureMpper.setPassword(id, password);

    return "redirect:/teacher";
  }
}
