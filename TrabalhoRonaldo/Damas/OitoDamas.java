package TrabalhoRonaldo.Damas;
public class OitoDamas {

    // Define o tamanho do tabuleiro. Para 8 damas, N é 8.
    private static final int N = 8;

    // O tabuleiro será representado por um array.
    // 'tabuleiro[coluna]' armazena a 'linha' onde a dama está naquela coluna.
    // Por exemplo, tabuleiro[0] = 3 significa que na coluna 0, a dama está na linha 3.
    private int[] tabuleiro;

    // Construtor para inicializar o tabuleiro
    public OitoDamas() {
        tabuleiro = new int[N];
        // Inicializa todas as posições como -1 para indicar que estão vazias
        for (int i = 0; i < N; i++) {
            tabuleiro[i] = -1;
        }
    }

    /**
     * Imprime uma representação visual do tabuleiro.
     * Mostra onde as damas estão (Q) e onde estão vazias.
     */
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

    /**
     * Verifica se é seguro colocar uma dama na posição (linha, coluna)
     * sem que ela seja atacada por damas já colocadas nas colunas anteriores.
     */
    private boolean ehSeguro(int linha, int coluna) {
        // Precisamos verificar apenas as colunas anteriores, pois as futuras ainda estão vazias.
        for (int c = 0; c < coluna; c++) {
            // 1. Verifica se há outra dama na mesma LINHA
            if (tabuleiro[c] == linha) {
                return false;
            }

            // 2. Verifica se há outra dama na mesma DIAGONAL PRINCIPAL (linha - coluna é constante)
            // Ex: (0,0) -> 0-0=0; (1,1) -> 1-1=0. Se estiverem na mesma diagonal, a diferença será a mesma.
            //No plano cartesiano e uma linha decrescente
            if (tabuleiro[c] - c == linha - coluna) {
                return false;
            }

            // 3. Verifica se há outra dama na mesma DIAGONAL SECUNDÁRIA (linha + coluna é constante)
            // Ex: (0,7) -> 0+7=7; (1,6) -> 1+6=7. Se estiverem na mesma diagonal, a soma será a mesma.
            //No plano cartesiano e uma linha crescente
            if (tabuleiro[c] + c == linha + coluna) {
                return false;
            }
        }
        return true; // Se passou por todas as verificações, é seguro!
    }

    /**
     * A função principal recursiva que resolve o problema usando backtracking.
     * 'colunaAtual' é a coluna que estamos tentando preencher agora.
     */
    public boolean resolverProblema(int colunaAtual) {
        // PASSO 5: Achou a Solução? (Caso base da recursão)
        // Se a coluna atual é igual a N (ou seja, conseguimos colocar damas em todas as 8 colunas)
        if (colunaAtual >= N) {
            System.out.println("Solução encontrada!");
            imprimirTabuleiro();
            return true; // Uma solução foi encontrada!
        }

        // PASSO 2: Teste Posições na Coluna Atual
        // Para cada linha possível na coluna atual...
        for (int linha = 0; linha < N; linha++) {
            if (ehSeguro(linha, colunaAtual)) {
                tabuleiro[colunaAtual] = linha; // Coloca a dama (marca a posição no tabuleiro)
                System.out.println("Tentando colocar dama na Coluna " + colunaAtual + ", Linha " + linha);
                if (resolverProblema(colunaAtual + 1)) {
                    return true; // Propaga o sucesso para cima (achamos uma solução!)
                }
                // SE CHEGOU AQUI, SIGNIFICA QUE A CHAMADA RECURSIVA ACIMA RETORNOU FALSE.
                // ISSO É UM BACKTRACK!
                System.out.println("BACKTRACK: Removendo dama da Coluna " + colunaAtual + ", Linha " + linha);
                tabuleiro[colunaAtual] = -1; // Remove a dama (volta ao estado "vazio")
                imprimirTabuleiro(); // Opcional: para visualizar o estado após o backtrack
            }
        }

        // Se o loop terminou e nenhuma linha na colunaAtual levou a uma solução,
        // significa que esta 'colunaAtual' não tem uma posição válida.
        // Então, esta chamada de função retorna 'false', acionando o backtrack na chamada anterior.
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