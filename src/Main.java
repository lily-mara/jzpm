import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Welcome to JZPM 1.0!");
			loop(System.in, ">>> ");
		} else {
			try {
				loop(new FileInputStream(new File(args[0])), "");
			} catch (FileNotFoundException e) {
				System.err.println("ERROR: File does not exist");
			}
		}
	}
	
	public static void loop(InputStream in, String prompt) {
		Map<VariableBinding, Variable> symbolTable = new HashMap<VariableBinding, Variable>();
		Scanner s = new Scanner(in);
		StatementParser parser;
		Statement statement;
		System.out.print(prompt);

		while (s.hasNext()) {
			System.out.print(prompt);
			parser = new StatementParser(s.nextLine());
			statement = parser.parse();
			if (statement != null) {
				statement.run(symbolTable);
			} else {
				System.err.println("SYNTAX ERROR");
			}
		}
		
		s.close();
	}
}