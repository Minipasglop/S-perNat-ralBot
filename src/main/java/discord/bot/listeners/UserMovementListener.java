package discord.bot.listeners;

import discord.bot.utils.misc.MessageSenderFactory;
import discord.bot.utils.save.PropertyEnum;
import discord.bot.utils.save.ServerPropertiesManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class UserMovementListener extends ListenerAdapter {

    private static Logger logger = Logger.getLogger(UserMovementListener.class);

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
        MessageSenderFactory.getInstance().sendSafeMessage(channel,":punch: Welcome : " + user.getAsMention() + " [Join]");
    }
    private void messageDepartServeur(User u, TextChannel c){
        MessageSenderFactory.getInstance().sendSafeMessage(c,"See you in a better world : " + u.getName() + " [Leave] :cry:");
    }


}

