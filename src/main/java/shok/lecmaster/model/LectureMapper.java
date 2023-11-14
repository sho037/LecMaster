package shok.lecmaster.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;

import java.util.ArrayList;

@Mapper
public interface LectureMapper {
  /**
   * 講義一覧を取得する
   *
   * @return 講義一覧 ArrayList<Lecture>
   */
  @Select("SELECT * FROM lecture;")
  ArrayList<Lecture> getLectures();

  @Select("SELECT name FROM lecture WHERE id =${id}")
  String getName(int id);

  /**
   * 講義のパスワードを取得する
   *
   * @param id
   * @return password
   */
  @Select("SELECT password FROM lecture WHERE id = ${id}")
  String getPassword(int id);

    /**
   * 生徒へのメッセージを取得する
   *
   * @param id
   * @return password
   */
  @Select("SELECT message FROM lecture WHERE id = ${id}")
  String getMessage(int id);

  /**
   * パスワードを設定する
   *
   * @param id       講義ID
   * @param password パスワード
   */
  @Update("UPDATE lecture SET password = #{password} WHERE id = #{id}")
  void setPassword(@Param("id") int id, @Param("password") String password);

  /**
   * 生徒へのメッセージを登録する
   *
   * @param id      講義ID
   * @param message 生徒へのメッセージ
   */
  @Update("UPDATE lecture set message = #{message} WHERE id = #{id};")
  void setMessage(@Param("id") int id, @Param("message") String message);

  /**
   * 生徒への問題を登録する
   *
   * @param lecture_id 講義ID
   * @param question   生徒への問題
   */

  @Insert("INSERT INTO question (lecture_id,question,answer) VALUES (#{lecture_id},#{question},#{answer});")
  void setQuestion(@Param("lecture_id") int lectuer_id, @Param("question") String question,
      @Param("answer") String answer);
}
