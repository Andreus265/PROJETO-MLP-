import org.antlr.v4.runtime.*;
import java.io.*;
import java.util.*;

public class TokenExtractor {
    
    // Mapa para armazenar a tabela de símbolos
    private static Map<String, String> simbolosTabela = new HashMap<>();
    
    // Lista de palavras reservadas
    private static final List<String> PALAVRAS_RESERVADAS = Arrays.asList(
        "inteiro", "real", "caracter", "se", "entao", "senao", "$", "$.", "NOT", "E", "OR", "enquanto", "NOT"
    );

    // Método que processa o arquivo e retorna os tokens com a tabela de símbolos
    public static List<String> extractTokens(String inputFile) throws IOException {
        // Lê o arquivo de entrada
        CharStream input = CharStreams.fromFileName(inputFile);

        // Instancia o lexer
        MLPLexer lexer = new MLPLexer(input);

        // Lista para armazenar os tokens com IDs
        List<String> tokenList = new ArrayList<>();
        
        TabelaSimbolos tabelaSimbolos = new TabelaSimbolos();
        // ID inicial
        int id = 1;
        tokenList.add("Token; Tipo; Linha");
        // Processa os tokens
        for (Token token : lexer.getAllTokens()) {
            String tokenText = token.getText();
            String tokenType = getCustomTokenType(tokenText, token.getType());
            int line = token.getLine();
            int position = token.getCharPositionInLine();

            // Adiciona o token com ID à lista
            String tokenInfo = String.format("'%s'; %s; %d",
                                              tokenText, tokenType, line);
            tokenList.add(tokenInfo);

            // Adiciona na tabela de símbolos se for uma palavra reservada ou variável
            if (PALAVRAS_RESERVADAS.contains(tokenText)) {
                simbolosTabela.put(tokenText, "palavra reservada");
                TabelaSimbolos.adicionar(tokenText, "palavra reservada");
            } 

            // Incrementa o ID
            id++;
        }

        return tokenList;
    }

    // Método auxiliar para mapear tipos customizados
    private static String getCustomTokenType(String tokenText, int tokenType) {
        // Mapeamento customizado
        switch (tokenText) {
            case "+": case "-": case "*": case "/": case "=": return "OPERADOR";
            case ";": case "," : case "." : case "(" : case ")" : return "SIMBOLO RESERVADO";
            case ">": case "<" : case "==": case "<=": case ">=": case "!=": return "COMPARADOR";
            default:
                // Usa o tipo simbólico do lexer se não for customizado
                return MLPLexer.VOCABULARY.getSymbolicName(tokenType) != null
                       ? MLPLexer.VOCABULARY.getSymbolicName(tokenType)
                       : tokenText;
        }
    }

    // Método para salvar a tabela de símbolos em um arquivo
    public static void saveSymbolsToFile(String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Identificador;Categoria\n"); // Cabeçalho do arquivo
            for (Map.Entry<String, String> entry : simbolosTabela.entrySet()) {
                writer.write(String.format("%s;%s\n", entry.getKey(), entry.getValue()));
            }
        }
    }

    // Método para salvar os tokens em um arquivo
    public static void saveTokensToFile(List<String> tokens, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String tokenEntry : tokens) {
                writer.write(tokenEntry);
                writer.newLine();
            }
        }
    }
}
