package CaxeiroViajante;

class Cidade {
    String nome;
    double x;
    double y;

    public Cidade(String nome, double x, double y) {
        this.nome = nome;
        this.x = x;
        this.y = y;
    }
}

class CaixeiroViajante {
    private Cidade[] cidades;
    private int n;
    private double menorDistancia;
    private int[] melhorRota;

    public CaixeiroViajante(Cidade[] cidades) {
        this.cidades = cidades;
        this.n = cidades.length;
        this.menorDistancia = Double.MAX_VALUE;
        this.melhorRota = new int[n];
    }

    // Método para calcular a distância entre duas cidades usando a fórmula Euclidiana
    private double calcularDistancia(Cidade cidade1, Cidade cidade2) {
        double dx = cidade1.x - cidade2.x;
        double dy = cidade1.y - cidade2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Método para calcular a distância total de uma rota
    private double calcularDistanciaTotal(int[] rota) {
        double distancia = 0;
        for (int i = 0; i < n - 1; i++) {
            distancia += calcularDistancia(cidades[rota[i]], cidades[rota[i + 1]]);
        }
        // Adiciona a distância de volta para a cidade inicial
        distancia += calcularDistancia(cidades[rota[n - 1]], cidades[rota[0]]);
        return distancia;
    }

    // Método para encontrar a melhor rota usando força bruta
    // vai gerar todas as permutações ate achar e menor rota possivel
    public void encontrarMelhorRota() {
        int[] rota = new int[n];
        for (int i = 0; i < n; i++) {
            rota[i] = i;
        }
        
        System.out.println("Iniciando busca da melhor rota...");
        System.out.println("Número de cidades: " + n);
        System.out.println("----------------------------------------");
        
        // Gera todas as permutações possíveis
        gerarPermutacoes(rota, 1);
        
        // Mostra a melhor rota encontrada
        System.out.println("\nMelhor rota encontrada:");
        System.out.println("----------------------------------------");
        
        double distanciaTotal = 0;
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + ". " + cidades[melhorRota[i]].nome);
            
            // Calcula e mostra a distância para a próxima cidade
            if (i < n - 1) {
                double distancia = calcularDistancia(cidades[melhorRota[i]], cidades[melhorRota[i + 1]]);
                System.out.println("   Distância até " + cidades[melhorRota[i + 1]].nome + ": " + String.format("%.2f", distancia));
                distanciaTotal += distancia;
            }
        }
        
        // Mostra a distância de volta para a cidade inicial
        double distanciaRetorno = calcularDistancia(cidades[melhorRota[n - 1]], cidades[melhorRota[0]]);
        System.out.println("1. " + cidades[melhorRota[0]].nome + " (retorno)");
        System.out.println("   Distância de retorno: " + String.format("%.2f", distanciaRetorno));
        distanciaTotal += distanciaRetorno;
        
        System.out.println("----------------------------------------");
        System.out.println("Distância total da rota: " + String.format("%.2f", distanciaTotal));
        
        // Mostra as coordenadas de cada cidade
        System.out.println("\nCoordenadas das cidades:");
        System.out.println("----------------------------------------");
        for (Cidade cidade : cidades) {
            System.out.println(cidade.nome + ": (" + cidade.x + ", " + cidade.y + ")");
        }
    }

    // Método para gerar todas as permutações possíveis
    private void gerarPermutacoes(int[] rota, int inicio) {
        if (inicio == n) {
            double distancia = calcularDistanciaTotal(rota);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                System.arraycopy(rota, 0, melhorRota, 0, n);
            }
            return;
        }

        for (int i = inicio; i < n; i++) {
            // Troca as posições
            int temp = rota[inicio];
            rota[inicio] = rota[i];
            rota[i] = temp;

            // Gera permutações para o resto do array
            gerarPermutacoes(rota, inicio + 1);

            // Desfaz a troca (backtracking)
            temp = rota[inicio];
            rota[inicio] = rota[i];
            rota[i] = temp;
        }
    }

    public static void main(String[] args) {
        // Criando 10 cidades com coordenadas x,y
        Cidade[] cidades = {
            new Cidade("Cidade 1", 0, 0),
            new Cidade("Cidade 2", 10, 10),
            new Cidade("Cidade 3", 20, 20),
            new Cidade("Cidade 4", 30, 30),
            new Cidade("Cidade 5", 40, 40),
            new Cidade("Cidade 6", 50, 50),
            new Cidade("Cidade 7", 60, 60),
            new Cidade("Cidade 8", 70, 70),
            new Cidade("Cidade 9", 80, 80),
            new Cidade("Cidade 10", 90, 90)
        };

        CaixeiroViajante cv = new CaixeiroViajante(cidades);
        cv.encontrarMelhorRota();
    }
} 