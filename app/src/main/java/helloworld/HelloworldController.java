package helloworld;

import java.util.List;
import java.util.Collections;

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

@Controller
public class HelloworldController {

  @Autowired
  private MessagesampleRepository messagesampleRepository;

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
    String systemPropertyMessage = System.getProperty("helloworld.message");

    // DBの値を全取得する
    List<Messagesample> message_sample = messagesampleRepository.findAll();
    for(Messagesample item : message_sample ) {
      System.out.printf("[DISP] ID=[%s] Message=[%s]\n", item.getId(), item.getMessage());
    }

    // 表示するデータをセット
    ModelAndView mav = new ModelAndView();
    mav.addObject("systemPropertyMessage", systemPropertyMessage);
    mav.addObject("applicationYamlMessage", applicationYamlMessage);
    mav.addObject("messageSample", message_sample);
    mav.setViewName("helloworld"); // ビュー名。Thymeleaf テンプレートファイルを指定

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
  //public String addNewUser(@RequestParam String message) {
  public String addNewUser (@RequestParam String message) {
    //public @ResponseBody String addNewUser (@RequestParam String message) {
    List<Messagesample> message_sample = messagesampleRepository.findAll();
    int msg_szie_new = message_sample.size() + 1;

    Messagesample n_ms = new Messagesample();
    String message_to_save =  message + " :: " + msg_szie_new;
    n_ms.setMessage(message_to_save);
    messagesampleRepository.save(n_ms);

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
  @GetMapping(path="/db/all")
  public @ResponseBody Iterable<Messagesample> getAllMessages() {
    // This returns a JSON
    return messagesampleRepository.findAll();
  }

}
