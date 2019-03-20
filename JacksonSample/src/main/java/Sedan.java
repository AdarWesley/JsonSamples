
public class Sedan extends Vehicle {
	private int engineSize;
	private String color;
	
	public Sedan() {
		super();
	}
	
	public int getEngineSize() {
		return engineSize;
	}

	public void setEngineSize(int engineSize) {
		this.engineSize = engineSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Sedan(int engineSize, String color) {
		super();
		this.engineSize = engineSize;
		this.color = color;
	}
}
