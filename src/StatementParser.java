import java.util.regex.*;

public class StatementParser {
	public static final String VARIABLE = "([a-zA-Z_]+)";
	public static final String INT  = "(-?[0-9]+)";
	public static final String OPERATOR = "([-+*]?=)";
	public static final String STRING = "\"([^\"]*)\"";
	public static final String PRINT = "PRINT";
	
	private static Pattern assignmentPattern = Pattern.compile(VARIABLE + " " + OPERATOR + " (.*) ;");
	private static Pattern variablePattern = Pattern.compile(VARIABLE);
	private static Pattern intPattern = Pattern.compile(INT);
	private static Pattern stringPattern = Pattern.compile(STRING);
	
	private String inputLine;
	
	public StatementParser(String input) {
		inputLine = input;
	}
	
	public Statement parse() {
		Statement s = parseAssignmentStatement();
		if (s != null) {
			return s;
		} else {
			return null
		}
	}
	
	private Statement parseAssignmentStatement() {
		Matcher rightHandMatcher;
		Matcher assignmentMathcer = assignmentPattern.matcher(inputLine);
		if (assignmentMathcer.find()) {
			String variable = assignmentMathcer.group(1);
			String operatorStr = assignmentMathcer.group(2);
			String rightHandSide = assignmentMathcer.group(3);
			
			OperatorType operator;
			
			if (operatorStr.equals("=")) {
				operator = OperatorType.ASSIGN;
			} else if (operatorStr.equals("+=")) {
				operator = OperatorType.ADD;
			} else if (operatorStr.equals("-=")) {
				operator = OperatorType.SUBTRACT;
			} else if (operatorStr.equals("*=")) {
				operator = OperatorType.MULTIPLY;
			} else {
				System.err.println("SYNTAX ERROR: Invalid operator '" + operatorStr + "'");
				System.exit(1);
				return null;
			}

			rightHandMatcher = stringPattern.matcher(rightHandSide);
			if (rightHandMatcher.find()) {
				String rightString = rightHandMatcher.group(1);
				return new AssignmentStatement(variable, operator, rightString);
			}
			
			rightHandMatcher = intPattern.matcher(rightHandSide);
			if (rightHandMatcher.find()) {
				int rightInt = Integer.parseInt(rightHandSide);
				return new AssignmentStatement(variable, operator, rightInt);
			}
			
			rightHandMatcher = variablePattern.matcher(rightHandSide);
			if (rightHandMatcher.find()) {
				VariableBinding rightVariable = new VariableBinding(rightHandMatcher.group(1));
				
				return new AssignmentStatement(variable, operator, rightVariable);
			}
			
			System.err.println("SYNTAX ERROR: Invalid right hand side of assignment statement '" + rightHandSide + "'");
			System.exit(1);
			return null;
		}
		
		return null;
	}
	
	private Statement parsePrintStatement() {
		Matcher rightHandMatcher;
		Matcher assignmentMathcer = assignmentPattern.matcher(inputLine);
		if (assignmentMathcer.find()) {
			String variable = assignmentMathcer.group(1);
			String operatorStr = assignmentMathcer.group(2);
			String rightHandSide = assignmentMathcer.group(3);
			
			OperatorType operator;
			
			if (operatorStr.equals("=")) {
				operator = OperatorType.ASSIGN;
			} else if (operatorStr.equals("+=")) {
				operator = OperatorType.ADD;
			} else if (operatorStr.equals("-=")) {
				operator = OperatorType.SUBTRACT;
			} else if (operatorStr.equals("*=")) {
				operator = OperatorType.MULTIPLY;
			} else {
				System.err.println("SYNTAX ERROR: Invalid operator '" + operatorStr + "'");
				System.exit(1);
				return null;
			}

			rightHandMatcher = stringPattern.matcher(rightHandSide);
			if (rightHandMatcher.find()) {
				String rightString = rightHandMatcher.group(1);
				return new AssignmentStatement(variable, operator, rightString);
			}
			
			rightHandMatcher = intPattern.matcher(rightHandSide);
			if (rightHandMatcher.find()) {
				int rightInt = Integer.parseInt(rightHandSide);
				return new AssignmentStatement(variable, operator, rightInt);
			}
			
			rightHandMatcher = variablePattern.matcher(rightHandSide);
			if (rightHandMatcher.find()) {
				VariableBinding rightVariable = new VariableBinding(rightHandMatcher.group(1));
				
				return new AssignmentStatement(variable, operator, rightVariable);
			}
			
			System.err.println("SYNTAX ERROR: Invalid right hand side of assignment statement '" + rightHandSide + "'");
			System.exit(1);
			return null;
		}
		
		return null;
	}
}