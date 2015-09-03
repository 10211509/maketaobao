package nobugs.team.shopping.mvp.presenter;


import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.mvp.view.MainPageView;
import nobugs.team.shopping.repo.Repository;

/**
 * Created by xiayong on 2015/8/22.
 */
public class MainPagePresenterImpl extends BasePresenter<MainPageView> implements MainPagePresenter{

    public MainPagePresenterImpl(MainPageView view){
        super(view);
    }

    @Override
    public void navigateToShop() {
        getView().showShop();
    }

    @Override
    public void navigateToOrderList() {
        getView().showOrderList();
    }

    public void initView(){
        //if the user is seller then only show the order list
        User loginer = Repository.getInstance().getLoginUser();
        getView().initView(loginer);
    }

}
