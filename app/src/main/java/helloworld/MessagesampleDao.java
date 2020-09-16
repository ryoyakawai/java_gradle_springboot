package helloworld;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface MessagesampleDao extends Serializable {
  public List<Map<String, Object>> findAllDao();

  public Map<String, Object> findOneDao();

  public int insertOneDao(String message);

  public int updateOneDao(Long message_id, String message);

  public int deleteOneDao(Long message_id);
}
