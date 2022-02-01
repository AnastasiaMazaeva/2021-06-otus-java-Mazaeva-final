package homework.db.sessionmanager;

@FunctionalInterface
public interface TransactionExecutor {

    void execute();
}
