package passwordmanager;

import java.io.Serializable;

public class DataTransferObject implements Serializable {
	
	// this object is used to store data that user entered
	private static final long serialVersionUID = -3823496932875935495L;
	private String name;
	private String password;
	private String note;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}	
	
}
