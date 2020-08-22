package helloworld;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

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

    List<Messagesample> message_sample = messagesampleRepository.findAll();
    for(Messagesample item : message_sample ) {
      System.out.printf("ID=[%s] Message=[%s]", item.getId(), item.getMessage());
      System.out.println();
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
  public @ResponseBody String addNewUser (@RequestParam String message) {
    Messagesample n_ms = new Messagesample();
    n_ms.setMessage("hogehoge");
    messagesampleRepository.save(n_ms);
    return "Saved";
  }

  @GetMapping(path="/db/all")
  public @ResponseBody Iterable<Messagesample> getAllMessages() {
    // This returns a JSON or XML with the users
    return messagesampleRepository.findAll();
  }

}
