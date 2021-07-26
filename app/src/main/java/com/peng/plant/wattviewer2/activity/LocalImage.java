package com.peng.plant.wattviewer2.activity;

import android.content.Context;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.peng.plant.wattviewer2.R;
import com.peng.plant.wattviewer2.data.Kalman;


public class LocalImage extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accSensor;

    private View mLayout;
    private float mX, mY;

    private Kalman mKalmanAccX;
    private Kalman mKalmanAccY;


    ImageView img;
//    PhotoView img;
    Button btn1, btn2, btn3, btn4, btn5;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_image_view);
        img = findViewById(R.id.image);

        Glide.with(this)
                .load(getIntent().getStringExtra("picturePath"))
                .into(img);

//        img = findViewById(R.id.photoView);
//        Glide.with(this).load(getIntent().getStringExtra("picturePath")).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.5f).into(img);

        mLayout = this.findViewById(R.id.imageV_layout);

        mKalmanAccX = new Kalman(0.0f);
        mKalmanAccY = new Kalman(0.0f);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sel, accSensor, SensorManager.SENSOR_DELAY_GAME);

        btn1 = findViewById(R.id.zoom1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation aniZoomOut = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_out);
                img.startAnimation(aniZoomOut);
            }
        });


        btn2 = findViewById(R.id.zoom2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animZoomIn = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_in);
                img.startAnimation(animZoomIn);
            }
        });

        btn3 = findViewById(R.id.zoom3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animZoomIn = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_in_2);
                img.startAnimation(animZoomIn);
            }
        });

        btn4 = findViewById(R.id.zoom4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animZoomIn = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_in_3);
                img.startAnimation(animZoomIn);
            }
        });

        btn5 = findViewById(R.id.zoom5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animZoomIn = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_in_4);
                img.startAnimation(animZoomIn);
            }
        });






    }

    private SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];

            float filteredX = 0.0f;
            float filteredY = 0.0f;

            // 칼만필터를 적용한다
            filteredX = (float) mKalmanAccY.update(x);
            filteredY = (float) mKalmanAccX.update(y);

            // 이 주석을 풀면 칼만필터를 사용하지 않는다
            // filteredX = x;
            // filteredY = y;
            // 부모 레이아웃을 스크롤시켜 마치 뷰객체(오브젝트)가 움직이는것처럼 보이게 한다
            // 저장해둔 예전값과 현재값의 차를 넣어 변화를 감지한다
            // 여기에 100을 곱하는것은 차의 숫자가 워낙 작아 움직임이 보이지 않기 때문이다.
            // 즉, 스피드라고도 보면 된다ㅎㅎ 더 큰숫자를 넣으면 더 빠르게 움직인다.
            mLayout.scrollBy((int) ((mX - filteredX) * 100), -(int) ((mY - filteredY) * 100));

            // 예전값을 저장한다
            mX = filteredX;
            mY = filteredY;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


}