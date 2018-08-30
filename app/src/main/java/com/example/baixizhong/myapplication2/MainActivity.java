package com.example.baixizhong.myapplication2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button buttonOff;
    DevicePolicyManager policyManager;
    ComponentName adminReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOff = (Button) findViewById(R.id.button);
        adminReceiver = new ComponentName(MainActivity.this, ScreenOffAdminReceiver.class);
        policyManager = (DevicePolicyManager) MainActivity.this.getSystemService(Context.DEVICE_POLICY_SERVICE);

        boolean admin = policyManager.isAdminActive(adminReceiver);
        if (!admin) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,  adminReceiver);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"开启后就可以使用锁屏功能了...");//显示位置见图二

            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(MainActivity.this,"已经拥有设备锁屏权限",
                    Toast.LENGTH_LONG).show();
        }

        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean admin = policyManager.isAdminActive(adminReceiver);
                if (admin) {
                    policyManager.lockNow();
                    //handler.sendEmptyMessageDelayed(1,3000);
                } else {
                    Toast.makeText(MainActivity.this,"没有设备管理权限",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
