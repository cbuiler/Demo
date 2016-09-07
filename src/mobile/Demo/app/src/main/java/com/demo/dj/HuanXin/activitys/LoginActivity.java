package com.demo.dj.HuanXin.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.models.DBManager;
import com.demo.dj.HuanXin.models.MeterReader;
import com.demo.dj.demo.R;

/**
 * Created by guozhaohui on 2016/8/9.
 */
public class LoginActivity extends Activity {
    private EditText edtUser;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = (EditText)findViewById(R.id.edtUser);
        edtPassword = (EditText)findViewById(R.id.edtPassword);

        Intent intent = getIntent();
        String readerId = intent.getStringExtra("readerId");
        edtUser.setText(readerId);
    }

    public void onAuthorize(View view) {
        //获取全局抄表员
        MeterReader reader = ((AppManager)getApplication()).getMeterReader();

        if (reader.checkAuthorition(edtUser.getText().toString(),
                edtPassword.getText().toString())) {
            //验证成功进入主页面
            Intent intent = new Intent();
            intent.setClass(this.getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            //验证失败
            Toast.makeText(this.getApplicationContext(),
                    "用户名或密码错误！", Toast.LENGTH_LONG).show();
        }
    }

}
