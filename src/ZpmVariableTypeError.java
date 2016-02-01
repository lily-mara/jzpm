public class ZpmVariableTypeError {
	public ZpmVariableTypeError(ZpmVariableType lhs, ZpmVariableType rhs, String operation) {
		this(String.format("Tried to %s %s value to %s variable.", rhs, operation, lhs));
	}
	
	public ZpmVariableTypeError(String message) {
		System.err.println("TYPE ERROR: " + message);
		System.exit(1);
	}

}