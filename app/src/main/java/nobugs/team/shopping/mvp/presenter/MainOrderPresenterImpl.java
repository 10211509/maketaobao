package nobugs.team.shopping.mvp.presenter;

import nobugs.team.shopping.mvp.model.Order;
import nobugs.team.shopping.mvp.view.MainOrderView;

/**
 * Created by xiayong on 2015/8/22.
 */
public class MainOrderPresenterImpl extends BasePresenter<MainOrderView> implements MainOrderPresenter {

    public MainOrderPresenterImpl(MainOrderView view){
        super(view);
    }

    @Override
    public void showOrderInprogess() {

    }

    @Override
    public void showOrderFinished() {

    }

    @Override
    public void onOrderSelected(Order order) {

    }
}
