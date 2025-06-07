import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//Quinta Questão
public class MochilaIlimitada {
    private String nome;
    private double peso;
    private double valor;

    public MochilaIlimitada(String nome, double peso, double valor) {
        if (peso <= 0) {
            throw new IllegalArgumentException("Peso do item deve ser maior que zero.");
        }
        this.nome = nome;
        this.peso = peso;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public double getPeso() {
        return peso;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("Item{nome='%s', peso=%.2fkg, valor=R$%.2f}", nome, peso, valor);
    }

    public static class ProblemaMochilaIlimitada {
        public void resolverMochilaIlimitada(double capacidadeMochila, List<MochilaIlimitada> itens) {
            int W = (int) capacidadeMochila;
            double[] dp = new double[W + 1];
            int[] itemCount = new int[W + 1];
            int[] lastItem = new int[W + 1];

            // Inicializa o array dp
            for (int w = 1; w <= W; w++) {
                for (int i = 0; i < itens.size(); i++) {
                    MochilaIlimitada item = itens.get(i);
                    if (item.getPeso() <= w) {
                        double valorSeIncluir = item.getValor() + dp[(int)(w - item.getPeso())];
                        if (valorSeIncluir > dp[w]) {
                            dp[w] = valorSeIncluir;
                            itemCount[w] = itemCount[(int)(w - item.getPeso())] + 1;
                            lastItem[w] = i;
                        }
                    }
                }
            }

            // Rastreia os itens selecionados
            Map<String, Integer> quantidadePorItem = new HashMap<>();
            int w = W;
            double valorTotal = 0;
            double pesoTotal = 0;

            System.out.println("\n--- Solução da Mochila Ilimitada (Programação Dinâmica) ---");
            System.out.printf("Capacidade da mochila: %.2f kg\n", capacidadeMochila);
            System.out.println("\nProcesso de seleção:");

            while (w > 0 && itemCount[w] > 0) {
                MochilaIlimitada item = itens.get(lastItem[w]);
                quantidadePorItem.put(item.getNome(), 
                    quantidadePorItem.getOrDefault(item.getNome(), 0) + 1);
                
                System.out.printf("ESCOLHA FEITA: Item '%s' (Peso: %.2f kg, Valor: R$%.2f) foi incluído.\n",
                                item.getNome(), item.getPeso(), item.getValor());
                
                valorTotal += item.getValor();
                pesoTotal += item.getPeso();
                w -= item.getPeso();
                
                System.out.printf("  Peso restante na mochila: %.2f kg\n", (double)w);
            }

            System.out.println("\nResultado final:");
            System.out.println("Quantidade de cada item na mochila:");
            for (Map.Entry<String, Integer> entry : quantidadePorItem.entrySet()) {
                System.out.printf("  - %s: %d unidade(s)\n", entry.getKey(), entry.getValue());
            }
            
            System.out.printf("\nValor total na mochila: R$%.2f\n", valorTotal);
            System.out.printf("Peso total ocupado: %.2f kg\n", pesoTotal);
            System.out.printf("Espaço restante na mochila: %.2f kg\n", capacidadeMochila - pesoTotal);
        }

        public static void main(String[] args) {
            // Criação dos objetos (itens) que podem ser colocados na mochila
            MochilaIlimitada obj1 = new MochilaIlimitada("Arroz", 5.0, 10.0);
            MochilaIlimitada obj2 = new MochilaIlimitada("Feijão", 2.0, 15.0);
            MochilaIlimitada obj3 = new MochilaIlimitada("Carne", 3.0, 20.0);
            MochilaIlimitada obj4 = new MochilaIlimitada("Pão", 1.0, 5.0);
            MochilaIlimitada obj5 = new MochilaIlimitada("Leite", 2.0, 10.0);
            MochilaIlimitada obj6 = new MochilaIlimitada("Queijo", 3.0, 15.0);
            MochilaIlimitada obj7 = new MochilaIlimitada("Chocolate", 0.2, 8.0);
            MochilaIlimitada obj8 = new MochilaIlimitada("Refrigerante", 2.0, 7.0);

            List<MochilaIlimitada> itens = new ArrayList<>();
            itens.add(obj1);
            itens.add(obj2);
            itens.add(obj3);
            itens.add(obj4);
            itens.add(obj5);
            itens.add(obj6);
            itens.add(obj7);
            itens.add(obj8);

            double capacidadeMochila = 10.0;

            ProblemaMochilaIlimitada problema = new ProblemaMochilaIlimitada();
            problema.resolverMochilaIlimitada(capacidadeMochila, itens);
        }
    }
} 