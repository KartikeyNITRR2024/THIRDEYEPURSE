package com.thirdeye.purse.externalcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.thirdeye.purse.utils.AllMicroservicesData;

@Component
public class Thirdeye_Holded_Stock_Viewer_Connection {
	@Value("${holdedstockmicroservices}")
    private String holdedstockmicroservices;
    
    @Autowired
	AllMicroservicesData allMicroservicesData;

    @Autowired
    private RestTemplate restTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(Thirdeye_Stock_Market_Viewer_Connection.class);
    
    public boolean updateStockMarketViewer() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Void> request = new HttpEntity<>(headers);
            String microserviceUrl = allMicroservicesData.allMicroservices.get(holdedstockmicroservices).getMicroserviceUrl();
            Integer microserviceUniqueId = allMicroservicesData.allMicroservices.get(holdedstockmicroservices).getMicroserviceUniqueId();
            Integer currentUniqueId = allMicroservicesData.current.getMicroserviceUniqueId();
            String url = microserviceUrl + "api/updateholdedstock/" + microserviceUniqueId + "/" + currentUniqueId;
            logger.info("Url to update holded Stock Viewer {} : ", url);
            ResponseEntity<Boolean> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                Boolean.class
            );
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error while sending request to update holded Stock Viewer: ", e);
            return false;
        }
    }
}
