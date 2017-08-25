package com.zocki.fragment;

import android.view.View;
import android.widget.Toast;

import com.weightlibrary.loading.DefaultStatusView;
import com.weightlibrary.loading.StatusView;
import com.zocki.R;
import com.zocki.baselibrary.fragment.BaseFragment;
import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.dao.IDBDao;
import com.zocki.db.library.factory.DBDaoFactory;
import com.zocki.dbtest.Person2;

/**
 * Created by kaisheng3 on 2017/8/18.
 */
public class Button1Fragment extends BaseFragment implements View.OnClickListener {

    private StatusView statusView;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_1;
    }

    @Override
    public void initView() {
        LogUtils.e(getClass().getName());
        getView(R.id.button).setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:

                Person2 person2 = new Person2();
                person2.setAaa(111);
                person2.setBbb(222);

                IDBDao<Person2> dao = DBDaoFactory.getInstance().getDao(Person2.class);
                long insert = dao.insert(person2);

                Toast.makeText(getContext(),"创建表" + insert,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void initData() {
    }

    @Override
    protected View getLoadingView() {
        statusView = DefaultStatusView.Builder(getContext())
                .setEmptyOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.e( "111111111111111111111" );
                    }
                })
                .create();
        statusView.showEmpty();
        return statusView.getLayout();
    }
}
