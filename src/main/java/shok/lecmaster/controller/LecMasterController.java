package shok.lecmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import shok.lecmaster.model.Lecture;
import shok.lecmaster.model.LectureMapper;
import shok.lecmaster.model.Question;
import shok.lecmaster.model.QuestionMapper;
import shok.lecmaster.model.Attend;
import shok.lecmaster.model.AttendMapper;
import shok.lecmaster.model.Reply;
import shok.lecmaster.model.ReplyMapper;

import java.util.ArrayList;

@Controller
public class LecMasterController {
  @Autowired
  LectureMapper lectureMapper;

  @Autowired
  AttendMapper attendMapper;

  @Autowired
  QuestionMapper questionMapper;

  @Autowired
  ReplyMapper replyMapper;

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
  public String student(@AuthenticationPrincipal UserDetails user, ModelMap model) {

    ArrayList<Lecture> lectures = lectureMapper.getLectures();

    if (user == null) {
      return "redirect:/login";
    } else if (user.getAuthorities().toString().equals("[ROLE_TEACHER]")) {
      return "redirect:/teacher";
    }

    model.addAttribute("lectures", lectures);

    return "student.html";
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

    ArrayList<Attend> attends = attendMapper.getAttends(id);
    model.addAttribute("attends", attends);
    ArrayList<Question> questions = questionMapper.getQuestions(id);
    model.addAttribute("questions", questions);
    ArrayList<Reply> replies = replyMapper.getReply(id);
    model.addAttribute("replies", replies);

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

    questionMapper.setQuestion(lecture_id, question, answer);

    return "redirect:/teacher";
  }

  @GetMapping("/attend")
  public String attend(@RequestParam int id, ModelMap model) {
    String name = lectureMapper.getName(id);

    model.addAttribute("id", id);
    model.addAttribute("name", name);

    return "attend.html";
  }

  @PostMapping("/attend")
  public String attend(HttpServletRequest request, Model model, Principal prin) {
    int id = Integer.parseInt(request.getParameter("id"));
    String pass = request.getParameter("password");
    String password = lectureMapper.getPassword(id);

    if (pass.equals(password)) {
      Attend attend = new Attend();
      attend.setEach_lecture_id(id);
      attend.setName(prin.getName());
      try {
        /* 同じ名前が挿入されないようにする */
        attendMapper.addAttend(attend);
      } catch (Exception e) {

      }
      return "redirect:/lecture" + "?id=" + id;
    } else {
      model.addAttribute("incorrect", id);
      return "redirect:/reattend" + "?id=" + id;
    }

  }

  @GetMapping("/reattend")
  public String reattend(@RequestParam int id, ModelMap model) {
    String name = lectureMapper.getName(id);

    model.addAttribute("id", id);
    model.addAttribute("name", name);
    model.addAttribute("incorrect", 1);

    return "attend.html";
  }

  /**
   * lectureページ
   * attendにデータが挿入されている生徒のみがアクセスできる
   */
  @GetMapping("/lecture")
  public String lecture(@RequestParam int id, ModelMap model) {
    String name = lectureMapper.getName(id);

    model.addAttribute("name", name);

    String message = lectureMapper.getMessage(id);
    model.addAttribute("message", message);

    ArrayList<Question> questions = questionMapper.getQuestions(id);
    model.addAttribute("questions", questions);

    return "lecture.html";
  }

  /**
   * 問題に対する回答を登録する
   */
  @PostMapping("/reply")
  public String reply(HttpServletRequest request, Model model, Principal prin) {
    int id = Integer.parseInt(request.getParameter("id"));
    String reply = request.getParameter("reply");
    String name = prin.getName();
    int question_id = Integer.parseInt(request.getParameter("question_id"));

    Reply replyObj = new Reply();
    replyObj.setName(name);
    replyObj.setEach_question_id(question_id);
    replyObj.setReply(reply);
    replyObj.setLecture_id(id);

    /* 同じ学生が挿入されないようになっているので例外処理 */
    try {
      replyMapper.setReply(replyObj);
    } catch (Exception e) {
    }

    return "redirect:/lecture" + "?id=" + id;
  }
}
