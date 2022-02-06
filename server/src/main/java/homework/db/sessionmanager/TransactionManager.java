package homework.db.sessionmanager;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);

    void callInTransaction(TransactionExecutor consumer);
}
