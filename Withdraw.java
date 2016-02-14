package fred.gravil.com;

import java.util.Random;

/**
 * Created by Fred on 2/11/2016.
 */
public class Withdraw implements Runnable {

    private AmountBuffer sharedBalance;
    private static Random generator = new Random();
    private String name; //thread name

    public Withdraw(String name,AmountBuffer balance ){
        sharedBalance = balance;
        this.name = name;
    }

    @Override
    public void run() {

        while (true)
        {
            try{
                sharedBalance.withdraw(name,generator.nextInt(50));
                Thread.yield();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
