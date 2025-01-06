import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class Main {

    public String conteudo;
    public static String LeitorDeArquivo() {
    
        String caminhoDoArquivo = "arquivos/entrada.txt";
        String conteudo = "";
        try {
            List<String> linhas = Files.readAllLines(Paths.get(caminhoDoArquivo));
            conteudo = String.join("\n", linhas);
            System.out.println("Conteúdo guardado na variável:");
            System.out.println(conteudo);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return conteudo;
    }
    public static void main(String[] args) throws Exception {
        // Passo 1: Criar o CharStream a partir da entrada (pode ser de um arquivo ou string)
        String comando = LeitorDeArquivo();
        
        CustomErrors.verificarInicioEFim(comando);

        CharStream input = CharStreams.fromString(comando);

         // Passo 2: Extrair tokens e salvar em um arquivo
         try {
            List<String> tokens = TokenExtractor.extractTokens("arquivos/entrada.txt");
            TokenExtractor.saveTokensToFile(tokens, "arquivos/tokens_com_id.txt");
            String inputFile = "entrada.txt";  // Altere para o seu arquivo de entrada
            String outputFile = "tabela_simbolos.csv";  // Arquivo de saída para a tabela de símbolos
            
            // Salvando a tabela de símbolos
            
            System.out.println("Tokens extraídos e salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao processar os tokens: " + e.getMessage());
        }
        

        // Passo 2: Criar o lexer
        MLPLexer lexer = new MLPLexer(input);

        // Passo 3: Criar o token stream
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Passo 4: Criar o parser
        MLPParser parser = new MLPParser(tokens);
        
        CustomErrors errorListener = new CustomErrors();
        parser.removeErrorListeners(); // Remover o error listener padrão
        parser.addErrorListener(errorListener);

        
        
        // Passo 5: Chamar a regra inicial (no seu caso, é 'inicio')
        ParseTree tree = parser.inicio();  // 'inicio' é a regra de entrada do seu arquivo .g4

        // Passo 6: Criar a instância do Arvore (que herda do MLPBaseListener)
        Arvore arvore = new Arvore();
        
        // Passo 7: Construir a árvore sintática abstrata (AST)
           String treeString = tree.toStringTree(parser);

    //System.out.println(treeString);

        ParseTreeWalker walker = new ParseTreeWalker();
        CustomListener customListener = new CustomListener(errorListener);
        walker.walk(customListener, tree);

        ParseTreeWalker walker1 = new ParseTreeWalker();
        AssemblyINTERMEDIARIO assembly = new AssemblyINTERMEDIARIO();
        walker.walk(assembly, tree);

        String assemblyCode = assembly.getAssemblyCode();
        
        System.out.println(assemblyCode);
        
        // Passo 9: Salvar no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("arquivos/output.txt"))) {
            writer.write(assemblyCode);  // Escreve o conteúdo no arquivo
            System.out.println("Código assembly salvo em 'output.txt'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Passo 8: Exibir a árvore AST
       
            //Arvore.extractNodes(treeString); // Lê a entrada do arquivo especificado
     // A função exibirArvore ajuda a visualizar a árvore de forma hierárquica

    }
 

    // Função para exibir a árvore AST (recursivamente)
    private static void exibirArvore(Galhos galho, int nivel) {
        String indentacao = "  ".repeat(nivel);  // Indentação para mostrar a hierarquia
        System.out.println(indentacao + galho.tipo);
        for (Galhos folha : galho.folhas) {
            exibirArvore(folha, nivel + 1);
        }
    }

    
        
    

}


