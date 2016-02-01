import java.util.List;
import java.util.Map;

public class ForStatement implements Statement {
	private int number;
	private List<Statement> body;
	
	public ForStatement(int number, List<Statement> body) {
		this.number = number;
		this.body = body;
	}

	@Override
	public void run(Map<VariableBinding, Variable> symbolTable) {
		for (int i = 0; i < number; i++) {
			for (int j = 0; j < body.size(); j++) {
				body.get(j).run(symbolTable);
			}
		}
	}

}
