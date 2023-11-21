package shok.lecmaster.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface ReplyMapper {
  /**
   * 生徒の回答を登録する
   */
  @Insert ("INSERT INTO reply (name,question_id,reply) VALUES (#{name},#{question_id},#{reply});")
  void setReply(Reply reply);
}
