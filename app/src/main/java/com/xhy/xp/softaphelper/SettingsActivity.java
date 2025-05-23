package com.xhy.xp.softaphelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.text.TextUtils;
import android.util.Patterns;
import com.noblelulu.andoid.softaphelper.util.MMKVManager;

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch switch5g = findViewById(R.id.switch_5g_lock);
        EditText editWifiIp = findViewById(R.id.edit_wifi_ip);
        EditText editUsbIp = findViewById(R.id.edit_usb_ip);
        EditText editBtIp = findViewById(R.id.edit_bt_ip);
        EditText editP2pIp = findViewById(R.id.edit_p2p_ip);
        EditText editEthIp = findViewById(R.id.edit_eth_ip);
        Switch switchStaticBssid = findViewById(R.id.switch_static_bssid);
        EditText editBssid = findViewById(R.id.edit_bssid);
        Button btnSave = findViewById(R.id.btn_save);

        // 读取MMKV
        switch5g.setChecked(MMKVManager.INSTANCE.getBoolean(this, "enable_5g_lock", true));
        editWifiIp.setText(MMKVManager.INSTANCE.getString(this, "wifi_ip", "192.168.43.1"));
        editUsbIp.setText(MMKVManager.INSTANCE.getString(this, "usb_ip", "192.168.42.1/24"));
        editBtIp.setText(MMKVManager.INSTANCE.getString(this, "bt_ip", "192.168.44.1/24"));
        editP2pIp.setText(MMKVManager.INSTANCE.getString(this, "p2p_ip", "192.168.49.1/24"));
        editEthIp.setText(MMKVManager.INSTANCE.getString(this, "eth_ip", "192.168.45.1/24"));
        switchStaticBssid.setChecked(MMKVManager.INSTANCE.getBoolean(this, "static_bssid", false));
        editBssid.setText(MMKVManager.INSTANCE.getString(this, "bssid", ""));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 校验IP和BSSID
                if (!isValidIp(editWifiIp.getText().toString())) {
                    toast("WiFi IP格式错误");
                    return;
                }
                if (!isValidIpWithMask(editUsbIp.getText().toString())) {
                    toast("USB IP格式错误");
                    return;
                }
                if (!isValidIpWithMask(editBtIp.getText().toString())) {
                    toast("蓝牙IP格式错误");
                    return;
                }
                if (!isValidIpWithMask(editP2pIp.getText().toString())) {
                    toast("P2P IP格式错误");
                    return;
                }
                if (!isValidIpWithMask(editEthIp.getText().toString())) {
                    toast("以太网IP格式错误");
                    return;
                }
                String bssid = editBssid.getText().toString();
                if (switchStaticBssid.isChecked() && !isValidBssid(bssid)) {
                    toast("BSSID格式错误，应为aa:bb:cc:dd:ee:ff");
                    return;
                }

                MMKVManager.INSTANCE.putBoolean(SettingsActivity.this, "enable_5g_lock", switch5g.isChecked());
                MMKVManager.INSTANCE.putString(SettingsActivity.this, "wifi_ip", editWifiIp.getText().toString());
                MMKVManager.INSTANCE.putString(SettingsActivity.this, "usb_ip", editUsbIp.getText().toString());
                MMKVManager.INSTANCE.putString(SettingsActivity.this, "bt_ip", editBtIp.getText().toString());
                MMKVManager.INSTANCE.putString(SettingsActivity.this, "p2p_ip", editP2pIp.getText().toString());
                MMKVManager.INSTANCE.putString(SettingsActivity.this, "eth_ip", editEthIp.getText().toString());
                MMKVManager.INSTANCE.putBoolean(SettingsActivity.this, "static_bssid", switchStaticBssid.isChecked());
                MMKVManager.INSTANCE.putString(SettingsActivity.this, "bssid", bssid);

                toast("保存成功");
                finish();
            }
        });
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidIp(String ip) {
        return !TextUtils.isEmpty(ip) && Patterns.IP_ADDRESS.matcher(ip).matches();
    }

    private boolean isValidIpWithMask(String ip) {
        if (TextUtils.isEmpty(ip)) return false;
        String[] parts = ip.split("/");
        return parts.length == 2 && isValidIp(parts[0]) && isValidMask(parts[1]);
    }

    private boolean isValidMask(String mask) {
        try {
            int m = Integer.parseInt(mask);
            return m >= 0 && m <= 32;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidBssid(String bssid) {
        return bssid.matches("([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}");
    }
}
