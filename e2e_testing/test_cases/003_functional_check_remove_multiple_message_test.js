/*
 * 003_functional_check_remove_multiple_message_test.js
 * Copyright (c) 2020 SF DU Team, I&I Company, Rakuten, Inc.
 *
 * https://codecept.io/helpers/Puppeteer/
 */

Feature('[003 Functional] Check Remove Multiple message');

Scenario('Check Remove Multiple message', async (I) => {

  let assert = require('assert');
  let count_to_add = 5;

  I.amOnPage('/');

  I.seeElement({xpath: "//input[@name='message']"});
  I.seeElement({xpath: "//input[@value='Submit']"});
  I.seeElement({xpath: "//input[@value='Reset']"});
  I.seeElement({xpath: "//input[@value='Remove']"});

  let before_message_checkbox_ids = await I.grabAttributeFrom({name: "message_checkbox"}, 'id');
  let before_message_count = before_message_checkbox_ids.length;

  // add messages
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
    I.scrollPageToBottom();
  }
  //I.wait(10);

  // remove multiple message at once
  let removed_id = new Array();
  let message_checkbox_ids = await I.grabAttributeFrom({name: "message_checkbox"}, 'id');
  for (let i=0; i<count_to_add; i++) {
    let target_id = message_checkbox_ids.pop();
    I.seeElement({id: target_id});
    I.checkOption({id: target_id });
    removed_id.push(target_id)
  }
  I.click({xpath: "//input[@value='Remove']"});
  I.waitForElement({xpath: "//input[@value='Submit']"}, 30);

  for(let i=0; i<removed_id.length; i++) {
    I.dontSee({id: removed_id[i]});
  }

  let after_message_checkbox_ids = await I.grabAttributeFrom({name: "message_checkbox"}, 'id');
  let after_message_count = before_message_checkbox_ids.length;
  assert.equal(before_message_count, after_message_count);

});


