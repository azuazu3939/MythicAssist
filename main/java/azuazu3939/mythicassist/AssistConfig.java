package azuazu3939.mythicassist;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class AssistConfig {

    File assistFile;
    String path;
    MythicAssist assist;

    public AssistConfig(@NotNull MythicAssist assist, String path) {

        this.path = path;
        this.assist = assist;

        try {
            if (!assist.getDataFolder().exists()) assist.getDataFolder().getParentFile().mkdir();
            File file = new File(assist.getDataFolder(), this.path);
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();

            assistFile = file;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Contract(" -> new")
    public @NotNull YamlConfiguration getAssistConfig() {
        return YamlConfiguration.loadConfiguration(assistFile);
    }
}
