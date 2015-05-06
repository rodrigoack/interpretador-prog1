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
	 * TODO: Verificar por que dá creps aqui se eu tô passando uma linkedlist
	 */
	public void parse(LinkedList<Token> tokens) throws Exception{
		this.tokens = (LinkedList<Token>) tokens.clone();
		lookahead   = this.tokens.getFirst();

		expression();

		if(lookahead.token != Token.EPSILON)
			throw new Exception("Símbolo inesperado \"" + lookahead.sequence + "\" encontrado. (parse)");
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

	private void expression() throws Exception{
		signedTerm();
		sumOp();
	}

	private void sumOp() throws Exception{
		if(lookahead.token == Token.PLUSMINUS){
			nextToken();
			term();
			sumOp();
		}else{
			//sumOp -> EPSILON
		}
	}

	private void signedTerm() throws Exception{
		if(lookahead.token == Token.PLUSMINUS){
			nextToken();
			term();
		}else{
			term();
		}
	}

	private void term() throws Exception{
		factor();
		termOp();
	}

	private void termOp() throws Exception{
		if(lookahead.token == Token.MULTDIV){
			nextToken();
			signedFactor();
			termOp();
		}else{
			// termOp -> EPSILON
		}
	}

	private void signedFactor() throws Exception{
		if(lookahead.token == Token.PLUSMINUS){
			nextToken();
			factor();
		}else{
			factor();
		}
	}

	private void factor() throws Exception{
		argument();
		factorOp();
	}

	private void factorOp() throws Exception{
		if(lookahead.token == Token.RAISED){
			nextToken();
			signedFactor();
		}else{
			//factorOp ->EPSILON
		}
	}

	private void argument() throws Exception{
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

	private void value() throws Exception{
		if(lookahead.token == Token.NUMBER){
			nextToken();
		}else if(lookahead.token == Token.VARIABLE){
			nextToken();
		}else{
			throw new Exception("Símbolo inesperado \"" + lookahead.sequence + "\" encontrado. (value)");
		}
	}
}