package test.learn.dto;

import java.io.Serializable;

public class Oper implements Serializable{

	private String oper;

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	@Override
	public String toString() {
		return "Oper [oper=" + oper + "]";
	}
	
	
}
