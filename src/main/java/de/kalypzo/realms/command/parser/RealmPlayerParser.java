package de.kalypzo.realms.command.parser;

import de.kalypzo.realms.player.RealmPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.bukkit.parser.OfflinePlayerParser;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RealmPlayerParser<C> implements ArgumentParser<C, RealmPlayer> {

    private final OfflinePlayerParser<C> offlinePlayerParser;

    public RealmPlayerParser() {
        this.offlinePlayerParser = new OfflinePlayerParser<>();
    }

    @Override
    public @NonNull ArgumentParseResult<@NonNull RealmPlayer> parse(@NonNull CommandContext<@NonNull C> commandContext, @NonNull CommandInput commandInput) {
        String s = commandInput.peekString(); // offline player parser will read the string.
        if (s.equalsIgnoreCase("-SERVER")) {
            commandInput.readString();
            return ArgumentParseResult.success(RealmPlayer.SERVER);
        }
        ArgumentParseResult<OfflinePlayer> parseResult = offlinePlayerParser.parse(commandContext, commandInput);
        if (parseResult.failure().isPresent()) {
            return ArgumentParseResult.failure(parseResult.failure().get());
        }
        if (parseResult.parsedValue().isPresent()) {
            return ArgumentParseResult.success(RealmPlayer.of(parseResult.parsedValue().get().getUniqueId()));
        } else {
            return ArgumentParseResult.failure(new IllegalStateException("Failed to parse RealmPlayer: No Failure or Parsed Value"));
        }
    }

    @Override
    public @NonNull SuggestionProvider<C> suggestionProvider() {
        return (context, input) -> CompletableFuture.supplyAsync(() -> {
            String realmPlayer = input.readString();
            List<Suggestion> suggestionList = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().startsWith(realmPlayer)) {
                    suggestionList.add(Suggestion.suggestion(player.getName()));
                }
            }
            suggestionList.add(Suggestion.suggestion("-SERVER"));
            return suggestionList;
        });

    }
}
