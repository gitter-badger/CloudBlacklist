package top.mc114.cloudb;

import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageUtils;
import org.jsoup.internal.StringUtil;

import java.util.List;
import java.util.function.Consumer;

public class Message implements Consumer<GroupMessageEvent> {
    @Override
    public void accept(GroupMessageEvent event) {
        List<Long> list = Main.list;
        if(event.getGroup().getBotPermission().getLevel()>0||event.getSender().getPermission().getLevel()==0) {
            Long senderid = event.getSender().getId();
            if(list.contains(senderid)) {
                event.getBot().recall(event.getMessage());
                event.getSender().mute(114514);
                event.getGroup().sendMessage(MessageUtils.newChain("云黑名单列表中包含")
                        .plus(new At(event.getSender()))
                        .plus("["+senderid+"]，已将其处理，原因：https://github.com/ShrBox/CoreBlack-list/blob/master/reasons/"+senderid+".md"));
                return;
            }
        }
        String message = event.getMessage().contentToString();
        if(message.contains("!qb")) {
            String account = message.replace("!qb", "").trim();
            if (!StringUtil.isNumeric(account)) {
                event.getGroup().sendMessage(MessageUtils.newChain(new At(event.getSender()))
                        .plus("Core云黑名单目前只支持查询QQ号"));
                return;
            }
            if (list.contains(Long.parseLong(account))) {
                event.getGroup().sendMessage(MessageUtils.newChain(new At(event.getSender()))
                        .plus("云黑名单列表中包含" + "[" + account + "]，详细请看：https://github.com/ShrBox/CoreBlack-list/blob/master/reasons/" + account + ".md"));
            } else {
                event.getGroup().sendMessage(MessageUtils.newChain(new At(event.getSender()))
                        .plus("未在云黑名单列表中找到"));
            }
        }
    }
}
