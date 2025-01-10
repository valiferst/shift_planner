// Dummy class for Shift
package at.qe.skeleton.model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ShiftPlan shiftPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setShiftPlan(ShiftPlan shiftPlan) {
        this.shiftPlan = shiftPlan;
    }
}
