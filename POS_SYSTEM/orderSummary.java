package POS_SYSTEM;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class orderSummary {
   private double Total;
   private double change;
   private double CashTend;
   private double vatRate;
   private double netTotal;
   private double totalVat;

    public <doube> void orderSumm(doube cashTend, doube total){
        // to follow pa yung order functions para sa receipt

        Total = (double) total;
        CashTend = (double) cashTend;

        vatRate = 12;
        netTotal = Total / (100 + vatRate) * 100;
        totalVat = Total - netTotal;
        change = CashTend - Total;

        BufferedWriter output;{
            try {
                output = new BufferedWriter(new FileWriter("Official_Receipt.txt"));
                output.write("Total:       \t" + Total + "\n");
                output.write("Cash Tendered:\t" + CashTend + "\n");
                output.write("Change:      \t" + change + "\n");
                output.write("=======================" + "\n");
                output.write("VATable sales:\t");
                output.write(String.format("%.5g",netTotal) + "\n");
                output.write("VAT 12.0000%:\t");
                output.write(String.format("%.4g",totalVat) + "\n");
                output.write("=======================" + "\n");
                output.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}





