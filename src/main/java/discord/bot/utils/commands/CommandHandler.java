package discord.bot.utils.commands;

import discord.bot.command.CommandEnum;
import discord.bot.command.ICommand;
import discord.bot.command.bot.info.InfoCommand;
import discord.bot.command.bot.info.ServerSettingsCommand;
import discord.bot.command.bot.info.WebsiteCommand;
import discord.bot.command.bot.managing.ForcePropertiesSaveCommand;
import discord.bot.command.bot.managing.SetGameCommand;
import discord.bot.command.bot.managing.SetPrefixCommand;
import discord.bot.command.bot.wow.CoolCommand;
import discord.bot.command.bot.wow.RaidCommand;
import discord.bot.command.misc.*;
import discord.bot.command.server.managing.*;
import discord.bot.utils.save.PropertyEnum;
import discord.bot.utils.save.ServerPropertiesManager;

import java.util.HashMap;
import java.util.Map;

//mechanics from https://github.com/thibautbessone

public class CommandHandler {

    private static Map<String, ICommand> wowCommands;
    private static Map<String, ICommand> serverCommands;
    private static Map<String, ICommand> miscCommands;
    private static Map<String, ICommand> ownerCommands;

    private static CommandHandler instance;

    public static CommandHandler getInstance(){
        if(instance == null){
            instance = new CommandHandler();
        }
        return instance;
    }

    public void handleCommand(String guildID, ChatCommandParser.CommandAttributes cmdAttributes) {
        if(cmdAttributes.raw.startsWith(ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(guildID, PropertyEnum.PREFIX.getPropertyName()))) {
            Map<String, ICommand> localMap = new HashMap<>();
            if(wowCommands.containsKey(cmdAttributes.invoke)) localMap = wowCommands;
            if(serverCommands.containsKey(cmdAttributes.invoke)) localMap = serverCommands;
            if(miscCommands.containsKey(cmdAttributes.invoke)) localMap = miscCommands;
            if(ownerCommands.containsKey(cmdAttributes.invoke)) localMap = ownerCommands;
            if(!localMap.isEmpty()) {
                boolean safe = localMap.get(cmdAttributes.invoke).called(cmdAttributes.args, cmdAttributes.event);
                if (safe) {
                    localMap.get(cmdAttributes.invoke).action(cmdAttributes.args, cmdAttributes.event);
                    localMap.get(cmdAttributes.invoke).executed(safe, cmdAttributes.event);
                } else {
                    localMap.get(cmdAttributes.invoke).executed(safe, cmdAttributes.event);
                }
            }
        }
    }

    private CommandHandler(){

        wowCommands = new HashMap<>();
        wowCommands.put(CommandEnum.RAID_MODE.getCommandName(), new RaidCommand(CommandEnum.RAID_MODE.getCommandName()));
        wowCommands.put(CommandEnum.COOL_ZORK.getCommandName(), new CoolCommand(CommandEnum.COOL_ZORK.getCommandName()));

        serverCommands = new HashMap<>();
        serverCommands.put(CommandEnum.USER_EVENT_CHANNEL.getCommandName(), new SetUserEventChannelCommand(CommandEnum.USER_EVENT_CHANNEL.getCommandName()));
        serverCommands.put(CommandEnum.USER_EVENT_TOGGLING.getCommandName(), new SetUserEventStatusCommand(CommandEnum.USER_EVENT_TOGGLING.getCommandName()));
        serverCommands.put(CommandEnum.HELP.getCommandName(), new HelpCommand(CommandEnum.HELP.getCommandName()));
        serverCommands.put(CommandEnum.SET_PREFIX.getCommandName(), new SetPrefixCommand(CommandEnum.SET_PREFIX.getCommandName()));

        miscCommands = new HashMap<>();
        miscCommands.put(CommandEnum.INFO.getCommandName(), new InfoCommand(CommandEnum.INFO.getCommandName()));
        miscCommands.put(CommandEnum.WEBSITE.getCommandName(), new WebsiteCommand(CommandEnum.WEBSITE.getCommandName()));
        miscCommands.put(CommandEnum.SERVER_SETTINGS.getCommandName(), new ServerSettingsCommand(CommandEnum.SERVER_SETTINGS.getCommandName()));
        miscCommands.put(CommandEnum.PING.getCommandName(), new PingCommand(CommandEnum.PING.getCommandName()));
        miscCommands.put(CommandEnum.CAT_PICTURE.getCommandName(), new CatCommand(CommandEnum.CAT_PICTURE.getCommandName()));
        miscCommands.put(CommandEnum.DOG_PICTURE.getCommandName(), new DogCommand(CommandEnum.DOG_PICTURE.getCommandName()));

        ownerCommands = new HashMap<>();
        ownerCommands.put(CommandEnum.SET_BOT_GAME.getCommandName(), new SetGameCommand(CommandEnum.SET_BOT_GAME.getCommandName()));
        ownerCommands.put(CommandEnum.FORCE_PROPERTIES_SAVING.getCommandName(), new ForcePropertiesSaveCommand(CommandEnum.FORCE_PROPERTIES_SAVING.getCommandName()));
    }

    public static Map<String, ICommand> getWowCommands() {
        return wowCommands;
    }

    public static Map<String, ICommand> getServerCommands() {
        return serverCommands;
    }

    public static Map<String, ICommand> getMiscCommands() {
        return miscCommands;
    }

    public static Map<String, ICommand> getOwnerCommands() {
        return ownerCommands;
    }
}
