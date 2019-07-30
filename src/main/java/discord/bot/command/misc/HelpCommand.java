package discord.bot.command.misc;

import discord.bot.command.ICommand;
import discord.bot.utils.commands.CommandHandler;
import discord.bot.utils.misc.MessageSenderFactory;
import discord.bot.utils.misc.SharedStringEnum;
import discord.bot.utils.save.PropertyEnum;
import discord.bot.utils.save.ServerPropertiesManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Map;

public class HelpCommand extends ICommand {

    private String HELP = "La commande `aled` Affiche toutes les commandes dispos. \nUsage: `!" + this.commandName + "`";
    private String MESSAGE_HEADER = "Azy' frérot voilà les commandes que je comprend : \n";
    private String MESSAGE_FOOTER ="\nSi t'as besoin d'aide, demande à Vigo :smirk:";

    public HelpCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length != 0) {
            return false;
        } else return true;
    }

    private String getFormattedStringFromCommandMap(Map<String, ICommand> commandMap){
        String commandList = "";
        Object[] commandArray = commandMap.keySet().toArray();
        for(int i = 0; i < commandArray.length; i++) {
            String command = commandArray[i].toString();
            commandList +=  "`" + command + "`" + "\t";
        }
        return commandList;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String wowCommandsList = getFormattedStringFromCommandMap(CommandHandler.getWowCommands());
        String serverCommandsList = getFormattedStringFromCommandMap(CommandHandler.getServerCommands());
        String miscCommandsList = getFormattedStringFromCommandMap(CommandHandler.getMiscCommands());
        String ownerCommandsList = getFormattedStringFromCommandMap(CommandHandler.getOwnerCommands());
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName());
        builder.setColor(Color.ORANGE);
        builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        builder.addField("Aide :bulb: ", MESSAGE_HEADER , true);
        builder.addField("Commandes liées à WOW :keyboard:", wowCommandsList + "\n", true);
        builder.addField("Commandes liées au serveur :desktop:", serverCommandsList + "\n", true);
        builder.addField("Commandes random :keyboard:", miscCommandsList + "\n", true);
        builder.addField("Commandes à Vigo :warning:", ownerCommandsList + "\n", true);
        builder.addBlankField(true);
        builder.addField("Plus d'infos :file_folder:", MESSAGE_FOOTER,true);
        MessageSenderFactory.getInstance().sendSafePrivateMessage(event.getAuthor(), builder.build(), event.getTextChannel(), SharedStringEnum.BROKEN.getSharedString());
    }

    @Override
    public String help() {
        return HELP;
    }

}