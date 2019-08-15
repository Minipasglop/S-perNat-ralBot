package discord.bot.command.bot.wow;

import discord.bot.command.ICommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NethsCommand extends ICommand {

    private final String HELP = "Permet d'afficher une citation du grand Homme-tagère. \nUsage: `!" + this.commandName;
    private final String WRONG_TYPE = "GUUUUH j'ai pas compris :cold_sweat: ! Va insulter Vigo qui sait pas coder...";

    public NethsCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length != 0 && args[0].equals("help") || args.length < 1) {
            return false;
        } else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return HELP;
    }

}
