package br.com.senac.findbus.model;

public class StopED extends CustomED {

	private static final long serialVersionUID = 9093644730825368464L;

	private Integer stopId;

	private String stopCode;

	private String stopName;

	private String stopDesc;

	private Double stopLat;

	private Double stopLon;


	public Integer getStopId() {
		return stopId;
	}

	public void setStopId(Integer stopId) {
		this.stopId = stopId;
	}

	public String getStopCode() {
		return stopCode;
	}

	public void setStopCode(String stopCode) {
		this.stopCode = stopCode;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public String getStopDesc() {
		return stopDesc;
	}

	public void setStopDesc(String stopDesc) {
		this.stopDesc = stopDesc;
	}

	public Double getStopLat() {
		return stopLat;
	}

	public void setStopLat(Double stopLat) {
		this.stopLat = stopLat;
	}

	public Double getStopLon() {
		return stopLon;
	}

	public void setStopLon(Double stopLon) {
		this.stopLon = stopLon;
	}

	
}
