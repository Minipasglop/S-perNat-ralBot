package discord.bot.command.bot.wow;

import discord.bot.command.ICommand;
import discord.bot.utils.misc.MessageSenderFactory;
import discord.bot.utils.misc.SharedStringEnum;
import discord.bot.utils.save.ServerPropertiesManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RaidCommand extends ICommand {

    private final String HELP = "Permet de passer en mode RAID pour une durée donnée. \nUsage: `!" + this.commandName + " 2h30`";
    private final String COMMAND_SUCCESS = "C'est pris en compte frr, bon raid !.";
    private final String WRONG_TYPE = "GUUUUH j'ai pas compris :cold_sweat: ! Va insulter Vigo qui sait pas coder...";

    public RaidCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length != 0 && args[0].equals("help") || args.length < 1) {
            return false;
        } else return true;
    }

    private String parseDureeEnArgument(String duree){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(duree.substring(0, duree.indexOf('h'))));
        calendar.add(Calendar.MINUTE, Integer.parseInt(duree.substring(duree.indexOf('h') + 1)));
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR) || event.getMember().getRoles().contains(event.getGuild().getRoleById("595362808053628934"))) {
            String dateExpiration = this.parseDureeEnArgument(args[0]);
            ServerPropertiesManager.getInstance().setPropertyForServer(event.getGuild().getId(),"raidModeStatus", "on");
            ServerPropertiesManager.getInstance().setPropertyForServer(event.getGuild().getId(),"raidModeExpiration", dateExpiration);
            MessageSenderFactory.getInstance().sendSafePrivateMessage(event.getAuthor(), COMMAND_SUCCESS, event.getTextChannel(), COMMAND_SUCCESS);
        }else {
            event.getMessage().delete().queue();
            MessageSenderFactory.getInstance().sendSafePrivateMessage(event.getAuthor(), SharedStringEnum.BOT_OWNER_ONLY.getSharedString(), event.getTextChannel(), SharedStringEnum.NOT_ALLOWED.getSharedString());
        }
    }

    @Override
    public String help() {
        return HELP;
    }

}
