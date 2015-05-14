import java.util.List;
import java.util.LinkedList;

/**
 * Classe responsável por interpretar uma sequência de tokens e direcioná-los
 * para as operações necessárias.
 */
public class Parser{
	 /** Lista de tokens a ser parseada */
	LinkedList<Token> tokens;
	/** Próximo token da lista */
	Token             lookahead;

	/**
	 * Método responsável por interpretar uma sequência de tokens e roteá-los para
	 * as operações necessárias.
	 * @param tokens
	 * 			Lista de tokens a ser parseada
	 * TODO: Verificar por que dá creps aqui se eu tô passando uma linkedlist com o tipo certo
	 */
	@SuppressWarnings("unchecked") //faz o compilador parar de resmungar
	public ExpressionNode parse(LinkedList<Token> tokens) throws Exception{
		this.tokens = (LinkedList<Token>) tokens.clone();
		lookahead   = this.tokens.getFirst();

		ExpressionNode expr = expression();

		if(lookahead.token != Token.EPSILON)
			throw new Exception("Símbolo inesperado \"" + lookahead.sequence + "\" encontrado. (parse)");

		return expr;
	}

	/**
	 * Tira o primeiro token da lista e verifica o que é o próximo token.
	 * Se a string estiver vazia, insere o EPSILON e encerra a expressão.
	 */
	private void nextToken(){
		tokens.pop();

		if(tokens.isEmpty())
			lookahead = new Token(Token.EPSILON, "", -1);
		else
			lookahead = tokens.getFirst();
	}

	private ExpressionNode expression() throws Exception{
		ExpressionNode expr = signedTerm();
		return sumOp(expr);
	}

	private ExpressionNode sumOp(ExpressionNode expr) throws Exception{
		if(lookahead.token == Token.PLUSMINUS){
			AdditionExpressionNode sum;
			if(expr.getType() == ExpressionNode.ADDITION_NODE)
				sum = (AdditionExpressionNode) expr;
			else
				sum = new AdditionExpressionNode(expr, true);

			boolean positive = lookahead.sequence.equals("+");
			nextToken();
			ExpressionNode t = term();
			sum.add(t, positive);

			return sumOp(sum);
		}

		return expr;
	}

	private ExpressionNode signedTerm() throws Exception{
		if(lookahead.token == Token.PLUSMINUS){
			boolean positive = lookahead.sequence.equals("+");
			nextToken();
			ExpressionNode t = term();

			if(positive)
				return t;
			else
				return new AdditionExpressionNode(t, false);
		}

		return term();
	}

	private ExpressionNode term() throws Exception{
		ExpressionNode f = factor();
		return termOp(f);
	}

	private ExpressionNode termOp(ExpressionNode expr) throws Exception{
		
		if(lookahead.token == Token.MULTDIV){
			MultiplicationExpressionNode prod;

			if(expr.getType() == ExpressionNode.MULTIPLICATION_NODE)
				prod = (MultiplicationExpressionNode) expr;
			else
				prod = new MultiplicationExpressionNode(expr, true);

			boolean positive = lookahead.sequence.equals("*");
			nextToken();
			ExpressionNode f = signedFactor();
			prod.add(f, positive);

			return termOp(prod);
		}

		return expr;
	}

	private ExpressionNode signedFactor() throws Exception{
		
		if(lookahead.token == Token.PLUSMINUS){
			boolean positive = lookahead.sequence.equals("+");
			nextToken();
			ExpressionNode t = factor();
			if(positive)
				return t;
			else
				return new AdditionExpressionNode(t, false);
		}

		return factor();
	}

	private ExpressionNode factor() throws Exception{
		ExpressionNode a = argument();
		return factorOp(a);
	}

	private ExpressionNode factorOp(ExpressionNode expr) throws Exception{
		if(lookahead.token == Token.RAISED){
			nextToken();
			ExpressionNode exponent = signedFactor();

			return new ExponentiationExpressionNode(expr, exponent);
		}

		return expr;
	}

	private ExpressionNode argument() throws Exception{

		if(lookahead.token == Token.FUNCTION){
			int function = FunctionExpressionNode.stringToFunction(lookahead.sequence);
			nextToken();
			ExpressionNode expr = argument();
			return new FunctionExpressionNode(function, expr);
		}

		else if (lookahead.token == Token.OPEN_BRACKET){
			nextToken();
			ExpressionNode expr = expression();
			
			if(lookahead.token != Token.CLOSE_BRACKET)
				throw new Exception("Fecha parênteses esperado e " + lookahead.sequence + "encontrado em vez disso");
			
			nextToken();
			return expr;
		}
			
		return value();
	}

	private ExpressionNode value() throws Exception{

		if(lookahead.token == Token.NUMBER){
			ExpressionNode expr = new ConstantExpressionNode(lookahead.sequence);
			nextToken();
			return expr;
		}

		if(lookahead.token == Token.VARIABLE){
			ExpressionNode expr = new VariableExpressionNode(lookahead.sequence);
			nextToken();
			return expr;
		}

		if(lookahead.token == Token.EPSILON)
			throw new Exception("Fim da entrada inesperado!");
		else
			throw new Exception("Simbolo inesperado \"" + lookahead.sequence + "\" encontrado. (value)");
		
	}
}