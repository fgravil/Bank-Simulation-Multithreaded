package fred.gravil.com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* Name: Fred Gravil
   Course: CNT 4714 Spring 2016
   Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
   Due Date: February 14, 2016
*/
public class Bank {

    public static void main(String args[]){

        ExecutorService application = Executors.newFixedThreadPool(9);

        AmountBuffer sharedBalance = new SynchronizedBuffer();

        System.out.printf( "%-40s%s\t\t\t%s\n%-40s%s\n\n", "Deposit Threads",
                "Withdrawal Threads", "      Balance", "--------------", "---------------\t\t\t\t   ------------" );

        Deposit Thread1 = new Deposit("Thread1",sharedBalance);
        Deposit Thread2 = new Deposit("Thread2",sharedBalance);
        Deposit Thread3 = new Deposit("Thread3",sharedBalance);
        Withdraw Thread4 = new Withdraw("Thread4",sharedBalance);
        Withdraw Thread5 = new Withdraw("Thread5",sharedBalance);
        Withdraw Thread6 = new Withdraw("Thread6",sharedBalance);
        Withdraw Thread7 = new Withdraw("Thread7",sharedBalance);
        Withdraw Thread8 = new Withdraw("Thread8",sharedBalance);
        Withdraw Thread9 = new Withdraw("Thread9",sharedBalance);

        try // try to start producer and consumer
        {
            application.execute( Thread1 );
            application.execute( Thread2 );
            application.execute( Thread3 );
            application.execute( Thread4 );
            application.execute( Thread5 );
            application.execute( Thread6 );
            application.execute( Thread7 );
            application.execute( Thread8 );
            application.execute( Thread9 );

        } // end try
        catch ( Exception exception )
        {
            exception.printStackTrace();
        } // end catch

        application.shutdown();

    }
}
