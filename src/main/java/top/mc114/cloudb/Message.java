package top.mc114.cloudb;

import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageUtils;

import java.util.List;
import java.util.function.Consumer;

public class Message implements Consumer<GroupMessageEvent> {
    @Override
    public void accept(GroupMessageEvent event) {
        if(event.getGroup().getBotPermission().getLevel()==0||event.getSender().getPermission().getLevel()>0) {
            return;
        }
        List<Long> list = Main.list;
        Long senderid = event.getSender().getId();
        //event.getGroup().sendMessage("接受消息来自："+senderid+"\n列表："+list);
        if(list.contains(senderid)) {
            event.getBot().recall(event.getMessage());
            event.getSender().mute(114514);
            event.getGroup().sendMessage(MessageUtils.newChain("云黑名单列表中包含")
                    .plus(new At(event.getSender()))
                    .plus("["+senderid+"]，已将其处理，原因：https://github.com/ShrBox/CoreBlack-list/blob/master/reasons/"+senderid+".md"));
        }
    }
}
