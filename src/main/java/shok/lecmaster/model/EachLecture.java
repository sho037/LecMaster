package shok.lecmaster.model;

import java.sql.Date;

public class EachLecture {
  int id;
  int number;
  int lecture_id;
  Date start_date;

  // セッター
  public void setId(int id) {
    this.id = id;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public void setLecture_id(int lecture_id) {
    this.lecture_id = lecture_id;
  }

  public void setStart_date(Date start_date) {
    this.start_date = start_date;
  }

  // ゲッター
  public int getId() {
    return id;
  }

  public int getNumber() {
    return number;
  }

  public int getLecture_id() {
    return lecture_id;
  }

  public Date getStart_date() {
    return start_date;
  }

}
