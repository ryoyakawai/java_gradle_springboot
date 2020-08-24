package helloworld;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import helloworld.Messagesample;

@Repository
public class MessagesampleDaoImpl implements MessagesampleDao {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public MessagesampleDaoImpl() {
    super();
  }

  public MessagesampleDaoImpl(EntityManager entityManager) {
    this();
    this.entityManager = entityManager;
  }

  @Override
  public Map<String, Object> findOneDao() {
    String query = "SELECT * FROM message_sample limit 1";
    Map<String, Object> result = jdbcTemplate.queryForMap(query);
    return result;
  }

  @Override
  public List<Map<String, Object>> findAllDao() {
    String query = "SELECT * FROM message_sample";
    List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
    return result;
  }

  @Override
  public int insertOneDao( String message ) {
    String query = "INSERT INTO message_sample(message)"
      + " VALUES(?)";

    int affectedRow = jdbcTemplate.update(query, message);
    return affectedRow;
  }

}
