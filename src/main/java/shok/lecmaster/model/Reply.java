package shok.lecmaster.model;

public class Reply {
  int id;
  String name;
  int question_id;
  int lecture_id;
  String reply;

  // セッター
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQuestion_id(int question_id) {
    this.question_id = question_id;
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

  public int getQuestion_id() {
    return question_id;
  }

  public int getLecture_id() {
    return lecture_id;
  }

  public String getReply() {
    return reply;
  }
}