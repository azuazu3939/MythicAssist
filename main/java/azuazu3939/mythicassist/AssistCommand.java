package azuazu3939.mythicassist;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AssistCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        try {
            if (sender instanceof Player player) {

                if (!player.hasPermission("MythicAssist.command.ma")) {
                    player.sendMessage(ChatColor.RED + "権限がありません。");
                } else {

                    if (reload(strings[0])) {
                        player.sendMessage(ChatColor.GREEN + "コンフィグを保存しました。");
                        return true;
                    }
                    if (create(strings[0], strings[1])) {
                        player.sendMessage(ChatColor.GREEN + "新しく空のファイルを作成しました。");
                        return true;
                    }
                    if (debug(strings[0], strings[1])) {
                        player.sendMessage(ChatColor.GREEN + "成功。");
                        return true;
                    }
                }
            } else {

                if (reload(strings[0])) {
                    sender.sendMessage(ChatColor.GREEN + "コンフィグを保存しました。");
                    return true;
                }
                if (create(strings[0], strings[1])) {
                    sender.sendMessage(ChatColor.GREEN + "新しく空のファイルを作成しました。");
                    return true;
                }
                if (debug(strings[0], strings[1])) {
                    sender.sendMessage(ChatColor.GREEN + "成功。");
                    return true;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "エラーが発生しました。");
            return true;
        }
    }

    public boolean reload(@NotNull String arg) {

        if (arg.equalsIgnoreCase("reload")) {
            MythicAssist.get().saveConfig();
            MythicAssist.get().saveDefaultConfig();
            MythicAssist.get().reloadConfig();
            return true;
        }
        return false;
    }

    public boolean create(@NotNull String arg, String name) {

        if (arg.equalsIgnoreCase("create")) {
            new AssistConfig(MythicAssist.get(), name + ".yml");
            return true;
        }
        return false;
    }

    public boolean debug(@NotNull String arg, String name) {

        if (arg.equalsIgnoreCase("debug")) {

            File file = new File(name);
            if (file.exists()) {
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
                return yml.getBoolean("Test");
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        try {
            if (sender instanceof Player player && player.hasPermission("MythicAssist.command.ma")) {

                if (strings.length == 1) {
                    List<String> list = new ArrayList<>();
                    list.add("reload");
                    list.add("create");
                    list.add("debug");
                    return list;
                }

                if (strings[0].equalsIgnoreCase("debug") && strings.length == 2) {

                    List<String> list = new ArrayList<>();
                    for (File file: Arrays.stream(Objects.requireNonNull(MythicAssist.get().getDataFolder().listFiles())).toList()) {

                        list.add(String.valueOf(file));
                    }
                    return list;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
