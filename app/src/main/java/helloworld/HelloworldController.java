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
  public ModelAndView index() {
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
      //for (String key : item.keySet()) { System.out.println(" >>>> " + key + " :: " + item.get(key)); }
      System.out.printf("[DISP] ID=[%s] Message=[%s] CreatedDate=[%s] UpdatedDate=[%s]\n",
                        item.get("message_id"), item.get("message"), item.get("created_date"), item.get("updated_date"));
    }

    // 表示するデータをセット
    ModelAndView mav = new ModelAndView();
    mav.addObject("systemPropertyMessage", systemPropertyMessage);
    mav.addObject("applicationYamlMessage", applicationYamlMessage);
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
  //public String addNewMessage(@RequestParam String message) {
  public String addNewMessage (@RequestParam String message) {
    //public @ResponseBody String addNewUser (@RequestParam String message) {
    List<Messagesample> message_sample = messagesampleRepository.findAll();
    int msg_szie_new = message_sample.size() + 1;
    String message_to_save =  message + " :: " + msg_szie_new;

    int affectedRow = messagesampleService.insertOne( message);

    System.out.printf("[SAVE] Saved!! message=[%s]\n", message_to_save);
    //return "Saved";
    return "redirect:/";
  }

  @PostMapping(path="/db/removemessages")
  public String removeMessage(@RequestParam("message_checkbox") List<String> message_checkbox) {
    if (message_checkbox != null) {
      for( String idx: message_checkbox ) {
        int int_idx = Integer.parseInt(idx);
        messagesampleRepository.deleteById(new Long(int_idx));
        System.out.printf("[REMOVE] idx=[%s]\n", int_idx);
      }
    }
    return "redirect:/";
  }

  /**
   * DBのデータを全取得してJSONでレスポンスを返す
   */
  @GetMapping(path="/api/db/all")
  public @ResponseBody Iterable<Messagesample> getAllMessages() {
    // This returns a JSON
    return messagesampleRepository.findAll();
  }

}
