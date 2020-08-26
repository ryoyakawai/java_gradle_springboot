package helloworld;

import javax.persistence.EntityManagerFactory;

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

}

