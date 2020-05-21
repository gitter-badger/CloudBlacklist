package top.mc114.cloudb;

import com.google.gson.Gson;
import net.mamoe.mirai.console.command.BlockingCommand;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.JCommandManager;
import net.mamoe.mirai.console.plugins.Config;
import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Main extends PluginBase {
    public static Config config;
    public static List<Long> list;
    public void startLoad() {
        String json = Parsing.getPageSource("https://mc114.top/list.json", "UTF-8");
        Get get = new Gson().fromJson(json, Get.class);
        //System.out.println(json);
        //System.out.println(get.black);
        config = loadConfig("blacklist.yml");
        config.set("ban", get.black);
        config.save();
        list = config.getLongList("ban");
    }

    public void onLoad() {
        startLoad();
    }

    public void onEnable() {
        this.getEventListener().subscribeAlways(GroupMessageEvent.class, new Message());
        JCommandManager.getInstance().register(this, new BlockingCommand(
                "cbreload", new ArrayList<>(),"重载云黑名单列表","/cbreload"
        ) {
            @Override
            public boolean onCommandBlocking(@NotNull CommandSender commandSender, @NotNull List<String> list) {
                startLoad();
                commandSender.sendMessageBlocking("重载成功");
                return true;
            }
        });
    }

}