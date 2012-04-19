package br.com.fabriciodeb.sample;

public class Address extends AbstractEntity {

	private String address;

	public Address() {
	}

	public Address(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [address=");
		builder.append(address);
		builder.append("]");
		return builder.toString();
	}

}
