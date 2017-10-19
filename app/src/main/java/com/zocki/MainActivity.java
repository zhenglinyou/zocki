package com.zocki;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Interpolator;


import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.zocki.baselibrary.http.HttpUtils;
import com.zocki.baselibrary.ioc.ViewId;
import com.zocki.entity.RecoverEntity;
import com.zocki.fragment.Button1Fragment;
import com.zocki.fragment.Button2Fragment;
import com.zocki.fragment.Button3Fragment;
import com.zocki.fragment.Button4Fragment;
import com.zocki.framelibrary.BaseSkinActivity;
import com.zocki.framelibrary.http.HttpCallBack;
import com.zocki.mainbutton.MainButton;

public class MainActivity extends BaseSkinActivity{

    @ViewId(R.id.main_button)
    private MainButton mMainButton;

    private Fragment button1Fragment = new Button1Fragment();
    private Fragment button2Fragment = new Button2Fragment();
    private Fragment button3Fragment = new Button3Fragment();
    private Fragment button4Fragment = new Button4Fragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        // android_manifest 中主题不同
        // setTheme(R.style.App_Page_Theme);
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        QMUIStatusBarHelper.translucent(this, Color.parseColor("#00ff00"));

        /*mMainViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return button1Fragment;
                    case 1: return button2Fragment;
                    case 2: return button3Fragment;
                    case 3: return button4Fragment;
                }
                return null;
            }
            @Override
            public int getCount() {
                return 4;
            }
        });*/

        mMainButton.setOnItemClickListener(new MainButton.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
               // mMainViewpager.setCurrentItem(position);
                Fragment fragment = null;
                switch (position) {
                    case 0: fragment = button1Fragment; break;
                    case 1: fragment = button2Fragment; break;
                    case 2: fragment = button3Fragment; break;
                    case 3: fragment = button4Fragment; break;
                }

                changeFragment(fragment);
            }
        });

        changeFragment(button1Fragment);

        /*
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout_content,button1Fragment)
                .add(R.id.framelayout_content,button2Fragment)
                .add(R.id.framelayout_content,button3Fragment)
                .add(R.id.framelayout_content,button4Fragment).commit();
        */
    }

    private void changeFragment(Fragment fragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().hide(button1Fragment).hide(button2Fragment).hide(button3Fragment).hide(button4Fragment);

        if( !fragment.isAdded() ) {
            ft.add(R.id.framelayout_content,fragment);
        }

        ft.show(fragment);

        ft.commit();

    }

   /* @Override
    protected View getTitleView(ViewGroup parent) {
        return LayoutInflater.from(this).inflate(R.layout.common_title,parent);
    }

    @Override
    protected View getLoadingView(ViewGroup parent) {
        return LayoutInflater.from(this).inflate(R.layout.loading_layout,parent);
    }*/

    @Override
    protected void initData() {

        HttpUtils.with(this).url("https://test.521meme.com/proxy/other/recommendList")
                .addParam("pageNum", 1)
                .addParam("pageSize", 100)
                .cache(true)
                .execute(new HttpCallBack<RecoverEntity>() {
                    @Override
                    public void onSuccess(RecoverEntity result) {
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                });

        View view = getView(R.id.container_id);

        ValueAnimator animator = ValueAnimator.ofFloat( 0.5f, 1.2f );
        animator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                float factor = 0.4f;
                float value = (float) (Math.pow(2, -10 * input) * Math.sin((factor - factor / 4) * (2 * Math.PI) / factor) + 1);
                return value;
            }
        });

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
                    .execute(new IHttpCallBack<RecoverEntity>() {
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
