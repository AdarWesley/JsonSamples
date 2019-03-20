
public class Jeep extends Vehicle {
	private int engineSize;
	private String color;
	
	public Jeep() {
		super();
	}

	public Jeep(int engineSize, String color) {
		super();
		this.engineSize = engineSize;
		this.color = color;
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
}
