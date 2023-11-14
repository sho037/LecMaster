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
import shok.lecmaster.model.LectureMapper;

import shok.lecmaster.model.Attend;
import shok.lecmaster.model.AttendMapper;

import java.util.ArrayList;

@Controller
public class LecMasterController {
  @Autowired
  LectureMapper lectureMapper;

  @Autowired
  AttendMapper attendMapper;

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
    ArrayList<Lecture> lectures = lectureMapper.getLectures();

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

    String name = lectureMapper.getName(id);
    String password = lectureMapper.getPassword(id);

    model.addAttribute("id", id);
    model.addAttribute("name", name);
    model.addAttribute("password", password);

    ArrayList<Attend> attends = attendMapper.getAttends(id);
    model.addAttribute("attends", attends);

    return "setting.html";
  }

  @PostMapping("/setting")
  public String updatePassword(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    String password = request.getParameter("password");

    lectureMapper.setPassword(id, password);

    return "redirect:/teacher";
  }

  @PostMapping("/message")
  public String sendMessage(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    String message = request.getParameter("message");

    lectureMapper.setMessage(id, message);

    return "redirect:/teacher";
  }

  @PostMapping("/question")
  public String sendQuestion(HttpServletRequest request) {
    int lecture_id = Integer.parseInt(request.getParameter("id"));
    String question = request.getParameter("question");
    String answer = request.getParameter("answer");

    lectureMapper.setQuestion(lecture_id, question, answer);

    return "redirect:/teacher";
  }

}
