import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
//Terceira Questão
public class MochilaGulosa {
    private String nome;
    private double peso;
    private double valor;
    private double valorPorPeso;

    public MochilaGulosa(String nome, double peso, double valor) {
        if (peso <= 0) {
            throw new IllegalArgumentException("Peso do item deve ser maior que zero.");
        }
        this.nome = nome;
        this.peso = peso;
        this.valor = valor;
        this.valorPorPeso = valor / peso;
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

    public double getValorPorPeso() {
        return valorPorPeso;
    }

    @Override
    public String toString() {
        return String.format("Item{nome='%s', peso=%.2fkg, valor=R$%.2f, valorPorPeso=R$%.2f/kg}",
                           nome, peso, valor, valorPorPeso); // aqui vou imprimir o item    
    }

    public static class ProblemaMochilaGulosa {// aqui vou criar a classe problema mochila gulosa
        public void resolverMochilaGulosa(double capacidadeMochila, List<MochilaGulosa> itens) {
            // Ordena os itens por valor/peso em ordem decrescente
            itens.sort(Comparator.comparingDouble(MochilaGulosa::getValorPorPeso).reversed());// aqui vou ordenar os itens por valor/peso em ordem decrescente

            double pesoRestante = capacidadeMochila;// aqui vou inicializar o peso restante da mochila
            double valorTotal = 0;// aqui vou inicializar o valor total da mochila
            List<MochilaGulosa> itensSelecionados = new ArrayList<>();// aqui vou inicializar a lista de itens selecionados

            System.out.println("\n--- Solução da Mochila (Algoritmo Guloso) ---");
            System.out.printf("Capacidade da mochila: %.2f kg\n", capacidadeMochila);
            System.out.println("\nProcesso de seleção:");

            for (MochilaGulosa item : itens) {// aqui vou percorrer os itens
                if (item.getPeso() <= pesoRestante) {// aqui vou verificar se o peso do item é menor ou igual ao peso restante da mochila
                    itensSelecionados.add(item);// aqui vou adicionar o item na lista de itens selecionados
                    pesoRestante -= item.getPeso();// aqui vou subtrair o peso do item do peso restante da mochila
                    valorTotal += item.getValor();// aqui vou somar o valor do item ao valor total da mochila
                    
                    System.out.printf("ESCOLHA FEITA: Item '%s' (Peso: %.2f kg, Valor: R$%.2f) foi incluído.\n",
                                    item.getNome(), item.getPeso(), item.getValor());// aqui vou imprimir que o item foi incluído
                    System.out.printf("  Peso restante na mochila: %.2f kg\n", pesoRestante);
                } else {// aqui vou imprimir que o item foi descartado
                    System.out.printf("DESCARTE: Item '%s' (Peso: %.2f kg, Valor: R$%.2f) NÃO foi incluído.\n",
                                    item.getNome(), item.getPeso(), item.getValor());// aqui vou imprimir que o item foi descartado
                }
            }

            System.out.println("\nResultado final:");
            System.out.println("Itens selecionados:");
            if (itensSelecionados.isEmpty()) {// aqui vou verificar se a lista de itens selecionados está vazia
                System.out.println("  Nenhum objeto foi adicionado à mochila.");// aqui vou imprimir que não há itens selecionados
            } else {
                for (MochilaGulosa item : itensSelecionados) {// aqui vou percorrer os itens selecionados
                    System.out.printf("  - %s (Peso: %.2f kg, Valor: R$%.2f)\n",
                                    item.getNome(), item.getPeso(), item.getValor());// aqui vou imprimir os itens selecionados
                }
            }
            System.out.printf("Valor total na mochila: R$%.2f\n", valorTotal);
            System.out.printf("Peso total ocupado: %.2f kg\n", capacidadeMochila - pesoRestante);
            System.out.printf("Espaço restante na mochila: %.2f kg\n", pesoRestante);
        }

        public static void main(String[] args) {
            // Criação dos objetos (itens) que podem ser colocados na mochila
            MochilaGulosa obj1 = new MochilaGulosa("Arroz", 1.0, 10.0);
            MochilaGulosa obj2 = new MochilaGulosa("Feijão", 2.0, 15.0);
            MochilaGulosa obj3 = new MochilaGulosa("Carne", 3.0, 20.0);
            MochilaGulosa obj4 = new MochilaGulosa("Pão", 1.0, 5.0);
            MochilaGulosa obj5 = new MochilaGulosa("Leite", 2.0, 10.0);
            MochilaGulosa obj6 = new MochilaGulosa("Queijo", 3.0, 15.0);
            MochilaGulosa obj7 = new MochilaGulosa("Chocolate", 0.2, 8.0);
            MochilaGulosa obj8 = new MochilaGulosa("Refrigerante", 2.0, 7.0);

            List<MochilaGulosa> itens = new ArrayList<>();
            itens.add(obj1);
            itens.add(obj2);
            itens.add(obj3);
            itens.add(obj4);
            itens.add(obj5);
            itens.add(obj6);
            itens.add(obj7);
            itens.add(obj8);

            double capacidadeMochila = 2.0;

            ProblemaMochilaGulosa problema = new ProblemaMochilaGulosa();
            problema.resolverMochilaGulosa(capacidadeMochila, itens);
        }
    }
} 