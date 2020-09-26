/*
 * 001_functional_check_submit_text_test.js
 * Copyright (c) 2020 SF DU Team, I&I Company, Rakuten, Inc.
 *
 * https://codecept.io/helpers/Puppeteer/
 */
Feature('[001 Functional] Check Submit Text');

Scenario('Check Submit Text', async (I) => {

  let assert = require('assert');
  let count_to_add = 5;

  I.amOnPage('/');

  I.seeElement({xpath: "//input[@name='message']"});
  I.seeElement({xpath: "//input[@value='Submit']"});
  I.seeElement({xpath: "//input[@value='Reset']"});
  I.seeElement({xpath: "//input[@value='Remove']"});

  let original_message_checkbox_count = await I.grabNumberOfVisibleElements({name: "message_checkbox"});

  for (let i=0; i<count_to_add; i++) {
    // input one text
    let before_message_checkbox_ids = await I.grabAttributeFrom({name: "message_checkbox"}, 'id');
    let before_message_count = before_message_checkbox_ids.length;
    let input_text = 'INPUT::' + new Date();
    I.fillField({xpath: "//input[@name='message']"}, input_text);
    I.click({xpath: "//input[@value='Submit']"});
    I.waitForElement({xpath: "//input[@value='Submit']"}, 30);

    I.see(input_text);
    let message_checkbox_ids = await I.grabAttributeFrom({name: "message_checkbox"}, 'id');
    let message_checkbox_count = message_checkbox_ids.length;
    assert.equal(before_message_count + 1, message_checkbox_count);
    let target_id = message_checkbox_ids.slice().pop();
    I.checkOption({id: target_id });
    I.click({xpath: "//input[@value='Remove']"});
    I.waitForElement({xpath: "//input[@value='Submit']"}, 30);

    I.dontSee({id: target_id});
  }

  let after_test_message_checkbox_count = await I.grabNumberOfVisibleElements({name: "message_checkbox"});
  assert.equal(original_message_checkbox_count, after_test_message_checkbox_count);

});


