package POS_SYSTEM;

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

      System.out.println("Total:       \t" + Total);
      System.out.println("Cash Tendered:\t" + CashTend);
      System.out.println("Change:      \t" + change);
      System.out.println("=======================");
      System.out.print("VATable sales:\t");
      System.out.print(String.format("%.5g",netTotal) + "\n");
      System.out.print("VAT 12.0000%:\t");
      System.out.print(String.format("%.4g",totalVat) + "\n");
      System.out.println("=======================");

   }

}





