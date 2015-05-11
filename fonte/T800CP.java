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
import java.io.*;

public class T800CP{
	public static void main(String args[]) throws Exception{
		Tokenizer tokenizer = new Tokenizer();
        Parser    parser    = new Parser();
        File f;
        Scanner s;
        String linhas[] = new String[2000];


        /*TODO: Testar tokenizer com tokens de operações simples pra depois trocar pra nossa linguagem.*/
        tokenizer.add("sin|cos|exp|ln|sqrt", Token.FUNCTION);   //funções matemáticas
        tokenizer.add("\\(", Token.OPEN_BRACKET);               //abre parênteses
        tokenizer.add("\\)", Token.CLOSE_BRACKET);              //fecha parênteses
        tokenizer.add("[+-]", Token.PLUSMINUS);                 //soma ou subtração
        tokenizer.add("[*/]", Token.MULTDIV);                   //multiplicação ou divisão
        tokenizer.add("\\^", Token.RAISED);                     //elevado
        tokenizer.add("[0-9]+", Token.NUMBER);                  //números inteiros
        tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", Token.VARIABLE); //variáveis 

        f = new File(args[0]);
        s = new Scanner(f);

        int i = 0;
        while(s.hasNext()){
            linhas[i] = s.nextLine();
            i++;
        }


        try{

            //expression.accept(new SetVariable("pi", Math.PI));
            for(int j = 0; j < linhas.length; j++){
                tokenizer.tokenize(linhas[j]);
                ExpressionNode exp = parser.parse(tokenizer.getTokens());
                System.out.println("resultado da linha " + j + ": " + exp.getValue());
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}