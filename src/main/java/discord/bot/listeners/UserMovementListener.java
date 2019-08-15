package discord.bot.listeners;

import discord.bot.utils.misc.MessageSenderFactory;
import discord.bot.utils.save.PropertyEnum;
import discord.bot.utils.save.ServerPropertiesManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UserMovementListener extends ListenerAdapter {

    private static Logger logger = Logger.getLogger(UserMovementListener.class);
    private final String imgPath = "welcomeFond.jpg";

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        String serverId = event.getGuild().getId();
        if(("true").equals(ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(serverId,"userEventEnabled"))){
            TextChannel customizedChannel = event.getGuild().getTextChannelsByName(ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(serverId,"userEventChannel"),true).get(0);
            if(customizedChannel != null){
                messageBienvenueJoinServeur(event.getUser(),customizedChannel);
            }else {
                messageBienvenueJoinServeur(event.getUser(), event.getGuild().getDefaultChannel());
            }
        }
        if(("on").equals(ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(event.getGuild().getId(), PropertyEnum.RAIDMODESTATUS.getPropertyName()))){
            event.getGuild().getController().addRolesToMember(event.getMember(),event.getGuild().getRolesByName("Raider PU", false)).complete();
        }
        try{
            if(!ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(event.getGuild().getId(),"autoRole").isEmpty()) {
                autoRole(event.getGuild(), event.getMember());
            }
        }catch(InsufficientPermissionException e){
            logger.log(Level.ERROR, "Le bot n'a pas les permissions requises pour l'autoRole sur le serveur " + event.getGuild().getName());
        }
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        String serverId = event.getGuild().getId();
        if(("true").equals(ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(serverId,"userEventEnabled"))){
            TextChannel customizedChannel = event.getGuild().getTextChannelsByName(ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(serverId,"userEventChannel"),true).get(0);
            if(customizedChannel != null){
                messageDepartServeur(event.getUser(),customizedChannel);
            }else {
                messageDepartServeur(event.getUser(), event.getGuild().getDefaultChannel());
            }
        }
    }

    private void autoRole(Guild serveur, Member user) {
        try{
            serveur.getController().addRolesToMember(user, serveur.getRolesByName(ServerPropertiesManager.getInstance().getPropertyOrBlankFromServer(serveur.getId(),"autoRole"),true)).complete();
        }catch (Exception e){
            logger.log(Level.ERROR, "Erreur lors de l'autorole sur le serveur : " + serveur.getName());
        }
    }

    private void messageBienvenueJoinServeur(User user,TextChannel channel){
        File myPicture = new File(imgPath);
        MessageSenderFactory.getInstance().sendSafeFile(channel, myPicture);
    }
    private void messageDepartServeur(User u, TextChannel c){
        MessageSenderFactory.getInstance().sendSafeMessage(c,"See you in a better world : " + u.getName() + " [Leave] :cry:");
    }


}


