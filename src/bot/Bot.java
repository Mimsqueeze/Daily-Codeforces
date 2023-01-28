package bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.EnumSet;

import static net.dv8tion.jda.api.interactions.commands.OptionType.INTEGER;

public class Bot extends ListenerAdapter {
    private final Color embedColor = new Color(255,215,0);

    public static void main(String[] args) throws LoginException {
        // Local variable to store the token String
        String token = ""; 

        // Retrieve the token from the .env file
        try {
            BufferedReader readToken = new BufferedReader(new FileReader("Token.env"));
            token = readToken.readLine();
            readToken.close();
        } catch (Exception e) {
            // Typically goes here if file is not found
            e.printStackTrace();
            System.exit(0);
        }
        
        // Deployment for bots in less than 100 servers
        JDA jda = JDABuilder.createLight(token, EnumSet.noneOf(GatewayIntent.class)).addEventListeners(new Bot()).build();
        jda.getPresence().setActivity(Activity.playing("Codeforces"));


        // Updating the commands
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(Commands.slash("help", "Returns a list of commands"));
        commands.addCommands(Commands.slash("github", "Displays the github link for the bot code"));
        commands.addCommands(Commands.slash("invite", "Shows the invite link to add the bot to your server"));
        commands.addCommands(Commands.slash("changelog", "Shows the changelog"));
        commands.queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null)
            return;
        
        // Handles events by calling the appropriate method
        switch (event.getName()) {
            case "help" -> help(event);
            case "github" -> github(event);
            case "invite" -> invite(event);
            case "changelog" -> changelog(event);
            // Command does not exist
            default -> event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }

    public void help(SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder(); // Building an embed
        embed.setTitle("Commands"); // Sets title
        embed.setColor(embedColor); // Sets color
        String message = (
            "WIP"
        );
        embed.appendDescription(message);
        event.replyEmbeds(embed.build()).queue();
    }

    public void github(SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("GitHub Link");
        embed.setColor(embedColor);
        embed.appendDescription("https://github.com/Mimsqueeze/Daily-Codeforces :heart:");
        event.replyEmbeds(embed.build()).queue();
    }

    public void invite(SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Invite Link");
        embed.setColor(embedColor);
        embed.appendDescription("https://discord.com/api/oauth2/authorize?client_id=1067538568924381235&permissions=8&scope=bot%20applications.commands");
        event.replyEmbeds(embed.build()).queue();
    }

    public void changelog(SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Changelog");
        embed.setColor(embedColor);
        String message = (
                "WIP"
            );
        embed.appendDescription(message);
        event.replyEmbeds(embed.build()).queue();
    }
}