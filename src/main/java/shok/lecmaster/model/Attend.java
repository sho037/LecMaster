package shok.lecmaster.model;

public class Attend {
  int id;
  int lecture_id;
  String name;

  // セッター
  public void setId(int id) {
    this.id = id;
  }

  public void setLecture_id(int lecture_id) {
    this.lecture_id = lecture_id;
  }

  public void setName(String name) {
    this.name = name;
  }

  // ゲッター
  public int getId() {
    return id;
  }

  public int getLecture_id() {
    return lecture_id;
  }

  public String getName() {
    return name;
  }

}
