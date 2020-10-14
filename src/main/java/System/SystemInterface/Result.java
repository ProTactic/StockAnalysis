package System.SystemInterface;

public class Result<T> {
    private final T entity;
    private final boolean isValid;
    private final String message;

    /**
     * Build Result without message
     * @param e the entity
     */
    public Result(boolean isValid, T e) {
        this(isValid, e, "");
    }

    /**
     * Build Result
     * @param e the entity
     */
    public Result(boolean isValid, T e, String message) {
        entity = e;
        this.message = message;
        this.isValid = isValid;
    }

    public boolean isValid(){
        return isValid;
    }

    public boolean isNotValid(){
        return !isValid;
    }

    public String getMessage(){
        return message;
    }

    public T getEntity(){
        return entity;
    }
}
