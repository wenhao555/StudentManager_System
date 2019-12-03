package com.example.studentmanager_system.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanager_system.R;
import com.example.studentmanager_system.service.Contants;
import com.example.studentmanager_system.service.MusicServer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 管理员主界面
 */
public class adminActivity extends Activity
{
    private Button select;//查询学生信息按钮
    private Button add;//添加学生信息按钮
    private Button order;//查看总成绩排名按钮
    private TextView forceOffline;//强制下线


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);


        NotificationManager notificationManager = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(adminActivity.this);
        //设置标题
        mBuilder.setContentTitle("学生管理系统")
                //设置内容
                .setContentText("管理员已登录")
                .setTicker("管理员已登录")
                //设置大图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher_round)
                //设置通知时间
                .setWhen(System.currentTimeMillis())
                //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
                .setDefaults(Notification.DEFAULT_SOUND);
        //发送通知请求
        notificationManager.notify(10, mBuilder.build());

        //初始化相关控件
        select = (Button) findViewById(R.id.admin_activity_select);
        add = (Button) findViewById(R.id.admin_activity_add);
        order = (Button) findViewById(R.id.admin_activity_order);
        forceOffline = (TextView) findViewById(R.id.admin_activity_forceOffline);

        /**
         * 查询学生信息
         */
        select.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(adminActivity.this, studentinfoActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 添加学生信息
         */
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(adminActivity.this, add_studentinfoActivity.class);
                intent.putExtra("haveData", "false");
                startActivity(intent);
            }
        });


        /**
         * 查询学生总成绩
         */
        order.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(adminActivity.this, student_total_score.class);
                startActivity(intent);
            }
        });

        /**
         * 强制下线
         */
        forceOffline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction("com.example.OfflineBradcast");
                sendBroadcast(intent);
                finish();
            }
        });

    }

    private Timer timer;
    private TimerTask task;
    private static final long TIME = 2000;
    private final static int ALARM = 2;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case ALARM:
                    Toast.makeText(adminActivity.this, "手机使用时间过长注意休息", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };


}
