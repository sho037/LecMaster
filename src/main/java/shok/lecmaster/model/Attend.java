package shok.lecmaster.model;

public class Attend {
  int id;
  int each_lecture_id;
  String name;

  // セッター
  public void setId(int id) {
    this.id = id;
  }
  public void setEachLectureId(int lecture_id) {
    this.each_lecture_id = lecture_id;
  }
  public void setName(String name) {
    this.name = name;
  }

  // ゲッター
  public int getId() {
    return id;
  }
  public int getEachLectureId() {
    return each_lecture_id;
  }
  public String getName() {
    return name;
  }

}
