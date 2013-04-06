/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conversorprodutos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.*;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

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
        caminho = caminho.replaceAll("\\\\", "/");
        String caminhoSaida = scan.next();
        scan.close();
        caminhoSaida = caminhoSaida.replaceAll("\\\\", "/");
        System.out.println(caminho);
        //caminho do arquivo .csv
        File arquivo = new File(caminho + "/CADPRO.csv");
        int contadorLinhas = 0;
        //layout a ser utilizado no .xls criado;
        String layout = "procod;prodes;promar;prouni;proatinat;procp1;procp2;proref;prosit;procp3;procp4;propeso;proqmi;propdc;propdv;propcv;proicm;proipi;comissao;proipe;prodesc;proemb;prodescon;prolotvd;probs;stsobs";
        String[] cabecalho = layout.split(";");
        //as classe BufferedReader,FileReader e File precisam tratar as exceções usando try-catch;
        try {
            //declarando o buffer que le o arquivo e irá ler as linhas do arquivo
            BufferedReader entrada = new BufferedReader(new FileReader(arquivo));

            //teste irá ler a primeira linha para que o nome dos campos da tabela não apareça nna matriz
            //que irá armazenar os dados
            String leitura = entrada.readLine();
            //contando o numero de linhas;
            while ((leitura = entrada.readLine()) != null) {
                contadorLinhas++;
            }
            //buffer deve ser fechado,don't ask why...
            entrada.close();
            //declarando matriz que irá preencher o arquivo final
            //contadorLinhas é o numero de produtos na tabela, a primeira linha
            //seria o layout a ser importado
            String[][] matrizCSV = new String[contadorLinhas + 1][26];
            //abrindo novamente o .csv
            //Utiliza InputStream para poder alterar o charset
            entrada = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "ISO-8859-1"));
            //pulando a primeira linha
            leitura = entrada.readLine();
            for (int i = 1; i <= contadorLinhas; i++) {
                leitura = entrada.readLine();
                //separandoColunas com o split para manipularmos as colunas
                String separandoColunas[] = leitura.split(";");
                //colunas principais utilizadas no arquivo final
                matrizCSV[i][0] = separandoColunas[0];
                matrizCSV[i][1] = separandoColunas[1];
                matrizCSV[i][3] = separandoColunas[3];
                matrizCSV[i][4] = "VERDADEIRO";
                //matrizCSV[i][8] = separandoColunas[9];
                switch (separandoColunas[9]) {
                    case ("T01"):
                        matrizCSV[i][8] = "000";
                        break;
                    case ("T02"):
                        matrizCSV[i][8] = "000";
                        break;
                    case ("T03"):
                        matrizCSV[i][8] = "000";
                        break;
                    case ("T04"):
                        matrizCSV[i][8] = "000";
                        break;
                    case ("Ise"):
                        matrizCSV[i][8] = "040";
                        break;
                    case ("Sub"):
                        matrizCSV[i][8] = "060";
                        break;
                    case ("Não"):
                        matrizCSV[i][8] = "041";
                        break;
                }
            }
            entrada.close();
            //Acessando segundo arquivo .CSV
            arquivo = new File(caminho + "/CADPRO1.csv");
            entrada = new BufferedReader(new FileReader(arquivo));
            leitura = entrada.readLine();
            for (int i = 1; i <= contadorLinhas; i++) {
                leitura = entrada.readLine();
                String[] separandoColunas = leitura.split(";");
                matrizCSV[i][13] = separandoColunas[9];
                matrizCSV[i][14] = separandoColunas[11];
                for (int j = 0; j < matrizCSV[0].length; j++) {
                    System.out.print(matrizCSV[i][j] + ";");
                }
                System.out.println();
            }
            entrada.close();
            //escrevendo .xls
            WritableWorkbook workbook = Workbook.createWorkbook(new File(caminhoSaida + "/Produtos.xls"));
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            //cabeçalho do .xls
            for(int i = 0; i < cabecalho.length; i++) {
                Label texto = new Label(i,0,cabecalho[i]);
                sheet.addCell(texto);
            }
            //conteudo principal do .xls
            for(int i = 1; i <= contadorLinhas; i++){
                Label texto = new Label(0,i,matrizCSV[i][0]);
                sheet.addCell(texto);
                texto = new Label(1,i,matrizCSV[i][1]);
                sheet.addCell(texto);
                texto = new Label(3,i,matrizCSV[i][3]);
                sheet.addCell(texto);
                texto = new Label(4,i,matrizCSV[i][4]);
                sheet.addCell(texto);
                texto = new Label(8,i,matrizCSV[i][8]);
                sheet.addCell(texto);
                texto = new Label(13,i,matrizCSV[i][13]);
                sheet.addCell(texto);
                texto = new Label(14,i,matrizCSV[i][14]);
                sheet.addCell(texto);
            }
            workbook.write();
            workbook.close();
            



        } catch (FileNotFoundException e) {
            System.out.println("Arquivo Não Encontrado");
        } catch (IOException ex) {
            Logger.getLogger(ConversorProdutos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(ConversorProdutos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
