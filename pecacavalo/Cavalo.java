package pecacavalo;

class Cavalo {
    private static final int TAMANHO = 8;  // tamanho do tabuleiro

    private int[][] tabuleiro; //as matrizes do tabuleiro

    private static final int[] MOVIMENTO_X = {2, 1, -1, -2, -2, -1, 1, 2}; //movimento possovel do cavalo
    private static final int[] MOVIMENTO_Y = {1, 2, 2, 1, -1, -2, -2, -1};// mesma coisa

    public Cavalo() {
        tabuleiro = new int[TAMANHO][TAMANHO]; //estou criando uma matriz 8x8
        for(int i = 0; i < TAMANHO; i++) {
            for(int j = 0; j < TAMANHO; j++) {
                tabuleiro[i][j] = -1; //estou inicializando a matriz com -1 ou seja posições não visitadas
            }
        }
    }

    private boolean movimentoValido(int x, int y) {
        return (x >= 0 && x < TAMANHO && y >= 0 && y < TAMANHO && tabuleiro[x][y] == -1);
    }

    private boolean resolverPasseio(int x, int y, int movimento) {
        if(movimento == TAMANHO * TAMANHO) {
            return true;
        }
        for(int i = 0; i < 8; i++) {
            int proximoX = x + MOVIMENTO_X[i];
            int proximoY = y + MOVIMENTO_Y[i];
            if(movimentoValido(proximoX, proximoY)){
                tabuleiro[proximoX][proximoY] = movimento;
               
                if (resolverPasseio(proximoX, proximoY, movimento + 1)) {
                    return true;
                }
                tabuleiro[proximoX][proximoY] = -1;
                
            }
        }
        return false;
        // aqui estou verificando se o movimento é valido e se é possivel resolver o passeio
        // na linha 31 a 36 ocorre a veruficação de movimento valido caso o movimento seja valido ele continua
        // caso não seja valido faz o backtracking
    }

    public void mostrarTabuleiro() {//mostrar o tabuleiro
        for(int i = 0; i < TAMANHO; i++) {
            for(int j = 0; j < TAMANHO; j++) {
                System.out.print(tabuleiro[i][j] + " "); // mostra cada posição do tabuleiro
            }
            System.out.println();
        } 
    }

    public static void main(String[] args) {
        Cavalo cavalo = new Cavalo();
        cavalo.tabuleiro[0][0] = 0;
        if(cavalo.resolverPasseio(0, 0, 1)) {
            System.out.println("Solução encontrada!");
            cavalo.mostrarTabuleiro(); // Mostra o tabuleiro com o caminho
        } else {
            System.out.println("Não há solução!");
        }
    }
}
