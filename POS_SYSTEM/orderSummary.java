package POS_SYSTEM;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class orderSummary {
    private double Total;
    private double change;
    private double CashTend;
    private double vatRate;
    private double netTotal;
    private double totalVat;
    private static String usrname;


    public static String getUsrname(){
        return usrname;
    }

    public static void setUsrname(String usrname) {
        orderSummary.usrname = usrname;
    }

    public <doube> String orderSumm(doube cashTend, doube total){

        // to follow pa yung order functions para sa receipt
        LocalDateTime locDate = LocalDateTime.now();
        DateTimeFormatter locDateObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String locDateTime = locDateObj.format(locDate);

        Total = (double) total;
        CashTend = (double) cashTend;

        vatRate = 12;
        netTotal = Total / (100 + vatRate) * 100;
        totalVat = Total - netTotal;
        change = CashTend - Total;

        BufferedWriter output;{
            try {
                output = new BufferedWriter(new FileWriter("Official_Receipt.txt"));
                output.write(String.format("%-24s","Total:") + Total + "\n");
                output.write(String.format("%-24s","Cash Tendered:") + CashTend + "\n");
                output.write(String.format("%-24s","Change:") + change + "\n");
                output.write("==============================" + "\n");
                output.write("VAT Information " + "\n");
                output.write(String.format("%-24s","VATable sales:"));
                output.write(String.format("%.5g",netTotal) + "\n");
                output.write(String.format("%-24s","VAT 12.0000%:"));
                output.write(String.format("%.4g",totalVat) + "\n");
                output.write("==============================" + "\n");
                output.write(String.format("%-24s","Official receipt:") + "1234"+ "\n");
                output.write(String.format("%-24s", "Cashier Name:")+ getUsrname() +("\n"));
                output.write(String.format("%-24s","Date:") + locDateTime + "\n");
                output.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return locDateTime;
    }
}





