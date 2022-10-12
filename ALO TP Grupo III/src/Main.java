import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    static Scanner in = new Scanner(System.in);
    static int id = 1;
    static List<Ouvintes> listaOuvintes;
    static List<Ouvintes> ranking;
    static Random rnd = new Random();
    static int[] jogadores;
    static double[] palpites;

    public static void main(String[] args) {
        listaOuvintes = new ArrayList<>();
        Inicializar();
        int op = 1;
        while (op != 0) {
            System.out.println("Bem vindo ao jogo do saco");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Criar, editar e eliminar ouvintes");
            System.out.println("2 - Ver ouvintes");
            System.out.println("3 - Ver ranking de ouvintes");
            System.out.println("4 - Jogar");
            System.out.println("0 - Sair");
            try {
            op = in.nextInt();

            switch (op) {
                case 0:
                    break;
                case 1:
                    EditOuvintes();
                    break;
                case 2:
                    VerOuvintes();
                    break;
                case 3:
                    VerRanking();
                    break;
                case 4:
                    Jogar();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }catch (Exception e) {
            System.out.println("Valor inválido, tente novamente");
            in.nextLine();
        }
        }
    }

    private static void EditOuvintes() {
        int op = 1;
        while (op != 0) {
            System.out.println("1 - Criar Ouvintes");
            System.out.println("2 - Editar Ouvintes");
            System.out.println("3 - Eliminar Ouvintes");
            System.out.println("0 - Voltar menu anterior");
            try {
                op = in.nextInt();

                switch (op) {
                    case 0:
                        break;
                    case 1:
                        Ouvintes.CriarOuvintes();
                        break;
                    case 2:
                        Ouvintes.EditarOuvintes();
                        break;
                    case 3:
                        Ouvintes.EliminarOuvinte();
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Valor inválido, tente novamente");
                in.nextLine();
            }
        }
    }

        public static void VerOuvintes () {
            for (int i = 0; i < listaOuvintes.size(); i++) {
                System.out.println(listaOuvintes.get(i));
            }
        }

        private static void VerRanking () {
            ranking = new ArrayList<>();
            Ouvintes temp = new Ouvintes();       //CRIA UM OUVINTE TEMPORARIO
            id--;                                 //FAZ O ACERTO DO ID

            //VAI A LISTA OUVINTES COPIA INFO NECESSARIA PARA A LISTA DO RANKING
            //ASSIM NÃO ATUALIZA A LISTA PRINCIPAL AO ORDENAR O RANKIE
            for (int i = 0; i < listaOuvintes.size(); i++) {
                ranking.add(new Ouvintes(listaOuvintes.get(i).getNome(), listaOuvintes.get(i).getTaxatAcerto(),
                        listaOuvintes.get(i).getNumJogos(),listaOuvintes.get(i).getNumAcertos()));
            }

            //ORDENAR RANKING
            for (int i = 0; i < ranking.size(); i++) {       //compara o elemento em cada posição com os seguinte
                for (int j = i + 1; j < ranking.size(); j++) {
                    if (ranking.get(i).getTaxatAcerto() < ranking.get(j).getTaxatAcerto()) {//ORDENA DECRESCENTEMENTE PELA TAXA DE ACERTO
                        temp = ranking.get(i);
                        ranking.set(i, ranking.get(j));        //FAZ A TROCA DA ORDEM
                        ranking.set(j, temp);
                    }
                }
            }

            // verica se existem taxas iguais e ordenar pelo nr de jogos
            for (int i = 0; i < ranking.size(); i++) {
                for (int j = i + 1; j < ranking.size(); j++) {
                    if (ranking.get(i).getTaxatAcerto() == ranking.get(j).getTaxatAcerto()) {
                        if (ranking.get(i).getNumJogos() > ranking.get(j).getNumJogos()) {  //SE TEM MAIS JOGOS E A MESMA % ESTÁ ATRAZ NO RANKIE
                            temp = ranking.get(i);
                            ranking.set(i, ranking.get(j));
                            ranking.set(j, temp);
                        }
                    }
                }
            }

            //VER RANKING
            System.out.println("Taxa de acertos:");
            for (int i = 0; i < ranking.size(); i++) {
                System.out.println(ranking.get(i).getNome() + ": " + ranking.get(i).getTaxatAcerto() + "% de acertos (" +ranking.get(i).getNumAcertos()+"/"+
                        ranking.get(i).getNumJogos() + " jogos)");
            }
        }

        private static void Jogar () {
            double limiteInf, limiteI, limiteSup, limiteS, pesoSaco, pesoS;
            limiteI = rnd.nextDouble(1, 10.001);
            limiteS = limiteI + 0.150;
            pesoS = rnd.nextDouble(limiteI, (limiteS + 0.001));
            //ARRENDONDAR LIMITES E PESO
            limiteInf = Math.round(limiteI * 1000) / 1000.0;
            limiteSup = Math.round(limiteS * 1000) / 1000d;
            pesoSaco = Math.round(pesoS * 1000) / 1000d;

            DefenirOuvintes();
            //MOSTRAR JOGADORES SELECIONADOS
            System.out.println("Jogadores selecionados:");
            for (int i = 0; i < jogadores.length; i++) {
                System.out.println((i + 1) + " - " + listaOuvintes.get(jogadores[i]).getNome() + ";");
            }
            System.out.println("-------------------------------");
           // System.out.println(pesoSaco);

            PedirPalpites(limiteInf, limiteSup);
            VerificarPalpites(pesoSaco);
        }

        private static void VerificarPalpites ( double pesoSaco){
            double[] distancia = new double[palpites.length];
            double[] temp=new double[palpites.length];
            int numacertosTemp;
            String nome1 = "", nome2 = "";

            //VERIFICA A PROXIMIDADE DO PALPITE COM O PESO CERTO
            for (int i = 0; i < distancia.length; i++) {
                //GUARDA A DISTANCIA NA MESMA POSICÃO QUE O ARRAY DOS JOGADORES
                distancia[i] = Math.round(Math.abs(pesoSaco - palpites[i])*1000)/1000.d;
                //DEVIDO AOS APONTADORES DE MEMORIA NÃO IGUALAR TEMP A DISTANCIA
                temp[i] = Math.round(Math.abs(pesoSaco - palpites[i])*1000)/1000.d;
            }
            //VERIFICAR QUAL O PALPITE MAIS PROXIMO DO PESO DO SACO
            Arrays.sort(temp);      //O PALPITE MAIS PROXIMO ESTÁ NA POSIÇÃO 0


            if (temp[0] != temp[1]) {      //1 VENCEDOR
                for (int i = 0; i < distancia.length; i++) {
                    if (distancia[i] == temp[0] && temp[0] != temp[1]) {   //O OUVINTE DO JOGADORES NA POSICAO i GANHOU O JOGO
                        System.out.println(listaOuvintes.get(jogadores[i]).getNome() + " ganhou o jogo.");
                        numacertosTemp = listaOuvintes.get(jogadores[i]).getNumAcertos();
                        listaOuvintes.get(jogadores[i]).setNumAcertos(numacertosTemp + 1);
                        //ATUALIZAR TAXA DE ACERTO
                        listaOuvintes.get(jogadores[i]).setTaxatAcerto((listaOuvintes.get(jogadores[i]).getNumAcertos() * 100) / listaOuvintes.get(jogadores[i]).getNumJogos());
                    }
                }
            } else {//SÓ PODE HAVER 2 PALPITES COM A MESMA DISTANCIA

                for (int i = 0; i < distancia.length; i++) {
                    if (temp[0] == distancia[i]) {
                        nome1 = listaOuvintes.get(jogadores[i]).getNome();
                        numacertosTemp = listaOuvintes.get(jogadores[i]).getNumAcertos();
                        listaOuvintes.get(jogadores[i]).setNumAcertos(numacertosTemp + 1);
                        //ATUALIZAR TAXA DE ACERTO
                        listaOuvintes.get(jogadores[i]).setTaxatAcerto((listaOuvintes.get(jogadores[i]).getNumAcertos() * 100) / listaOuvintes.get(jogadores[i]).getNumJogos());
                        i++;
                    }
                    if (temp[1] == distancia[i]) {
                        nome2 = listaOuvintes.get(jogadores[i]).getNome();
                        numacertosTemp = listaOuvintes.get(jogadores[i]).getNumAcertos();
                        listaOuvintes.get(jogadores[i]).setNumAcertos(numacertosTemp + 1);
                        //ATUALIZAR TAXA DE ACERTO
                        listaOuvintes.get(jogadores[i]).setTaxatAcerto((listaOuvintes.get(jogadores[i]).getNumAcertos() * 100) / listaOuvintes.get(jogadores[i]).getNumJogos());
                    }
                }
                System.out.println(nome1 + " e " + nome2 + " ganharam o jogo.");
            }
        }

        private static void PedirPalpites ( double limiteInf, double limiteSup){
            palpites = new double[jogadores.length];
            in = new Scanner(System.in);
            System.out.println("O intrevalo de peso do saco é:");
            System.out.println(limiteInf + "Kg e " + limiteSup + "Kg");

            //GUARDA PALPITES NA MESMA POSICAO QUE ARRAY DOS JOGADORES

            for (int i = 0; i < jogadores.length; i++) {
                System.out.println(listaOuvintes.get(jogadores[i]).getNome() + " qual o seu palpite?");
                try {
                    palpites[i] = in.nextDouble();
                    while (palpites[i] < limiteInf || palpites[i] > limiteSup) {
                        System.out.println("Palpite fora do intrevalo");
                        System.out.println("Escolha um valor entre " + limiteInf + " e " + limiteSup);
                        System.out.println("Qual o palpite?");
                        palpites[i] = in.nextDouble();
                    }
                    //NAO PERMITIR PALPITES IGUAIS
                    //percorrer array com palpites e ir verificando se existe iguais
                    for (int j = 0; j < palpites.length; j++) {
                        if (palpites[i] == palpites[j] && i != j && palpites[j] != 0) {    //PALPITE IGUAL
                            System.out.println("Palpite já selecionado, escolha um novo.");
                            i--;
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Valor inválido, tente novamente (user , em vez de .)");
                    in.nextLine();
                    i--;
                }
            }
        }

        private static void DefenirOuvintes () {
            int numJogadores;
            int numJogosTemp;

            numJogadores = rnd.nextInt(2, listaOuvintes.size() + 1);     //DEFINE QUANTOS OUVINTES VÃO A JOGO MINIMO 2 JOGADORES
            jogadores = new int[numJogadores];
            //SELECIONAR OUVINTES A JOGAR

            //SE TODOS OS JOGADORES FOREM A JOGO
            if (numJogadores == listaOuvintes.size()) {
                for (int i = 0; i < jogadores.length; i++) {
                    jogadores[i] = i;
                }
            } else {
                for (int i = 0; i < numJogadores; i++) {
                    jogadores[i] = rnd.nextInt(1, listaOuvintes.size()); //PARA NÃO SAIR 0 E SER MAIS FACIL FAZER A VALIDAÇAO SEGUINTE
                    //VERIFICAR SE SAI OUVINTES REPETIDOS
                    for (int j = 0; j < jogadores.length; j++) {    //PRECORRE O ARRAY
                        while (jogadores[i] == jogadores[j] && i != j && jogadores[j] != 0) {      //VERIFICA SE O 2º É IGUAL AO 1º E SE O VALOR É DIF DE 0
                            //(PARA NÃO COMPARAR A MESMA POSICAO E SE A POSICÃO AINDA NAO TIVER SIDO PREENCHIDA (J) )
                            jogadores[i] = rnd.nextInt(1, listaOuvintes.size());
                            j = 0;
                        }
                    }
                }
                //RETIRAR 1 EM TODAS AS POSSIÇÕES NO ARRAY PARA ESTAR DENTRO DO RANGE
                for (int i = 0; i < jogadores.length; i++) {
                    jogadores[i] = jogadores[i] - 1;
                }
            }

            //ADICIONA O JOGO AOS OUVINTES
            for (int i = 0; i < jogadores.length; i++) {
                numJogosTemp = listaOuvintes.get(jogadores[i]).getNumJogos();
                listaOuvintes.get(jogadores[i]).setNumJogos(numJogosTemp + 1);
                //ATUALIZAR TAXA DE ACERTO
                listaOuvintes.get(jogadores[i]).setTaxatAcerto((listaOuvintes.get(jogadores[i]).getNumAcertos() * 100) / listaOuvintes.get(jogadores[i]).getNumJogos());
            }
        }

        private static void Inicializar () {
            listaOuvintes.add(new Ouvintes("Ana", "Povoa", 10, 4));
            listaOuvintes.add(new Ouvintes("Diogo", "Porto", 11, 5));
            listaOuvintes.add(new Ouvintes("Anabela", "Malhadas", 5, 1));
            listaOuvintes.add(new Ouvintes("Jose", "Belmonte", 7, 1));
            listaOuvintes.add(new Ouvintes("Carlos", "Ericeira", 4, 0));
            listaOuvintes.add(new Ouvintes("Luis", "Lisboa", 3, 3));
        }
    }