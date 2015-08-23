package nobugs.team.shopping.repo.api.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class Result<DATA> {
    @Expose
    private Integer code;
    @Expose
    private String codeMsg;
    @Expose
    private List<DATA> data = new ArrayList<>();

    public List<DATA> getData() {
        return data;
    }

    public void setData(List<DATA> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

}
