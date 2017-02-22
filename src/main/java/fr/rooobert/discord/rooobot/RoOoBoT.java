package fr.rooobert.discord.rooobot;

import java.util.List;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/** Program entry point class */
public class RoOoBoT {
	
	/** Program entry point
	 * @param args */
	public static void main(String args[]) {
		if (args.length == 1) {
			String token = args[0];
			
			IDiscordClient dc = RoOoBoT.createClient(token, false);
			
			//
			try {
				dc.login();
				
				// Text channels
				List<IChannel> textChannels = dc.getChannels();
				for (IChannel channel : textChannels) {
					channel.sendMessage("Bonjour.");
				}
				
				// Voice channels
				List<IVoiceChannel> voiceChannels = dc.getVoiceChannels();
				for (IVoiceChannel channel : voiceChannels) {
					/*IMessage message = new MessageBuilder(dc)
							.appendContent("Ceci est un test !")
							.withTTS().build();*/
					
					channel.sendMessage("Ceci est un test", true);
				}
				
				
			} catch (DiscordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RateLimitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MissingPermissionsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Disconnect
			try {
				dc.logout();
			} catch (DiscordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println(String.format("Usage : java %s <token>", RoOoBoT.class.getCanonicalName()));
		}
	}
	
	/** Returns a new instance of the Discord client */
	public static IDiscordClient createClient(String token, boolean login) { 
		
		// Creates the ClientBuilder instance
		ClientBuilder clientBuilder = new ClientBuilder(); 
		clientBuilder.withToken(token); // Adds the login info to the builder
		try {
			if (login) {
				return clientBuilder.login(); // Creates the client instance and logs the client in
			} else {
				return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
			}
		} catch (DiscordException e) { // This is thrown if there was a problem building the client
			e.printStackTrace();
			return null;
		}
	}
}
