import java.util.Map;

public class PrintStatement implements Statement {
	private VariableBinding toPrint;
	
	public PrintStatement(VariableBinding b) {
		toPrint = b;
	}

	@Override
	public void run(Map<VariableBinding, Variable> symbolTable) {
		if (!symbolTable.containsKey(toPrint)) {
			System.err.println("ERROR: Attempted to use variable before declaration (" + toPrint.getName() + ")");
			System.exit(1);
			return;
		}
		Variable v = symbolTable.get(toPrint);
		System.out.println(v.toStringForPrint());
	}
	
	public String toString() {
		return "PRINT " + toPrint;
	}
}