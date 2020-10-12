package System.SystemInterface;

public interface ISettingController {

    /**
     * Save or update the api key for a given supplier
     * @param keySupplier api supplier
     * @param key api key
     * @return {@code true} if the api key was saved or updated
     */
    boolean saveOrUpdateAPIKey(APIKeySupplier keySupplier, String key);
}
