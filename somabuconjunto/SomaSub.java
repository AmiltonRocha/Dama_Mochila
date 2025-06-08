package somabuconjunto;
//força bruta
class SomaSub  {
    private int[] numeros;

    public SomaSub(int[] numeros){
        this.numeros = numeros;
    }
    
    public void encontrarSubconjuntos(){
        int n = numeros.length;
        System.out.println("Array de entrada: " + arrayToString(numeros));
        System.out.println("Número de elementos: " + n);
        System.out.println("Número total de subconjuntos possíveis: " + (1 << n));
        System.out.println("\nIniciando busca de subconjuntos...\n");

        // aqui que esta ocorrendo a força bruta
        for(int i = 0; i < (1 << n); i++){ //aqui estou percorrendo todos os subconjuntos possiveis
            //1 << n significa 2^n Por exemplo, se n = 3, teremos 8 combinações (2³)
            int soma = 0;
            System.out.println("Testando combinação " + i + ":");
            System.out.print("Subconjunto: ");
            for(int j = 0; j < n; j++){
                if((i & (1 << j)) != 0){ //verifica o bit j-ésimo do número i
                    soma += numeros[j];//adiciona o numero ao subconjunto
                    System.out.print(numeros[j] + " ");
                }
            }
            System.out.println("}"); //aqui estou imprimindo o subconjunto
            System.out.println("Soma: " + soma);
            //aqui estou verificando se a soma é zero
            if (soma == 0) {
                System.out.println("*** Subconjunto que soma zero encontrado! ***");
            }
            System.out.println("------------------------");
        }
    }
//metodo auxiliar para converter array em string
    private String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);//
            if (i < arr.length - 1) {
                sb.append(", ");//aqui estou adicionando a virgula e o espaço
            }
        }
        sb.append("}");//aqui estou adicionando o fechamento do array
        return sb.toString();//aqui estou retornando a string
    }

    public static void main(String[] args) {
        int[] numeros = {-7, -3, -2, 5, 8};
        SomaSub somaSub = new SomaSub(numeros);
        System.out.println("Procurando subconjuntos que somam zero...");
        somaSub.encontrarSubconjuntos();
    }
}


