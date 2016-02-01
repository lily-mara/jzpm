import java.util.Map;

public class AssignmentStatement implements Statement {
	VariableBinding leftHandSide;
	int rightInt;
	String rightString;
	RightHandSideType rightType;
	OperatorType operator;
	VariableBinding rightVar;

	public AssignmentStatement(VariableBinding left, OperatorType op, int right) {
		rightType = RightHandSideType.INT;
		rightInt = right;
		operator = op;
		leftHandSide = left;
	}

	public AssignmentStatement(VariableBinding left, OperatorType op, String right) {
		rightType = RightHandSideType.STRING;
		rightString = right;
		operator = op;
		leftHandSide = left;
	}

	public AssignmentStatement(VariableBinding variable, OperatorType op, VariableBinding rightVariable) {
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

	@Override
	public void run(Map<VariableBinding, Variable> symbolTable) {
		Variable rhs = getRightHandSide(symbolTable);
		switch (operator) {
		case ASSIGN:
			symbolTable.put(leftHandSide, rhs);
			return;
		case ADD:
			checkLhs(symbolTable);
			symbolTable.get(leftHandSide).add(rhs);
			return;
		case SUBTRACT:
			checkLhs(symbolTable);
			symbolTable.get(leftHandSide).subtract(rhs);
			return;
		case MULTIPLY:
			checkLhs(symbolTable);
			symbolTable.get(leftHandSide).multiply(rhs);
			return;
		}

	}

	private void checkLhs(Map<VariableBinding, Variable> symbolTable) {
		if (!symbolTable.containsKey(leftHandSide)) {
			System.err.println("ERROR: Attempted to use variable before declaration (" + leftHandSide.getName() + ")");
			System.exit(1);
			return;
		}
	}

	public Variable getRightHandSide(Map<VariableBinding, Variable> symbolTable) {
		switch (rightType) {
		case INT:
			return new Variable(rightInt);
		case VARIABLE:
			if (!symbolTable.containsKey(rightVar)) {
				System.err.println("ERROR: Attempted to use variable before declaration (" + rightVar.getName() + ")");
				System.exit(1);
				return null;
			}
			return symbolTable.get(rightVar).clone();
		case STRING:
			return new Variable(rightString);
		}
		return null;
	}
}