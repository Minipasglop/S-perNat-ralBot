package discord.bot.command.misc;

import discord.bot.command.ICommand;
import discord.bot.utils.misc.DogApiCall;
import discord.bot.utils.misc.MessageSenderFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DogCommand extends ICommand {

    private final String HELP = "Affiche une photo cool de :dog: \nUsage : `!" + this.commandName + "`";
    private final String FAILED = "J'ai pas réussi à charger une photo de :dog: :sweat: ";

    public DogCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length != 0) {
            return false;
        } else return true;    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String imageUri = DogApiCall.getInstance().getRandomDogImage();
        if(imageUri != null && !imageUri.isEmpty()){
            MessageSenderFactory.getInstance().sendSafeMessage(event.getTextChannel(),imageUri);
        }else {
            MessageSenderFactory.getInstance().sendSafeMessage(event.getTextChannel(),FAILED);
        }
    }

    @Override
    public String help() {
        return HELP;
    }

}
