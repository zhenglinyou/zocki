package com.zocki;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zocki.framelibrary.BaseSkinActivity;

public class MainActivity extends BaseSkinActivity{

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected View getTitleView(ViewGroup parent) {
        return LayoutInflater.from(this).inflate(R.layout.common_title,parent);
    }

    @Override
    protected View getLoadingView(ViewGroup parent) {
        return LayoutInflater.from(this).inflate(R.layout.loading_layout,parent);
    }

    @Override
    protected void initData() {
        // 启动服务
        /*startService(new Intent(this, MessageService.class));

        startService(new Intent(this, ProtectService.class));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startService(new Intent(this, LiveService.class));
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startService(new Intent(this, JobWakeUpService.class));
        }*/

        // 连接服务
        /*Intent intent = new Intent(this,MessageService.class);
        intent.setAction("com.study.aidl.user");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);*/
    }

    /*@OnClick({R.id.button,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button10,R.id.button11})
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
                    .cache(true)
                    .execute(new HttpCallBack<RecoverEntity>() {
                        @Override
                        public void onSuccess(RecoverEntity result) {
                            LogUtils.e( result.isNewData + " -- " + result );
                        }
                        @Override
                        public void onError(Exception e) {
                        }
                    });

        } else if( view.getId() == R.id.button3 ) {

            long startTime = System.currentTimeMillis();

            IDBDao<Person> personDao = DBDaoFactory.getInstance().getDao(Person.class);
            List<Person> personList = new ArrayList<>();
            for( int i = 0; i < 5000; i++ ) personList.add( new Person("zhangsan" + i , i + 23));
            personDao.insert(personList);

            LogUtils.e( System.currentTimeMillis() - startTime );

        } else if( view.getId() == R.id.button4 ) {

            IDBDao<Person> personDao = DBDaoFactory.getInstance().getDao(Person.class);
            *//*List<Person> query = personDao.query();
            for (Person person : query) {
                LogUtils.e( person );
            }*//*
            List<Person> persons = personDao.query().setSelection("name=?").setSelectionArgs(new String[]{"zhangsan2"}).setLimit(1).query();

            for (Person person : persons) {
                LogUtils.e( person );
            }
        } else if( view.getId() == R.id.button5) {
            IDBDao<Person> personDao = DBDaoFactory.getInstance().getDao(Person.class);
            *//*List<Person> query = personDao.query();
            for (Person person : query) {
                LogUtils.e( person );
            }*//*
            personDao.delete("name=?", new String[]{"zhangsan2"});
        } else if( view.getId() == R.id.button6 ) {


        } else if( view.getId() == R.id.button7 ) {

            Toast.makeText(this,"换肤",Toast.LENGTH_SHORT).show();

            try {
                String skinPath = Environment.getExternalStorageDirectory().getCanonicalPath() + File.separator + "skin.skin";
                int result = SkinManager.getInstance().loadSkin(skinPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if( view.getId() == R.id.button8 ) {
            Toast.makeText(this,"恢复皮肤",Toast.LENGTH_SHORT).show();
            try {
                int result = SkinManager.getInstance().restoreDefaultSkin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if( view.getId() == R.id.button10 ) {
            Intent intent = new Intent( this,MainActivity.class );
            startActivity(intent);
        } else if( view.getId() == R.id.button11 ) {
        }
        *//*try {
            // 测试 目前暂且放在本地
            String patchFileString =  Environment.getExternalStorageDirectory()+"/fix.apatch";
            Log.e("TAG", patchFileString);
            // 修复apatch，不需要重启可立即生效
            ZockiApplication.mPatchManager.addPatch(patchFileString);
            Toast.makeText(this, "Bug修复成功", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Bug修复失败", Toast.LENGTH_LONG).show();
        }*//*
    }*/
}
