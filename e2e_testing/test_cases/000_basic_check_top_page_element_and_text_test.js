/*
 * 000_basic_check_top_page_element_and_text_test.js
 * Copyright (c) 2020 SF DU Team, I&I Company, Rakuten, Inc.
 *
 * https://codecept.io/helpers/Puppeteer/
 */

Feature('[000 Basic] Check Top Page Element and Text');

Scenario('Check Top Page Element and Text', async (I) => {

  I.amOnPage('/');

  let message_count = await I.grabNumberOfVisibleElements({name: "message_checkbox"});

  I.see('Hello, world.');
  I.see('System Property: dev_or_production');
  I.see('application.yml: Hello, application yaml.');
  I.see('Mode: CREATE NEW');
  I.see('Message :');
  I.see(`TOTAL: ${message_count} Records`);

  I.seeElement({xpath: "//img[@src='./assets/sample-300x300.jpg']"})
  I.seeElement({xpath: "//input[@name='message']"});
  I.seeElement({xpath: "//input[@value='Submit']"});
  I.seeElement({xpath: "//input[@value='Reset']"});
  I.seeElement({xpath: "//input[@value='Remove']"});

});


