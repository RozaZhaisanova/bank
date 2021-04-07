import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Сущность, которая проводит обслуживание клиентов.
 * Каждый операционист - это отдельный поток, который запускается вместе с началом работы банка.
 * Oн выполняет снятие или пополнение кассы.
 */
public class Operator extends Thread {

    private Deque<Client> queue;
    private CashBox cashbox;
    private final static Logger LOGGER = Logger.getLogger(Operator.class.getName());
    private int numOfWindow;

    Operator(CashBox cashbox, int numOfWindow) {
        this.cashbox = cashbox;
        this.numOfWindow = numOfWindow;
    }
   // У каждого операциониста есть своя очередь клиентов, которых он должен обслужить
    public Deque<Client> getQueue() {
        return queue;
    }

    @Override
    public void run() {
        queue = new ArrayDeque<>();
        for(;;) {
            //Если в очереди никого нет, то ждем пока там кто-то появится
            if (queue.size() == 0) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Client client = queue.pop();
            if (client.getAction() == ActionsEnum.PUT) {
                synchronized (cashbox) {
                    cashbox.setCash(cashbox.getCash() + client.getCash());
                    LOGGER.log(Level.INFO, "Операция " + client.toString() + " в окне банка " + numOfWindow);
                    LOGGER.log(Level.INFO, "Сейчас денег: " + cashbox.getCash());
                }
            } else {
                synchronized (cashbox) {
                    if (cashbox.getCash() >= client.getCash()) {
                        cashbox.setCash(cashbox.getCash() - client.getCash());
                        LOGGER.log(Level.INFO, "Операция " + client.toString() + " в окне банка " + numOfWindow);
                        LOGGER.log(Level.INFO, "Сейчас денег: " + cashbox.getCash());
                    }
                }
            }
            try {
                Thread.sleep(client.getServiceTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}