
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class Mochila { // Esta classe foi renomeada para 'ItemMochila' no meu exemplo anterior para clareza
                       // mas mantida como 'Mochila' aqui, conforme seu pedido.
    private String nome;
    private double peso;
    private double valor;
    private double valorPorPeso; // esse sera o que vou usar para calcular o algoritmo guloso

    public Mochila(String nome, double peso, double valor) {
        // Adição: Verificação para evitar divisão por zero ou peso negativo/inválido
        if (peso <= 0) {
            throw new IllegalArgumentException("Peso do item deve ser maior que zero para o cálculo do valor por peso.");
        }
        this.nome = nome;
        this.peso = peso;
        this.valor = valor;
        this.valorPorPeso = valor / peso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValorPorPeso() {
        return valorPorPeso;
    }

    public void setValorPorPeso(double valorPorPeso) {
        this.valorPorPeso = valorPorPeso;
    }

    @Override
    public String toString() { // aqui vou imprimir o nome,peso,valor e valor por peso
        // Ajuste no formato para melhor legibilidade na saída
        return String.format("Mochila{nome='%s', peso=%.2fkg, valor=R$%.2f, valorPorPeso=R$%.2f/kg}",
                             nome, peso, valor, valorPorPeso);
    }

    // aqui vou criar o metodo guloso para resolver o problema da mochila
    // ele implementa na mochila de 0/1 ele vai selecionar o item com o maior valor por peso
    public static class ProblemaMochilaGuloso {
        // aqui vou criar o metodo guloso para resolver o problema da mochila
        // ele implementa na mochila de 0/1 ele vai selecionar o item com o maior valor por peso
        // Mudança: O método agora recebe a capacidade da mochila como parâmetro para ser flexível.
        public void resolverMochilaGuloso(double capacidadeMochila, List<Mochila> objetos) { // 'calcularValorPorPeso' renomeado para 'resolverMochilaGuloso', e tipo de lista ajustado para 'Mochila'

            // Aqui vou iniciar o calculo do valor por peso para cada objeto
            for (Mochila obj : objetos) { // para cada objeto na lista de objetos
                if (obj.getPeso() > 0) { // nessa linha estou pegando o peso e evitanto que ele tenha uma divisao por zero
                    obj.setValorPorPeso(obj.getValor() / obj.getPeso()); // aqui estou calculando o valor por peso
                } else {
                    obj.setValorPorPeso(0.0); // se o peso for zero, o valor por peso sera zero
                }
            }
            // agora vou fazer a ordenação do objeto com o maior valor por peso
            Collections.sort(objetos, (obj1, obj2) -> Double.compare(obj2.getValorPorPeso(), obj1.getValorPorPeso()));
            // aqui estou ordenando os objetos com o maior valor por peso
            // o collections.sort é uma função que ordena os objetos com o maior valor por peso

            // Removidas variáveis locais 'mochila', 'capacidadeMochila', 'valorTotal' que causavam confusão
            // e substituídas por variáveis de controle que refletem o estado atual da mochila.

            System.out.printf("--- Iniciando a seleção de objetos para a mochila (Capacidade: %.2f kg) ---\n", capacidadeMochila);
            System.out.println("Objetos disponíveis (ordenados por Valor/Peso):");

            for (Mochila obj : objetos) { // objetos: Pega um objeto da lista de objetos
                // obj: Atribui esse objeto a variavel obj
                // Mochila: Declara uma variável temporária
                System.out.println("  " + obj); // imprime o objeto
            }
            System.out.println("----------------------------------------------------------------------");

            // Variáveis para controle da mochila
            double pesoAtualMochila = 0.0;
            double valorTotalMochila = 0.0;
            List<String> itensNomesNaMochila = new ArrayList<>(); // Mudado para armazenar apenas nomes, é mais comum

            // O parâmetro 'capacidadeMochila' agora é usado diretamente.
            // A linha 'capacidadeMochila = 10.0;' foi removida daqui, pois a capacidade deve vir do main.

            // Agora vou encher a mochila com os objetos
            for (Mochila obj : objetos) {
                System.out.printf("\nAnalisando: '%s' (Peso: %.2f kg, Preço: R$%.2f, Valor/Peso: R$%.2f/kg)\n",
                                  obj.getNome(), obj.getPeso(), obj.getValor(), obj.getValorPorPeso());

                // verificação se o objeto cabe na mochila
                if (pesoAtualMochila + obj.getPeso() <= capacidadeMochila) {
                    // se o objeto couber ele vai adidionar na mochila
                    itensNomesNaMochila.add(obj.getNome()); // Adiciona o nome do item à lista
                    valorTotalMochila += obj.getValor(); // aqui vou somar o valor do item na mochila
                    pesoAtualMochila += obj.getPeso(); // aqui vou somar o peso do item na mochila
                    System.out.printf("  ✅ Escolha feita: '%s' ADICIONADO à mochila.\n", obj.getNome()); // aqui vou imprimir que o item foi adicionado na mochila
                    System.out.printf("     Peso atual na mochila: %.2f kg / %.2f kg (Total)\n", pesoAtualMochila, capacidadeMochila); // aqui vou imprimir o peso atual na mochila e o peso total da mochila
                    System.out.printf("     Valor total na mochila: R$%.2f\n", valorTotalMochila); // aqui vou imprimir o valor total na mochila
                } else {
                    System.out.printf("  ❌ Descartado: '%s' (Peso: %.2f kg, Valor: R$%.2f). Capacidade restante: %.2f kg.\n", // Adicionado: Mensagem mais detalhada.
                                      obj.getNome(), obj.getPeso(), obj.getValor(), (capacidadeMochila - pesoAtualMochila)); // aqui vou imprimir que o item foi descartado e o peso restante da mochila
                }
            }

            System.out.println("\n--- Seleção de objetos concluída! ---");
            System.out.println("\n** Resumo Final da Mochila **"); 
            System.out.println("Objetos selecionados:");
            if (itensNomesNaMochila.isEmpty()) { // Adicionado: Mensagem se a mochila estiver vazia
                System.out.println("  Nenhum objeto foi adicionado à mochila.");
            } else {
                for (String itemNome : itensNomesNaMochila) { // Iterar sobre os nomes dos itens
                    System.out.println("  - " + itemNome);
                }
            }
            System.out.printf("Valor total combinado na mochila: R$%.2f\n", valorTotalMochila);
            System.out.printf("Peso total na mochila: %.2f kg\n", pesoAtualMochila);
        }

        public static void main(String[] args) {
            //criando os objetos
            // Usando o nome da classe 'Mochila' conforme sua definição
            Mochila obj1 = new Mochila("Arroz", 1.0, 10.0);
            Mochila obj2 = new Mochila("Feijão", 2.0, 15.0);
            Mochila obj3 = new Mochila("Carne", 3.0, 20.0);
            Mochila obj4 = new Mochila("Pão", 1.0, 5.0);
            Mochila obj5 = new Mochila("Leite", 2.0, 10.0);
            Mochila obj6 = new Mochila("Queijo", 3.0, 15.0);
            Mochila obj7 = new Mochila("Chocolate", 0.2, 8.0); 
            Mochila obj8 = new Mochila("Refrigerante", 2.0, 7.0); 


            //criando a lista de objetos para serem comparados
            List<Mochila> objetos = new ArrayList<>();
            objetos.add(obj1);
            objetos.add(obj2);
            objetos.add(obj3);
            objetos.add(obj4);
            objetos.add(obj5);
            objetos.add(obj6);
            objetos.add(obj7);
            objetos.add(obj8);

            // Definindo a capacidade da mochila fora do método de solução
            double capacidadeMochila = 10.0;

            //chamando o metodo para resolver o problema
            ProblemaMochilaGuloso problema = new ProblemaMochilaGuloso();
            // Chamando o método com a capacidade da mochila como parâmetro
            problema.resolverMochilaGuloso(capacidadeMochila, objetos);
        }
    }
}