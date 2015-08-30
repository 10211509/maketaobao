package nobugs.team.shopping.repo.mapper;

import nobugs.team.shopping.mvp.model.User;
import nobugs.team.shopping.repo.model.UserPo;

/**
 * Created by wangyf on 2015/8/30 0030.
 */
public class UserMapper implements IModelMapper<User, UserPo> {

    @Override
    public UserPo fromModel(User user) {
        UserPo po = new UserPo();
        po.setId(user.getId());
        po.setUsername(user.getName());
        po.setPassword(user.getPassword());
        po.setPhone(user.getPhone());
        po.setType(mapUserType(user.getType()));
        return po;
    }

    @Override
    public User toModel(UserPo userPo) {
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

    private String mapUserType(User.Type type) {
        switch (type) {
            case BUYER:
                return "1";
            case SELLER:
                return "2";
        }
        return "1";
    }
}
