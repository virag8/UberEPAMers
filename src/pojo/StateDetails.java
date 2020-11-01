package pojo;

import java.util.List;

public class StateDetails {

	String stateName;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public String getActivated() {
		return activated;
	}

	public void setActivated(String activated) {
		this.activated = activated;
	}

	public String getRecovered() {
		return recovered;
	}

	public void setRecovered(String recovered) {
		this.recovered = recovered;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public void setDistrictData(List<DistrictDetails> districtData) {
		this.districtData = districtData;
	}

	public List<DistrictDetails> getDistrictData() {
		return this.districtData;
	}

	String confirmed;
	String activated;
	String recovered;
	String ratio;
	private List<DistrictDetails> districtData;

}
