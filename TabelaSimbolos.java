import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TabelaSimbolos {
    private static HashMap<String, String> tabela;

    
        public TabelaSimbolos() {
            this.tabela = new HashMap<>();
        }
    
        public static void adicionar(String nome, String tipo) {
            tabela.put(nome, tipo);
            
            try{
            saveSymbolsToFile("arquivos/tabela_simbolos.csv");
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                }
                
                public HashMap<String, String> getTable(){
                    return  this.tabela;
                }
                public boolean existe(String nome) {
                    return tabela.containsKey(nome);
                }
            
                public String getTipo(String nome) {
                    return tabela.get(nome);
                }
        public static void saveSymbolsToFile(String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Identificador;Categoria\n"); // Cabeçalho do arquivo
            for (Map.Entry<String, String> entry : tabela.entrySet()) {
                if((entry.getValue().equals("inteiro") || entry.getValue().equals("real") || entry.getValue().equals("caracter"))){
                writer.write(String.format("%s;variavel %s\n", entry.getKey(), entry.getValue()));}
                else{
                writer.write(String.format("%s; %s\n", entry.getKey(), entry.getValue()));}
            }
        }
    }
}

