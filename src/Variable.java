public class Variable {
	private ZpmVariableType type;
	private int intValue;
	private String stringValue;

	public Variable(int value) {
		intValue = value;
		type = ZpmVariableType.INT;
	}

	public Variable(String value) {
		stringValue = value;
		type = ZpmVariableType.STRING;
	}

	public void assign(int value) {
		intValue = value;
		type = ZpmVariableType.INT;
	}

	public void assign(String value) {
		stringValue = value;
		type = ZpmVariableType.STRING;
	}

	public void assign(Variable other) {
		if (other.type == ZpmVariableType.INT) {
			type = ZpmVariableType.INT;
			stringValue = null;
			intValue = other.intValue;
		} else {
			type = ZpmVariableType.STRING;
			stringValue = other.stringValue;
			intValue = 0;
		}
	}

	public void add(Variable other) {
		if (other.type == ZpmVariableType.STRING) {
			add(other.stringValue);
		} else {
			add(other.intValue);
		}
	}

	public void add(int other) {
		if (type == ZpmVariableType.STRING) {
			new ZpmVariableTypeError(type, ZpmVariableType.INT, "add");
		} else {
			intValue += other;
		}
	}

	public void add(String other) {
		if (type == ZpmVariableType.INT) {
			new ZpmVariableTypeError("Cannot add a string to an int");
		} else {
			stringValue += other;
		}
	}

	public void subtract(Variable other) {
		if (other.type == ZpmVariableType.STRING || type == ZpmVariableType.STRING) {
			new ZpmVariableTypeError("Subtraction is undefined for strings");
		} else {
			subtract(other.intValue);
		}
	}

	public void subtract(int other) {
		if (type == ZpmVariableType.STRING) {
			new ZpmVariableTypeError("Subtraction is undefined for strings");
		} else {
			intValue += other;
		}
	}
	
	public void multiply(Variable other) {
		if (other.type == ZpmVariableType.STRING || type == ZpmVariableType.STRING) {
			new ZpmVariableTypeError("Multiplication is undefined for strings");
		} else {
			subtract(other.intValue);
		}
	}

	public void multiply(int other) {
		if (type == ZpmVariableType.STRING) {
			new ZpmVariableTypeError("Multiplication is undefined for strings");
		} else {
			intValue *= other;
		}
	}
	
	public boolean equals(Object other) {
		if (other instanceof Variable) {
			Variable otherVar = (Variable) other;
			if (type == ZpmVariableType.INT && otherVar.type == type) {
				return intValue == otherVar.intValue;
			} else if (type == ZpmVariableType.STRING && otherVar.type == type) {
				return stringValue.equals(otherVar.stringValue);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String toString() {
		String valueToReturn = "Variable (" + type + ": ";
		if (type == ZpmVariableType.STRING) {
			valueToReturn += stringValue;
		} else {
			valueToReturn += intValue;
		}
		return valueToReturn + ")";
	}
	
	public String toStringForPrint() {
		if (type == ZpmVariableType.STRING) {
			return stringValue;
		} else {
			return "" + intValue;
		}
	}
	
	public String getStringValue() {
		if (type == ZpmVariableType.INT) {
			new ZpmVariableTypeError("Tried to get the string value of an int variable");
			return null;
		} else {
			return stringValue;
		}
	}
	
	public int getIntValue() {
		if (type == ZpmVariableType.STRING) {
			new ZpmVariableTypeError("Tried to get the int value of a string variable");
			return 0;
		} else {
			return intValue;
		}
	}
	
	public ZpmVariableType getType() {
		return type;
	}
	
	public Variable clone() {
		if (type == ZpmVariableType.INT) {
			return new Variable(intValue);
		} else {
			return new Variable(stringValue);
		}
	}
}