package shok.lecmaster.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import shok.lecmaster.model.Attend;
import shok.lecmaster.model.AttendMapper;
import shok.lecmaster.model.Lecture;
import shok.lecmaster.model.LectureMapper;
import shok.lecmaster.model.Question;
import shok.lecmaster.model.QuestionMapper;
import shok.lecmaster.model.Reply;
import shok.lecmaster.model.ReplyMapper;

@Controller
public class StudentContoroller {

  @Autowired
  LectureMapper lectureMapper;

  @Autowired
  AttendMapper attendMapper;

  @Autowired
  QuestionMapper questionMapper;

  @Autowired
  ReplyMapper replyMapper;

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

  @PostMapping("/attend")
  public String attend(HttpServletRequest request, Model model, Principal prin) {
      int id = Integer.parseInt(request.getParameter("id"));
      String pass = request.getParameter("password");
      String password = lectureMapper.getPassword(id);

      if (pass.equals(password)) {
        Attend attend = new Attend();
        attend.setEachLectureId(id);
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

  @GetMapping("/attend")
  public String attend(@RequestParam int id, ModelMap model) {
    String name = lectureMapper.getName(id);

    model.addAttribute("id", id);
    model.addAttribute("name", name);

    return "attend.html";
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
    replyObj.setEachQuestionId(question_id);
    replyObj.setReply(reply);
    replyObj.setLectureId(id);

    /* 同じ学生が挿入されないようになっているので例外処理 */
    try {
      replyMapper.setReply(replyObj);
    } catch (Exception e) {
    }

    return "redirect:/lecture" + "?id=" + id;
  }
}