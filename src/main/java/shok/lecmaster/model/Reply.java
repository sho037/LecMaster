package shok.lecmaster.model;

public class Reply {
  int id;
  String name;
  int each_question_id;
  int lecture_id;
  String reply;

  // セッター
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEach_question_id(int question_id) {
    this.each_question_id = question_id;
  }

  public void setLecture_id(int lecture_id) {
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

  public int getEach_question_id() {
    return each_question_id;
  }

  public int getLecture_id() {
    return lecture_id;
  }

  public String getReply() {
    return reply;
  }
}
