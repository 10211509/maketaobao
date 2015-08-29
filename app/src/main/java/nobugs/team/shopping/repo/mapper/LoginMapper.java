package nobugs.team.shopping.repo.mapper;

import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.api.model.LoginResult;
import nobugs.team.shopping.repo.model.UserPo;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public class LoginMapper implements Mapper<LoginResult, User> {

    @Override
    public User map(LoginResult loginResult) {
        User user = null;
        for (UserPo userPo : loginResult.getData()) {
            user = mapUser(userPo);
            if (user != null) {
                return user;
            }
        }
        return user;
    }

    public User mapUser(UserPo userPo) {
        User user = new User();
        user.setId(userPo.getId());
        user.setName(userPo.getUsername());
        user.setPassword(userPo.getPassword());
        user.setPhone(userPo.getPhone());
        user.setType(mapUserType(userPo.getType()));
        return user;
    }

    private User.Type mapUserType(String type) {
        switch (type) {
            case "1":
                return User.Type.BUYER;
            case "2":
                return User.Type.SELLER;
        }
        return User.Type.BUYER;
    }


}
