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

  /**
   * 特定の授業のすべて講義回を取得する
   *
   * @return
   */
  @Select("SELECT * FROM eachLecture where lecture_id=  #{id};")
  ArrayList<EachLecture> getEachLectures(int id);

  /**
   * 特定の授業の特定の講義回を取得する
   *
   * @param lecture_id
   * @return number
   *
   */

  @Select("SELECT number FROM eachLecture where lecture_id=  #{lecture_id};")
  int getNum(int lecture_id);

}
