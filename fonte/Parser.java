import java.util.List;
import java.util.LinkedList;

public class Parser{
	
	LinkedList<Token> tokens;
	Token             lookahead;

	public void parse(List<Token> tokens){
		this.tokens = (LinkedList<Token>) tokens.clone();
		lookahead = this.tokens.getFirst();

		expression();

		if(lookahead.token != Token.EPSILON)
			throw new Exception("Símbolo inesperado \"%s\" encontrado.", lookahead);
	}

	private void nextToken(){
		tokens.pop();

		if(tokens.isEmpty())
			lookahead = new Token(Token.EPSILON, "", -1);
		else
			lookahead = tokens.getFirst();
	}

	private void expression(){
		signedTerm();
		sumOp();
	}

	private void sumOp(){
		if(lookahead.token == Token.PLUSMINUS){
			nextToken();
			term();
			sumOp();
		}else{
			//sumOp -> EPSILON
		}
	}

	private void signedTerm(){
		if(lookahead.token == Token.PLUSMINUS){
			nextToken();
			term();
		}else{
			term();
		}
	}

	private void term(){
		factor();
		termOp();
	}

	private void termOp(){
		if(lookahead.token == Token.MULTDIV){
			nextToken();
			signedFactor();
			termOp();
		}else{
			// termOp -> EPSILON
		}
	}

	private void signedFactor(){
		if(lookahead.token == Token.PLUSMINUS){
			nextToken();
			factor();
		}else{
			factor();
		}
	}

	private void factor(){
		argument();
		factorOp();
	}

	private void factorOp(){
		if(lookahead.token == Token.RAISED){
			nextToken();
			signedFactor();
		}else{
			//factorOp ->EPSILON
		}
	}

	private void argument(){
		if(lookahead.token == Token.FUNCTION){
			nextToken();
			argument();
		}else if (lookahead.token == Token.OPEN_BRACKET){
			nextToken();
			expression();
			if(lookahead.token != Token.CLOSE_BRACKET)
				throw new Exception("Fecha parênteses esperado e " + lookahead.sequence + "encontrado em vez disso");
			
			nextToken();
		}else{
			value();
		}
	}

	private void value(){
		if(lookahead.token == Token.NUMBER){
			nextToken();
		}else if(lookahead.token == Token.VARIABLE){
			nextToken();
		}else{
			throw new Exception("Símbolo inesperado \"%s\" encontrado.", lookahead.sequence);
		}
	}
}