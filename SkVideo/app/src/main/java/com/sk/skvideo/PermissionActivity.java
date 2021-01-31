package com.sk.skvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sk.permission.annotation.ApplyPermission;
import com.sk.permission.annotation.CancerPermission;
import com.sk.permission.annotation.RefusePermission;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        requestPermission();
    }


    @ApplyPermission(permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode = 1)
    private void requestPermission() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @CancerPermission
    private void permissionCancer(int requestCode) {
        Toast.makeText(this, "权限申请已被取消", Toast.LENGTH_SHORT).show();
    }


    @RefusePermission
    private void permissionRefused(int requestCode) {
        Toast.makeText(this, "权限申请已被拒绝", Toast.LENGTH_SHORT).show();
    }
}