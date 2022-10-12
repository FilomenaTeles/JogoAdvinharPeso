import java.util.Scanner;

public class Ouvintes {
    private int id;
    private String nome;
    private String cidade;
    private int numJogos;
    private int numAcertos;
    private int taxatAcerto;

    public Ouvintes() {
        id=Main.id;
        nome="";
        cidade="";
        numJogos=0;
        numAcertos=0;
        taxatAcerto=0;
        Main.id++;      //AUMENTA O CONTADOR DE ID'S
    }

    public Ouvintes(String nome, String cidade, int numJogos, int numAcertos) {
        id=Main.id;
        this.nome = nome;
        this.cidade = cidade;
        this.numJogos = numJogos;
        this.numAcertos = numAcertos;
        taxatAcerto=(numAcertos*100)/numJogos;
        Main.id++;      //AUMENTA O CONTADOR DE ID'S
    }

    public Ouvintes(String nome, String cidade) {
        id=Main.id;
        this.nome = nome;
        this.cidade = cidade;
        numJogos = 0;
        numAcertos = 0;
        taxatAcerto=0;
        Main.id++;      //AUMENTA O CONTADOR DE ID'S
    }

    public Ouvintes(String nome, int taxatAcerto, int numJogos, int numAcertos) {
        this.nome = nome;
        this.taxatAcerto=taxatAcerto;
        this.numJogos=numJogos;
        this.numAcertos=numAcertos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getNumJogos() {
        return numJogos;
    }

    public void setNumJogos(int numJogos) {
        this.numJogos = numJogos;
    }

    public int getNumAcertos() {
        return numAcertos;
    }

    public void setNumAcertos(int numAcertos) {
        this.numAcertos = numAcertos;
    }

    public int getTaxatAcerto() {
        return taxatAcerto;
    }

    public void setTaxatAcerto(int taxatAcerto) {
        this.taxatAcerto = taxatAcerto;
    }



    public static void CriarOuvintes() {
       Scanner in=new Scanner(System.in);
        String nome, cidade;

        System.out.println("Qual o nome do ouvinte?");
        nome=in.nextLine();

        System.out.println("Qual a cidade do ouvinte?");
        cidade=in.nextLine();

        Main.listaOuvintes.add(new Ouvintes(nome,cidade));
        System.out.println("Ouvinte criado com sucesso.");
    }

    public static void EditarOuvintes() {
        Scanner in=new Scanner(System.in);
        int ouvinte,op=1;
        String nome, cidade;

        System.out.println("Qual ouvinte deseja editar?");
        Main.VerOuvintes();
        try {
            ouvinte = in.nextInt();

            if (ouvinte > 0 && ouvinte < Main.id) {      //VERIFICA SE ECOLHE UM OUVINTE VALIDO
                while (op != 0) {
                    System.out.println("O que deseja editar?");
                    System.out.println("1 - Nome");
                    System.out.println("2 - Cidade");
                    System.out.println("0 - Voltar menu anterior.");
                    op = in.nextInt();
                    in = new Scanner(System.in);
                    if (op == 0) return;
                    else if (op == 1) {         //ALTERA NOME
                        System.out.println("Qual o novo nome do ouvinte?");
                        nome = in.nextLine();
                        Main.listaOuvintes.get(ouvinte - 1).setNome(nome);
                        System.out.println("Nome alterado com sucesso");
                    } else if (op == 2) {     //ALTERA CIDADE
                        System.out.println("Qual a nova cidade do ouvinte?");
                        cidade = in.nextLine();
                        Main.listaOuvintes.get(ouvinte - 1).setCidade(cidade);
                        System.out.println("Cidade alterada com sucesso");
                    } else System.out.println("Opção inválida, tente novamente.");
                }

            } else System.out.println("Ouvinte inválido");
        }catch (Exception e) {
            System.out.println("Valor inválido, tente novamente");
            in.nextLine();
        }
    }

    public static void EliminarOuvinte(){
        Scanner in=new Scanner(System.in);
        int ouvinte;

        System.out.println("Qual ouvinte deseja editar?");
        Main.VerOuvintes();
        try {
            ouvinte = in.nextInt();
            if (ouvinte > 0 && ouvinte < Main.id) {                    //VERIFICA SE ECOLHE UM OUVINTE VALIDO
                Main.listaOuvintes.remove(ouvinte - 1);       //VAI AO INDEX E REMOVE O OUVINTE
                System.out.println("Ouvinte removido com sucesso");
            } else System.out.println("Ouvinte inválido");
        }catch (Exception e) {
            System.out.println("Valor inválido, tente novamente");
            in.nextLine();
        }
    }
    @Override
    public String toString() {
        return  "Ouvinte:"+id+"\tNome:"+nome+"\tCidade:"+cidade+"\tJogos:"+numJogos+
                "\tAcertos:"+numAcertos+"\tTaxa de acertos:"+taxatAcerto+"%";

    }
}
