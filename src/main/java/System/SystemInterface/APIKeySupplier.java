package System.SystemInterface;

public enum APIKeySupplier{
    ALPHA_VANTAGE;

    public static String getSupplierName(APIKeySupplier keySupplier){
        switch (keySupplier){
            case ALPHA_VANTAGE:
                return "ALPHA VANTAGE";
        }
        return null;
    }
}
