# java_gradle_springboot_helloworld

## What is this?

This is sample web application by using Spring Boot.

## How to run the app

```bash
$ git clone https://github.com/ryoyakawai/java_gradle_springboot_helloworld.git
$ cd java_gradle_springboot_helloworld
$ docker-compose up --build
```

Then access to `http://localhost:8080` from your web browser.


## How to run the End to End testing

Make sure the app is running at `http://localhost:8080`.

```bash
$ cd e2e_testing;
$ npm install;
$ npx run --steps;
```

If the app runs at different URL from `http://localhost:8080`, add parameter to override base URL.

```bash
$ npx codeceptjs run --steps --override '{"helpers": {"Puppeteer": {"url": "http://host_hoge_hoge:8080"}}}';
```

As specifying parameter like below, allow to watch all of process on browser window that automatically popped up at execution.

```bash
$ npx codeceptjs run --steps --override '{"helpers": {"Puppeteer": {"show": true}}}';
```

# more detail

Link here is explaining what is in this repository. (Japanese only)
- [Spring Boot + GradleでWebサイトを作ってみる（jdk1.8.x） - Qiita](https://qiita.com/ryoyakawai/items/092a83db06d9b54312ca)
