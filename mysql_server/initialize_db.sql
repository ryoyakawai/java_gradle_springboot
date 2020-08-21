CREATE TABLE message_sample
(
  message_id    INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  message       VARCHAR(255) NOT NULL,
  created_date  TIMESTAMP not null default current_timestamp,
  updated_date  TIMESTAMP not null default current_timestamp on update current_timestamp
);
INSERT INTO message_sample(message) VALUES('hello world!! 0');
INSERT INTO message_sample(message) VALUES('hello world!! 1');
INSERT INTO message_sample(message) VALUES('hello world!! 2');
INSERT INTO message_sample(message) VALUES('hello world!! 3');
