import java.util.Map;

public interface Statement {
	public void run(Map<VariableBinding, Variable> symbolTable);
}