package at.qe.skeleton.model;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Entity representing absences.
 *
 */
@Entity
public class Absence implements Persistable<Long>, Serializable, Comparable<Absence> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Userx user;

    private LocalDateTime validFrom;

    private LocalDateTime validUntil;

    private LocalTime absentFrom;

    private LocalTime absentUntil;

    private DayOfWeek absentDay;

    public Userx getUser() {
        return user;
    }

    public void setUser(Userx user) {
        this.user = user;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public LocalTime getAbsentFrom() {
        return absentFrom;
    }

    public void setAbsentFrom(LocalTime absentFrom) {
        this.absentFrom = absentFrom;
    }

    public LocalTime getAbsentUntil() {
        return absentUntil;
    }

    public void setAbsentUntil(LocalTime absentUntil) {
        this.absentUntil = absentUntil;
    }

    public DayOfWeek getAbsentDay() {
        return absentDay;
    }

    public void setAbsentDay(DayOfWeek absentDay) {
        this.absentDay = absentDay;
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
        if (!(obj instanceof Absence)) {
            return false;
        }
        final Absence other = (Absence) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public String toString() {
        return "at.qe.skeleton.model.Absence[ id=" + id + ", user=" + user.getUsername() + " ]";
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
    public int compareTo(Absence o) {
        return this.id.compareTo(o.getId());
    }


}
