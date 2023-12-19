package shok.lecmaster.model;

import java.time.LocalTime;
import java.sql.Timestamp;

public class EachLecture {
  int id;
  int number;
  int lecture_id;
  Timestamp start_date;
  LocalTime start_time;
  LocalTime end_time;

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
  public void setStartDate(Timestamp start_date) {
    this.start_date = start_date;
  }
  public void setStartTime(LocalTime start_time){
    this.start_time = start_time;
  }
  public void setEndTime(LocalTime end_time){
    this.end_time = end_time;
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
  public Timestamp getStartDate() {
    return start_date;
  }
  public LocalTime getStartTime(){
    return start_time;
  }
  public LocalTime getEndTime(){
    return end_time;
  }

}
