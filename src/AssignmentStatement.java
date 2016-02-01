public class AssignmentStatement implements Statement {
	String leftHandSide;
	int rightInt;
	String rightString;
	RightHandSideType rightType;
	OperatorType operator;
	VariableBinding rightVar;
	
	public AssignmentStatement(String left, OperatorType op, int right) {
		rightType = RightHandSideType.INT;
		rightInt = right;
		operator = op;
		leftHandSide = left;
	}
	
	public AssignmentStatement(String left, OperatorType op, String right) {
		rightType = RightHandSideType.STRING;
		rightString = right;
		operator = op;
		leftHandSide = left;
	}
	
	public AssignmentStatement(String variable, OperatorType op, VariableBinding rightVariable) {
		rightType = RightHandSideType.VARIABLE;
		rightVar = rightVariable;
		operator = op;
		leftHandSide = variable;
	}

	public String toString() {
		String toReturn = "AssignmentStatement(" + leftHandSide + " " + operator + " ";
		
		if (rightType == RightHandSideType.INT) {
			toReturn += rightInt;
		} else if (rightType == RightHandSideType.STRING) {
			toReturn += String.format("\"%s\"", rightString);
		} else {
			toReturn += rightVar;
		}
		
		return toReturn + ")";
	}
	
	public void run() {
		
	}
}