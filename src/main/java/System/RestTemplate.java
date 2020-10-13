package System;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class role is to make it more easy to build rest template
 */
public class RestTemplate {
    private final LinkedHashMap<String, String> restKeysAndValues;
    private String url;

    public RestTemplate(String url){
        this.url = url;
        restKeysAndValues = new LinkedHashMap<>();
    }

    /**
     * Add the key and value to the url
     * @param key The key
     * @param value The value
     * @return The same instance for compound adding
     */
    public RestTemplate addKeyValue(String key, String value){
        restKeysAndValues.put(key, value);
        return this;
    }

    private String keysAndValues(){
        StringBuilder params = new StringBuilder();
        for (String key: restKeysAndValues.keySet()) {
            params.append(String.format("&%s=%s", key, restKeysAndValues.get(key)));
        }

        // Remove the first &
        if(params.length() > 0){
            return params.substring(1);
        }
        return params.toString();
    }

    /**
     * Build and return the url
     * @return The fully composed url
     */
    public String getRestUrl(){
        return url + keysAndValues();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
