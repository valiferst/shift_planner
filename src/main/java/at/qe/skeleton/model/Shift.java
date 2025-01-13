package at.qe.skeleton.model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

@Entity
public class Shift implements Persistable<Long>, Serializable, Comparable<Shift> {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime startTime;
	private LocalDateTime endTime;
	@ManyToOne
	private ShiftPlan shiftPlan;
	@JoinTable(name = "Shift_Workers")
	@ManyToMany
	private Set<Userx> shiftWorkers;


	public void addWorker(Userx worker) {
		this.shiftWorkers.add(worker);
	}

	public void removeWorker(Userx worker) {
		this.shiftWorkers.remove(worker);
	}

	// Getter and Setter
	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public ShiftPlan getShiftPlan() {
		return shiftPlan;
	}

	public void setShiftPlan(ShiftPlan shiftPlan) {
		this.shiftPlan = shiftPlan;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}

	public Set<Userx> getShiftWorkers(){
		return shiftWorkers;
	}

	public void setShiftWorkers(Set<Userx> shiftWorkers){
		this.shiftWorkers = shiftWorkers;
	}

	public Duration getShiftDuration(){
		return Duration.between(this.startTime, this.endTime);
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
	    if (!(obj instanceof Shift)) {
	      return false;
	    }
	    final Shift other = (Shift) obj;
	    return Objects.equals(this.getId(), other.getId());
	  }

	  @Override
	  public String toString() {
	    return "at.qe.skeleton.model.Shift[ id=" + id + " ]";
	  }

	  @Override
	  public int compareTo(Shift o) {
	    return this.id.compareTo(o.getId());
	  }

	  @Override
	  public boolean isNew() {
		  return (null == id);
	  }

	  @Override
	  public Long getId() {
		  return id;
	  }
}
