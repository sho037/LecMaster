package shok.lecmaster.model;

public class Attend {
  int id;
  int each_lecture_id;
  String name;

  // セッター
  public void setId(int id) {
    this.id = id;
  }

  public void setEach_lecture_id(int lecture_id) {
    this.each_lecture_id = lecture_id;
  }

  public void setName(String name) {
    this.name = name;
  }

  // ゲッター
  public int getId() {
    return id;
  }

  public int getEach_lecture_id() {
    return each_lecture_id;
  }

  public String getName() {
    return name;
  }

}
