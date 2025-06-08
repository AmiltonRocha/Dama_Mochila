package casadepraia;

// Classe para representar um arquivo PDF
class PDF {
    String nome;
    int paginas;
    int tamanhoMB;

    public PDF(String nome, int paginas, int tamanhoMB) {
        this.nome = nome;
        this.paginas = paginas;
        this.tamanhoMB = tamanhoMB;
    }
}
//Aqui ocorre o GULOSEIMO
class CasaPraiaAlogaritimo {
    // Método principal que vamos implementar
    public void escolherPDFs(int capacidadePendrive, PDF[] pdfs) {
        // PASSO 1: Ordenar os PDFs pela razão páginas/tamanho
        for (int i = 0; i < pdfs.length - 1; i++) {
            for (int j = 0; j < pdfs.length - i - 1; j++) {
                // Calcula a razão páginas/tamanho para cada PDF
                double razao1 = (double) pdfs[j].paginas / pdfs[j].tamanhoMB;
                double razao2 = (double) pdfs[j + 1].paginas / pdfs[j + 1].tamanhoMB;
                
                // Se a razão do próximo é maior, troca os PDFs de lugar
                if (razao2 > razao1) {
                    PDF temp = pdfs[j];
                    pdfs[j] = pdfs[j + 1];
                    pdfs[j + 1] = temp;
                }
            }
        }
        
        // PASSO 2: Escolher os PDFs que cabem no pendrive
        int espacoRestante = capacidadePendrive;
        int totalPaginas = 0;
        
        System.out.println("PDFs escolhidos para o pendrive de " + capacidadePendrive + "MB:");
        System.out.println("----------------------------------------");
        
        for (PDF pdf : pdfs) {
            if (pdf.tamanhoMB <= espacoRestante) {
                System.out.println("Nome: " + pdf.nome);
                System.out.println("Páginas: " + pdf.paginas);
                System.out.println("Tamanho: " + pdf.tamanhoMB + "MB");
                System.out.println("----------------------------------------");
                
                espacoRestante -= pdf.tamanhoMB;
                totalPaginas += pdf.paginas;
            }
        }
        
        System.out.println("Total de páginas: " + totalPaginas);
        System.out.println("Espaço restante no pendrive: " + espacoRestante + "MB");
    }

    public static void main(String[] args) {
        PDF[] pdfs = {
            new PDF("Algoritmos1", 100, 50),//2
            new PDF("Algoritmos2", 200, 80),//2,5
            new PDF("Algoritmos3", 150, 60)//2,5
        };
        
        CasaPraiaAlogaritimo algoritmo = new CasaPraiaAlogaritimo();
        algoritmo.escolherPDFs(99, pdfs); // Pendrive de 99MB
    }
}
