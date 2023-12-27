package shok.lecmaster.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.model.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import shok.lecmaster.model.Lecture;
import shok.lecmaster.model.LectureMapper;
import shok.lecmaster.model.QuestionMapper;
import shok.lecmaster.model.Reply;
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
  EachLectureMapper eachLectureMapper;]

  @Autowired
  ReplyMapper replyMapper;

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

    ArrayList<EachLecture> eachLectures = eachLectureMapper.getEachLectures(id);
    model.addAttribute("each_lectures", eachLectures);

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
  public String addLecture(@RequestParam(required = false) String error, ModelMap model) {

    if (error != null) {
      model.addAttribute("error", error);
    }

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
    DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm");

    // 日付と時間の文字列を結合し、LocalDateTimeに変換
    LocalDateTime startDateTime = LocalDateTime.parse(startDateStr + " " + startTimeStr, formatter);

    //時間の文字列をLocalTimeに変換
    LocalTime localTime = LocalTime.parse(startTimeStr, Timeformatter);

    // LocalDateTimeをjava.sql.Timestampに変換
    Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
    int lecture_times = Integer.parseInt(request.getParameter("lecture_times"));

    try {
      lectureMapper.addLecture(name, password);
    } catch (Exception e) {
      try {
        String errorMessage = "講義名が重複しています";
        String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());
        return "redirect:/teacher/add_lecture?error=" + encodedErrorMessage;
      } catch (Exception er) {
      }
    }

    int lecture_id = Integer.parseInt(lectureMapper.getLectureId(name));

    for (int i = 0; i < lecture_times; i++) {
      EachLecture eachLecture = new EachLecture();
      eachLecture.setLectureId(lecture_id);
      eachLecture.setNumber(i + 1);
      eachLecture.setStartDate(startTimestamp);
      eachLecture.setStartTime(localTime);
      eachLecture.setEndTime(localTime.plusMinutes(100));
      eachLectureMapper.addEachLecture(eachLecture);

      // 次の講義の開始時間を計算(１週間後)
      startTimestamp = Timestamp.valueOf(startDateTime.plusWeeks(i + 1));
    }

    return "redirect:/teacher";
  }

  @GetMapping("each_lecture_setting")
  public String each_lecture_setting(@RequestParam int id, @RequestParam int number, ModelMap model) {

    String name = lectureMapper.getName(id);

    int each_lecture_id = eachLectureMapper.getId(id, number);
    //返答を取得
    ArrayList<Reply> replies = replyMapper.getReply(each_lecture_id);

    model.addAttribute("name", name);
    model.addAttribute("number", number);
    model.addAttribute("id", id);


    return "each_lecture_setting.html";
  }

  @PostMapping("each_lecture_setting")
  public ModelAndView each_lecture_setting(HttpServletRequest request, ModelMap model) {

    int lecture_id = Integer.parseInt(request.getParameter("id"));
    int number = Integer.parseInt(request.getParameter("number"));
    String question = request.getParameter("question");
    String answer = request.getParameter("answer");

    lecture_id=eachLectureMapper.getId(lecture_id, number);
    

    questionMapper.setQuestion(lecture_id, question, answer);

    return new ModelAndView("each_lecture_setting", model);
  }

}
