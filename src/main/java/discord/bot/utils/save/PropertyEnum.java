package discord.bot.utils.save;

public enum PropertyEnum {
    AUTOROLE("autoRole","Role set quand quelqu'un rejoint et qu'on est en raid mode", ""),
    USEREVENTCHANNEL("userEventChannel","Salon où le bot va mettre les notifs", ""),
    USEREVENTENABLED("userEventEnabled","Les messages d'accueil / départ sont activés ou non", ""),
    RAIDMODESTATUS("raidModeStatus", "Statut actuel du mode raid", "off"),
    RAIDMODEEXPIRATION("raidModeExpiration", "(Technique) Date à laquelle le raidmode expire", ""),
    PREFIX("prefix", "Prefixe des commandes pour le bot", "!");

    private String propertyName;
    private String propertyNameForUser;
    private String defaultValue;

    PropertyEnum(String propertyName, String propertyNameForUser, String defaultValue){
        this.propertyName = propertyName;
        this.propertyNameForUser = propertyNameForUser;
        this.defaultValue = defaultValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyNameForUser() {
        return propertyNameForUser;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
