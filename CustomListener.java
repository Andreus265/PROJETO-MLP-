import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CustomListener extends MLPBaseListener {

    private final CustomErrors customErrors;
    public static Map<String, String> tabelaSimbolos = new HashMap<>();
        private static final List<String> PALAVRAS_RESERVADAS = Arrays.asList(
        "inteiro", "real", "caracter", "se", "entao", "senao", 
        "$", "$.", "NOT", "E", "OR", "enquanto"
    );
    
    // Construtor que recebe o error handler
    public CustomListener(CustomErrors customErrors) {
        this.customErrors = customErrors;
        
    }

    private int profundidadeAtual = 0; // Controle de profundidade
    //TabelaSimbolos TabelaSimbolos = new TabelaSimbolos();
    @Override
    public void enterTipo(MLPParser.TipoContext ctx) {
        
        String tipo = ctx.getStart().getText();
        // Validação do comprimento dos identificadores
        for (TerminalNode identificador : ctx.IDENTIFICADOR()) {
            String nome = identificador.getText();

            if (PALAVRAS_RESERVADAS.contains(nome)) {
                
                tabelaSimbolos.put(nome, "PALAVRA_RESERVADA");
                //try {
                //    saveSymbolsToFile(tabelaSimbolos, "arquivos/tabela_simbolos");
                //} catch (IOException e) {
                //    e.printStackTrace();  // Imprime o erro na saída de erro padrão
                //}
        }
            customErrors.IdentificadorMaximo(identificador.getText(), ctx.start.getLine());
            if(tabelaSimbolos.containsKey(identificador.getText())){
                customErrors.variavelDuplicada(identificador.getText(), ctx.start.getLine());
            //System.out.println("passei aqui");
            }else{
                tabelaSimbolos.put(nome, tipo);
                TabelaSimbolos.adicionar(nome,tipo);
           
                //try {
                //    saveSymbolsToFile(tabelaSimbolos, "arquivos/tabela_simbolos");
                //} catch (IOException e) {
                //    e.printStackTrace();  // Imprime o erro na saída de erro padrão
                //}
            }
        }
    }

    @Override
    public void enterComando(MLPParser.ComandoContext ctx) {
        // Incrementa a profundidade e verifica
        profundidadeAtual++;
        customErrors.profundidadeMaxima(ctx.start.getLine());
    }

    @Override
    public void enterAtribuicao(MLPParser.AtribuicaoContext ctx) {
    String nomeVariavel = ctx.IDENTIFICADOR(0).getText();
    //String atribuicao = ctx.IDENTIFICADOR(1).getText();
    if (PALAVRAS_RESERVADAS.contains(nomeVariavel)) {
        customErrors.usoIndevidoPalavraReservada(nomeVariavel, ctx.start.getLine());
         // Ignora a adição na tabela de símbolos
}
    String tipoDeclarado = tabelaSimbolos.get(nomeVariavel);
    // Verificar se a variável foi declarada
    if (!tabelaSimbolos.containsKey(nomeVariavel)) {
        customErrors.variavelNaoDeclarada(nomeVariavel, ctx.start.getLine());
    } else {
        if(ctx.expressao() != null && !ctx.expressao().isEmpty()){
        String atribuicaoNum = ctx.expressao(0).getText();
        // Validação opcional: verificar tipo da variável na tabela
        
        //System.out.println(atribuicao);
        if(tipoDeclarado.equals("inteiro")){
            try{
                
                Integer.parseInt(atribuicaoNum);
                
            } catch(NumberFormatException e){
                customErrors.atribuicaoInvalida(nomeVariavel, ctx.start.getLine(), "foi declarada como inteiro, mas foi feito uma tentativa de atribuição de outro tipo.");
            }

        }else if(tipoDeclarado.equals("real")){
            try{

                double resultado = avaliarExpressao(ctx.expressao(0));
                String resultadoStr = String.valueOf(resultado);
                
                // Verifica se o número possui casas decimais
                if (resultadoStr.contains(".")) {
                    
                } else {
                    customErrors.atribuicaoInvalida(nomeVariavel, ctx.start.getLine(), "foi declarada como real, porem foi feito uma tentativa de atribuição de outro tipo.");
                }
            } catch (NumberFormatException e) {
                customErrors.atribuicaoInvalida(nomeVariavel, ctx.start.getLine(), "foi declarada como real, mas foi feito uma tentativa de atribuição de outro tipo.");
            }
        }
        
    }else if(ctx.IDENTIFICADOR(1) != null){
        String atribuicaoStri = ctx.IDENTIFICADOR(1).getText();
        if(tipoDeclarado.equals("caracter")){
            if (atribuicaoStri.startsWith("\"") && atribuicaoStri.endsWith("\"")) {
                
            } else {
                
                customErrors.atribuicaoInvalida(nomeVariavel, ctx.start.getLine(), "foi declarada como caracter, mas foi feito uma tentativa de atribuição de outro tipo. Verifique a existẽncia de aspas.");
            }
        }

    }


}}

    @Override
    public void exitComando(MLPParser.ComandoContext ctx) {
        // Decrementa a profundidade
        profundidadeAtual--;
    }

    @Override
    public void enterCondicional(MLPParser.CondicionalContext ctx) {
        String inicio = ctx.getStart().getText();

        if( (ctx.condicao().IDENTIFICADOR(0) != null && !ctx.condicao().IDENTIFICADOR(0).getText().isEmpty())){
        String var1 = ctx.condicao().IDENTIFICADOR(0).getText();
        String tipoVar1 = tabelaSimbolos.get(var1);
    
        if((ctx.condicao().IDENTIFICADOR(1) != null && !ctx.condicao().IDENTIFICADOR(1).getText().isEmpty())){
        String var2 = ctx.condicao().IDENTIFICADOR(1).getText();
        
        String tipoVar2 = tabelaSimbolos.get(var2);
        if (!tipoVar1.equals(tipoVar2)) {
            customErrors.variaveisIncompativeis(var1, var2, ctx.start.getLine());
        }
    }
}
        //System.out.println(inicio);
        // Validar estrutura do comando 'secalc
        if(inicio.equals("se")){
           // System.out.println(ctx.condicao());
            if(ctx.condicao() != null){
                if(ctx.getText().contains("entao")){
                    
                }
                else{
                    customErrors.syntaxError(null, null, ctx.start.getLine(), ctx.start.getCharPositionInLine(), "Erro: Não possui 'entao' na estrutura", null);
                }
            }else{
                customErrors.syntaxError(null, null, ctx.start.getLine(), ctx.start.getCharPositionInLine(), "Erro: Condição não fornecida", null);
            }
        }
       
        

        String condicaoTexto = ctx.condicao().getText();
        if (condicaoTexto.contains("<") || condicaoTexto.contains(">") || condicaoTexto.contains("==")) {
            if((ctx.condicao().IDENTIFICADOR(0) != null && !ctx.condicao().IDENTIFICADOR(0).getText().isEmpty())){
                String var1 = ctx.condicao().IDENTIFICADOR(0).getText();
                String tipoVar1 = tabelaSimbolos.get(var1);
            // Verificando se a comparação é válida para os tipos
            if ((tipoVar1.equals("inteiro") || tipoVar1.equals("real")) && (condicaoTexto.contains("\""))) {
                // Caso esteja tentando comparar um inteiro com uma string ou outro tipo incompatível
                customErrors.variaveisIncompativeis(var1, "", ctx.start.getLine());
            }

            
            if(tipoVar1.equals("caracter") && !condicaoTexto.matches(".*[a-zA-Z].*")){
                customErrors.variaveisIncompativeis(var1, "", ctx.start.getLine());
            }
        }
    }

    }
    private double avaliarExpressao(MLPParser.ExpressaoContext ctx) {
        if (ctx.NUMERO() != null) {
            return Double.parseDouble(ctx.NUMERO().getText());
        }
        if (ctx.operador() != null) {
            double op1 = avaliarExpressao(ctx.expressao(0));
            double op2 = avaliarExpressao(ctx.expressao(1));
            switch (ctx.operador().getText()) {
                case "+":
                    return op1 + op2;
                case "-":
                    return op1 - op2;
                case "*":
                    return op1 * op2;
                case "/":
                    return op1 / op2; // Importante: Divisão sempre retorna decimal
            }
        }
        throw new IllegalArgumentException("Expressão inválida.");
    }
    public Map<String, String> getTabelaSimbolos(){
        return tabelaSimbolos;
    }
    public static void saveSymbolsToFile(Map<String, String> tabelaSimbolos, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Itera sobre as entradas do mapa (chave e valor)
            writer.write(String.format("Identificador; Categoria \n"));
            for (Map.Entry<String, String> entry : tabelaSimbolos.entrySet()) {
                // Escreve a chave e o valor no arquivo
                if(entry.getValue().equals("inteiro") || entry.getValue().equals("real") || entry.getValue().equals("caracter")){
                writer.write(String.format("%s;variavel %s", entry.getKey(), entry.getValue()));
                writer.newLine();  // Adiciona uma nova linha após cada entrada
            }
        }
        }
    }
    
}
