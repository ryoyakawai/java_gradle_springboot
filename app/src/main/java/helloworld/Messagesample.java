package helloworld;

import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="message_sample")
public class Messagesample {
  @Id
  @Column(name="message_id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long message_id;

  @Column(name="message", nullable = false)
  private String message;

  @Column(name="created_date", nullable = false)
  private Timestamp created_date;

  @Column(name="updated_date", nullable = false)
  private Timestamp updated_date;

  public Long getMessage_id() {
    return message_id;
  }

  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  public Timestamp getCreated_date() {
    return created_date;
  }
  public void setCreated_date() {
    this.created_date = new Timestamp(System.currentTimeMillis());
  }

  public Timestamp getUpdated_date() {
    return updated_date;
  }
  public void setUpdated_date() {
    this.updated_date = new Timestamp(System.currentTimeMillis());
  }

}
