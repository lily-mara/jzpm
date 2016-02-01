import java.io.InputStream;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		Statement s = new StatementParser("x = 12 ;").parse();
		System.out.println(s);
		
		s = new StatementParser("x = \"13\" ;").parse();
		System.out.println(s);
		
		s = new StatementParser("x = y ;").parse();
		System.out.println(s);
		
		loop(System.in);
	}
	
	public static void loop(InputStream in) {
		Scanner s = new Scanner(in);
		StatementParser parser;
		Statement statement;
		
		while (s.hasNext()) {
			parser = new StatementParser(s.nextLine());
			statement = parser.parse();
			System.out.println(statement);
		}
	}
}