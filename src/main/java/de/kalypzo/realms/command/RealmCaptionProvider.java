package de.kalypzo.realms.command;

import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionProvider;

public class RealmCaptionProvider implements CaptionProvider<CommandSender> {
    @Override
    public @Nullable String provide(@NonNull Caption caption, @NonNull CommandSender recipient) {
        return null;
    }
}
