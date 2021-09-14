package com.peng.plant.wattviewer2.view;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.peng.plant.wattviewer2.R;


public class LocalImage extends AppCompatActivity implements View.OnClickListener{//, AccelerometerController.SensorEventListener
    private static final String TAG = "LocalImage";


    private SensorManager sensorManager;
    private Sensor accSensor;
    private float mX, mY;
    private WindowManager mWindowmagager;
    private Display mDisplay;
    private Point size;

    private float x, y;

    private Kalman mKalmanAccX;
    private Kalman mKalmanAccY;

    //    private SubsamplingScaleImageView img;
    private ImageView miniimg;
    private ImageView imgEdge;

    private FrameLayout.LayoutParams layoutParams;
    private boolean clickfix;


    private ImageView img;
    Button btn1, btn2, btn3, btn4, btn5, fixbtn, fixoffbtn;
    Animation aniZoomOut, animZoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_image_view);

        img = (ImageView) findViewById(R.id.image);

//        Subsampling_scale 라이브러리 사용 코드
//        img = (SubsamplingScaleImageView) findViewById(R.id.image);
//        img.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
//        img.setMinScale(0.0F);
//        Glide.with(this)
//                .load(getIntent().getStringExtra("picturePath"))
//                .downloadOnly(new SimpleTarget<File>() {
//                    @Override
//                    public void onResourceReady(File resource, Transition<? super File> transition) {
//                        img.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.0F, new PointF(0,0), 0));
//                    }
//                });
        Glide.with(this).load(getIntent().getStringExtra("picturePath")).fitCenter().into(img);
        Log.d(TAG, "이미지 glide load" );

        miniimg = (ImageView) findViewById(R.id.miniImg);
        Glide.with(this).load(getIntent().getStringExtra("picturePath")).into(miniimg);

        imgEdge = (ImageView) findViewById(R.id.miniedge);

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

        fixbtn = findViewById(R.id.viewFix);
        fixoffbtn = findViewById(R.id.viewFixoff);



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.zoom1:
                stopSensor();
                Log.d(TAG, "sensor stop");

                aniZoomOut = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_out);
                img.startAnimation(aniZoomOut);
//                Glide.with(this)
//                        .load(getIntent().getStringExtra("picturePath"))
//                        .downloadOnly(new SimpleTarget<File>() {
//                            @Override
//                            public void onResourceReady(File resource, Transition<? super File> transition) {
//                                img.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.0F, new PointF(0,0), 0));
//                            }
//                        });
                fixbtn.setVisibility(View.GONE);
                fixoffbtn.setVisibility(View.GONE);

                //위치 초기화화
                img.scrollTo(0,0);
                layoutParams = new FrameLayout.LayoutParams(120,80);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.setMargins(0, 80, 40, 0);
                imgEdge.setLayoutParams(layoutParams);

                break;

            case R.id.zoom2:
                animZoomIn = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_in);
                img.startAnimation(animZoomIn);

                layoutParams = new FrameLayout.LayoutParams(100,60);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.setMargins(0, 80, 40, 0);
                imgEdge.setLayoutParams(layoutParams);

                mKalmanAccX = new Kalman(0.0f);
                mKalmanAccY = new Kalman(0.0f);
                Log.d(TAG, "kalman reset");

                if (clickfix){
                    stopSensor();
                }else {
                    startSensor();
                }


                Log.d(TAG, "sensor start!!!!!!!!!!!!");

                fixbtn.setVisibility(View.VISIBLE);
                fixbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickfix = true;
                        fixbtn.setVisibility(View.GONE);
                        fixoffbtn.setVisibility(View.VISIBLE);
                        stopSensor();
                        fixoffbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clickfix = false;
                                fixoffbtn.setVisibility(View.GONE);
                                fixbtn.setVisibility(View.VISIBLE);
                                startSensor();
                            }
                        });
                    }
                });

                break;

            case R.id.zoom3:
                animZoomIn = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_in_2);
                img.startAnimation(animZoomIn);

                layoutParams = new FrameLayout.LayoutParams(80,50);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.setMargins(0, 80, 40, 0);
                imgEdge.setLayoutParams(layoutParams);

                mKalmanAccX = new Kalman(0.0f);
                mKalmanAccY = new Kalman(0.0f);

                if (clickfix){
                    stopSensor();
                }else {
                    startSensor();
                }
                Log.d(TAG, "sensor start!!!!!!!!!!!!");

                fixbtn.setVisibility(View.VISIBLE);
                fixbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickfix = true;
                        fixbtn.setVisibility(View.GONE);
                        fixoffbtn.setVisibility(View.VISIBLE);
                        stopSensor();
                        fixoffbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clickfix = false;
                                fixoffbtn.setVisibility(View.GONE);
                                fixbtn.setVisibility(View.VISIBLE);
                                startSensor();
                            }
                        });
                    }
                });

                break;
            case R.id.zoom4:
                animZoomIn = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_in_3);
                img.startAnimation(animZoomIn);

                layoutParams = new FrameLayout.LayoutParams(60,35);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.setMargins(0, 80, 40, 0);
                imgEdge.setLayoutParams(layoutParams);

                mKalmanAccX = new Kalman(0.0f);
                mKalmanAccY = new Kalman(0.0f);
                if (clickfix){
                    stopSensor();
                }else {
                    startSensor();
                }
                Log.d(TAG, "sensor start!!!!!!!!!!!!");

                fixbtn.setVisibility(View.VISIBLE);
                fixbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickfix = true;
                        fixbtn.setVisibility(View.GONE);
                        fixoffbtn.setVisibility(View.VISIBLE);
                        stopSensor();
                        fixoffbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clickfix = false;
                                fixoffbtn.setVisibility(View.GONE);
                                fixbtn.setVisibility(View.VISIBLE);
                                startSensor();
                            }
                        });
                    }
                });

                break;
            case R.id.zoom5:
                animZoomIn = AnimationUtils.loadAnimation(LocalImage.this, R.anim.zoom_in_4);
                img.startAnimation(animZoomIn);

                layoutParams = new FrameLayout.LayoutParams(40,20);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.setMargins(0, 80, 40, 0);
                imgEdge.setLayoutParams(layoutParams);

                mKalmanAccX = new Kalman(0.0f);
                mKalmanAccY = new Kalman(0.0f);
                if (clickfix){
                    stopSensor();
                }else {
                    startSensor();
                }
                Log.d(TAG, "sensor start!!!!!!!!!!!!");

                fixbtn.setVisibility(View.VISIBLE);
                fixbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickfix = true;
                        fixbtn.setVisibility(View.GONE);
                        fixoffbtn.setVisibility(View.VISIBLE);
                        stopSensor();
                        fixoffbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clickfix = false;
                                fixoffbtn.setVisibility(View.GONE);
                                fixbtn.setVisibility(View.VISIBLE);
                                startSensor();
                            }
                        });
                    }
                });

                break;

        }
    }

//    @Override
//    public void onMove(int x, int y,float deltaX) {
//
//      img.scrollBy(x * 10, y * 10);
//
//    }

    public void startSensor(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sel, accSensor, SensorManager.SENSOR_DELAY_GAME);
//        mAccelerometerController = new AccelerometerController(getApplicationContext(), this::onMove);
        Log.d(TAG, "startSensor creat!!!!!!!!!");
    }

    public void stopSensor(){
        sensorManager.unregisterListener(sel);
//        mAccelerometerController.releaseAllSensors();
        Log.d(TAG, "stopSensor !!!!!!");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
                return;

            x = event.values[0];
            y = event.values[2];

            // 칼만필터를 적용한다
            float filteredX = (float) mKalmanAccY.update(x);
            float filteredY = (float) mKalmanAccX.update(y);

//             이 주석을 풀면 칼만필터를 사용하지 않는다
//             filteredX = x;
//             filteredY = y;
//             부모 레이아웃을 스크롤시켜 마치 뷰객체(오브젝트)가 움직이는것처럼 보이게 한다
//             저장해둔 예전값과 현재값의 차를 넣어 변화를 감지한다

            img.scrollBy((int) -((mX - filteredX) * 130), (int) -((mY - filteredY) * 40));
//            img.setTranslationX((int) -((mX - filteredX) * 200));
//            img.setTranslationY((int) -((mY - filteredY) * 100));

            imgEdge.setTranslationX((int) ((mX - filteredX) * 100));
            imgEdge.setTranslationY((int) -((mY - filteredY) * 50));

            // 예전값을 저장한다
            mX = filteredX;
            mY = filteredY;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

}








