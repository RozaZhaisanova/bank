/**
 * Класс определяющий сущность клиент
 */
public class Client {

    private ActionsEnum action;
    private int cash;
    private int serviceTime;
    private int number;

    public int getNumber() {
        return number;
    }

    public Client(ActionsEnum action, int cash, int serviceTime, int number) {
        this.action = action;
        this.cash = cash;
        this.serviceTime = serviceTime;
        this.number = number;
    }

    public ActionsEnum getAction() {
        return action;
    }

    public int getCash() {
        return cash;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "Client{"
                + "number=" + number
                + ", action=" + action
                + ", cash=" +  cash
                + ", serviceTime=" + serviceTime
                + '}';
    }

}