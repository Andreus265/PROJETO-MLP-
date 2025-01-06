import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Arvore extends MLPBaseListener {

    public static void extractNodes(String treeString) {
        // Remover os delimitadores externos "inicio $ ... $."
        treeString = treeString.trim();
        if (treeString.startsWith("inicio $")) {
            treeString = treeString.substring(8, treeString.length() - 3).trim();
            //System.out.println(treeString);
        }

        // Raiz da árvore
        int nivel_atual = 0;
        Galhos root = new Galhos("Inicio", nivel_atual);
        nivel_atual++;
        // Pilha para gerenciar os níveis da árvore
        Stack<Galhos> stack = new Stack<>();
        stack.push(root);

        // Dividir a entrada nos elementos da árvore
        String[] tokens = treeString.split("\\s+|(?=\\()|(?<=\\))|(?=\\$)|(?<=\\$)");
        
        for (String token : tokens) {
            System.out.println(token +" " + nivel_atual);
        
            if (token.isEmpty()) continue;

            if (token.contains("(")) {
                nivel_atual++;
                
                // Abrir um novo nível (não há ação específica aqui)
            } else if (token.equals(";")) {
                // Fechar o nível atual
                stack.pop();
                nivel_atual--;
                
            } else if (token.equals(";)")) {
                // Adicionar o delimitador como folha
                stack.peek().adicionarFolha(new Galhos(";", nivel_atual));
            } else {
                // Criar um novo nó e adicioná-lo à árvore
                Galhos newNode = new Galhos(token, nivel_atual);
                stack.peek().adicionarFolha(newNode);
                if (token.matches("[;=<>+\\-*/]")) {
                    // Apenas empilhar se não for um delimitador ou operador
                    stack.push(newNode);
                }
            }
        }

        // Imprimir a árvore
        root.printTree("");
    }

}