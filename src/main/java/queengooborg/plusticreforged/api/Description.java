package queengooborg.plusticreforged.api;

public class Description {
	String flavor = "";
	String traits = "";

	public Description() {}

	public Description(String traits) {
		this.traits = traits;
	}

	public Description(String flavor, String traits) {
		this.flavor = flavor;
		this.traits = traits;
	}
}