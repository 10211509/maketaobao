package nobugs.team.shopping.repo.mapper;

import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.api.entity.LoginResult;
import nobugs.team.shopping.repo.entity.UserPo;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class LoginMapper implements IResultMapper<LoginResult, User> {
    public UserMapper modelMapper = new UserMapper();

    @Override
    public User map(LoginResult loginResult) {
        User user = null;
        for (UserPo userPo : loginResult.getData()) {
            user = modelMapper.toModel(userPo);
            if (user != null) {
                return user;
            }
        }
        return user;
    }

}
