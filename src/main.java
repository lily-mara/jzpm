import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		loop(System.in);
	}
	
	public static void loop(InputStream in) {
		Map<VariableBinding, Variable> symbolTable = new HashMap<VariableBinding, Variable>();
		Scanner s = new Scanner(in);
		StatementParser parser;
		Statement statement;
		
		while (s.hasNext()) {
			parser = new StatementParser(s.nextLine());
			statement = parser.parse();
			statement.run(symbolTable);
			
			System.out.println(symbolTable);
		}
	}
}