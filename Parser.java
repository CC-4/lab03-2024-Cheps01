/*
    Laboratorio No. 3 - Recursive Descent Parsing
    CC4 - Compiladores

    Clase que representa el parser

    Actualizado: agosto de 2021, Luis Cu
*/

import java.util.LinkedList;
import java.util.Stack;

public class Parser {

    // Puntero next que apunta al siguiente token
    private int next;
    // Stacks para evaluar en el momento
    private Stack<Double> operandos;
    private Stack<Token> operadores;
    // LinkedList de tokens
    private LinkedList<Token> tokens;

    // Funcion que manda a llamar main para parsear la expresion
    public boolean parse(LinkedList<Token> tokens) {
        this.tokens = tokens;
        this.next = 0;
        this.operandos = new Stack<Double>();
        this.operadores = new Stack<Token>();

        // Recursive Descent Parser
        // Imprime si el input fue aceptado
        System.out.println("Aceptada? " + S());

        // Shunting Yard Algorithm
        // Imprime el resultado de operar el input
        // System.out.println("Resultado: " + this.operandos.peek());

        // Verifica si terminamos de consumir el input
        if(this.next != this.tokens.size()) {
            return false;
        }
        return true;
    }

    // Verifica que el id sea igual que el id del token al que apunta next
    // Si si avanza el puntero es decir lo consume.
    private boolean term(int id) {
        if(this.next < this.tokens.size() && this.tokens.get(this.next).equals(id)) {
            
            // Codigo para el Shunting Yard Algorithm
            
            if (id == Token.NUMBER) {
				// Encontramos un numero
				// Debemos guardarlo en el stack de operandos
				operandos.push( this.tokens.get(this.next).getVal() );

			} else if (id == Token.SEMI) {
				// Encontramos un punto y coma
				// Debemos operar todo lo que quedo pendiente
				while (!this.operadores.empty()) {
					popOp();
				}
				
			} else {
				// Encontramos algun otro token, es decir un operador
				// Lo guardamos en el stack de operadores
				// Que pushOp haga el trabajo, no quiero hacerlo yo aqui
				pushOp( this.tokens.get(this.next) );
			}

            this.next++;
            return true;
        }
        return false;
    }

    // Funcion que verifica la precedencia de un operador
    private int pre(Token op) {
        /* TODO: Su codigo aqui */
        switch(op.getId()) {
        	case Token.PLUS:
        		return 1;
            case Token.MINUS:
                return 1;
        	case Token.MULT:
        		return 2;
            case Token.DIV:
        		return 2;
            case Token.MOD:
        		return 2;
            case Token.EXP:
                return 3;
            case Token.UNARY:
                return 4;
            case Token.LPAREN:
                return 5;
            case Token.RPAREN:
        		return 5;
        	default:
        		return -1;
        }
        /* El codigo de esta seccion se explicara en clase */
    }

    private void popOp() {
        Token op = this.operadores.pop();

        /* TODO: Su codigo aqui */

        /* El codigo de esta seccion se explicara en clase */

        if (op.equals(Token.PLUS)) {
        	double a = this.operandos.pop();
        	double b = this.operandos.pop();
        	// print para debug, quitarlo al terminar
        	System.out.println("suma " + a + " + " + b);
        	this.operandos.push(a + b);
        } else if (op.equals(Token.MULT)) {
        	double a = this.operandos.pop();
        	double b = this.operandos.pop();
        	// print para debug, quitarlo al terminar
        	System.out.println("mult " + a + " * " + b);
        	this.operandos.push(a * b);
        }
    }

    private void pushOp(Token op) {
        /* TODO: Su codigo aqui */

        /* Casi todo el codigo para esta seccion se vera en clase */
    	
    	// Si no hay operandos automaticamente ingresamos op al stack

    	// Si si hay operandos:
    		// Obtenemos la precedencia de op
        	// Obtenemos la precedencia de quien ya estaba en el stack
        	// Comparamos las precedencias y decidimos si hay que operar
        	// Es posible que necesitemos un ciclo aqui, una vez tengamos varios niveles de precedencia
        	// Al terminar operaciones pendientes, guardamos op en stack

    }

    private boolean S() {
        // System.out.println("S()" + this.next);
        return E() && term(Token.SEMI);
    }

    private boolean E() {
        // System.out.println("E()" + this.next);
        int save = this.next;
        next = save;
        if (E1()) return true;
        next = save;
        if (E2()) return true;
        next = save;
        if (E3()) return true;
        next = save;
        if (E4()) return true;
        return false;
    }

    private boolean C() { 
        // System.out.println("C()" + this.next);  
        int save = this.next;
        next = save;
        if (C1()) return true;
        next = save;
        if (C2()) return true;
        next = save;
        if (C3()) return true;
        next = save;
        if (C4()) return true;
        next = save;
        if (C5()) return true;
        next = save;
        if (C6()) return true;
        return false;
    }

    /* TODO: sus otras funciones aqui */
    private boolean E1() {
        // System.out.println("E1()" + this.next);
        return term(Token.NUMBER) && C();
    }   
    private boolean E2() {
        // System.out.println("E2()" + this.next);
        return term(Token.NUMBER);
    }
    private boolean E3() {
        // System.out.println("E3()" + this.next);
        return term(Token.UNARY) && E();
    }
    private boolean E4() {
        // System.out.println("E4()" + this.next);
        return term(Token.LPAREN) && E() && term(Token.RPAREN);
    }
    private boolean C1() {
        // System.out.println("C1()" + this.next);
        return term(Token.PLUS) && E();
    }
    private boolean C2() {
        // System.out.println("C2()" + this.next);
        return term(Token.MINUS) && E();
    }
    private boolean C3() {
        // System.out.println("C3()" + this.next);
        return term(Token.MULT) && E();
    }
    private boolean C4() {
        // System.out.println("C4()" + this.next);
        return term(Token.DIV) && E();
    } 
    private boolean C5() {
        // System.out.println("C5()" + this.next);
        return term(Token.MOD) && E();
    }
    private boolean C6() {
        // System.out.println("C6()" + this.next);
        return term(Token.EXP) && E();
    }
}