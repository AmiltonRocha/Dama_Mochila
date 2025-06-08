import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
//Quarta Questão
public class MochilaDinamica {
    private String nome;
    private double peso;
    private double valor;
    private double valorPorPeso; // esse sera o que vou usar para calcular o algoritmo guloso

    public MochilaDinamica(String nome, double peso, double valor) {
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
    //implementa a solução do Problema da Mochila 0/1 usando Programação Dinâmica

    public static class ProblemaMochilaDinamica {
// Resolve o Problema da Mochila 0/1 usando Programação Dinâmica. Este método calcula o valor máximo que pode ser colocado em uma mochila de dada capacidade, escolhendo itens que não podem ser fracionados (0 ou 1, entra ou não entra).
        public void resolverMochilaDinamica(double capacidadeMochila, List<MochilaDinamica> itens) {
            int n = itens.size(); //pega o numero de itens
            int W = (int) capacidadeMochila; //pega a capacidade da mochila

//armazenará o valor máximo que pode ser obtido usando os primeiros 'i' itens com uma capacidade de mochila 'w'. As dimensões são (n + 1) para itens e (W + 1) para capacidade para incluir o caso base de 0 itens/0 capacidade.
        double[][] dp = new double[n + 1][W + 1];
//Percorremos os itens (de 1 a 'n')
        for (int i = 1; i <= n; i++) {
// Percorremos as capacidades da mochila (de 0 a 'W')
            for (int w = 0; w <= W; w++){
// Caso Base: Se não há itens (i=0) ou capacidade (w=0), o valor é 0.
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                }else{
// Pega o item atual que estou  considerando (o item 'i' na lista de objetos está no índice i-1)
                     MochilaDinamica currentItem = itens.get(i - 1); 
//Pegando um item da lista itens na posição i-1 Armazenando esse item em uma variável chamada currentItem do tipo MochilaDinamica
                     if (currentItem.getPeso() <= w) { // verifica se  o peso do item atual é menor ou igual à capacidade atual da mochila
// Opção 1: Incluir o item atual na mochila. O valor será o valor do item atual MAIS o valor máximo que podíamos obter com os itens anteriores (i-1) e a capacidade restante (w - peso do item atual).
                        double valorSeIncluir = currentItem.getValor() + dp[i - 1][(int)(w - currentItem.getPeso())];
// Opção 2: Não incluir o item atual na mochila. O valor será o mesmo que o valor máximo que podíamos obter com os itens anteriores (i-1) e a mesma capacidade 'w'.
                        double valorSeNaoIncluir = dp[i - 1][w];
// Escolhemos o máximo entre incluir e não incluir o item
                        dp[i][w] = Math.max(valorSeIncluir, valorSeNaoIncluir);
                    } else {
// Se o item atual não cabe na capacidade 'w', não podemos incluí-lo. Então, o valor é o mesmo que o valor máximo obtido com os itens anteriores (i-1) e a mesma capacidade 'w'.
                        dp[i][w] = dp[i - 1][w];
                    }  
                }
            }
        }
//Rastreamento dos Itens Selecionados
//Depois de preencher a matriz DP, dp[n][W] contém o valor máximo total.
// Aqui vamos ver quais itens foram incluídos para atingir esse valor.
         List<String> itensSelecionados = new ArrayList<>();
         double res = dp[n][W]; // Começa com o valor máximo final
         int currentW = W; // Começa com a capacidade total
         // Percorre a matriz DP de trás para frente, de baixo para cima
         for (int i = n; i > 0 && res > 0; i--) { // Se o valor atual (dp[i][currentW]) é diferente do valor que teríamos sem o item 'i' (dp[i-1][currentW]),
// isso significa que o item 'i' foi incluído na solução ótima.
//(Nota: É importante ter cuidado com comparações de ponto flutuante, mas para este problema
             if (res != dp[i - 1][currentW]) {
                MochilaDinamica includedItem = itens.get(i - 1);
                 itensSelecionados.add(includedItem.getNome());// Adiciona o nome do item à lista
                res -= includedItem.getValor();// Subtrai o valor do item para continuar o rastreamento
                 currentW -= includedItem.getPeso();// Diminui a capacidade para refletir o item removido
                 // Mostrar a escolha feita (para o requisito do Passo 3 da questão)
   System.out.printf("ESCOLHA FEITA: Item '%s' (Peso: %.2f kg, Valor: R$%.2f) foi incluído.\n",
 includedItem.getNome(), includedItem.getPeso(), includedItem.getValor());
  System.out.printf("  Peso restante na mochila: %.2f kg. Valor restante a rastrear: R$%.2f.\n", (double)currentW, res);}
   else {
 // Mostrar que o item não foi escolhido (para o requisito do Passo 3 da questão)
                MochilaDinamica discardedItem = itens.get(i - 1);
System.out.printf("DESCARTE: Item '%s' (Peso: %.2f kg, Valor: R$%.2f) NÃO foi incluído.\n",
 discardedItem.getNome(), discardedItem.getPeso(), discardedItem.getValor());
             }
        }
        
        // A lista de itens foi preenchida de trás para frente, então ao invertemos para a ordem correta
         Collections.reverse(itensSelecionados); 

//         Impressão dos Resultados 
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
                  // Para calcular o peso total, teríamos que somar os pesos dos itens selecionados,
         // pois a matriz DP apenas nos dá o valor máximo.
         // Alternativamente, poderíamos modificar o rastreamento para acumular o peso também.
         // Por simplicidade aqui, vamos apenas indicar que o peso total não é diretamente da DP.
        System.out.println("O peso total ocupado é o peso dos itens selecionados (precisaria ser calculado separadamente ou durante o rastreamento).");
     }

     public static void main(String[] args) {
       // Criação dos objetos (itens) que podem ser colocados na mochila
         MochilaDinamica obj1 = new MochilaDinamica("Arroz", 1.0, 10.0);
         MochilaDinamica obj2 = new MochilaDinamica("Feijão", 2.0, 15.0);
         MochilaDinamica obj3 = new MochilaDinamica("Carne", 3.0, 20.0);
         MochilaDinamica obj4 = new MochilaDinamica("Pão", 1.0, 5.0);
         MochilaDinamica obj5 = new MochilaDinamica("Leite", 2.0, 10.0);
         MochilaDinamica obj6 = new MochilaDinamica("Queijo", 3.0, 15.0);
         MochilaDinamica obj7 = new MochilaDinamica("Chocolate", 0.2, 8.0);
        MochilaDinamica obj8 = new MochilaDinamica("Refrigerante", 2.0, 7.0);

        // Cria a lista de todos os objetos disponíveis
         List<MochilaDinamica> objetos = new ArrayList<>();
        objetos.add(obj1);
         objetos.add(obj2);
         objetos.add(obj3);
         objetos.add(obj4);
         objetos.add(obj5);
        objetos.add(obj6);
         objetos.add(obj7);
         objetos.add(obj8);

//         // Define a capacidade máxima da mochila
        double capacidadeMochila = 10.0; // Exemplo: 10 kg

//         // Instancia a classe que resolve o problema usando Programação Dinâmica
         ProblemaMochilaDinamica problema = new ProblemaMochilaDinamica();
        
        // Chama o método para resolver e exibir a solução
         problema.resolverMochilaDinamica(capacidadeMochila, objetos);
     }
    } // end of ProblemaMochilaDinamica class
}

