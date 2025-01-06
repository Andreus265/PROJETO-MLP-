import java.util.ArrayList;
import java.util.List;

class Galhos {
    String tipo;
    List<Galhos> folhas;
    int nivel;

    public Galhos(String tipo, int nivel) {
        this.tipo = tipo;
        this.nivel = nivel;
        this.folhas = new ArrayList<>();
    }

    public void adicionarFolha(Galhos folha) {
        folhas.add(folha);
    }

    public void printTree(String prefixo) {
        System.out.println(prefixo + "- " + tipo);
        for (Galhos folha : folhas) {
            folha.printTree(prefixo + "  ");
        }
    }
}

