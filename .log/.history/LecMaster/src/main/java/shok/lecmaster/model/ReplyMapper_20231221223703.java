package shok.lecmaster.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Array;

import org.apache.ibatis.annotations.Insert;
import java.util.ArrayList;

@Mapper
public interface ReplyMapper {
  /**
   * 生徒の回答を登録する
   */
  @Insert("INSERT INTO reply (name,question_id,lecture_id,reply) VALUES (#{name},#{question_id},#{lecture_id},#{reply});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void setReply(Reply reply);

  /**
   * 生徒の回答を取得する
   */
  @Select("SELECT * FROM reply WHERE lecture_id = ${id} order by question_id;")
  ArrayList<Reply> getReply(int id);

  /**
   * 生徒の回答を登録する
   */
  @Insert("INSERT INTO reply (name,question_id,each_lecture_id,reply) VALUES (#{name},#{question_id},#{each_lecture_id},#{reply});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void setReplyEach(Reply reply);
}
