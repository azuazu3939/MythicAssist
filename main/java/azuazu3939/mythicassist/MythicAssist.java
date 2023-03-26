package azuazu3939.mythicassist;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public final class MythicAssist extends JavaPlugin {


    private static MythicAssist get;
    public MythicAssist() {get = this;}
    public static MythicAssist get() {return get;}

    AssistConfig assist = new AssistConfig(this, "example.yml");
    YamlConfiguration config = assist.getAssistConfig();
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        saveConfig();


        Objects.requireNonNull(getCommand("ma")).setExecutor(new AssistCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
