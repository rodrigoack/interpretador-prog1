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
        Scanner hue = new Scanner(System.in);
        /*TODO: Testar tokenizer com tokens de operações simples pra depois trocar pra nossa linguagem.*/
        tokenizer.add("sin|cos|exp|ln|sqrt[^a-zA-Z0-9]?", 1);   //funções matemáticas
        tokenizer.add("\\(", 2);                   //abre parênteses
        tokenizer.add("\\)", 3);                   //fecha parênteses
        tokenizer.add("[+-]", 4);                  //soma ou subtração
        tokenizer.add("[*/]", 5);                  //multiplicação ou divisão
        tokenizer.add("\\^", 6);                   //elevado
        tokenizer.add("[0-9]+", 7);                //números inteiros
        tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", 8); //variáveis 

        try{
            tokenizer.tokenize(" sin(x) * (1 + var_12) + ln(3 / 2) ^ ");
            tokenizer.tokenize(hue.nextLine());
            for(Tokenizer.Token tok : tokenizer.getTokens()){
                System.out.println("" + tok.token + " " + tok.sequence);
            }

        //TODO: ParserException
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}