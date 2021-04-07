import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Поток - генератор клиентов.
 */
public class ClientGenerate implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(ClientGenerate.class.getName());
    private CashBox cashbox;
    private Operator[] operators;

    public ClientGenerate(CashBox cashbox, Operator[] operators) {
        this.cashbox = cashbox;
        this.operators = operators;
    }
    /**
     * Создает объекты клиентов в случайные моменты времени и направляет их в очередь к одному из операционистов.
     * В качестве операциониста выбирается тот, у кого в очереди меньше всего объектов.
     */
    @Override
    public void run() {
        Random random = new Random();
        int number = 0;
        for (; ; ) {
            number++;
            Operator operatorWithSmallQueue = findSmallQueue();
            int cash = random.nextInt(100861) + 1000;
            Client client = new Client(randomEnum(ActionsEnum.class), cash, random.nextInt(2000) + 500 , number);
            operatorWithSmallQueue.getQueue().addLast(client);
            LOGGER.log(Level.INFO, "Пришел клиент " + client.toString());
            synchronized (operatorWithSmallQueue){
                operatorWithSmallQueue.notify();
            }
            try {
                Thread.sleep(random.nextInt(400) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Метод возвращает оператора, у которого самая маленькая очередь
     * @return оператор
     */
    private Operator findSmallQueue() {
        Operator operator = operators[0];
        int min = operators[0].getQueue().size();
        for (Operator o : operators) {
            if (o.getQueue().size() < min) {
                min = o.getQueue().size();
                operator = o;
            }
        }
        return operator;
    }
    /**
     * Метод возвращает произвольный Enum
     * @param clazz
     * @param <T>
     * @return произвольный Enum
     */
    private <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        Random random = new Random();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}