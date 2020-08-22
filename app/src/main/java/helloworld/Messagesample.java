package helloworld;

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
  private Long id;
  private String message;

  public Long getId() {
    return id;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
}
