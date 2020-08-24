package helloworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesampleService {

  @Autowired
  private MessagesampleRepository messagesampleRepository;

  @Autowired
  private MessagesampleDaoImpl messagesampleDaoImpl;

  public List<Map<String, Object>> findAll() {
    List<Map<String, Object>> result = messagesampleDaoImpl.findAllDao();
    return result;
  }

  public int insertOne( String message) {
    int affectedRow = messagesampleDaoImpl.insertOneDao( message );
    return affectedRow;
  }
}
