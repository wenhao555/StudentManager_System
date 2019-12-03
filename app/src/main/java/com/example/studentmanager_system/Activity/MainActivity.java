package com.example.studentmanager_system.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.studentmanager_system.R;
import com.example.studentmanager_system.Util.myDatabaseHelper;
import com.example.studentmanager_system.service.Contants;
import com.example.studentmanager_system.service.MusicServer;


/**
 * 登录主界面，主界面就先把数据库中的表先建好
 */
public class MainActivity extends Activity
{

    private Button admin;
    private myDatabaseHelper dbHelper;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admin = (Button) findViewById(R.id.main_activity_admin);
        dbHelper = myDatabaseHelper.getInstance(this);
        dbHelper.getWritableDatabase();
        intent = new Intent(MainActivity.this, MusicServer.class);
        intent.putExtra(Contants.CONNET_DATA, "1");
        startService(intent);

        //跳转到管理员登录界面
        admin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, admin_login_activity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    @Override
    protected void onStop()
    {
        super.onStop();
        intent = new Intent(MainActivity.this, MusicServer.class);
        stopService(intent);
    }
}
