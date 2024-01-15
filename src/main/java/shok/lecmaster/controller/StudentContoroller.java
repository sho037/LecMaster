package shok.lecmaster.controller;

import java.security.Principal;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import shok.lecmaster.model.Attend;
import shok.lecmaster.model.AttendMapper;
import shok.lecmaster.model.EachLecture;
import shok.lecmaster.model.Lecture;
import shok.lecmaster.model.LectureMapper;
import shok.lecmaster.model.Question;
import shok.lecmaster.model.QuestionMapper;
import shok.lecmaster.model.Reply;
import shok.lecmaster.model.ReplyMapper;
import shok.lecmaster.model.EachLectureMapper;

@Controller
@RequestMapping("/student")
public class StudentContoroller {

  @Autowired
  LectureMapper lectureMapper;

  @Autowired
  AttendMapper attendMapper;

  @Autowired
  QuestionMapper questionMapper;

  @Autowired
  ReplyMapper replyMapper;

  @Autowired
  EachLectureMapper eachLectureMapper;

  /**
   * 生徒用トップページ
   * ログインしていない場合はログインページにリダイレクトする
   * ログインしているユーザーの権限が生徒でない場合はトップページにリダイレクトする
   *
   * @param user
   * @param model
   * @return
   */
  @GetMapping
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

  /**
   * 出席ページ
   * 講義のパスワードが間違っていた場合はreattendにリダイレクトする
   */
  @PostMapping("attend")
  public String attend(HttpServletRequest request, Model model, Principal prin) {
    int id = Integer.parseInt(request.getParameter("id"));
    String pass = request.getParameter("password");
    String lec_pass = lectureMapper.getPassword(id);

    // パスワードが間違っていた場合
    if (!pass.equals(lec_pass)) {
      model.addAttribute("incorrect", id);
      return "redirect:/student/reattend?id=" + id;
    }

    LocalDateTime now = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(now);
    LocalDate date = timestamp.toLocalDateTime().toLocalDate();

    // 出席時間が講義時間中のみ出席とする
    ArrayList<EachLecture> eachLectures = eachLectureMapper.getEachLectures(id);
    for (EachLecture eachLecture : eachLectures) {
      if (isAttendable(eachLecture, date, now)) {
        registerAttendance(eachLecture.getId(), prin.getName());

        request.getSession().setAttribute("lecture_id", id);
        return "redirect:/student/lecture";
      }
    }

    request.getSession().setAttribute("lecture_id", id);
    return "redirect:/student/lecture";
  }

  /**
   * 出席可能かどうかを判定する
   *
   * @param eachLecture
   * @param date
   * @param now
   * @return
   */
  private boolean isAttendable(EachLecture eachLecture, LocalDate date, LocalDateTime now) {
    LocalDate lectureStartDate = eachLecture.getStartDate().toLocalDateTime().toLocalDate();
    // 今日の授業でない場合
    if (!date.equals(lectureStartDate)) {
      return false;
    }

    LocalTime startTime = eachLecture.getStartTime();
    LocalTime endTime = eachLecture.getEndTime();
    Timestamp startTimestamp = Timestamp.valueOf(now.toLocalDate().atTime(startTime).minusMinutes(10));
    Timestamp endTimestamp = Timestamp.valueOf(now.toLocalDate().atTime(endTime).plusMinutes(10));

    return now.isAfter(startTimestamp.toLocalDateTime()) && now.isBefore(endTimestamp.toLocalDateTime());
  }

  /**
   * 出席を登録する
   *
   * @param lectureId
   * @param studentName
   */
  private void registerAttendance(int lectureId, String studentName) {
    Attend attend = new Attend();
    attend.setEachLectureId(lectureId);
    attend.setName(studentName);

    // 複数回の講義に出席しつつ、同じ講義回には1度しか出席できないようにする
    // 授業回にnullが存在する場合はなにもしない
    if (attendMapper.checkAttend(attend) >= 1) {
      return;
    }
    attendMapper.addAttend(attend);
  }

  /**
   * 出席ページ
   * 講義のパスワードが間違っていた場合はreattendにリダイレクトする
   */
  @GetMapping("attend")
  public String attend(@RequestParam int id, ModelMap model) {
    String name = lectureMapper.getName(id);

    model.addAttribute("id", id);
    model.addAttribute("name", name);

    return "attend.html";
  }

  /**
   * 出席ページ
   * 講義のパスワードが間違っていた場合はreattendにリダイレクトする
   */
  @GetMapping("reattend")
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
  @GetMapping("lecture")
  public String lecture(HttpServletRequest request, ModelMap model) {
    int id = (int) request.getSession().getAttribute("lecture_id");
    String name = lectureMapper.getName(id);

    model.addAttribute("name", name);
    model.addAttribute("id", id);

    String message = lectureMapper.getMessage(id);
    model.addAttribute("message", message);

    String send_time = lectureMapper.getSend_time(id);
    model.addAttribute("send_time", send_time);

    ArrayList<Question> questions = questionMapper.getQuestions(id);
    model.addAttribute("questions", questions);

    ArrayList<EachLecture> eachLectures = eachLectureMapper.getEachLectures(id);
    model.addAttribute("each_lectures", eachLectures);

    return "lecture.html";
  }

  /**
   * 問題に対する回答を登録する
   */
  @PostMapping("reply")
  public String reply(HttpServletRequest request, Model model, Principal prin) {
    int id = Integer.parseInt(request.getParameter("id"));
    String reply = request.getParameter("reply");
    String name = prin.getName();
    int question_id = Integer.parseInt(request.getParameter("question_id"));
    int number = Integer.parseInt(request.getParameter("number"));
    int each_lecture_id = eachLectureMapper.getId(id, number);

    Reply replyObj = new Reply();
    replyObj.setName(name);
    replyObj.setQuestion_id(question_id);
    replyObj.setReply(reply);
    replyObj.setEach_lecture_id(each_lecture_id);

    /* 同じ学生が挿入されないようになっているので例外処理 */
    try {
      replyMapper.setReply(replyObj);
    } catch (Exception e) {
    }

    request.getSession().setAttribute("lecture_id", id);
    return "redirect:/student/lecture";
  }

  /**
   * 講義ページ
   */
  @PostMapping("each_lecture")
  public String eachLecture(HttpServletRequest request, ModelMap model) {
    int id = Integer.parseInt(request.getParameter("lecture_id"));
    int number = Integer.parseInt(request.getParameter("lecture_number"));
    String name = lectureMapper.getName(id);
    model.addAttribute("id", id);

    model.addAttribute("name", name);
    model.addAttribute("number", number);
    int each_lecture_id = eachLectureMapper.getId(id, number);

    ArrayList<Question> questions = questionMapper.getQuestions(each_lecture_id);
    model.addAttribute("questions", questions);

    return "each_lecture.html";
  }
}
