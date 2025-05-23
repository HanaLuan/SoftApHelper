package com.xhy.xp.softaphelper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noblelulu.andoid.softaphelper.util.MMKVManager;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.sample_text);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setTypeface(null, Typeface.BOLD);

        // 添加设置按钮
        Button btnSettings = new Button(this);
        btnSettings.setText("设置");
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        // 正确获取自定义LinearLayout并添加按钮
        LinearLayout layout = findViewById(R.id.root_layout);
        layout.addView(btnSettings, 0);

        refreshMainInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMainInfo();
    }

    private void refreshMainInfo() {
        ArrayList<String> pkgNameList = new ArrayList<>(
                Arrays.asList(
                        "com.android.networkstack.tethering.inprocess",
                        "com.android.networkstack.tethering",
                        "com.google.android.networkstack.tethering.inprocess",
                        "com.google.android.networkstack.tethering"
                ));

        StringBuilder sb = new StringBuilder("已安装的Tethering组件（如未找到请选择android）：\n\n");
        for (String pkgName : pkgNameList) {
            if (isInstalled(pkgName)) {
                sb.append("✔ ").append(pkgName).append("\n");
            } else {
                sb.append("✘ ").append(pkgName).append("\n");
            }
        }

        sb.append("\n");
        boolean isSupport5G = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2;
        boolean isEnable5G = MMKVManager.INSTANCE.getBoolean(this, "enable_5g_lock", true);
        if (isSupport5G) {
            sb.append("✅ 当前系统支持5G信道与带宽锁定\n");
            sb.append(isEnable5G ? "✅ 5G锁定已启用\n" : "❌ 5G锁定未启用\n");
        } else {
            sb.append("⚠️ 仅Android 13+支持5G信道与带宽锁定\n");
        }

        // Dump MainHook相关设置
        sb.append("\n[当前设置内容]\n");
        sb.append("wifi_ip: ").append(MMKVManager.INSTANCE.getString(this, "wifi_ip", "192.168.43.1")).append("\n");
        sb.append("usb_ip: ").append(MMKVManager.INSTANCE.getString(this, "usb_ip", "192.168.42.1/24")).append("\n");
        sb.append("bt_ip: ").append(MMKVManager.INSTANCE.getString(this, "bt_ip", "192.168.44.1/24")).append("\n");
        sb.append("p2p_ip: ").append(MMKVManager.INSTANCE.getString(this, "p2p_ip", "192.168.49.1/24")).append("\n");
        sb.append("eth_ip: ").append(MMKVManager.INSTANCE.getString(this, "eth_ip", "192.168.45.1/24")).append("\n");
        sb.append("static_bssid: ").append(MMKVManager.INSTANCE.getBoolean(this, "static_bssid", false)).append("\n");
        sb.append("bssid: ").append(MMKVManager.INSTANCE.getString(this, "bssid", "aa:bb:cc:dd:ee:ff")).append("\n");
        sb.append("enable_5g_lock: ").append(MMKVManager.INSTANCE.getBoolean(this, "enable_5g_lock", true)).append("\n");

        // 其它MainHook静态常量（未出现在设置菜单的）

        textView.setText(sb.toString());
    }

    public boolean isInstalled(String pkgName) {
        PackageManager packageManager = this.getApplicationContext().getPackageManager();
        try {
            packageManager.getApplicationInfo(pkgName, PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

}