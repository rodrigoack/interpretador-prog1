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


            //Passo a passo pra calcular a expressão 3*2^4 + sqrt(1+3) = 50 (teste das expressões)
            AdditionExpressionNode innerSum = new AdditionExpressionNode();
            innerSum.add(new ConstantExpressionNode(1), true);
            innerSum.add(new ConstantExpressionNode(3), true);

            ExpressionNode sqrt = new FunctionExpressionNode(FunctionExpressionNode.SQRT, innerSum);

            ExpressionNode expo = new ExponentiationExpressionNode(new ConstantExpressionNode(2), new ConstantExpressionNode(4));

            MultiplicationExpressionNode prod = new MultiplicationExpressionNode();
            prod.add(new ConstantExpressionNode(3), true);
            prod.add(expo, true);

            AdditionExpressionNode expression = new AdditionExpressionNode();
            expression.add(prod, true);
            expression.add(sqrt, true);

            System.out.println("O resultado eh: " + expression.getValue());
            //Fim do cálculo manual da expressão




            tokenizer.tokenize("3*2^4 + sqrt(1+3)");
            ExpressionNode expression2 = parser.parse(tokenizer.getTokens());
            System.out.println("O valor da expressao parseada eh: " + expression2.getValue());


            tokenizer.tokenize("sin(pi/2)");
            ExpressionNode expression3 = parser.parse(tokenizer.getTokens());
            expression3.accept(new SetVariable("pi", Math.PI));
            System.out.println("O valor da expressao do pi eh: " + expression3.getValue());


            for(Token tok : tokenizer.getTokens()){
                System.out.println("" + tok.token + " " + tok.sequence);
            }



        //TODO: ParserException
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}