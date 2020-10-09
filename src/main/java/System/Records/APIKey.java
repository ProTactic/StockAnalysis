package System.Records;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "APIKeys")
public class APIKey {
    @Id
    public String keyOf;
    @NotNull
    public String key;

    public APIKey() {
    }

    public APIKey(String keyOf, String key) {
        this.keyOf = keyOf;
        this.key = key;
    }

    public String getKeyOf() {
        return keyOf;
    }

    public void setKeyOf(String keyOf) {
        this.keyOf = keyOf;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
