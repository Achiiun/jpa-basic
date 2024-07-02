package hellojpa;

import jakarta.persistence.*;

import java.util.Date;

@Entity
//@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
//@TableGenerator(name = "MEMBER_SEQ_GENERATOR", table = "MY_SEQUENCES", pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member {

  @Id
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
//  @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", nullable = false)
  private String username;

  public Member() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
