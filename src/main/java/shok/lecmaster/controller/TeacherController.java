package shok.lecmaster.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import shok.lecmaster.model.Lecture;
import shok.lecmaster.model.LectureMapper;
import shok.lecmaster.model.QuestionMapper;
import shok.lecmaster.model.EachLecture;
import shok.lecmaster.model.EachLectureMapper;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

  @Autowired
  LectureMapper lectureMapper;

  @Autowired
  QuestionMapper questionMapper;

  @Autowired
  EachLectureMapper eachLectureMapper;

  @GetMapping
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

  @GetMapping("setting")
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

  @PostMapping("setting")
  public String updatePassword(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));

    String password = request.getParameter("password");

    lectureMapper.setPassword(id, password);

    return "redirect:/teacher/setting?id=" + id;
  }

  @PostMapping("message")
  public String sendMessage(HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    String message = request.getParameter("message");

    lectureMapper.setMessage(id, message);

    return "redirect:/teacher";
  }

  @PostMapping("question")
  public String sendQuestion(HttpServletRequest request) {
    int lecture_id = Integer.parseInt(request.getParameter("id"));
    String question = request.getParameter("question");
    String answer = request.getParameter("answer");

    questionMapper.setQuestion(lecture_id, question, answer);

    return "redirect:/teacher";
  }

  @GetMapping("add_lecture")
  public String addLecture() {
    return "add_lecture.html";
  }

  @PostMapping("add_lecture")
  public String addLecture(HttpServletRequest request) {
    String name = request.getParameter("lecture_name");
    String password = request.getParameter("lecture_password");
    // 日付と時間のパラメータを取得
    String startDateStr = request.getParameter("lecture_start_date");
    String startTimeStr = request.getParameter("lecture_start_time");

    // LocalDateTimeのフォーマットを定義
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // 日付と時間の文字列を結合し、LocalDateTimeに変換
    LocalDateTime startDateTime = LocalDateTime.parse(startDateStr + " " + startTimeStr, formatter);

    // LocalDateTimeをjava.sql.Timestampに変換
    Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
    int lecture_times = Integer.parseInt(request.getParameter("lecture_times"));

    int lecture_id = lectureMapper.addLecture(name, password);

    for (int i = 0; i < lecture_times; i++) {
      EachLecture eachLecture = new EachLecture();
      eachLecture.setLectureId(lecture_id);
      eachLecture.setNumber(i + 1);
      eachLecture.setStartDate(startTimestamp);
      eachLectureMapper.addEachLecture(eachLecture);

      // 次の講義の開始時間を計算(１週間後)
      startTimestamp = Timestamp.valueOf(startDateTime.plusWeeks(1));
    }

    return "redirect:/teacher";
  }
}
