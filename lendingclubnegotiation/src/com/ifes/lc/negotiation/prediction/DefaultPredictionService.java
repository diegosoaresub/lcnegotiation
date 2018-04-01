package com.ifes.lc.negotiation.prediction;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.ifes.lc.negotiation.util.Log;

public class DefaultPredictionService {
	private static final String REST_URI = "http://127.0.0.1:5000/";

	private final WebTarget webTarget;
	 
    public DefaultPredictionService() {
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, 30000)
                .property(ClientProperties.CONNECT_TIMEOUT, 5000);
 
        webTarget = ClientBuilder
                .newClient(clientConfig)
                .target(REST_URI);
    }
	
	public DefaultPredictionResponse predict(DefaultPredictionParams params) {
		
		DefaultPredictionResponse result = webTarget
							.path("predictDefault")
							.request(MediaType.APPLICATION_JSON)
							.post(Entity.json(params))
							.readEntity(DefaultPredictionResponse.class);
		
		Log.println("Predicted Class: " + result.getPredicted_class());
		Log.println("Delinquent Prob: " + result.getDelinquent_prob());
		
		return result;
	}
	
}
