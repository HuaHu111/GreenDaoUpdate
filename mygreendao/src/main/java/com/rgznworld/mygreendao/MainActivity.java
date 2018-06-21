package com.rgznworld.mygreendao;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.rgznworld.mygreendao.Utils.DbMangaer;
import com.rgznworld.mygreendao.bean.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewGroup bar_layout;
    private DbMangaer instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatus();

        Button btn_zeng = (Button) findViewById(R.id.btnzeng);
        Button btn_shan = (Button) findViewById(R.id.btnshan);
        Button btn_gai = (Button) findViewById(R.id.btngai);
        Button btn_cha = (Button) findViewById(R.id.btncha);
        instance = DbMangaer.getInstance(this);


        btn_zeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User sanfeng = new User("张无忌", 88,"男");
                User guojing = new User("华筝", 25,"女");
                User ouyang = new User("瑛姑", 75,"女");
                User duanyu = new User("乔峰", 17,"男");
                User huangrong = new User("黄药师", 19,"男");
                ArrayList<User> users = new ArrayList<>();
                users.add(sanfeng);
                users.add(guojing);
                users.add(ouyang);
                users.add(duanyu);
                users.add(huangrong);
                instance.saveUsers(users);
            }
        });


        btn_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> users = instance.queryUser();
                for (User user:users){
                    Log.e("user",user.getName()+"    年龄："+user.getAge()+"    性别："+user.getSex());
                }
            }
        });






    }


    /**
     * 下面是设置沉浸式状态栏的代码  可以无视
     */

    private void setStatus(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            bar_layout= (ViewGroup) findViewById(R.id.bar_layout);
            if (bar_layout!=null){
                final int statusBarHeight = getStatusBarHeight();
                bar_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        int height = bar_layout.getHeight();
                        ViewGroup.LayoutParams layoutParams = bar_layout.getLayoutParams();
                        layoutParams.height= statusBarHeight+height;
                        bar_layout.setLayoutParams(layoutParams);
                    }
                });
            }
        }
    }

    protected int getStatusBarHeight(){
        try
        {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return  getResources().getDimensionPixelSize(x);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
