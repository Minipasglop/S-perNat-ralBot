package discord.bot.command.server.managing;

import discord.bot.command.ICommand;
import discord.bot.utils.misc.MessageSenderFactory;
import discord.bot.utils.save.ServerPropertiesManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetUserEventStatusCommand extends ICommand {

    private final String HELP = "Active / Desactive les messages d'accueil. \nUsage : `!"+ this.commandName +" (on | off)`";
    private final String COMMAND_SUCCESS_ON = "Yes mon gars maintenant je vais envoyer des messages quand ya du mouvement.";
    private final String COMMAND_SUCCESS_OFF = "Rip j'ai plus le droit de spammer... Tant pis je vous tiens plus au jus de qui fait quoi :eyes:";
    private final String COMMAND_FAILED = "Wouaish ca marche pas... Préviens Vigo stp !";

    public SetUserEventStatusCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length != 0 && args[0].equals("help") || args.length < 1) return false;
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
            if(("on").equals(args[args.length -1])){
                MessageSenderFactory.getInstance().sendSafeMessage(event.getTextChannel(), COMMAND_SUCCESS_ON);
                ServerPropertiesManager.getInstance().setPropertyForServer(event.getGuild().getId(), "userEventEnabled", "true");
            }
            else if(("off").equals(args[args.length -1])){
                MessageSenderFactory.getInstance().sendSafeMessage(event.getTextChannel(), COMMAND_SUCCESS_OFF);
                ServerPropertiesManager.getInstance().setPropertyForServer(event.getGuild().getId(), "userEventEnabled", "false");
            }
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