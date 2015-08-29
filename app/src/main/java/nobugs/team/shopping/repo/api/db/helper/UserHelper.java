package nobugs.team.shopping.repo.api.db.helper;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import nobugs.team.shopping.repo.api.db.dao.UserDao;
import nobugs.team.shopping.repo.model.UserPo;

/**
 * Created by xiayong on 2015/8/27.
 */
public class UserHelper extends DaoHelper<UserPo>{


    /*private static UserHelper mInstance;

    public static UserHelper getInstance(@NonNull Context contex){
        if(mInstance == null){
            mInstance = new UserHelper(contex);
        }
        return new UserHelper(contex);
    }*/
//    public UserHelper(Context contex){
//        initMembers(contex);
//    }

    public UserHelper() {
        super();
    }

    @Override
    protected AbstractDao getDao() {
        return daoSession.getUserDao();
    }

    @Override
    public void insert(UserPo userPo) {
        dao.insert(userPo);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public List<UserPo> loadAll() {
       return dao.loadAll();
    }

    @Override
    public void delete(UserPo basePo) {
        dao.delete(basePo);
    }

    @Override
    public void update(UserPo basePo) {
        dao.update(basePo);
    }



    /**
     * 获取数据库表中唯一的用户信息
     * @return
     */
    public UserPo selectUser(){
        List<UserPo> users =  loadAll();
        if(users != null && users.size()>0){
            //理论上只有一条数据，just in case！
            return users.get(0);
        }
        return new UserPo(0L, "","","","");//better not return null!
    }

    public void clearAllAndInsert(UserPo user){
        if(user == null)
            return;
        deleteAll();
        insert(user);
    }
}
