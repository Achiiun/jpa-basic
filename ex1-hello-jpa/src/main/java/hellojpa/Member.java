package hellojpa;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Member extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "MEMBER_ID")
  private Long id;

  @Column(name = "USERNAME")
  private String username;

  //기간
  @Embedded
  private Period workPeriod;

  //주소
  @Embedded
  private Address homeAddress;

  @ElementCollection
  @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
  @JoinColumn(name = "MEMBER_ID"))
  @Column(name = "FOOD_NAME")
  private Set<String> favoriteFoods = new HashSet<>();

//  @ElementCollection
//  @CollectionTable(name = "ADDRESS", joinColumns =
//  @JoinColumn(name = "MEMBER_ID"))
//  private List<Address> addressHistory = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "MEMBER_ID")
  private List<AddressEntity> addressHistory = new ArrayList<>();

//  @Embedded
//  @AttributeOverrides({
//    @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
//    @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
//    @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))
//  })
//  private Address workAddress;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Period getWorkPeriod() {
    return workPeriod;
  }

  public void setWorkPeriod(Period workPeriod) {
    this.workPeriod = workPeriod;
  }

  public Address getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
  }

  public Set<String> getFavoriteFoods() {
    return favoriteFoods;
  }

  public void setFavoriteFoods() {
    this.favoriteFoods = favoriteFoods;
  }

  public void setFavoriteFoods(Set<String> favoriteFoods) {
    this.favoriteFoods = favoriteFoods;
  }

  public List<AddressEntity> getAddressHistory() {
    return addressHistory;
  }

  public void setAddressHistory(List<AddressEntity> addressHistory) {
    this.addressHistory = addressHistory;
  }
}
