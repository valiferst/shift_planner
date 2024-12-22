package at.qe.skeleton.model;

import java.time.LocalDateTime;
import java.util.List;

class Shift {
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private List<Userx> shiftWorkers;
	
	public Shift(LocalDateTime startTime, LocalDateTime endTime, Userx worker) {
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.shiftWorkers.add(worker);
	}
	
	// Getter/Setter Times
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

}
