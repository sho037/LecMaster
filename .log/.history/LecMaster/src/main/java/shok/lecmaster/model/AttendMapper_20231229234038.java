package shok.lecmaster.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import java.util.ArrayList;

@Mapper
public interface AttendMapper {
  /**
   * 出席者一覧を取得する
   *
   * @return 出席者一覧 ArrayList<Attend>
   */
  @Select("SELECT * FROM attend WHERE lecture_id = #{id};")
  ArrayList<Attend> getAttends(int id);

  /**
   * 出席者を追加する
   *
   * @param attend 出席者
   */
  @Insert("INSERT INTO attend (each_lecture_id, name) VALUES (#{each_lecture_id}, #{name});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void addAttend(Attend attend);

  /**
   * 出席してるか確認する
   *
   * @param attend 出席者
   */

  @Select("SELECT COUNT(*) FROM attend WHERE each_lecture_id = #{each_lecture_id} AND name = #{name};")
  int checkAttend(Attend attend);

  /**
   * 特定の講義の出席者を取得する
   */
  @Select("SELECT * FROM attend WHERE each_lecture_id = #{each_lecture_id};")
  

}
