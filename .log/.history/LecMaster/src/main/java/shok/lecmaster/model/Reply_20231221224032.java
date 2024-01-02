package shok.lecmaster.model;

public class Reply {
  int id;
  String name;
  int question_id;
  int each_lecture_id;
  String reply;


  public int getQuestion_id() {
    return question_id;
  }
  public void setQuestion_id(int question_id) {
    this.question_id = question_id;
  }
  


  
  public void setEach_lecture_id(int each_lecture_id) {
    this.each_lecture_id = each_lecture_id;
  }
 
  public int getEach_lecture_id() {
    return each_lecture_id;
  }
  // セッター
  public void setId(int id) {
    this.id = id;
  }
  public void setName(String name) {
    this.name = name;
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

  public String getReply() {
    return reply;
  }
}
