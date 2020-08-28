package helloworld;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import helloworld.MessagesampleService;
import helloworld.MessagesampleDaoImpl;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(HelloworldController.class)
class HelloworldErrorControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MessagesampleRepository mMessagesampleRepository;

  @MockBean
  private MessagesampleDaoImpl mMessagesampleDaoImpl;

  @MockBean
  private MessagesampleService mMessagesampleService;

  @MockBean
  private EntityManagerFactory mEntityManagerFactory;

  @Test
  public void get_test_exception() throws Exception {
    assertThatThrownBy(() ->
                       this.mockMvc.perform(get("/exception"))
                       ).hasCause(new RuntimeException("exception of /exception"));
  }

  @Test
  public void post_test_db_add_insert_success() throws Exception {
    String message = "";
    int ret_updat_one = 1;
    String messageinputmode = "INSERT";
    when(mMessagesampleService.updateOne(null, message)).thenReturn(ret_updat_one);
    this.mockMvc.perform(post("/db/add")
                         .param("messageinputmode", messageinputmode)
                         .param("message", message))
      .andExpect(status().is(302))
      .andExpect(redirectedUrl("/"));
  }

  @Test
  public void post_test_db_add_update_fail() throws Exception {
    assertThatThrownBy(() -> {
        String message = "";
        int ret_updat_one = 0;
        String messageinputmode = "UPDATE";
        when(mMessagesampleService.updateOne(null, message)).thenReturn(ret_updat_one);
        this.mockMvc.perform(post("/db/add")
                             .param("messageinputmode", messageinputmode)
                             .param("message", message));
      }).hasCause(new RuntimeException("[exception] msg=[no message_id specified]"));
  }

  @Test
  public void post_test_db_add_update_success() throws Exception {
    String message = "";
    int ret_updat_one = 1;
    Long message_id = Long.valueOf((int) 0);
    String messageinputmode = "UPDATE";
    when(mMessagesampleService.updateOne(message_id, message)).thenReturn(ret_updat_one);
    this.mockMvc.perform(post("/db/add")
                         .param("messageinputmode", messageinputmode)
                         .param("message_id", Long.toString( message_id ))
                         .param("message", message))
      .andExpect(status().is(302))
      .andExpect(redirectedUrl("/"));
  }

  @Test
  public void post_test_removemessages_fail() throws Exception {
    Long message_id = Long.valueOf((int) 1);
    int ret_delete_one = 1;
    when(mMessagesampleService.deleteOne(message_id)).thenReturn(ret_delete_one);
    this.mockMvc.perform(post("/db/removemessages"))
      //.andExpect(status().isBadRequest());
      .andExpect(status().is(400));
  }

  @Test
  public void post_test_removemessages_success() throws Exception {
    //List<String> message_checkbox = Arrays.asList("1");
    String[] message_checkbox = new String[1];

    Long message_id = Long.valueOf((int) 1);
    int ret_delete_one = 1;
    when(mMessagesampleService.deleteOne(message_id)).thenReturn(ret_delete_one);
    this.mockMvc.perform(post("/db/removemessages").param("message_checkbox", "1"))
      .andExpect(status().is(302))
      .andExpect(redirectedUrl("/"));
  }

  @Test
  public void post_test_api_v1_db_all_success() throws Exception
  {
    Long message_id = 1L;
    String message = "AAAA";

    Messagesample mMessagesample = new Messagesample();
    mMessagesample.setMessage_id(message_id);
    mMessagesample.setMessage(message);
    mMessagesample.setCreated_date();
    mMessagesample.setUpdated_date();
    List<Messagesample> expected_object = Arrays.asList(mMessagesample);

    when(mMessagesampleRepository.findAll()).thenReturn(expected_object);
    this.mockMvc.perform(get("/api/v1/db/all"))
      .andExpect(status().is(200))
      .andExpect(content().string(containsString("[{\"message_id\":" + Long.toString(message_id) + ",\"message\":\"" + message + "\"")));
  }

  @Test
  public void post_test_api_v1_db_add_success() throws Exception
  {
    String message = "AAAA";
    int ret_insert_one = (int) 1;

    when(mMessagesampleService.insertOne(message)).thenReturn(ret_insert_one);

    this.mockMvc.perform(post("/api/v1/db/add").param("message", message))
      .andExpect(status().is(200))
      .andExpect(content().string(containsString("Saved : " + String.valueOf(ret_insert_one))));
  }

}

