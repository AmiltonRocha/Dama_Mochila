package TrabalhoRonaldo.Damas;
import java.util.Scanner;

public class Nqueens {
    // Vamos usar o mesmo codigo da classe OitoDamas.java para resolver o problema da N-Damas,sua diferenca e que o N é um numero qualquer

    private int N; // agora o N é um numero qualquer
    private int[] tabuleiro; //isso vai representar o tabuleiro

    /**
     * Construtor para inicializar o tabuleiro com N damas.
     * @param n O número de damas e o tamanho do tabuleiro (N x N). (é uma tag de documentação Java (JavaDoc) usada para documentar parâmetros de métodos.)
     */
    public Nqueens(int n) { // O construtor recebe N 
        this.N = n; //issa linha define o tamanho do tabuleiro
        tabuleiro = new int[N]; //inicializa o tabuleiro com N posições
        
        // Vamos inicializar o tabuleiro com -1 para indicar que as posições estão vazias
        for (int i = 0; i < N; i++) { //percorre todas as posições do tabuleiro
            tabuleiro[i] = -1; //coloca -1 para indicar que a posição esta vazia
        }
    }

    //Nessa parte irei imprimir a vizualização do tabuleiro e mostra a onde esta a dama
    public void imprimirTabuleiro() {
        //Ajustar o tamanho do seprador para que seja N
        System.out.println("-----------------"); //esse e o separador
        for (int i = 0; i < N; i++) { //percorre todas as linhas do tabuleiro
            System.out.print("|"); //esse e a coluna
        }
        System.out.println(); //cria nova linha apos o separador superior
        for (int linha = 0; linha < N; linha++) { //percorre todas as linhas do tabuleiro
            System.out.print("|"); //esse e a coluna
            for (int coluna = 0; coluna < N; coluna++) { //percorre todas as colunas do tabuleiro
                System.out.print(tabuleiro[coluna] == linha ? "Q|" : " |"); //verifica se a posição e uma dama
            }
            System.out.println(); // Nova linha após cada linha do tabuleiro
        }
        // Ajusta a impressão do separador para o tamanho de N
        System.out.print("-");
        for (int i = 0; i < N; i++) {
            System.out.print("---"); // 3 hifens por coluna
        }
        System.out.println(); // Nova linha após o separador inferior
    }

    /**
     * Verifica se é seguro colocar uma dama na posição (linha, coluna)
     * sem que ela seja atacada por damas já colocadas nas colunas anteriores.
     */
    private boolean ehSeguro(int linha, int coluna) {
        //verificar apenas as colunas anteriores, pois as futuras ainda estão vazias.
        for (int c = 0; c < coluna; c++) {
            // 1. Verifica se há outra dama na mesma LINHA
            if (tabuleiro[c] == linha) {
                return false;
            }
            // 2. Verifica se há outra dama na mesma DIAGONAL PRINCIPAL (linha - coluna é constante)
            // Ex: (0,0) -> 0-0=0; (1,1) -> 1-1=0. Se estiverem na mesma diagonal, a diferença será a mesma.
            if (tabuleiro[c] - c == linha - coluna) {
                return false;
            }
            // 3. Verifica se há outra dama na mesma DIAGONAL SECUNDÁRIA (linha + coluna é constante)
            // Ex: (0,7) -> 0+7=7; (1,6) -> 1+6=7. Se estiverem na mesma diagonal, a soma será a mesma.
            if (tabuleiro[c] + c == linha + coluna) {
                return false;
            }
        }
        return true; // Se passou por todas as verificações, é seguro!
    }

/** A função principal recursiva que resolve o problema usando backtracking. 'colunaAtual' é a coluna que estamos tentando preencher agora.*/

    public boolean resolverProblema(int colunaAtual) {
// Caso base da recursão: Se a coluna atual é igual a N, todas as damas foram colocadas com sucesso.
        if (colunaAtual >= N) {
            System.out.println("Solução encontrada para N = " + N + "!");
            imprimirTabuleiro();
            return true; // Uma solução foi encontrada!
        }

// Tenta colocar a dama em cada linha da coluna atual
        for (int linha = 0; linha < N; linha++) {
            if (ehSeguro(linha, colunaAtual)) {
                tabuleiro[colunaAtual] = linha; // Coloca a dama

// COMENTÁRIOS PARA A QUESTÃO 3 (Adaptado para N Damas): Esta linha indica que estamos tentando colocar uma dama em uma posição específica.
                System.out.println("Tentando colocar dama na Coluna " + colunaAtual + ", Linha " + linha);
// Chama recursivamente para a próxima coluna
                if (resolverProblema(colunaAtual + 1)) {
                    return true; // Se a próxima chamada recursiva encontrou uma solução, propaga o sucesso.
                }
// SE CHEGOU AQUI, SIGNIFICA QUE A CHAMADA RECURSIVA ACIMA RETORNOU FALSE. ISSO É UM BACKTRACK!
                System.out.println("BACKTRACK: Removendo dama da Coluna " + colunaAtual + ", Linha " + linha);
                tabuleiro[colunaAtual] = -1; // Remove a dama (desfaz a última tentativa)
 // Opcional: Imprime o tabuleiro após o backtrack para visualizar a remoção
                imprimirTabuleiro();
            }
        }
// Se nenhuma linha na coluna atual levou a uma solução, retorna false, indicando que a chamada anterior deve tentar outra posição (o que também é um backtrack).
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int nQueens;

        while (true) {
            System.out.print("Digite o número de damas (N) para o tabuleiro N x N (N >= 4): ");
            if (scanner.hasNextInt()) {
                nQueens = scanner.nextInt();
                if (nQueens >= 4) { // O problema das N damas geralmente tem soluções para N >= 4
                    break;
                } else {
                    System.out.println("N deve ser um número inteiro igual ou maior que 4. Tente novamente.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
                scanner.next(); // Descarta a entrada inválida
            }
        }

        Nqueens jogo = new Nqueens(nQueens); // Cria uma instância do jogo com o N fornecido
        System.out.println("Resolvendo o Problema das " + nQueens + " Damas...");

        if (!jogo.resolverProblema(0)) {
            System.out.println("Nenhuma solução encontrada para N = " + nQueens + ".");
        }

        scanner.close(); // Fecha o scanner
    }
}
 //OBS porque coloquei o N >= 4 isso ocorre porque se for N =2 ou 3 não tem solução e o programa vai rodar infinitamente. ja N = 1 tem uma solução e N = 0 não tem solução. as soluções começam a aparecer quando N >= 4
 // O backtracking. começa a ser executado quando a função recursiva retorna false, ou seja, quando não encontrou uma solução para a coluna atual. este codigo esta na linha 75.
