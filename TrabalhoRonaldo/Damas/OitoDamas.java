package TrabalhoRonaldo.Damas;
public class OitoDamas {

    private static final int N = 8;

    private int[] tabuleiro;

    public OitoDamas() {
        tabuleiro = new int[N];
        for (int i = 0; i < N; i++) {
            tabuleiro[i] = -1;
        }
    }

    public void imprimirTabuleiro() {
        System.out.println("-----------------");
        for (int linha = 0; linha < N; linha++) {
            System.out.print("|");
            for (int coluna = 0; coluna < N; coluna++) {
                System.out.print(tabuleiro[coluna] == linha ? "Q|" : " |");
            }
            System.out.println("\n-----------------");
        }
        System.out.println();
    }

/**Verifica se é seguro colocar uma dama na posição (linha, coluna) sem que ela seja atacada por damas já colocadas nas colunas anteriores.*/

    private boolean ehSeguro(int linha, int coluna) { //Tempo: O(N)

// Precisamos verificar apenas as colunas anteriores, pois as futuras ainda estão vazias.

        for (int c = 0; c < coluna; c++) { // Tempo: O(N)(Coluna vai até N-1) 3

// 1. Verifica se há outra dama na mesma LINHA
            if (tabuleiro[c] == linha) {
                return false;
            }
// 2. Verifica se há outra dama na mesma DIAGONAL PRINCIPAL (linha - coluna é constante) Ex: (0,0) -> 0-0=0; (1,1) -> 1-1=0. Se estiverem na mesma diagonal, a diferença será a mesma. 
//No plano cartesiano e uma linha decrescente
            if (tabuleiro[c] - c == linha - coluna) {
                return false;
            }
// 3. Verifica se há outra dama na mesma DIAGONAL SECUNDÁRIA (linha + coluna é constante) Ex: (0,7) -> 0+7=7; (1,6) -> 1+6=7. Se estiverem na mesma diagonal, a soma será a mesma. 
//No plano cartesiano e uma linha crescente
            if (tabuleiro[c] + c == linha + coluna) {
                return false;
            }
        }
        return true; 
    }

    /**A função principal recursiva que resolve o problema usando backtracking.'colunaAtual' é a coluna que estamos tentando preencher agora.*/
    public boolean resolverProblema(int colunaAtual) { // Custo Total de Tempo: O(N!) 
        if (colunaAtual >= N) { //Tempo: O(1)
            System.out.println("Solução encontrada!");
            imprimirTabuleiro(); //Tempo: O(N^2)
            return true; // Uma solução foi encontrada!
        }
// PASSO 2: Teste Posições na Coluna Atual Para cada linha possível na coluna atual...
        for (int linha = 0; linha < N; linha++) { // Tempo: O(N)
            if (ehSeguro(linha, colunaAtual)) { //Tempo: O(N)
                tabuleiro[colunaAtual] = linha; //Tempo: O(1) // Coloca a dama (marca a posição no tabuleiro)
                System.out.println("Tentando colocar dama na Coluna " + (colunaAtual + 1) + ", Linha " + (linha + 1));
                if (resolverProblema(colunaAtual + 1)) { //Tempo: T(N-1)
                    return true; // Propaga o sucesso para cima (achamos uma solução!)
                }
// SE CHEGOU AQUI, SIGNIFICA QUE A CHAMADA RECURSIVA ACIMA RETORNOU FALSE. ISSO É UM BACKTRACK!
                System.out.println("BACKTRACK: Removendo dama da Coluna " + (colunaAtual + 1) + ", Linha " + (linha + 1));
                tabuleiro[colunaAtual] = -1;//Tempo: O(1) // Remove a dama (volta ao estado "vazio")
                imprimirTabuleiro(); 
                // Opcional: para visualizar o estado após o backtrack
            }
        }
        return false;
    }

    public static void main(String[] args) {
        OitoDamas jogo = new OitoDamas();
        System.out.println("Resolvendo o Problema das 8 Damas...");
        if (!jogo.resolverProblema(0)) {
            System.out.println("Nenhuma solução encontrada.");
        }
    }
}

//Obs a solução BACKTRACK! esta ocorrendo na linha 81 a 92 e na linha 100 a 111. ou seja quando a adiciona uma dama e não encontra uma solução ela volta para o estado anterior e tenta outra posição.
//OBS o backtracking começa a ser executado quando a função recursiva retorna false, ou seja, quando não encontrou uma solução para a coluna atual.