import org.antlr.v4.runtime.tree.ParseTreeListener;
import java.util.Stack;

public class AssemblyINTERMEDIARIO extends MLPBaseListener {

    private StringBuilder assemblyCode = new StringBuilder();
    private Stack<String> labels = new Stack<>();
    private Stack<String> availableRegisters = new Stack<>();
    private int labelCounter = 1;
    private int registerCounter = 1;

    public String getAssemblyCode() {
        return assemblyCode.toString();
    }

    private String getRegister() {
        // Se a pilha de registradores disponíveis estiver vazia, cria novos registros.
        if (availableRegisters.isEmpty()) {
            return "R" + registerCounter++;  // Gera R1, R2, R3, etc.
        } else {
            return availableRegisters.pop();  // Pega o último registrador da pilha
        }
    }

    private void releaseRegister(String reg) {
        // Libera o registrador, colocando-o de volta na pilha.
        availableRegisters.push(reg);
    }

    @Override
    public void enterInicio(MLPParser.InicioContext ctx) {
        assemblyCode.append("; Início do programa\n");
    }

    @Override
    public void exitInicio(MLPParser.InicioContext ctx) {
        assemblyCode.append("; Fim do programa\n");
    }

    @Override
    public void enterTipo(MLPParser.TipoContext ctx) {
        String tipo = ctx.getChild(0).getText();
        for (int i = 1; i < ctx.children.size(); i++) {
            String var = ctx.children.get(i).getText();
            if (!var.equals(";") && !var.equals(",")) {
                assemblyCode.append("; Declarando ").append(tipo).append(" ").append(var).append("\n");
            }
        }
    }

    @Override
public void enterAtribuicao(MLPParser.AtribuicaoContext ctx) {
    String var = ctx.IDENTIFICADOR(0).getText();
    String reg = getRegister(); // Obtém um registrador para a operação

    // Verificar se a expressão existe e tratar o caso corretamente
    if (ctx.expressao() != null && !ctx.expressao().isEmpty()) {
        String exprValue = ctx.expressao(0).getText(); // Pega a primeira expressão, se houver
        System.out.println(exprValue);
        
        // Detectando tipo de operação
        if (ctx.operador() != null && !ctx.operador().isEmpty()) {
            String op = ctx.operador(0).getText(); // Pega o primeiro operador
            if (ctx.expressao().size() > 1) {
                if(op.contains("+")) {  // Se a expressão envolver soma
                    String leftExpr = ctx.expressao(0).getText();
                    String rightExpr = ctx.expressao(1).getText();
                    String reg1 = getRegister();
                    String reg2 = getRegister();
                    assemblyCode.append("    LOAD ").append(reg1).append(", ").append(leftExpr).append("    ; Carrega ").append(leftExpr).append(" em ").append(reg1).append("\n");
                    assemblyCode.append("    LOAD ").append(reg2).append(", ").append(rightExpr).append("    ; Carrega ").append(rightExpr).append(" em ").append(reg2).append("\n");
                    assemblyCode.append("    ADD ").append(reg).append(", ").append(reg1).append(", ").append(reg2).append("    ; Soma ").append(leftExpr).append(" e ").append(rightExpr).append("\n");
                    releaseRegister(reg1);
                    releaseRegister(reg2);
                }
            }
        } else if (ctx.expressao().size() > 1) {
            String op = ctx.operador(0).getText();
            if(op.contains("-")) {
            String reg1 = getRegister();
            String reg2 = getRegister();
            String leftExpr = ctx.expressao(0).getText();
            String rightExpr = ctx.expressao(1).getText();
            assemblyCode.append("    LOAD ").append(reg1).append(", ").append(leftExpr).append("    ; Carrega ").append(leftExpr).append(" em ").append(reg1).append("\n");
            assemblyCode.append("    LOAD ").append(reg2).append(", ").append(rightExpr).append("    ; Carrega ").append(rightExpr).append(" em ").append(reg2).append("\n");
            assemblyCode.append("    SUB ").append(reg).append(", ").append(reg1).append(", ").append(reg2).append("    ; Subtrai ").append(rightExpr).append(" de ").append(leftExpr).append("\n");
            releaseRegister(reg1);
            releaseRegister(reg2);
        }} else if (ctx.expressao().size() > 1) {
            String op = ctx.operador(0).getText();
            if(op.contains("*")) {
                String leftExpr = ctx.expressao(0).getText();
                String rightExpr = ctx.expressao(1).getText();
                String reg1 = getRegister();
                String reg2 = getRegister();
                assemblyCode.append("    LOAD ").append(reg1).append(", ").append(leftExpr).append("    ; Carrega ").append(leftExpr).append(" em ").append(reg1).append("\n");
                assemblyCode.append("    LOAD ").append(reg2).append(", ").append(rightExpr).append("    ; Carrega ").append(rightExpr).append(" em ").append(reg2).append("\n");
                assemblyCode.append("    MUL ").append(reg).append(", ").append(reg1).append(", ").append(reg2).append("    ; Multiplica ").append(leftExpr).append(" por ").append(rightExpr).append("\n");
                releaseRegister(reg1);
                releaseRegister(reg2);
        }}
        else if (ctx.expressao().size() > 1) {
            String op = ctx.operador(0).getText();
            if(op.contains("/")) {
                String reg1 = getRegister();
                String reg2 = getRegister();
                String leftExpr = ctx.expressao(0).getText();
                String rightExpr = ctx.expressao(1).getText();
                assemblyCode.append("    LOAD ").append(reg1).append(", ").append(leftExpr).append("    ; Carrega ").append(leftExpr).append(" em ").append(reg1).append("\n");
                assemblyCode.append("    LOAD ").append(reg2).append(", ").append(rightExpr).append("    ; Carrega ").append(rightExpr).append(" em ").append(reg2).append("\n");
                assemblyCode.append("    DIV ").append(reg).append(", ").append(reg1).append(", ").append(reg2).append("    ; Divide ").append(leftExpr).append(" por ").append(rightExpr).append("\n");
                releaseRegister(reg1);
                releaseRegister(reg2);
        }}else if (exprValue.matches("\\d+")) {  // Se for um número inteiro
            assemblyCode.append("    LOADI ").append(reg).append(", ").append(exprValue).append("    ; Carrega ").append(exprValue).append(" em ").append(reg).append("\n");
        }
    } else if (ctx.FRASE() != null) {
        String phraseValue = ctx.FRASE().getText();
        assemblyCode.append("    LOADS ").append(reg).append(", ").append(phraseValue).append("    ; Carrega frase em ").append(reg).append("\n");
    }

    // Se houver operador, processar também


    assemblyCode.append("    STORE ").append(var).append(", ").append(reg).append("    ; Armazena em ").append(var).append("\n");
    releaseRegister(reg); // Libera o registrador após a operação
}


    
    

@Override
public void enterCondicional(MLPParser.CondicionalContext ctx) {
    String labelStart = "L" + labelCounter++;
    String labelEnd = "L" + labelCounter++;
    String labelElse = "L" + labelCounter++;

    // Empurrando as labels para a pilha
    labels.push(labelStart);
    labels.push(labelElse);
    labels.push(labelEnd);

    // Início do bloco condicional
    assemblyCode.append(labelStart).append(":    ; Início do bloco 'se'\n");

    // Extração de operandos e comparação
    String operando1 = ctx.condicao().getChild(1).getText(); // Primeiro identificador
    String operador = ctx.condicao().getChild(2).getText(); // Operador lógico
    String operando2 = ctx.condicao().getChild(3).getText(); // Segundo identificador ou número

    String reg1 = getRegister();
    String reg2 = getRegister();

    // Carregar os operandos
    assemblyCode.append("    LOAD ").append(reg1).append(", ").append(operando1).append("    ; Carrega o valor de ").append(operando1).append("\n");
    assemblyCode.append("    LOAD ").append(reg2).append(", ").append(operando2).append("    ; Carrega o valor de ").append(operando2).append("\n");

    // Gerar comparação
    String instrucao = switch (operador) {
        case ">" -> "CMPGT";
        case "<" -> "CMPLT";
        case "==" -> "CMPEQ";
        case "!=" -> "CMPNE";
        case "<=" -> "CMPLE";
        case ">=" -> "CMPGE";
        default -> throw new RuntimeException("Operador desconhecido: " + operador);
    };

    String regCond = getRegister();
    assemblyCode.append("    ").append(instrucao).append(" ").append(regCond).append(", ").append(reg1).append(", ").append(reg2).append("    ; Compara ").append(operando1).append(" ").append(operador).append(" ").append(operando2).append("\n");
    releaseRegister(reg1);
    releaseRegister(reg2);

    // Salto condicional para o bloco 'entretanto'
    assemblyCode.append("    JMPFALSE ").append(regCond).append(", ").append(labelElse).append("    ; Salta para 'entretanto' se a comparação for falsa\n");
    releaseRegister(regCond);
}



@Override
public void exitCondicional(MLPParser.CondicionalContext ctx) {
    // Garantir que as labels tenham sido empurradas para a pilha corretamente
    String labelEnd = labels.pop();
    String labelElse = labels.pop();
    String labelStart = labels.pop(); // Remover a label do início do bloco 'se'

    // Finalizar bloco "então" e saltar para o fim
    assemblyCode.append("    JMP ").append(labelEnd).append("    ; Salta para o fim da condicional\n");

    // Bloco "else"
    assemblyCode.append(labelElse).append(":    ; Início do bloco 'entao'\n");
    assemblyCode.append(labelEnd).append(":    ; Fim da condicional\n");
}


@Override
public void enterIterativo(MLPParser.IterativoContext ctx) {
    String labelStart = "L" + labelCounter++;
    String labelEnd = "L" + labelCounter++;

    labels.push(labelStart);
    labels.push(labelEnd);

    assemblyCode.append(labelStart).append(":    ; Início do laço\n");

    // Carregar operandos para a condição do loop
    String operando1 = ctx.condicao().getChild(1).getText();
    String operador = ctx.condicao().getChild(2).getText();
    String operando2 = ctx.condicao().getChild(3).getText();

    String reg1 = getRegister();
    String reg2 = getRegister();

    assemblyCode.append("    LOAD ").append(reg1).append(", ").append(operando1).append("    ; Carrega o valor de ").append(operando1).append("\n");
    assemblyCode.append("    LOAD ").append(reg2).append(", ").append(operando2).append("    ; Carrega o valor de ").append(operando2).append("\n");

    String instrucao = switch (operador) {
        case ">" -> "CMPGT";
        case "<" -> "CMPLT";
        case "==" -> "CMPEQ";
        case "!=" -> "CMPNE";
        case "<=" -> "CMPLE";
        case ">=" -> "CMPGE";
        default -> throw new RuntimeException("Operador desconhecido: " + operador);
    };

    String regCond = getRegister();
    assemblyCode.append("    ").append(instrucao).append(" ").append(regCond).append(", ").append(reg1).append(", ").append(reg2).append("    ; Compara ").append(operando1).append(" ").append(operador).append(" ").append(operando2).append("\n");
    releaseRegister(reg1);
    releaseRegister(reg2);

    // Salto condicional para o fim do loop
    assemblyCode.append("    JMPFALSE ").append(regCond).append(", ").append(labelEnd).append("    ; Salta para o fim do laço se falso\n");
    releaseRegister(regCond);
}

@Override
public void exitIterativo(MLPParser.IterativoContext ctx) {
    String labelEnd = labels.pop();
    String labelStart = labels.pop();

    // Código do loop (incremento, por exemplo)
    String regC = getRegister();
    assemblyCode.append("    LOAD ").append(regC).append(", c    ; Carrega o valor de c\n");
    assemblyCode.append("    ADD ").append(regC).append(", ").append(regC).append(", 1    ; Incrementa c\n");
    assemblyCode.append("    STORE c, ").append(regC).append("    ; Armazena o valor incrementado de c\n");
    releaseRegister(regC);

    // Voltar ao início do loop
    assemblyCode.append("    JMP ").append(labelStart).append("    ; Volta ao início do laço\n");

    assemblyCode.append(labelEnd).append(":    ; Fim do laço\n");
}
}