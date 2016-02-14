package fred.gravil.com;

import java.util.Random;

/**
 * Created by Fred on 2/11/2016.
 */
public class Deposit implements Runnable {

    private static Random generator = new Random();
    private AmountBuffer currentBalance;
    private int depositAmount;
    private String name;

    public Deposit(String name, AmountBuffer balance){
        currentBalance = balance;
        this.name = name;
    }

    @Override
    public void run() {

        while (true)
        {
            try {
                Thread.sleep(generator.nextInt(30));
                currentBalance.deposit(name,generator.nextInt(200));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
