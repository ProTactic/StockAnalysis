package Exceptions;

public class StockSystemException extends Exception {

    public StockSystemException(SystemExceptionType type){
        super(SystemExceptionType.messageOfType(type));
    }

    public enum SystemExceptionType{
        NOT_INITIALIZED_API_KEY;
        protected static String messageOfType(SystemExceptionType type){
            if(type == NOT_INITIALIZED_API_KEY){
                return "API key has not been initialized";
            }
            return "";
        }
    }
}
