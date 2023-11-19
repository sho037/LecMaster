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
  @Insert("INSERT INTO attend (lecture_id, name) VALUES (#{lecture_id}, #{name});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void addAttend(Attend attend);
}
