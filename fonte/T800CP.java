/**
 * T800 COMMAND PROTOCOL
 * 
 * 
 * Alunos:
 * Guilherme Hermes: <guilherme.hermes182@gmail.com>
 * Rodrigo Cavichioli: <rodrigoack@gmail.com>
 * Vanessa Sassi: <burnoutvanessa@gmail.com>
 */

import java.util.Scanner;

public class T800CP{
	public static void main(String args[]){
		Tokenizer tokenizer = new Tokenizer();
        Parser    parser    = new Parser();

        /*TODO: Testar tokenizer com tokens de operações simples pra depois trocar pra nossa linguagem.*/
        tokenizer.add("sin|cos|exp|ln|sqrt", Token.FUNCTION);   //funções matemáticas
        tokenizer.add("\\(", Token.OPEN_BRACKET);               //abre parênteses
        tokenizer.add("\\)", Token.CLOSE_BRACKET);              //fecha parênteses
        tokenizer.add("[+-]", Token.PLUSMINUS);                 //soma ou subtração
        tokenizer.add("[*/]", Token.MULTDIV);                   //multiplicação ou divisão
        tokenizer.add("\\^", Token.RAISED);                     //elevado
        tokenizer.add("[0-9]+", Token.NUMBER);                  //números inteiros
        tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", Token.VARIABLE); //variáveis 

        try{
            tokenizer.tokenize(" sin(x)  * (1 + var_12)");
            parser.parse(tokenizer.getTokens());

            for(Token tok : tokenizer.getTokens()){
                System.out.println("" + tok.token + " " + tok.sequence);
            }

        //TODO: ParserException
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}