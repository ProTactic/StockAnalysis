package business;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import System.RestTemplate;

public class RestTemplateTest {

    private RestTemplate restTemplate;
    private final String URL = "https://www.google.com/search?";

    @Before
    public void setUp(){
        restTemplate = new RestTemplate(URL);
    }

    @Test
    public void addKeyValueTest(){
        String assembledURL = URL + "client=firefox&q=google";
        restTemplate.addKeyValue("client","firefox")
                .addKeyValue("q","google");
        Assert.assertEquals("Rest URL is not assembled right",
                restTemplate.getRestUrl(), assembledURL);
    }
}
