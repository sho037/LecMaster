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
  public void setLectureId(int lecture_id) {
    this.lecture_id = lecture_id;
  }
  public void setStartDate(Date start_date) {
    this.start_date = start_date;
  }

  // ゲッター
  public int getId() {
    return id;
  }
  public int getNumber() {
    return number;
  }
  public int getLectureId() {
    return lecture_id;
  }
  public Date getStartDate() {
    return start_date;
  }

}
