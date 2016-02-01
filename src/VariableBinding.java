public class VariableBinding {
	public String name;
	
	public VariableBinding(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "VariableBinding(" + name + ")";
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public boolean equals(Object other) {
		if (other instanceof VariableBinding) {
			VariableBinding b = (VariableBinding) other;
			return b.name.equals(name);
		}
		return false;
	}
	
	public int hashCode() {
		return name.hashCode();
	}
}