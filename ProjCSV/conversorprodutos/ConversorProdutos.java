/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conversorprodutos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Rubens
 */
public class ConversorProdutos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String caminho = scan.next();
        scan.close();
        caminho = caminho.replaceAll("\\\\","/");
        System.out.println(caminho);
         //caminho do arquivo .csv
        File arquivo = new File(caminho + "/CADPRO.csv");
        int contadorLinhas = 0;
        //as classe BufferedReader,FileReader e File precisam tratar as exceções usando try-catch;
        try{
            //declarando o buffer que le o arquivo e irá ler as linhas do arquivo
            BufferedReader entrada = new BufferedReader(new FileReader(arquivo));
            try {
                //teste irá ler a primeira linha para que o nome dos campos da tabela não apareça nna matriz
                //que irá armazenar os dados
                String leitura = entrada.readLine();
                //contando o numero de linhas;
                while((leitura = entrada.readLine()) != null){
                    contadorLinhas++;
                }
                //buffer deve ser fechado,don't ask why...
                entrada.close();
                //declarando matriz que irá preencher o arquivo final
                //contadorLinhas é o numero de produtos na tabela, a primeira linha
                //seria o layout a ser importado
                String[][] matrizCSV = new String[contadorLinhas+1][26];
                //abrindo novamente o .csv
                entrada = new BufferedReader(new FileReader(arquivo));
                //pulando a primeira linha
                leitura = entrada.readLine();
                for(int i = 1; i <= contadorLinhas; i++){
                    leitura = entrada.readLine();
                    //separandoColunas com o split para manipularmos as colunas
                    String separandoColunas[] = leitura.split(";");
                    //colunas principais utilizadas no arquivo final
                    matrizCSV[i][0] = separandoColunas[0];
                    matrizCSV[i][1] = separandoColunas[1];
                    matrizCSV[i][3] = separandoColunas[3];
                    matrizCSV[i][4] = "VERDADEIRO";
                    //matrizCSV[i][8] = separandoColunas[9];
                    switch(separandoColunas[9]){
                        case("T01"):
                            matrizCSV[i][8] = "000";
                            break;
                        case("T02"):
                            matrizCSV[i][8] = "000";
                            break;
                        case("T03"):
                            matrizCSV[i][8] = "000";
                            break;
                        case("T04"):
                            matrizCSV[i][8] = "000";
                            break;
                        case("Ise"):
                            matrizCSV[i][8] = "040";
                            break;
                        case("Sub"):
                            matrizCSV[i][8] = "060";
                            break;
                        case("Não"):
                            matrizCSV[i][8] = "041";
                            break;
                    }
                }
                entrada.close();
                //Acessando segundo arquivo .CSV
                arquivo = new File(caminho + "/CADPRO1.csv");
                entrada = new BufferedReader(new FileReader(arquivo));
                leitura = entrada.readLine();
                for(int i = 1; i <= contadorLinhas; i ++){
                    leitura = entrada.readLine();
                    String[] separandoColunas = leitura.split(";");
                    matrizCSV[i][13] = separandoColunas[9];
                    matrizCSV[i][14] = separandoColunas[11];
                    for(int j = 0; j < matrizCSV[0].length; j ++){
                        System.out.print(matrizCSV[i][j] + ";");
                    }
                    System.out.println();
                }
                entrada.close();
            } 
            //exceção aceita pelo fileReader e criada pelo próprio netbeans
            catch (IOException ex) {
                Logger.getLogger(ConversorProdutos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //exceção aceita pelo file,simplesmente deixou eu customizar o conteudo das chaves
        catch(FileNotFoundException e){
            System.out.println("Arquivo Não Encontrado");
        }
    }
}
