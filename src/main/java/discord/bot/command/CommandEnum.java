package discord.bot.command;

public enum CommandEnum {
    USER_EVENT_CHANNEL("salonAnnonce"),
    USER_EVENT_TOGGLING("annonce"),
    RAID_MODE("raid"),
    COOL_ZORK("cool"),
    HELP("aled"),
    INFO("info"),
    SERVER_SETTINGS("serverconf"),
    PING("ping"),
    CAT_PICTURE("chat"),
    DOG_PICTURE("chien"),
    SET_BOT_GAME("setGame"),
    FORCE_PROPERTIES_SAVING("saveProperties"),
    SET_PREFIX("prefixe"),
    WEBSITE("siteWebPubVigoLol");

    private String commandName;

    CommandEnum(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName(){
        return this.commandName;
    }
}
