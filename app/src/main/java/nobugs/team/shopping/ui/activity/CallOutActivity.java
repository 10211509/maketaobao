package nobugs.team.shopping.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.hisun.phone.core.voice.Device;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.db.entity.User;
import nobugs.team.shopping.utils.CCPHelper;
/**
 * launch a call action
 */
public class CallOutActivity extends BaseActivity {


    @Bind(R.id.btn_hangup)
    Button btnHangup;//button to hang up the call

    // call ID
    private String mCurrentCallId;

    private User mSeller;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_call_out);
        EventBus.getDefault().register(this);//register the eventbus
    }

    private void makeCall() {
        if (CCPHelper.getInstance().checkDevice()) {
            //发起一个通话，通过mCurrentCallId来主动挂断通话
            mCurrentCallId = CCPHelper.getInstance().getDevice().makeCall(Device.CallType.VIDEO, mSeller.getVoipAccount());
        }
    }

    @OnClick(R.id.btn_hangup)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_hangup:
                releaseCall();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void releaseCall(){
        if(!TextUtils.isEmpty(mCurrentCallId) && CCPHelper.getInstance().checkDevice()){
            CCPHelper.getInstance().getDevice().releaseCall(mCurrentCallId);
        }
    }

    public void onEventMainThread(User user){
        this.mSeller = user;
    }

}
