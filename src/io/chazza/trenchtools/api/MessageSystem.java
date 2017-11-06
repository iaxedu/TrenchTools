package io.chazza.trenchtools.api;

import io.chazza.trenchtools.util.ColorUtil;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Chazmondo
 */
public class MessageSystem {

    private String id;
    private String content;

    public MessageSystem(String id){
        this.id = id;
        this.content = TrenchToolAPI.getCore().getConfig().getString("message."+id);
    }

    public void show(CommandSender cs, HashMap<String, String> replace){
        if(content != null && !content.isEmpty()){
            String msgContent = ColorUtil.translate(content);

            Iterator it = replace.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                msgContent = msgContent.replace(pair.getKey().toString(), pair.getValue().toString());
            }


            cs.sendMessage(msgContent);
        }
    }

    public String get(HashMap<String, String> replace){
        if(content != null && !content.isEmpty()){
            String msgContent = ColorUtil.translate(content);

            Iterator it = replace.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                msgContent = msgContent.replace(pair.getKey().toString(), pair.getValue().toString());
            }
           return msgContent;
        }
        return null;
    }
}
