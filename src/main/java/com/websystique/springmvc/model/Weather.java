package com.websystique.springmvc.model;

public class Weather {

	private long id;
	
	private String description;
	
	private double temp;
	
	private String date;
	
	public Weather(){
		id=0;
	}
	
	public Weather(long id, String description, double temp, String date){
		this.id = id;
		this.description = description;
		this.temp = temp;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Weather))
			return false;
		Weather other = (Weather) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Weather [id=" + id + ", description=" + description + ", temp=" + temp
				+ ", date=" + date + "]";
	}
	

	
}
