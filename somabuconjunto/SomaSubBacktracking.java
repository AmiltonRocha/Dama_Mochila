package somabuconjunto;

class SomaSubBacktracking {
    // PASSO 1: Definir as variáveis necessárias
    private int[] numeros;    // Array com os números
    private boolean[] usado;  // Array para marcar quais números foram usados
    private int n;           // Tamanho do array

    // PASSO 2: Criar o construtor
    public SomaSubBacktracking(int[] numeros) {
        this.numeros = numeros;
        this.n = numeros.length;
        this.usado = new boolean[n];  // Inicializa array de controle
    }

    // PASSO 3: Criar o método principal que inicia o backtracking
    public void encontrarSubconjuntos() {
        System.out.println("Array de entrada: " + arrayToString(numeros));
        System.out.println("Número de elementos: " + n);
        System.out.println("\nIniciando busca com backtracking...\n");
        
        backtracking(0, 0);  // Começa o backtracking na posição 0 com soma 0
    }

    // PASSO 4: Implementar o algoritmo de backtracking
    private void backtracking(int pos, int soma) {
        // Caso base 1: Encontrou um subconjunto que soma zero
        if (soma == 0 && pos > 0) {
            System.out.println("*** Subconjunto que soma zero encontrado! ***");
            System.out.print("Subconjunto: {");
            for (int i = 0; i < n; i++) {
                if (usado[i]) {
                    System.out.print(numeros[i] + " ");
                }
            }
            System.out.println("}");
            System.out.println("------------------------");
        }

        // Caso base 2: Já processou todos os números
        if (pos >= n) { // pos é a posição atual do array / n é o tamanho do array logo / (pos >= n)verifica se já passamos por todos os números
            return;
        }

        // Tenta incluir o número atual
        usado[pos] = true;
        System.out.println("Testando incluir " + numeros[pos] + " (soma atual: " + (soma + numeros[pos]) + ")");
        backtracking(pos + 1, soma + numeros[pos]);

        // Tenta não incluir o número atual (backtracking)
        usado[pos] = false;
        System.out.println("Testando não incluir " + numeros[pos] + " (soma atual: " + soma + ")");
        backtracking(pos + 1, soma);
    }

    // PASSO 5: Método auxiliar para mostrar o array
    private String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    // PASSO 6: Método main para testar
    public static void main(String[] args) {
        int[] numeros = {-7, -3, -2, 5, 8};
        SomaSubBacktracking somaSub = new SomaSubBacktracking(numeros);
        System.out.println("Procurando subconjuntos que somam zero usando backtracking...");
        somaSub.encontrarSubconjuntos();
    }
} 