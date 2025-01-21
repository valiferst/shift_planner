package at.qe.skeleton.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

/**
 * Entity representing users.
 *
 * This class is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
@Entity
public class Userx implements Persistable<Long>, Serializable, Comparable<Userx> {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(optional = false)
  private Userx createUser;
  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createDate;
  @ManyToOne(optional = true)
  private Userx updateUser;
  @UpdateTimestamp
  private LocalDateTime updateDate;
  
  @Column(unique = true, nullable = false, length = 100)
  private String username;
  private String password;
  
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  
  @ElementCollection(targetClass = UserxRole.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "Userx_UserxRole")
  @Enumerated(EnumType.STRING)
  private Set<UserxRole> roles;

  @ManyToMany
  private Set<Shift> shifts;

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private Set<Absence> absences;
  boolean enabled;
  
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Set<UserxRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserxRole> roles) {
    this.roles = roles;
  }

  public Userx getCreateUser() {
    return createUser;
  }

  public void setCreateUser(Userx createUser) {
    this.createUser = createUser;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public Userx getUpdateUser() {
    return updateUser;
  }
  public void setUpdateUser(Userx updateUser) {
    this.updateUser = updateUser;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
  }

  public Set<Absence> getAbsences() {
    return absences;
  }

  public void setAbsences(Set<Absence> absences) {
    this.absences = absences;
  }

  public Set<Shift> getShifts() {
    return shifts;
  }

  public void setShifts(Set<Shift> shifts) {
    this.shifts = shifts;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + Objects.hashCode(this.getId());
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Userx)) {
      return false;
    }
    final Userx other = (Userx) obj;
    return Objects.equals(this.getId(), other.getId());
  }

  @Override
  public String toString() {
    return "at.qe.skeleton.model.User[ id=" + username + " ]";
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean isNew() {
    return (null == id);
  }

  @Override
  public int compareTo(Userx o) {
    return this.id.compareTo(o.getId());
  }

}