package com.xhy.xp.softaphelper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2){
            sb.append("✅ 5G信道与带宽锁定已启用！\n");
        }else{
            sb.append("⚠️ 仅Android 13+支持5G信道与带宽锁定\n");
        }

        TextView textView = findViewById(R.id.sample_text);
        textView.setText(sb.toString());
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setTypeface(null, Typeface.BOLD);
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