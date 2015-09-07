package nobugs.team.shopping.repo.mapper;

import java.util.ArrayList;
import java.util.List;

import nobugs.team.shopping.mvp.model.ProductType;
import nobugs.team.shopping.repo.api.entity.TypeListResult;
import nobugs.team.shopping.repo.api.entity.UnitListResult;
import nobugs.team.shopping.repo.entity.TypePo;
import nobugs.team.shopping.repo.entity.UnitPo;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class UnitListMapper implements IResultMapper<UnitListResult, List<String>> {

    @Override
    public List<String> map(UnitListResult from) {
        List<String> unitList = new ArrayList<>();
        for (UnitPo typePo : from.getData()) {
            unitList.add(typePo.getUnitName());
        }
        return unitList;
    }
}
