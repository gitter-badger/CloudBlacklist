package top.mc114.cloudb;

import com.google.gson.Gson;
import net.mamoe.mirai.console.plugins.Config;
import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.message.GroupMessageEvent;


class Main extends PluginBase {
    public static Config config;
    public void onLoad() {
        String json = Parsing.getPageSource("https://shrbox.github.io/CoreBlack-list/list.json", "UTF-8");
        Gson njson = new Gson();
        Get get = njson.fromJson(json, Get.class);
        System.out.println(json);
        System.out.println(get.black);
        config = loadConfig("blacklist.yml");
        config.set("ban", get.black);
        config.save();
    }

    public void onEnable() {
        this.getEventListener().subscribeAlways(GroupMessageEvent.class, new Message());
    }

}