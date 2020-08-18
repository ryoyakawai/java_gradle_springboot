package com.example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Web アプリケーション全体のエラーコントローラー。
 * ErrorController インターフェースの実装クラス。
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}") // エラーページへのマッピング
public class HelloworldErrorController implements ErrorController {

  /**
   * エラーページのパス。
   */
  @Value("${server.error.path:${error.path:/error}}")
  private String errorPath;

  /**
   * エラーページのパスを返す。
   *
   * @return エラーページのパス
   */
  @Override
  public String getErrorPath() {
    return errorPath;
  }

  /**
   * レスポンス用の ModelAndView オブジェクトを返す。
   *
   * @param req リクエスト情報
   * @param mav レスポンス情報
   * @return HTML レスポンス用の ModelAndView オブジェクト
   */
  @RequestMapping
  public ModelAndView error(HttpServletRequest req, ModelAndView mav) {

    System.out.println("HelloWorldErrorController#error");

    // どのエラーでも 404 Not Found にする
    // 必要に応じてステータコードや出力内容をカスタマイズ可能
    HttpStatus status = HttpStatus.NOT_FOUND;
    mav.setStatus(status);
    mav.setViewName("error"); // error.html
    return mav;
  }
}
