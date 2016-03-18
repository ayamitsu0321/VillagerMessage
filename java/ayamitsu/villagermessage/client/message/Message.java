package ayamitsu.villagermessage.client.message;

/**
 * Created by ayamitsu0321 on 2016/03/16.
 */
public class Message {

    public String text = "";
    public boolean vertical = false;

    @Override
    public String toString() {
        return "text: " + text + ", vertical: " + vertical;
    }

}
