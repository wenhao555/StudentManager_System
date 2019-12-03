package com.example.studentmanager_system.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanager_system.R;
import com.example.studentmanager_system.Util.PrefUtils;
import com.example.studentmanager_system.Util.myDatabaseHelper;

/**
 * 管理员登录界面
 */
public class admin_login_activity extends Activity
{

    private EditText name;//用户名
    private EditText password;//用户密码
    private Button login;//登录按钮
    private TextView register;//注册
    private TextView forgetNum;//忘记密码
    private myDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_layout);

        //初始化相关控件
        dbHelper = myDatabaseHelper.getInstance(this);

        name = (EditText) findViewById(R.id.admin_login_activity_name_input);
        password = (EditText) findViewById(R.id.admin_login_activity_password_input);
        login = (Button) findViewById(R.id.admin_login_activity_login);
        register = (TextView) findViewById(R.id.admin_login_activity_register);
        forgetNum = (TextView) findViewById(R.id.admin_login_activity_forgetNum);
        forgetNum.setVisibility(View.GONE);
        String nameInfo1 = PrefUtils.getString(admin_login_activity.this, "name", "");
        String passwordInfo1 = PrefUtils.getString(admin_login_activity.this, "pwd", "");
        if (!nameInfo1.equals("") && !passwordInfo1.equals(""))
        {
            name.setText(nameInfo1);
            password.setText(passwordInfo1);
        }
        //跳转到登录过的管理员界面
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String nameInfo = name.getText().toString();
                String passwordInfo = password.getText().toString();
                //从数据库中获取密码并判断是否相同
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select password from admin where name=?", new String[]{nameInfo});
                String pi = null;
                if (cursor.moveToNext())
                {
                    pi = cursor.getString(cursor.getColumnIndex("password"));//获取密码
                }

                //如果密码正确跳转到登录后的界面
                if (passwordInfo.equals(pi))
                {
                    PrefUtils.setString(admin_login_activity.this, "name", nameInfo);
                    PrefUtils.setString(admin_login_activity.this, "pwd", passwordInfo);
                    Intent intent = new Intent(admin_login_activity.this, adminActivity.class);
                    startActivity(intent);
                    finish();
                    cursor.close();
                } else
                {
                    Toast.makeText(admin_login_activity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //自定义AlertDiaglog用于注册
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(admin_login_activity.this);
                LayoutInflater factory = LayoutInflater.from(admin_login_activity.this);
                final View textEntryView = factory.inflate(R.layout.register, null);
                builder.setTitle("用户注册");
                builder.setView(textEntryView);

                final EditText name = (EditText) textEntryView.findViewById(R.id.admin_register_name);
                final EditText firstPassword = (EditText) textEntryView.findViewById(R.id.admin_register_first_password);
                final EditText secondPassword = (EditText) textEntryView.findViewById(R.id.admin_register_second_password);

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String nameInfo = name.getText().toString();
                        String firstPasswordInfo = firstPassword.getText().toString();
                        String secondPasswordInfo = secondPassword.getText().toString();
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        //两次密码相同
                        if (firstPasswordInfo.equals(secondPasswordInfo))
                        {
                            Cursor cursor = db.rawQuery("select name from admin where name=? ", new String[]{nameInfo});
                            //用户是否存在
                            if (cursor.moveToNext())
                            {
                                Toast.makeText(admin_login_activity.this, "该用户已经存在", Toast.LENGTH_SHORT).show();
                            } else
                            {
                                db.execSQL("insert into admin(name,password)values(?,?)", new String[]{nameInfo, firstPasswordInfo});
                            }
                        } else
                        {
                            Toast.makeText(admin_login_activity.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.create().show();
            }
        });


        /**
         * 忘记密码
         */
        forgetNum.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(admin_login_activity.this, "此功能暂不支持", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private long exit_time; //用于实现按两次返回键退出

    @Override
    //按两次back键退出
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //获取按键并比较两次按back的时间大于2s不退出，否则退出
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (System.currentTimeMillis() - exit_time > 2000)
            {
                Toast.makeText(admin_login_activity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exit_time = System.currentTimeMillis();
            } else
            {
                finish();
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
