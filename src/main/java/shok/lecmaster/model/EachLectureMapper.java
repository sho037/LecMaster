package shok.lecmaster.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import java.util.ArrayList;

@Mapper
public interface EachLectureMapper {
  /**
   * それぞれの講義を追加する
   *
   * @param eachLecture
   * @return
   */
  @Insert("INSERT INTO eachLecture (number, lecture_id, start_date) VALUES (#{number}, #{lecture_id}, #{start_date});")
  void addEachLecture(EachLecture eachLecture);
}
