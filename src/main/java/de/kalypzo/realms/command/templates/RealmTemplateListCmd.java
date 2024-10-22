package de.kalypzo.realms.command.templates;

import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

public class RealmTemplateListCmd {


    @Command("realm|realms templates list")
    @CommandDescription("List all available realm templates")
    @Permission("realms.templates.list")
    public void listTemplates(CommandSender commandSender) {

    }


}
