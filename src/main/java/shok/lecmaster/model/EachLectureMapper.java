package shok.lecmaster.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.ArrayList;
import java.time.LocalTime;
import java.sql.Timestamp;

@Mapper
public interface EachLectureMapper {
  /**
   * それぞれの講義を追加する
   *
   * @param eachLecture
   * @return
   */
  @Insert("INSERT INTO eachLecture (number, lecture_id, start_date,start_time,end_time) VALUES (#{number}, #{lecture_id}, #{start_date},#{start_time},#{end_time});")
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

  /*
   * 授業の開始時間を取得する
   *
   */
     @Select("SELECT start_time FROM eachLecture where lecture_id=  #{lecture_id} AND number = 1;")
   LocalTime getStart_time(int lecture_id);

  /*
   * 授業の終了時間を取得する
   *
   */
    @Select("SELECT end_time FROM eachLecture where lecture_id=  #{lecture_id} AND number = 1;")
   LocalTime getEnd_time(int lecture_id);

   /*
    *授業の日付を取得する
    *
    */
    @Select("SELECT start_date FROM eachLecture where lecture_id = #{lecture_id}")
    Timestamp getStart_date(int lecture_id);

    /*
     * lecture_idとnumberからidを取得する
     */
    @Select("SELECT id FROM eachLecture where lecture_id = #{lecture_id} AND number = #{number}")
    int getId(int lecture_id, int number);
}
