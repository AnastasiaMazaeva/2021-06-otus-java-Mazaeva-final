package storage.exception;

public class StorageException extends RuntimeException {
    public StorageException(String message, Throwable e) {
        super(message, e);
    }
}
