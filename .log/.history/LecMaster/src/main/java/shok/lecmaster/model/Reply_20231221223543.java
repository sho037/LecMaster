package shok.lecmaster.model;

public class Reply {
  int id;
  String name;
  int question_id;
  int lecture_id;
  int each_lecture_id;
  String reply;


  public void setLecture_id(int lecture_id) {
    this.lecture_id = lecture_id;
  }
  public void setEach_lecture_id(int each_lecture_id) {
    this.each_lecture_id = each_lecture_id;
  }
  public int getLecture_id() {
    return lecture_id;
  }
  public int getEach_lecture_id() {
    return each_lecture_id;
  }
  // セッター
  public void setId(int id) {
    this.id = id;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setLectureId(int lecture_id) {
    this.lecture_id = lecture_id;
  }
  public void setReply(String reply) {
    this.reply = reply;
  }

  // ゲッター
  public int getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public int getLectureId() {
    return lecture_id;
  }
  public String getReply() {
    return reply;
  }
}
