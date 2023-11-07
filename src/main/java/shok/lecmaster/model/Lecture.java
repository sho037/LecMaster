package shok.lecmaster.model;

public class Lecture {
  int id;
  String name;

  // セッター
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  // ゲッター
  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}
