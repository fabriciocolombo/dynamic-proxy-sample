package br.com.fabriciodeb.sample;

import java.util.ArrayList;
import java.util.List;

public class Person extends AbstractEntity {

	private String name;

	private List<Address> addresses = new ArrayList<Address>();
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [id=");
		builder.append(getId());
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	public String getName() {
		System.out.println(getClass().getName() + " getName");
		return name;
	}

	public void setName(String name) {
		System.out.println(getClass().getName() + " setName");
		this.name = name;
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}
