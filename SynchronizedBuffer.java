package fred.gravil.com;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Fred on 2/12/2016.
 */
public class SynchronizedBuffer implements AmountBuffer {

    private Lock accessLock = new ReentrantLock();
    private Condition canTake = accessLock.newCondition();
    private Condition canPut = accessLock.newCondition();

    private int balance = 0;
    private boolean occupied = true;
    @Override
    public void deposit(String threadName,int amount) {
        accessLock.lock();

        try{
            while(occupied){
                canPut.await();
            }

            balance += amount;
            occupied = true;
            displayDeposit(threadName + " deposits $" + amount);
            canTake.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            accessLock.unlock();
        }

    }

    @Override
    public void withdraw(String threadName,int amount) {
        accessLock.lock();

        try{
            while(!occupied) {
                canTake.await();
            }

            if(balance - amount < 0){
                displayWithdraw(false,threadName + " withdraws $" + amount);
                occupied = false;
            }
            else{
                balance -= amount;
                displayWithdraw(true,threadName + " withdraws $" + amount);
            }

            canPut.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void displayDeposit( String operation )
    {

        StringBuffer str = new StringBuffer("Balance is $");
        str.append(balance);
        System.out.printf( "%-40s%s\t\t\t\t\t\t\t\t%s\n", operation, "", str.toString());
    }
    public void displayWithdraw( boolean canWithdraw, String operation )
    {
        if(!canWithdraw){

            System.out.printf( "%-40s%s\t\t\t%s\n", "", operation,
                    "Withdrawal - Blocked - Insufficient Funds");
        }
        else{
            StringBuffer str = new StringBuffer("Balance is $");
            str.append(balance);

            System.out.printf( "%-40s%s\t\t\t%s\n", "", operation,
                    str.toString());
        }

    }
}
