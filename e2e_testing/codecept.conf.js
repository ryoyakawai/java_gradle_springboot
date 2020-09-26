const { setHeadlessWhen } = require('@codeceptjs/configure');

// turn on headless mode when running with HEADLESS=true environment variable
// export HEADLESS=true && npx codeceptjs run
setHeadlessWhen(process.env.HEADLESS);

exports.config = {
  tests: './test_cases/*_test.js',
  output: './output',
  helpers: {
    Puppeteer: {
      url: 'http://localhost:8080',
      // The parameter can be override by specofying option in comannd line vvv
      // npx codeceptjs run --steps --plugins allure --override '{"helpers": {"Puppeteer": {"url": "http://host.docker.internal:8080"}}}';
      show: false,
      windowSize: '1200x900',
      "chrome":{
        "args": ["--no-sandbox"]
      }
    }
  },
  include: {
    I: './steps_file.js'
  },
  bootstrap: null,
  mocha: {
    "reporterOptions": {
      "reportDir": "output",
      "uniqueScreenshotNames": "true"
    }
  },
  name: 'e2e_testing',
  plugins: {
    retryFailedStep: {
      enabled: true
    },
    screenshotOnFail: {
      enabled: true
    },
    allure: {
      enabled: true,
      outputDir: './output/allure/allure-results'
    }
  }
}
