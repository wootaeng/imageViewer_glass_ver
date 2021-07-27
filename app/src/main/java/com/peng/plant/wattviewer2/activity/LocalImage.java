package com.peng.plant.wattviewer2.activity;

import android.content.Context;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.peng.plant.wattviewer2.R;
import com.peng.plant.wattviewer2.data.Kalman;

import java.io.File;


public class LocalImage extends AppCompatActivity implements View.OnClickListener{//

    private SensorManager sensorManager;
    private Sensor accSensor;
    private float mX, mY;

    private Kalman mKalmanAccX;
    private Kalman mKalmanAccY;





    private SubsamplingScaleImageView img;
//    ImageView img;
    Button btn1, btn2, btn3, btn4, btn5;
    Animation aniZoomOut, animZoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_image_view);
        img = (SubsamplingScaleImageView) findViewById(R.id.image);
        img.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        img.setMinScale(0.0F);


        Glide.with(this)
                .load(getIntent().getStringExtra("picturePath"))
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, Transition<? super File> transition) {
                        img.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.0F, new PointF(0,0), 0));
                    }
                });




        btn1 = findViewById(R.id.zoom1);
        btn1.setOnClickListener((View.OnClickListener) this);

        btn2 = findViewById(R.id.zoom2);
        btn2.setOnClickListener((View.OnClickListener) this);

        btn3 = findViewById(R.id.zoom3);
        btn3.setOnClickListener((View.OnClickListener) this);

        btn4 = findViewById(R.id.zoom4);
        btn4.setOnClickListener((View.OnClickListener) this);

        btn5 = findViewById(R.id.zoom5);
        btn5.setOnClickListener((View.OnClickListener) this);




    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.zoom1:
//                aniZoomOut = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_out);
//                img.startAnimation(aniZoomOut);

                sensorManager.unregisterListener(sel);
                Glide.with(this)
                        .load(getIntent().getStringExtra("picturePath")).fitCenter()
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, Transition<? super File> transition) {
                                img.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.0F, new PointF(0,0), 0));
                            }
                        });


                break;

            case R.id.zoom2:
//                animZoomIn = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_in);
//                img.startAnimation(animZoomIn);
                Glide.with(this)
                        .load(getIntent().getStringExtra("picturePath"))
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, Transition<? super File> transition) {
                                img.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.3F, new PointF(0,0), 0));
                            }
                        });

                mKalmanAccX = new Kalman(0.0f);
                mKalmanAccY = new Kalman(0.0f);
//
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(sel, accSensor, SensorManager.SENSOR_DELAY_GAME);

                break;

            case R.id.zoom3:
                Glide.with(this)
                        .load(getIntent().getStringExtra("picturePath"))
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, Transition<? super File> transition) {
                                img.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.4F, new PointF(0,0), 0));
                            }
                        });
                mKalmanAccX = new Kalman(0.0f);
                mKalmanAccY = new Kalman(0.0f);
//
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(sel, accSensor, SensorManager.SENSOR_DELAY_GAME);

                break;
            case R.id.zoom4:
                Glide.with(this)
                        .load(getIntent().getStringExtra("picturePath"))
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, Transition<? super File> transition) {
                                img.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.6F, new PointF(0,0), 0));
                            }
                        });
                mKalmanAccX = new Kalman(0.0f);
                mKalmanAccY = new Kalman(0.0f);
//
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(sel, accSensor, SensorManager.SENSOR_DELAY_GAME);


                break;
            case R.id.zoom5:
                Glide.with(this)
                        .load(getIntent().getStringExtra("picturePath"))
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, Transition<? super File> transition) {
                                img.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.8F, new PointF(0,0), 0));
                            }
                        });
                mKalmanAccX = new Kalman(0.0f);
                mKalmanAccY = new Kalman(0.0f);
//
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(sel, accSensor, SensorManager.SENSOR_DELAY_GAME);

                break;

        }
    }
//
//
    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];


            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sel, accSensor, SensorManager.SENSOR_DELAY_GAME);



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

            img.scrollBy((int) -((mX - filteredX) * 100), (int) ((mY - filteredY) * 100));


            // 예전값을 저장한다
            mX = filteredX;
            mY = filteredY;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };



}