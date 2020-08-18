package com.example.helloworld;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class HelloworldServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    System.out.println("HelloworldServletInitializer#configure");
    return application.sources(HelloworldApplication.class);
  }
}
