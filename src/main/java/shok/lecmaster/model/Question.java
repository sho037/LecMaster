package shok.lecmaster.model;

public class Question {
  int id;
  int each_lecture_id;
  String question;
  String answer;

  // セッター
  public void setId(int id) {
    this.id = id;
  }
  public void setEachLectureId(int lecture_id) {
    this.each_lecture_id = lecture_id;
  }
  public void setQuestion(String question) {
    this.question = question;
  }
  public void setAnswer(String answer) {
    this.answer = answer;
  }

  // ゲッター
  public int getId() {
    return id;
  }
  public int getEachLectureId() {
    return each_lecture_id;
  }
  public String getQuestion() {
    return question;
  }
  public String getAnswer() {
    return answer;
  }
}
