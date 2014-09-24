package br.com.senac.findbus.model;

import java.io.Serializable;

public abstract class CustomED implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4930240755572243905L;
	private Integer sequenceAndroid;

	public Integer getSequenceAndroid() {
		return sequenceAndroid;
	}

	public void setSequenceAndroid(Integer id) {
		this.sequenceAndroid = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sequenceAndroid == null) ? 0 : sequenceAndroid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomED other = (CustomED) obj;
		if (sequenceAndroid == null) {
			if (other.sequenceAndroid != null)
				return false;
		} else if (!sequenceAndroid.equals(other.sequenceAndroid))
			return false;
		return true;
	}

}
