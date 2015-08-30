package nobugs.team.shopping.repo.db.helper;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import nobugs.team.shopping.app.base.MyApplication;
import nobugs.team.shopping.repo.db.DaoMaster;
import nobugs.team.shopping.repo.db.DaoSession;
import nobugs.team.shopping.repo.model.BasePo;

/**
 * Created by xiayong on 2015/8/29.
 */
public abstract class DaoHelper<T extends BasePo> {
    protected SQLiteDatabase db;

    protected DaoMaster daoMaster;
    protected DaoSession daoSession;
    protected AbstractDao dao;

    public DaoHelper(){
        init();
    }

    //You must invoke this Methed before use this object
    private void init(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), "user-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        dao = getDao();
    }
    protected abstract  AbstractDao getDao();

    public abstract void insert(T basePo);

    public abstract  void deleteAll();

    public abstract List<T> loadAll();

    public abstract void delete(T basePo);

    public abstract void update(T basePo);
    //add other methods here!
}
