import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class StatementParser {
	public static final String VARIABLE = "([a-zA-Z_]+)";
	public static final String INT  = "(-?[0-9]+)";
	public static final String OPERATOR = "([-+*]?=)";
	public static final String STRING = "\"([^\"]*)\"";
	public static final String PRINT = "PRINT";
	public static final String FOR = "FOR";
	public static final String ENDFOR = "ENDFOR";
	
	
	private static Pattern assignmentPattern = Pattern.compile(VARIABLE + " " + OPERATOR + " (.*) ;");
	private static Pattern variablePattern = Pattern.compile(VARIABLE);
	private static Pattern intPattern = Pattern.compile(INT);
	private static Pattern stringPattern = Pattern.compile(STRING);
	private static Pattern printPattern = Pattern.compile(PRINT + " " + VARIABLE + " ;");
	private static Pattern forPattern = Pattern.compile(FOR + " " + INT + "(.*) "+ ENDFOR);
	
	private String inputLine;
	
	public StatementParser(String input) {
		inputLine = input;
	}
	
	public Statement parse() {
		Statement s = parseForStatement();
		if (s != null) {
			return s;
		}
				
		s = parseAssignmentStatement();
		if (s != null) {
			return s;
		}
		
		s = parsePrintStatement();
		if (s != null) {
			return s;
		}
		
		return null;
	}
	
	private Statement parseAssignmentStatement() {
		Matcher rightHandMatcher;
		Matcher assignmentMathcer = assignmentPattern.matcher(inputLine);
		if (assignmentMathcer.find()) {
			VariableBinding variable = new VariableBinding(assignmentMathcer.group(1));
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
	
	private ArrayList<Statement> parseMultipleStatements(String input) {
		ArrayList<Statement> statements = new ArrayList<Statement>();
		Matcher m;
		StatementParser parser;
		
		for (;;) {
			m = assignmentPattern.matcher(input);
			parser = new StatementParser(input);
			if (m.find()) {
				String body = m.group(0);
				statements.add(parser.parseAssignmentStatement());
				input = input.substring(m.end());
			} else {
				m = printPattern.matcher(input);
				if (m.find()) {
					String body = m.group(0);
					statements.add(parser.parsePrintStatement());
					input = input.substring(m.end());
				} else {
					m = forPattern.matcher(input);
					if (m.find()) {
						String body = m.group(0);
						statements.add(parser.parseForStatement());
						input = input.substring(m.end());
					} else {
						break;
					}
				}
			}
		}
		
		return statements;
	}
	
	private Statement parsePrintStatement() {
		Matcher printMatcher = printPattern.matcher(inputLine);
		if (printMatcher.find()) {
			String variable = printMatcher.group(1);
			VariableBinding variableName = new VariableBinding(variable);
			
			return new PrintStatement(variableName);
		}
		return null;
	}
	
	private Statement parseForStatement() {
		Matcher forMatcher = forPattern.matcher(inputLine);
		if (forMatcher.find()) {
			String restOfBody = forMatcher.group(2);
			int number = Integer.parseInt(forMatcher.group(1));
			ArrayList<Statement> body = parseMultipleStatements(restOfBody);
			
			return new ForStatement(number, body);
		}
		
		return null;
	}
}