package pojo;

public class DistrictDetails {
	private String name;

	private String notes;

	private int active;

	private int confirmed;

	private int deceased;

	private int recovered;

	private Delta delta;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getActive() {
		return this.active;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public int getConfirmed() {
		return this.confirmed;
	}

	public void setDeceased(int deceased) {
		this.deceased = deceased;
	}

	public int getDeceased() {
		return this.deceased;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getRecovered() {
		return this.recovered;
	}

	public void setDelta(Delta delta) {
		this.delta = delta;
	}

	public Delta getDelta() {
		return this.delta;
	}
}

class Delta {
	private int confirmed;

	private int deceased;

	private int recovered;

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public int getConfirmed() {
		return this.confirmed;
	}

	public void setDeceased(int deceased) {
		this.deceased = deceased;
	}

	public int getDeceased() {
		return this.deceased;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getRecovered() {
		return this.recovered;
	}
}
