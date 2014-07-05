package com.hlinski;

import org.joda.time.LocalDate;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlinski on 6/30/14.
 */
public class DataLoader {
    public static void main(String[] args) {
        RestTemplate client = new RestTemplate();
        Map<String, Object> params = new HashMap<String, Object>();
        String url = "http://localhost:8080/create/%s/%s/%s/%s";


        LocalDate current = LocalDate.now();
        client.postForObject(String.format(url, 1, 1, 5, current),params, Integer.class);
        client.postForObject(String.format(url, 2, 1, 10, current),params, Integer.class);
        client.postForObject(String.format(url, 3, 1, 15, current),params, Integer.class);

        client.postForObject(String.format(url, 1, 1, 5, current.minusDays(1)),params, Integer.class);
        client.postForObject(String.format(url, 2, 1, 20, current.minusDays(1)),params, Integer.class);
        client.postForObject(String.format(url, 3, 1, 30, current.minusDays(1)),params, Integer.class);
        /*Integer[] groups = {1,2,3,4,5,6,7};
        Random rand = new Random();
        int randomNum = rand.nextInt((6 - 0) + 1);
        for(long i=0; i < 20000; i++){
            for(int j=0; j < 356; j++){
                client.postForObject(String.format(url, i, groups[randomNum], i, current.minusDays(j)),
                        params, Integer.class);
            }
            if(i%1000 == 0){
                System.out.println("Users loaded  " + i);
            }
        }*/
    }
}
