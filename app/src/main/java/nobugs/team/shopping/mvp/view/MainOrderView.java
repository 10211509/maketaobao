package nobugs.team.shopping.mvp.view;

import java.util.List;

import nobugs.team.shopping.repo.db.entity.OrderPo;

/**
 * Created by xiayong on 2015/8/22.
 */
public interface MainOrderView extends IView  {
    void navigateToOrderDetailsActivity(OrderPo orderPo);

    void showOrdersInProgress(List<OrderPo> orderPoList);

    void showOrderFinished(List<OrderPo> orderPoList);

}
