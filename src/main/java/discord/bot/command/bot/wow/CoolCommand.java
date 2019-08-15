package discord.bot.command.bot.wow;

import discord.bot.command.ICommand;
import discord.bot.utils.misc.MessageSenderFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public class CoolCommand extends ICommand {

    private final String HELP = "You gotta be cool man ! \nUsage: `!" + this.commandName;
    private final String COOL_IMG_URI = "https://media.discordapp.net/attachments/371741365496971265/606469298004951050/image0.jpg?width=349&height=465";

    public CoolCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length != 0 && args[0].equals("help") || args.length > 0) {
            return false;
        } else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        MessageSenderFactory.getInstance().sendSafeMessage(event.getTextChannel(), COOL_IMG_URI);
    }

    @Override
    public String help() {
        return HELP;
    }

}
