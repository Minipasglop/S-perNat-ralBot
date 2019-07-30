package discord.bot.utils.wow;

import discord.bot.BotGlobalManager;
import discord.bot.utils.save.PropertyEnum;
import discord.bot.utils.save.ServerPropertiesManager;
import net.dv8tion.jda.core.entities.Guild;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RaidModeThread extends Thread {

    public void run() {
        Timer timer = new Timer ();
        TimerTask autoRaidModeTask = new TimerTask () {
            @Override
            public void run () {
                List<Guild> guildList = BotGlobalManager.getServers();
                for (Guild guild : guildList) {
                    if (("on").equals(ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(guild.getId(), PropertyEnum.RAIDMODESTATUS.getPropertyName()))) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date dateExpiration = null;
                        try {
                            dateExpiration = format.parse((ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(guild.getId(), PropertyEnum.RAIDMODEEXPIRATION.getPropertyName())));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(Objects.requireNonNull(dateExpiration).getTime() < new Date().getTime()){
                            ServerPropertiesManager.getInstance().setPropertyForServer(guild.getId(), "raidModeStatus","off");
                        }
                    }
                    System.runFinalization();
                    System.gc();
                }
            }
        };
        timer.scheduleAtFixedRate(autoRaidModeTask, 1000*60, 1000*60);
    }
}