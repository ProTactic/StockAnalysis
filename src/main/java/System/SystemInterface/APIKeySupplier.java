package System.SystemInterface;

public enum APIKeySupplier{
    ALPHA_ADVANTAGE;

    public static String getSupplierName(APIKeySupplier keySupplier){
        switch (keySupplier){
            case ALPHA_ADVANTAGE:
                return "ALPHA ADVANTAGE";
        }
        return null;
    }
}
