package com.zocki;

import android.view.View;
import android.widget.Toast;

import com.zocki.baselibrary.dialog.AlertDialog;
import com.zocki.baselibrary.http.HttpUtils;
import com.zocki.baselibrary.ioc.OnClick;
import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.IDBDaoSupport;
import com.zocki.db.library.factory.DBDaoSupportFactory;
import com.zocki.entity.RecoverEntity;
import com.zocki.framelibrary.BaseSkinActivity;
import com.zocki.framelibrary.http.HttpCallBack;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseSkinActivity{

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.button,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    private void aliHotFix(View view) {

        if( view.getId() == R.id.button2 ) {

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setContentView(R.layout.dialog_custom_layout)
                    .addDefaultAnimation()
                    .setWidthAndHeight(600,600)
                    .show();

            alertDialog.setOnClickListener(R.id.text, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"123123",Toast.LENGTH_SHORT).show();
                }
            });

            alertDialog.setText(R.id.text,"123123");

            HttpUtils.with(this).url("https://test.521meme.com/proxy/other/recommendList")
                    .addParam("pageNum", 1)
                    .addParam("pageSize", 100)
                    .execute(new HttpCallBack<RecoverEntity>() {
                        @Override
                        public void onSuccess(RecoverEntity result) {
                            LogUtils.e( result );
                        }
                        @Override
                        public void onError(Exception e) {
                        }
                    });

        } else if( view.getId() == R.id.button3 ) {

            long startTime = System.currentTimeMillis();

            IDBDaoSupport<Person> personDao = DBDaoSupportFactory.getInstance().getDao(Person.class);
            List<Person> personList = new ArrayList<>();
            for( int i = 0; i < 10; i++ ) personList.add( new Person("zhangsan" + i , i + 23));
            personDao.insert(personList);

            LogUtils.e( System.currentTimeMillis() - startTime );


        } else if( view.getId() == R.id.button4 ) {

            IDBDaoSupport<Person> personDao = DBDaoSupportFactory.getInstance().getDao(Person.class);
            /*List<Person> query = personDao.query();
            for (Person person : query) {
                LogUtils.e( person );
            }*/
            List<Person> persons = personDao.query("name=?", new String[]{"zhangsan2"});

            for (Person person : persons) {
                LogUtils.e( person );
            }
        } else if( view.getId() == R.id.button5) {
            IDBDaoSupport<Person> personDao = DBDaoSupportFactory.getInstance().getDao(Person.class);
            /*List<Person> query = personDao.query();
            for (Person person : query) {
                LogUtils.e( person );
            }*/

            personDao.delete("name=?", new String[]{"zhangsan2"});
        }

       /* try {
            // 测试 目前暂且放在本地
            String patchFileString =  Environment.getExternalStorageDirectory()+"/fix.apatch";
            Log.e("TAG", patchFileString);
            // 修复apatch，不需要重启可立即生效
            ZockiApplication.mPatchManager.addPatch(patchFileString);
            Toast.makeText(this, "Bug修复成功", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Bug修复失败", Toast.LENGTH_LONG).show();
        }*/
    }
}
