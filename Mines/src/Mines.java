import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Mines {

    public static void main(String[] args){
        // Saber o tamanho do campo, o user escolhe
        Scanner sc = new Scanner(System.in);
        System.out.print("Tamanho do Campo: ");
        int tamanhoDoCampo = sc.nextInt();

        // Armazena onde estão as bombas
        String[] mapa = generateBombsMap(tamanhoDoCampo).toArray(new String[0]);

        // Mostra o campo
        ArrayList<Integer> userTryIndex = new ArrayList<>(); // Lista de tentativas do usuário

        boolean temBomba = false; // Se o espaço que o user digita tem bomba
        boolean ganhou = false;
        do{
            mostrarOCampo(tamanhoDoCampo, userTryIndex);

            System.out.print("Coluna: ");
            int coluna = sc.nextInt();
            System.out.print("Linha: ");
            int linha = sc.nextInt();

            if (coluna < 1 || coluna > tamanhoDoCampo || linha < 1 || linha > tamanhoDoCampo) {
                System.out.println("Coordenadas inválidas. Tente novamente.");
                continue;
            }

            temBomba = isUserChoiceABomb(coluna, linha, tamanhoDoCampo, mapa);
            userTryIndex.add((coluna+(linha-1)*(tamanhoDoCampo))-1);

            System.out.println();

            // Condição de vitória (Todos os lugares livres de bomba foram explorados)
            ganhou = condicaoDeVitoria(mapa, userTryIndex);
            if(ganhou){
                break;
            }
        }while(!temBomba);

        if(ganhou){
            mostrarOCampo(tamanhoDoCampo, userTryIndex);
            System.out.println("Você ganhou!");
        }
        else{
        System.out.println("Você pisou numa bomba!");
        }

        sc.close();
    }

    static ArrayList<String> generateBombsMap(int tamanhoDoCampo){
        Random rd = new Random();
        ArrayList<String> mapaDasBombas = new ArrayList<>(); // Armazena se a casa (index) é bomba ou não
        for (int casasDoTabuleiro=0; casasDoTabuleiro<tamanhoDoCampo*tamanhoDoCampo; casasDoTabuleiro++){ // tamanhoDoCampo*tamanhoDoCampo pois é um quadrado
            double isBomb = rd.nextFloat(); // Caso seja menor que 0,20, será bomba
            mapaDasBombas.add(isBomb<=0.20? "B": "F"); // B == Bomb, F == Free
        }

        return mapaDasBombas;
    }

    static Boolean isUserChoiceABomb(int coluna, int linha, int tamanhoDoCampo, String[] mapa){
        int indexDaEscolha = (coluna+(linha-1)*(tamanhoDoCampo))-1;

        return mapa[indexDaEscolha].equals("B");
    }

    static void mostrarOCampo(int tamanhoDoCampo, ArrayList<Integer> userTryIndex){
        ArrayList<String> campoParaUser = new ArrayList<>();
        int indexCounter = 0;
        for (int x=0; x<tamanhoDoCampo;x++){
            for (int y=0; y<tamanhoDoCampo;y++){
                if (!userTryIndex.contains(indexCounter)){
                    campoParaUser.add("* ");
                }
                else{
                    campoParaUser.add("F ");
                }
                indexCounter++;
            }
        }
        // Coordenadas das colunas
        for (int columnLine=0; columnLine<=tamanhoDoCampo; columnLine++){
            System.out.print(columnLine+" ");
        }
        System.out.println();
        int getIndex = 0;
        for(int linhaLine=1;linhaLine<=tamanhoDoCampo;linhaLine++){ // Coordenadas das linhas
            System.out.print(linhaLine+" ");
            for (int i=0; i<tamanhoDoCampo;i++){
                System.out.print(campoParaUser.get(getIndex));
                getIndex++;
            }
            System.out.println();
        }
    }

    static boolean condicaoDeVitoria(String[] mapa, ArrayList<Integer> userTryIndex){
        int numeroDeFreeMapa = 0;
        int numeroDeFreeUserTry = userTryIndex.size();

        for (String s : mapa){
            if(s.equals("F")) {
                numeroDeFreeMapa++;
            }
        }
        return (numeroDeFreeMapa == numeroDeFreeUserTry); // Se é true ou false
    }
}
