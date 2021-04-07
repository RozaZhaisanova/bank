import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Банк.
 */
public class Bank {

    private final static Logger LOGGER = Logger.getLogger(Bank.class.getName());
    /**
     * Метод, стартующий работу банка.
     */
    public void startWork() throws InterruptedException {
        LOGGER.log(Level.INFO,"Начало работы");
        CashBox cashbox = new CashBox(100000);
        LOGGER.log(Level.INFO, "Всего денег: " + cashbox.getCash());
        int operatorsCount = 4;
        Operator[] operators = new Operator[operatorsCount];
        for (int i = 0; i < operatorsCount; i++) {
            operators[i] = new Operator(cashbox, i+1);
            operators[i].start();
        }
        Thread thread = new Thread(new ClientGenerate(cashbox, operators));
        thread.start();
        thread.join();
    }
}