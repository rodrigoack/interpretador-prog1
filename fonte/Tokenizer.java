import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Classe responsável definir o que é um token e extrair os tokens do código.
 */
public class Tokenizer{

	/** Lista que define o que são tokens */
	private LinkedList<TokenInfo> tokenInfos;
	/** Tokens existentes depois que a string for tokenizada*/
	private LinkedList<Token>     tokens;


	public Tokenizer(){
		tokenInfos = new LinkedList<TokenInfo>();
		tokens     = new LinkedList<Token>();
	}

	/**
	 * Adiciona um novo tipo de token na lista.
	 * @param regex
	 *			Expressão regular que diz o que é o token
	 * @param token
	 *			Valor numérico do token (constantes da classe Token)
	 */
	public void add(String regex, int token){
		tokenInfos.add(new TokenInfo(Pattern.compile("^(" + regex + ")"), token));
	}

	/**
	 * Percorre uma string recolhendo os tokens dela e adicionando à lista tokens.
	 * @param str
	 * 			String a ser percorrida pelo método
	 */
	public void tokenize(String str) throws Exception{
		String s = new String(str.trim());
		tokens.clear();

		while(!s.equals("")){
			boolean match = false;

			for(TokenInfo info : tokenInfos){
				Matcher m = info.regex.matcher(s);

				if(m.find()){
					match = true;

					String tok = m.group().trim();
					tokens.add(new Token(info.token, tok, 0/*TODO: tirar esse 0 daqui*/));

					s = m.replaceFirst("").trim();
					break;
				}
			}

			//TODO: Arranjar uma exceção melhor (ParserException)
			if (!match) throw new Exception(
				"Caractere inesperado na entrada: " + s);
		}
	}

	/** Getter dos tokens. */
	public LinkedList<Token> getTokens(){
		return tokens;
	}

	/** Getter dos tokenInfos */
	public LinkedList<TokenInfo> getTokenInfos(){
		return tokenInfos;
	}

	/**
	 * Encapsula os tipos de token
	 */
	public class TokenInfo{
		/** Pattern de uma regex que informa o que é o token */
		public final Pattern regex;
		/** Valor numérico do token */
		public final int     token;


		public TokenInfo(Pattern regex, int token){
			super();
			this.regex = regex;
			this.token = token;
		}

	}
	
}