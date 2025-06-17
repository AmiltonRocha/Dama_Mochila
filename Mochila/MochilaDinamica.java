import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

// Quarta Questão
public class MochilaDinamica {
    private String nome;
    private double peso;
    private double valor;
    private double valorPorPeso;

    public MochilaDinamica(String nome, double peso, double valor) {
        if (peso <= 0) {
            throw new IllegalArgumentException("Peso do item deve ser maior que zero para o cálculo do valor por peso.");
        }
        this.nome = nome;
        this.peso = peso;
        this.valor = valor;
        this.valorPorPeso = valor / peso;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public double getValorPorPeso() { return valorPorPeso; }
    public void setValorPorPeso(double valorPorPeso) { this.valorPorPeso = valorPorPeso; }

    @Override
    public String toString() {
        return String.format("Mochila{nome='%s', peso=%.2fkg, valor=R$%.2f, valorPorPeso=R$%.2f/kg}",
                             nome, peso, valor, valorPorPeso);
    }

    public static class ProblemaMochilaDinamica {
        public void resolverMochilaDinamica(double capacidadeMochila, List<MochilaDinamica> itens) {
            int n = itens.size(); 
            int W = (int) capacidadeMochila; 

            double[][] dp = new double[n + 1][W + 1];

            for (int i = 1; i <= n; i++) {
                for (int w = 0; w <= W; w++) {
                        MochilaDinamica currentItem = itens.get(i - 1); 
                        if (currentItem.getPeso() <= w) { 
                            double valorSeIncluir = currentItem.getValor() + dp[i - 1][(int)(w - currentItem.getPeso())];
                            double valorSeNaoIncluir = dp[i - 1][w];
                            dp[i][w] = Math.max(valorSeIncluir, valorSeNaoIncluir);
                        } else {
                            dp[i][w] = dp[i - 1][w];
                        }                     
                }
            }

            // Impressão da matriz dp[][] (programação dinâmica)
            System.out.println("\n--- Matriz de Programação Dinâmica (dp[][]) ---");
            System.out.print("      ");
            for (int w = 0; w <= W; w++) {
                System.out.printf("%6d", w);
            }
            System.out.println();
            for (int i = 0; i <= n; i++) {
                if (i == 0)
                    System.out.print("i=0 | ");
                else
                    System.out.printf("i=%-2d| ", i);

                for (int w = 0; w <= W; w++) {
                    System.out.printf("%6.1f", dp[i][w]);
                }
                System.out.println();
            }

            // Rastreando itens selecionados
            List<String> itensSelecionados = new ArrayList<>();
            double res = dp[n][W];
            int currentW = W; 

            for (int i = n; i > 0 && res > 0; i--) {
                if (res != dp[i - 1][currentW]) {
                    MochilaDinamica includedItem = itens.get(i - 1);
                    itensSelecionados.add(includedItem.getNome());
                    res -= includedItem.getValor();
                    currentW -= includedItem.getPeso();

                    System.out.printf("ESCOLHA FEITA: Item '%s' (Peso: %.2f kg, Valor: R$%.2f) foi incluído.\n",
                            includedItem.getNome(), includedItem.getPeso(), includedItem.getValor());
                    System.out.printf("  Peso restante na mochila: %.2f kg. Valor restante a rastrear: R$%.2f.\n", 
                            (double) currentW, res);
                } else {
                    MochilaDinamica discardedItem = itens.get(i - 1);
                    System.out.printf("DESCARTE: Item '%s' (Peso: %.2f kg, Valor: R$%.2f) NÃO foi incluído.\n",
                            discardedItem.getNome(), discardedItem.getPeso(), discardedItem.getValor());
                }
            }

            Collections.reverse(itensSelecionados);

            System.out.println("\n--- Solução da Mochila (Programação Dinâmica) ---");
            System.out.printf("Capacidade da mochila: %.2f kg\n", capacidadeMochila);
            System.out.println("Objetos selecionados:");
            if (itensSelecionados.isEmpty()) {
                System.out.println("  Nenhum objeto foi adicionado à mochila.");
            } else {
                for (String itemNome : itensSelecionados) {
                    System.out.println("  - " + itemNome);
                }
            }
            System.out.printf("Valor total na mochila: R$%.2f\n", dp[n][W]);
            System.out.println("O peso total ocupado é o peso dos itens selecionados (precisaria ser calculado separadamente ou durante o rastreamento).");
        }

        public static void main(String[] args) {
            MochilaDinamica obj1 = new MochilaDinamica("Arroz", 1.0, 10.0);
            MochilaDinamica obj2 = new MochilaDinamica("Feijão", 2.0, 15.0);
            MochilaDinamica obj3 = new MochilaDinamica("Carne", 3.0, 20.0);
            MochilaDinamica obj4 = new MochilaDinamica("Pão", 1.0, 5.0);
            MochilaDinamica obj5 = new MochilaDinamica("Leite", 2.0, 10.0);
            MochilaDinamica obj6 = new MochilaDinamica("Queijo", 3.0, 15.0);
            MochilaDinamica obj7 = new MochilaDinamica("Chocolate", 0.2, 8.0);
            MochilaDinamica obj8 = new MochilaDinamica("Refrigerante", 2.0, 7.0);

            List<MochilaDinamica> objetos = new ArrayList<>();
            objetos.add(obj1);
            objetos.add(obj2);
            objetos.add(obj3);
            objetos.add(obj4);
            objetos.add(obj5);
            objetos.add(obj6);
            objetos.add(obj7);
            objetos.add(obj8);

            double capacidadeMochila = 10.0;

            ProblemaMochilaDinamica problema = new ProblemaMochilaDinamica();
            problema.resolverMochilaDinamica(capacidadeMochila, objetos);
        }
    } // end of ProblemaMochilaDinamica class
}
