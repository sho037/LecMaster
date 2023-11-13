package shok.lecmaster.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.ArrayList;

@Mapper
public interface AttendMapper {
  /**
   * 出席者一覧を取得する
   *
   * @return 講義一覧 ArrayList<Attend>
   */
  @Select("SELECT * FROM attend WHERE lecture_id = #{id};")
  ArrayList<Attend> getAttends(int id);
}
