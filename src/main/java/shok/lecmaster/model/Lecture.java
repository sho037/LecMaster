package shok.lecmaster.model;

public class Lecture {
  int id;
  String name;
  String password;

  // セッター
  public void setId(int id) {
    this.id = id;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  // ゲッター
  public int getId() {
    return this.id;
  }
  public String getName() {
    return this.name;
  }
  public String getPassword() {
    return this.password;
  }
}
