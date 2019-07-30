package discord.bot.command.server.managing;

import discord.bot.command.ICommand;
import discord.bot.utils.misc.MessageSenderFactory;
import discord.bot.utils.save.ServerPropertiesManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetUserEventChannelCommand extends ICommand {

    private final String HELP = "Définis le salon dans lequel tu veux envoyer les messages d'arrivée / départ. \nUsage : `!"+ this.commandName +" nom du salon`";
    private final String COMMAND_SUCCESS = "C'est bon je sais où spammer maintenant.\nFais gaffe d'avoir le `!greetingsmessage on` pour que ça fonctionne.";
    private final String COMMAND_FAILED = "Hééé sa marsh pa, apail Vigo";

    public SetUserEventChannelCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length != 0 && args[0].equals("help") || args.length < 1) return false;
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!event.getGuild().getTextChannelsByName(args[args.length - 1],true).isEmpty()) {
            ServerPropertiesManager.getInstance().setPropertyForServer(event.getGuild().getId(), "userEventChannel", args[args.length - 1]);
            MessageSenderFactory.getInstance().sendSafeMessage(event.getTextChannel(), COMMAND_SUCCESS);
        } else {
            event.getMessage().delete().queue();
            MessageSenderFactory.getInstance().sendSafePrivateMessage(event.getAuthor(), COMMAND_FAILED, event.getTextChannel(), COMMAND_FAILED);
        }
    }

    @Override
    public String help() {
        return HELP;
    }
}