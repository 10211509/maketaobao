package nobugs.team.shopping.app.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import butterknife.ButterKnife;

/**
 * Created by xiayong on 2015/8/7.
 */
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initEvent();
        initData();
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateData();
    }

    protected void initView(){};

    protected void initEvent(){};

    protected void initData(){};

    protected void updateData(){};

}
