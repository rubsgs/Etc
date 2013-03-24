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
import java.util.regex.Pattern;

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
                System.out.println(leitura);

                while((leitura = entrada.readLine()) != null){
                    contadorLinhas++;
                }
                //System.out.println(entrada.readLine());
                System.out.println();
                System.out.println(contadorLinhas);
                //buffer deve ser fechado,don't ask why...
                entrada.close();
                String[][] matrizCSV = new String[contadorLinhas][26];
                entrada = new BufferedReader(new FileReader(arquivo));
                leitura = entrada.readLine();
                for(int i = 0;i < contadorLinhas; i++){
                    leitura = entrada.readLine();
                    System.out.println(leitura);
                    matrizCSV[i] = leitura.split(";");
                    for(int j = 0; j < 26; j++){
                        System.out.print(matrizCSV[i][j] + " ");
                    }
                    System.out.println();
                }
                
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
