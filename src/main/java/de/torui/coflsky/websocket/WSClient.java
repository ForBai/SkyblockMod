package de.torui.coflsky.websocket;

import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketState;
import net.minecraft.client.Minecraft;
import de.torui.coflsky.CoflSky;
import de.torui.coflsky.WSCommandHandler;
import de.torui.coflsky.core.Command;
import de.torui.coflsky.core.StringCommand;

public class WSClient extends WebSocketAdapter {

	
	public static Gson gson;
	
	
	static {
		gson = new GsonBuilder()/*.setFieldNamingStrategy(new FieldNamingStrategy() {
			@Override
			public String translateName(Field f) {
				
				String name = f.getName();
				char firstChar = name.charAt(0);
				return Character.toLowerCase(firstChar) + name.substring(1);
			}
		})*/.create();
	}
	public URI uri;
	public WebSocket socket;
	public boolean shouldRun = false;
	public WebSocketState currentState = WebSocketState.CLOSED;
	
	public WSClient(URI uri) {
		this.uri = uri;
		
	}
	
	public void start() throws IOException, WebSocketException, NoSuchAlgorithmException {
		WebSocketFactory factory = new WebSocketFactory();
		
		/*// Create a custom SSL context.
		SSLContext context = NaiveSSLContext.getInstance("TLS");

		// Set the custom SSL context.
		factory.setSSLContext(context);

		// Disable manual hostname verification for NaiveSSLContext.
		//
		// Manual hostname verification has been enabled since the
		// version 2.1. Because the verification is executed manually
		// after Socket.connect(SocketAddress, int) succeeds, the
		// hostname verification is always executed even if you has
		// passed an SSLContext which naively accepts any server
		// certificate. However, this behavior is not desirable in
		// some cases and you may want to disable the hostname
		// verification. You can disable the hostname verification
		// by calling WebSocketFactory.setVerifyHostname(false).
		factory.setVerifyHostname(false);
		factory.*/
		
		this.socket = factory.createSocket(uri);
		this.socket.addListener(this);
		this.socket.connect();
	}
	
	public void stop() {
		System.out.println("Closing Socket");
	//	socket.sendClose();
		socket.clearListeners();
	
		socket.disconnect();
		/*try {
			socket.getConnectedSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("Socket closed");

	}
	
	@Override
	public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
		System.out.println("WebSocket Changed state to: " + newState);
		currentState = newState;
		
		if(newState == WebSocketState.CLOSED && shouldRun) {
			CoflSky.Wrapper.restartWebsocketConnection();
		}
		
		super.onStateChanged(websocket, newState);
	}

	
	
	 @Override
	    public void onTextMessage(WebSocket websocket, String text) throws Exception{
		
		//super.onTextMessage(websocket, text);
		 System.out.println("Received: "+ text);
		Command cmd = gson.fromJson(text, Command.class);
		//System.out.println(cmd);
		WSCommandHandler.HandleCommand(cmd, Minecraft.getMinecraft().thePlayer);
		
	}

	public void SendCommand(Command cmd) {
		String json = gson.toJson(cmd);
		this.socket.sendText(json);
	}

	public void SendCommand(StringCommand sc) {
		String json = gson.toJson(sc);
		this.socket.sendText(json);
	}

		
	
	
}