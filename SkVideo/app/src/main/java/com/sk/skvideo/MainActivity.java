package com.sk.skvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sk.permission.annotation.ApplyPermission;
import com.sk.permission.annotation.CancerPermission;
import com.sk.permission.annotation.RefusePermission;
import com.sk.skvideo.utils.Constant;
import com.sk.skvideo.widget.CameraView;
import com.sk.skvideo.widget.RecordButton;

public class MainActivity extends AppCompatActivity implements RecordButton.OnRecordListener,
        RadioGroup.OnCheckedChangeListener {

    private CameraView cameraView;
    private RecordButton btnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        cameraView = findViewById(R.id.cameraView);
        btnRecord = findViewById(R.id.btn_record);
        btnRecord.setOnRecordListener(this);

        //速度
        RadioGroup rgSpeed = findViewById(R.id.rg_speed);
        rgSpeed.setOnCheckedChangeListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.btn_extra_slow:
                cameraView.setSpeed(CameraView.Speed.MODE_EXTRA_SLOW);
                break;
            case R.id.btn_slow:
                cameraView.setSpeed(CameraView.Speed.MODE_SLOW);
                break;
            case R.id.btn_normal:
                cameraView.setSpeed(CameraView.Speed.MODE_NORMAL);
                break;
            case R.id.btn_fast:
                cameraView.setSpeed(CameraView.Speed.MODE_FAST);
                break;
            case R.id.btn_extra_fast:
                cameraView.setSpeed(CameraView.Speed.MODE_EXTRA_FAST);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRecordStart() {
        btnRecord.setText("拍摄中");
        cameraView.startRecord();
    }

    @Override
    public void onRecordStop() {
        btnRecord.setText("按住拍");
        cameraView.stopRecord();
        Toast.makeText(this, "视频录制成功，已保存至" + Constant.VIDEO_PATH, Toast.LENGTH_SHORT).show();
    }
}