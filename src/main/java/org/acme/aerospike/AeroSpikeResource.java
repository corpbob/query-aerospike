package org.acme.aerospike;

import javax.enterprise.event.Observes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aerospike.client.AerospikeClient;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.json.JsonObject;

@Path("/balance")
public class AeroSpikeResource {

	private AerospikeClient client;

	private String aerospikeHost;
	private int aerospikePort;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {

		JsonObject json = new JsonObject();
		json.put("balance", 5.0);
		return json.toString();
	}

	void onStart(@Observes StartupEvent ev) {

		if (false) {
			System.out.println("Starting AerospikeResource");
			String parameters = System.getenv("PARAMETERS");
			JsonObject json = new JsonObject(parameters);

			aerospikeHost = json.getString("aerospike_host");
			String _str_aerospikePort = json.getString("aerospike_port");

			if (null == aerospikeHost || "".equals(aerospikeHost)) {
				aerospikeHost = "localhost";
			}

			if (null == _str_aerospikePort || "".equals(_str_aerospikePort)) {
				aerospikePort = 3000;
			} else {
				aerospikePort = Integer.parseInt(_str_aerospikePort);
			}

			client = new AerospikeClient(aerospikeHost, aerospikePort);
		}

	}

	void onStop(@Observes ShutdownEvent ev) {
		if (false) {
			System.out.println("Shutting down aerospike resource");
			if (null != client) {
				client.close();
			}
		}
	}
}