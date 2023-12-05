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
   * 講義を設定する
   *
   * @param name
   * @param password
   * @return 講義ID
   */
  @Insert("INSERT INTO lecture (name, password) VALUES (#{name}, #{password});")
  int addLecture(@Param("name") String name, @Param("password") String password);

  /**
   * 講義IDを取得する
   *
   * @param id
   * @return
   */
  @Select("SELECT id FROM lecture WHERE name = #{name};")
  String getLectureId(@Param("name") String name);

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

}
