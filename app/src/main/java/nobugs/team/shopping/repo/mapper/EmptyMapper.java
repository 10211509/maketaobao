package nobugs.team.shopping.repo.mapper;

import nobugs.team.shopping.repo.api.entity.EmptyResult;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class EmptyMapper implements IResultMapper<EmptyResult, Integer> {
    public UserMapper modelMapper = new UserMapper();

    @Override
    public Integer map(EmptyResult result) {
        return result.getCode();
    }

}
