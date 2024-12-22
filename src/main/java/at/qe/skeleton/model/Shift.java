package at.qe.skeleton.model;

import java.time.LocalDateTime;
import java.util.List;

class Shift {
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private ShiftPlan shiftPlan;
	private List<Userx> shiftWorkers;
		
	public Shift(LocalDateTime startTime, LocalDateTime endTime, ShiftPlan shiftPlan, Userx worker) {
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setShiftPlan(shiftPlan);
		this.shiftWorkers.add(worker);
	}
	
	public void addWorker(Userx worker) {
		this.shiftWorkers.add(worker);
	}
	
	public void removeWorker(Userx worker) {
		//assuming smart manager: worker has just one list entry
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

}
