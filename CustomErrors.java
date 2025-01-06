import org.antlr.v4.runtime.*;

public class CustomErrors extends BaseErrorListener {

    private int profundidadeMaxima = 10; // Máxima permitida

        // Verifica o início e o fim da entrada
    public static void verificarInicioEFim(String inputCode) {
        // Se o código não começar com "$" ou não terminar com "$;"
        if (!inputCode.startsWith("$")) {
            // Exibe erro apenas uma vez e termina o processo
            System.err.println("Cod. 01: Erro de sintaxe a linguagem deve comecar com '$' ;'");
            System.exit(1);  // Interrompe a execução
        }
        if (!inputCode.endsWith("$.")){
            System.err.println("Cod. 01: Erro de sintaxe a linguagem deve terminar com '$.' ;'");
        //    System.exit(1); 
        }
    }
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        System.err.println("Cod. 02: Erro de sintaxe na seguinte linha: " + line + ":" + charPositionInLine + " - " + msg);
    }   


    // Verificar comprimento do identificador
    public void IdentificadorMaximo(String identifier, int line) {
        if (identifier.length() > 10) {
            System.err.println("Cod. 03: Erro lexico na seguinte linha: " + line + ": Identificador '" + identifier + "' excede o limite de 10 caracteres.");
        }
    }

    // Verificar profundidade de comandos
    public void profundidadeMaxima(int line) {
        if (profundidadeMaxima-- <= 0) {
            System.err.println("Cod. 04: Erro semantico na seguinte linha: " + line + ": Profundidade maxima de comandos excedida.");
        }
    }

    //declarando variavel novamente.
    public void variavelDuplicada(String nome, int line) {
        System.err.println("Cod. 05: Erro semantico na seguinte linha: " + line + ": A variavel '" + nome + "' ja foi declarada.");
    }

    //variavel não declarada anteriormente
    public void variavelNaoDeclarada(String nome, int line) {
        System.err.println("Cod. 06: Erro semantico na seguinte linha: " + line + ": A variavel '" + nome + "' nao foi declarada antes do uso.");
    }
    
    public void atribuicaoInvalida(String variavel, int Line, String msg){
    System.err.println("Cod. 07: Erro semantico na seguinte linha: " + Line + ": Atribuicao invalida na variavel '" + variavel + "' " + msg);
    }

    //variaveis incompativeis
    public void variaveisIncompativeis(String nome1, String nome2, int line){
        System.err.println("Cod. 08: Erro semantico na seguinte linha: " + line + ": A variavel '" + nome1 + "' e a variavel '" + nome2 + "' sao incompativeis.");
    }

    //uso indevido de palavra reservada
    public void usoIndevidoPalavraReservada(String nome, int Line){
        System.err.println("Cod. 09: Erro de sintaxena linha " + Line + ": uso indevido da palavra reservada '" + nome + "' ");
    }

    public void exitCommand() {
        profundidadeMaxima++;
    }
}