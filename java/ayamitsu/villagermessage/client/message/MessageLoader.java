package ayamitsu.villagermessage.client.message;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ayamitsu0321 on 2016/03/16.
 */
public final class MessageLoader {


    private static MessageList currentList;


    private static String getMessageDirectory() {
        return "assets/villagermessage/text/";
    }

    /**
     * call on ClientProxy#init , LanguageReloadListener#onResourceManagerReload
     *
     * @param lang like "en_US", "ja_JP", etc. ...
     */
    public static void loadMessage(String lang) throws IOException {
        try {
            load(lang);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            load("en_US");
        }
    }

    /**
     * call on RenderMessageEvent
     */
    public static MessageList getCurrentList() {
        return currentList;
    }

    private static void load(String lang) throws IOException {
        MessageList list = null;

        try (InputStream is = MessageLoader.class.getClassLoader().getResourceAsStream(getMessageDirectory() + lang + ".txt")) {
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            JsonReader jr = new JsonReader(isr);
            Gson gson = new Gson();
            list = gson.fromJson(jr, MessageList.class);
        }

        if (list != null) {
            currentList = list;
        }

    }

}
