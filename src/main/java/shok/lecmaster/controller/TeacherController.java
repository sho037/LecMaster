package shok.lecmaster.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import shok.lecmaster.model.Lecture;
import shok.lecmaster.model.LectureMapper;
import shok.lecmaster.model.QuestionMapper;

@Controller
public class TeacherController {

  @Autowired
  LectureMapper lectureMapper;

  @Autowired
  QuestionMapper questionMapper;

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

  @GetMapping("/setting")
  public String setting(@RequestParam int id, ModelMap model) {

    String name = lectureMapper.getName(id);
    String password = lectureMapper.getPassword(id);
    String message = lectureMapper.getMessage(id);

    model.addAttribute("id", id);
    model.addAttribute("name", name);
    model.addAttribute("password", password);
    model.addAttribute("message", message);

    return "setting.html";
  }

  @PostMapping("/setting")
  public String updatePassword(HttpServletRequest request) {
    String idString = request.getParameter("id");
    // idがnullの場合は新規作成
    if (idString == null || idString.isEmpty()) {
      lectureMapper.setLecture(request.getParameter("lecture_name"));
      idString = lectureMapper.getLectureId(request.getParameter("lecture_name"));
      return "redirect:/setting?id=" + idString;
    }
    int id = Integer.parseInt(idString);

    String password = request.getParameter("password");

    lectureMapper.setPassword(id, password);

    return "redirect:/setting";
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

    questionMapper.setQuestion(lecture_id, question, answer);

    return "redirect:/teacher";
  }

  @GetMapping("/add_lecture")
  public String addLecture() {
    return "add_lecture.html";
  }
}
