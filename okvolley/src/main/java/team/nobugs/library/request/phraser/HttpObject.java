package team.nobugs.library.request.phraser;

/**
 * Created by xiayong on 2015/8/12.
 */
public class HttpObject {
    private String data;
    private String codeMsg;
    private int code;//�涨 code=0 �ɹ� �� code != 0 ʧ��

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
