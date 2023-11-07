package shok.lecmaster.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface LectureMpper {
  /**
   * 講義一覧を取得する
   *
   * @return 講義一覧 ArrayList<Lecture>
   */
  @Select("SELECT * FROM lecture;")
  ArrayList<Lecture> getLectures();

  @Select("SELECT name FROM lecture WHERE id =${id}")
  String getName(int id);
}
