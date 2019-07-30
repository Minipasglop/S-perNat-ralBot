package discord.bot.command.bot.managing;

import discord.bot.BotGlobalManager;
import discord.bot.command.ICommand;
import discord.bot.utils.misc.MessageSenderFactory;
import discord.bot.utils.misc.SharedStringEnum;
import discord.bot.utils.save.PropertyEnum;
import discord.bot.utils.save.ServerPropertiesManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetPrefixCommand extends ICommand {

    private final String HELP = "Permet de paramétrer le prefixe du bot avant les commandes. \nUsage: `!" + this.commandName + " prefixe`";
    private final String COMMAND_SUCCESS = "J'ai réussi à modifier le préfixe.";

    public SetPrefixCommand(String commandName) {
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
        if(event.getAuthor().getId().equals(BotGlobalManager.getConfig().getBotOwnerUserId())) {
            ServerPropertiesManager.getInstance().setPropertyForServer(event.getGuild().getId(), PropertyEnum.PREFIX.getPropertyName(), args[0]);
            MessageSenderFactory.getInstance().sendSafeMessage(event.getTextChannel(), COMMAND_SUCCESS);
        }else {
            event.getMessage().delete().queue();
            MessageSenderFactory.getInstance().sendSafePrivateMessage(event.getAuthor(), SharedStringEnum.BOT_OWNER_ONLY.getSharedString(), event.getTextChannel(), SharedStringEnum.BOT_OWNER_ONLY.getSharedString());
        }
    }

    @Override
    public String help() {
        return HELP;
    }

}
