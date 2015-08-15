package team.nobugs.library.request.phraser;

import com.google.gson.JsonElement;

import org.json.JSONArray;

/**
 * Created by xiayong on 2015/8/12.
 */
public class HttpObject {
//    private String data;
    private JsonElement data;
    private String codeMsg;
    private int code;

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccessful(){
        return this.code == 0;
    }
}
