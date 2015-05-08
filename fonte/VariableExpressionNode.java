public class VariableExpressionNode implements ExpressionNode{
	private String name;
	private double value;
	private boolean valueSet;

	public VariableExpressionNode(String name){
		this.name = name;
		valueSet  = false;
	}

	public int getType(){
		return ExpressionNode.VARIABLE_NODE;
	}

	public void setValue(double value){
		this.value    = value;
		this.valueSet = true;
	}

	public double getValue() throws Exception{
		if(valueSet)
			return value;
		else
			throw new Exception("Variavel " + name + " nao foi inicializada.");
	}
}