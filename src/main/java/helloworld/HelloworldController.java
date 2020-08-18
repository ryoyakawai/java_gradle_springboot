package com.example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloworldController {

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
    String systemPropertyMessage = System.getProperty("com.example.helloworld.message");

    // 表示するデータをセット
    ModelAndView mav = new ModelAndView();
    mav.addObject("systemPropertyMessage", systemPropertyMessage);
    mav.addObject("applicationYamlMessage", applicationYamlMessage);
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
}
