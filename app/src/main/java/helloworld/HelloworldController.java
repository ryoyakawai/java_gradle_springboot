package helloworld;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import helloworld.MessagesampleService;

@Controller
public class HelloworldController {

  @Autowired
  private MessagesampleRepository messagesampleRepository;

  @Autowired
  private MessagesampleService messagesampleService;

  @PersistenceContext
  EntityManager entityManager;

  /**
   * application.yml から取得したメッセージ。
   */
  @Value("${application.message}")
  private String applicationYamlMessage;

  /**
   * トップページのレスポンスを返す。
   *
   * @return ページ表示情報
   */
  @GetMapping("/")
  public ModelAndView index(@RequestParam Optional<String> message_id) {
    System.out.println("HelloworldController#index");

    // システム・プロパティから取得
    String systemPropertyMessage = System.getProperty("helloworld.greeting");

    /*
    // DBの値を全取得する (標準で利用可能なRepositoryを利用)
    List<Messagesample> all_messagesample = messagesampleRepository.findAll();
    for(Messagesample item : all_messagesample ) {
      System.out.printf("[DISP] ID=[%s] Message=[%s] CreatedDate=[%s] UpdatedDate=[%s]\n",
                        item.getMessage_id(), item.getMessage(), item.getCreated_date(), item.getUpdated_date());
    }
    */

    // DBの値を全取得する (独自実装したServiceを利用)
    List<Map<String, Object>> all_messagesample = messagesampleService.findAll();
    for (Map<String, Object> item : all_messagesample) {
      System.out.printf("[DISP] ID=[%s] Message=[%s] CreatedDate=[%s] UpdatedDate=[%s]\n",
                        item.get("message_id"), item.get("message"), item.get("created_date"), item.get("updated_date"));
    }

    String display_mode_text = "CREATE NEW";
    String message_input_mode = "INSERT";

    // GET parameterのハンドリング
    String str_message_id = "";
    String message_text_value = "";
    if(message_id.isPresent()) {
      str_message_id = message_id.get();
    }
    if(str_message_id != "") {
      Long long_message_id = Long.valueOf(Integer.parseInt(str_message_id));
      for (Map<String, Object> item : all_messagesample) {
        Long long_t_message_id = Long.valueOf((int) item.get("message_id"));
        String str_t_message = (String) item.get("message");
        if(long_message_id == long_t_message_id) {
          message_text_value = str_t_message;
        }
      }
      message_input_mode = "UPDATE";
      display_mode_text = "EDIT ID=[" + str_message_id + "]";
      System.out.printf("[MODE] EDIT ID=[%s] MESSAGE=[%s]\n", str_message_id, message_text_value);
    }

    // 表示するデータをセット
    ModelAndView mav = new ModelAndView();
    mav.addObject("systemPropertyMessage", systemPropertyMessage);
    mav.addObject("applicationYamlMessage", applicationYamlMessage);
    mav.addObject("messageInputMode", message_input_mode);
    mav.addObject("messageModeText", display_mode_text);
    mav.addObject("messageId", str_message_id);
    mav.addObject("messageTextValue", message_text_value);
    mav.addObject("messageTotalCount", all_messagesample.size());
    mav.addObject("messageSample", all_messagesample);

    // Thymeleaf テンプレートファイルを指定
    mav.setViewName("helloworld");

    return mav;
  }

  /**
   * エラーページを表示するテスト用メソッド。
   */
  @GetMapping("/exception/")
  public void exception() {
    System.out.println("HelloworldController#exception");
    throw new RuntimeException("This is a sample exception.");
  }

  @PostMapping(path="/db/add")
  public String addNewMessage (@RequestParam String messageinputmode,
                               @RequestParam Optional<String> message_id,
                               @RequestParam String message) {
    int affectedRow = 0;
    switch(messageinputmode) {
    case "UPDATE":
      if(message_id.isPresent()) {
        String str_message_id = message_id.get();
        Long long_message_id = Long.valueOf( str_message_id );
        affectedRow = messagesampleService.updateOne( long_message_id, message );
      }
      break;
    case "INSERT":
    default:
      List<Messagesample> message_sample = messagesampleRepository.findAll();
      int msg_szie_new = message_sample.size() + 1;
      String message_to_save =  message + " :: " + msg_szie_new;

      affectedRow = messagesampleService.insertOne( message );
      break;
    }

    System.out.printf("[SAVE] Saved!! message=[%s] affected=[%d]\n", message, affectedRow);

    return "redirect:/";
  }

  @PostMapping(path="/db/removemessages")
  public String deleteOneMessage (@RequestParam("message_checkbox") List<String> message_checkbox) {
    if (message_checkbox != null) {
      for( String idx: message_checkbox ) {
        Long message_id = Long.valueOf(Integer.parseInt(idx));
        int affectedRow = messagesampleService.deleteOne( message_id );
        System.out.printf("[REMOVE] idx=[%s]\n", idx);
      }
    }
    return "redirect:/";
  }

  /**
   * DBのデータを全取得してJSONでレスポンスを返す
   */
  @GetMapping(path="/api/v1/db/all")
  public @ResponseBody Iterable<Messagesample> getAllMessages() {
    // This returns a JSON
    return messagesampleRepository.findAll();
  }

  @PostMapping(path="/api/v1/db/add")
  public @ResponseBody String addNewMessageApi (@RequestParam String message) {
    List<Messagesample> message_sample = messagesampleRepository.findAll();
    int msg_szie_new = message_sample.size() + 1;
    String message_to_save =  message + " :: " + msg_szie_new;

    int affectedRow = messagesampleService.insertOne( message );

    System.out.printf("[SAVE] Saved!! message=[%s] affected=[%d]\n", message_to_save, affectedRow);

    return "Saved : " + affectedRow;
  }
}
