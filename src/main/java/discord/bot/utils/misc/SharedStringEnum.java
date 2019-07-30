package discord.bot.utils.misc;

public enum SharedStringEnum {

    NOT_ALLOWED("Tu n'as pas le droit de faire ça ! Check tes permissions ou paye un kebab a vigo !"),
    NO_PRIVATE_CHANNEL_OPEN("Je peux pas envoyer de message privé... File ton 06 pour voir :smirk:"),
    BOT_OWNER_ONLY("Ya que Vigo qui peut le faire déso ! Comme quoi c'est cool d'avoir une transmo moche :p"),
    BROKEN("C KC"),
    MISSING_PERMISSIONS("Le bot n'a pas les droits de faire ce que tu lui demande gros ! Chill un coup et préviens Vigo");

    private String sharedString;

    SharedStringEnum(String sharedString) {
        this.sharedString = sharedString;
    }

    public String getSharedString() {
        return this.sharedString;
    }
}
