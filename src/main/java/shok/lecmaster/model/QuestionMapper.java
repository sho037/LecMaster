package shok.lecmaster.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;

import java.util.ArrayList;

@Mapper
public interface QuestionMapper {
  /**
   * 生徒への問題を登録する
   *
   * @param lecture_id 講義ID
   * @param question   生徒への問題
   */

  @Insert("INSERT INTO question (lecture_id,question,answer) VALUES (#{lecture_id},#{question},#{answer});")
  void setQuestion(@Param("lecture_id") int lectuer_id, @Param("question") String question,
      @Param("answer") String answer);

  @Select("SELECT * FROM question WHERE lecture_id = ${id}")
  ArrayList<Question> getQuestions(int id);
}
