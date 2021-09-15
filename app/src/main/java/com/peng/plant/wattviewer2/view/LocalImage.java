package com.peng.plant.wattviewer2.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.peng.plant.wattviewer2.MainActivity;
import com.peng.plant.wattviewer2.R;
import com.peng.plant.wattviewer2.controller.TiltScrollController;
import com.peng.plant.wattviewer2.controller.ZoomController;


public class LocalImage extends AppCompatActivity implements TiltScrollController.ScrollListener {
    private static final String TAG = "LocalImage";


    private ImageView img, miniView;
    private TiltScrollController mTiltScrollController;
    private boolean sensor_control = true;
    private Button Zoom1, Zoom2, Zoom3, Zoom4, Zoom5, Stopimg;
    private TextView ZoomV1, ZoomV2, ZoomV3, ZoomV4, ZoomV5, imageM, imageP;
    private ZoomController mZoomcontrol;
    private RelativeLayout mRelativeLayout;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_image_view);

        img = (ImageView) findViewById(R.id.image);

        init();

        //미니맵 그리기
        miniMapDraw();


    }




    //줌 버튼 리스너
    private View.OnClickListener ZoomLevel_moveControll = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.zoom1:
                    Zoomlevel(1f);
                    break;
                case R.id.zoom2:
                    Zoomlevel(2f);
                    break;
                case R.id.zoom3:
                    Zoomlevel(3f);
                    break;
                case R.id.zoom4:
                    Zoomlevel(4f);
                    break;
                case R.id.zoom5:
                    Zoomlevel(5f);
                    break;
            }
        }
    };

    //줌 음성 리스너
    private View.OnClickListener ZoomLevel_Move_Voice = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageStart:
                    sensor_control = true;
                    Stopimg.setVisibility(View.GONE);
                    break;
                case R.id.imageStop:
                    sensor_control = false;
                    Stopimg.setVisibility(View.VISIBLE);
                    break;
                case R.id.zoomV1:
                    Zoomlevel(1f);
                    break;
                case R.id.zoomV2:
                    Zoomlevel(2f);
                    break;
                case R.id.zoomV3:
                    Zoomlevel(3f);
                    break;
                case R.id.zoomV4:
                    Zoomlevel(4f);
                    break;
                case R.id.zoomV5:
                    Zoomlevel(5f);
                    break;
            }
        }
    };

    //미니맵 그리기 메소드
    private void miniMapDraw() {
        Glide.with(this).load(getIntent().getStringExtra("picturePath")).into(miniView);
    }




    //줌레벨 적용 메소드
    private void Zoomlevel(float num) {
        mZoomcontrol.zoomlevel(num);
    }

    private void init() {
        //미니맵뷰
        mRelativeLayout = findViewById(R.id.mRelativelayout);
        //줌 버튼
        Zoom1 = findViewById(R.id.zoom1);
        Zoom2 = findViewById(R.id.zoom2);
        Zoom3 = findViewById(R.id.zoom3);
        Zoom4 = findViewById(R.id.zoom4);
        Zoom5 = findViewById(R.id.zoom5);
        //센서 텍스트
        imageM = findViewById(R.id.imageStart);
        imageP = findViewById(R.id.imageStop);
        //센서 리스너
        imageM.setOnClickListener(ZoomLevel_Move_Voice);
        imageP.setOnClickListener(ZoomLevel_Move_Voice);
        //정지버튼
        Stopimg = findViewById(R.id.stop_img);
        //줌 텍스트
        ZoomV1 = findViewById(R.id.zoomV1);
        ZoomV2 = findViewById(R.id.zoomV2);
        ZoomV3 = findViewById(R.id.zoomV3);
        ZoomV4 = findViewById(R.id.zoomV4);
        ZoomV5 = findViewById(R.id.zoomV5);
        //줌 버튼 리스너
        Zoom1.setOnClickListener(ZoomLevel_moveControll);
        Zoom2.setOnClickListener(ZoomLevel_moveControll);
        Zoom3.setOnClickListener(ZoomLevel_moveControll);
        Zoom4.setOnClickListener(ZoomLevel_moveControll);
        Zoom5.setOnClickListener(ZoomLevel_moveControll);
        //줌 음성 리스너
        ZoomV1.setOnClickListener(ZoomLevel_Move_Voice);
        ZoomV2.setOnClickListener(ZoomLevel_Move_Voice);
        ZoomV3.setOnClickListener(ZoomLevel_Move_Voice);
        ZoomV4.setOnClickListener(ZoomLevel_Move_Voice);
        ZoomV5.setOnClickListener(ZoomLevel_Move_Voice);


        mTiltScrollController = new TiltScrollController(getApplicationContext(), this);

        //뷰 에 올리기
        v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.image_minimap, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        miniView = v.findViewById(R.id.minimapV);
        //줌컨트롤 연결
        mZoomcontrol = new ZoomController(this);
        mZoomcontrol.addView(v);
        mZoomcontrol.setLayoutParams(layoutParams);
        //미니맵 표시
        mZoomcontrol.setMiniMapEnabled(true);
        //최대 줌
        mZoomcontrol.setMaxZoom(5f);
        //미니맵 크기지정
        mZoomcontrol.setMiniMapHeight(500);
        //미니맵 추가
        mRelativeLayout.addView(mZoomcontrol);


    }

    @Override
    public void onTilt(int x, int y, float delta) {
        if (sensor_control) {

            mZoomcontrol.Move_Sensor(-x * 4, -y * 4);
        }
    }
}







