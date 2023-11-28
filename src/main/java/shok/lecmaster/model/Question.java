package shok.lecmaster.model;

public class Question {
  int id;
  int each_lecture_id;
  String question;
  String answer;
  public int getId() {
    return id;
  }
  public int getEach_lecture_id() {
    return each_lecture_id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public void setEach_lecture_id(int lecture_id) {
    this.each_lecture_id = lecture_id;
  }
  public void setQuestion(String question) {
    this.question = question;
  }
  public void setAnswer(String answer) {
    this.answer = answer;
  }
  public String getQuestion() {
    return question;
  }
  public String getAnswer() {
    return answer;
  }


}
