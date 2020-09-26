/*
 * 002_functional_check_edit_text_test.js
 * Copyright (c) 2020 SF DU Team, I&I Company, Rakuten, Inc.
 *
 * https://codecept.io/helpers/Puppeteer/
 */

Feature('[002_Functional] Check Edit Text');

Scenario('Check Edit Text', async (I) => {

  let assert = require('assert');

  I.amOnPage('/');

  I.seeElement({xpath: "//input[@name='message']"});
  I.seeElement({xpath: "//input[@value='Submit']"});
  I.seeElement({xpath: "//input[@value='Reset']"});
  I.seeElement({xpath: "//input[@value='Remove']"});

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

  let target_link_id = (message_checkbox_ids.slice().pop()).replace('checkbox', 'edit_link');
  I.click({id: target_link_id});

  let target_message_id = (message_checkbox_ids.slice().pop()).replace('checkbox', 'text');
  let text_message = await I.grabTextFrom({id: target_message_id});
  assert.equal(text_message, input_text);

  let new_input_text = '[EDIT] ' + text_message
  I.fillField({xpath: "//input[@name='message']"}, new_input_text);
  I.click({xpath: "//input[@value='Submit']"});
  let new_text_message = await I.grabTextFrom({id: target_message_id});
  assert.equal(new_text_message, new_input_text);

  let target_id = message_checkbox_ids.slice().pop();
  I.checkOption({id: target_id});
  I.click({xpath: "//input[@value='Remove']"});

  I.dontSee({id: target_id});

});


