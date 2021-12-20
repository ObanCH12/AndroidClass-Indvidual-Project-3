package com.example.individualproject3;

import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.individualproject3.databinding.ActivityBeginnerLevel3Binding;
import com.jjoe64.graphview.GraphView;

import butterknife.ButterKnife;

public class BeginnerLevel3 extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MYPREF = "My_PREF_FILE_NAME";
    public static final String EMAIL = "EmailKey";
    private MediaPlayer mMediaPlayer;
    private SoundEffects mSoundEffects;

    MyStrtDrggngLstnr mStrtDrg;
    MyEndDrgLstnr mEndDrg;

    GameView n;

    int arrow;

    int move1, move2, move3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner_level3);
        sharedpreferences = getSharedPreferences(MYPREF,
                Context.MODE_PRIVATE);
        mSoundEffects =  SoundEffects.getInstance(getApplicationContext());
        n=(GameView) findViewById(R.id.cc);
        n.setLevel(3);
        n.postInvalidate();

        ButterKnife.bind(this);

        mStrtDrg=new MyStrtDrggngLstnr();
        mEndDrg=new MyEndDrgLstnr();

        findViewById(R.id.leftArrow).setOnLongClickListener(mStrtDrg);
        findViewById(R.id.rightArrow).setOnLongClickListener(mStrtDrg);
        findViewById(R.id.downArrow).setOnLongClickListener(mStrtDrg);
        findViewById(R.id.upArrow).setOnLongClickListener(mStrtDrg);

        findViewById(R.id.btn_input1).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input2).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input3).setOnDragListener(mEndDrg);

        findViewById(R.id.btn_play).setOnClickListener(this::playBall);
    }

    private void playBall(View view) {
        if ((move1 == 2) && (move2 == 4) && (move3 == 1)) {

            ImageView iv2 = findViewById(R.id.ball);
            ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
            animator.setDuration(3000);
            animator.setInterpolator(new LinearInterpolator());

            animator.addUpdateListener(animation -> {
                float rotation = (float) animation.getAnimatedValue();
                iv2.setRotation(rotation);
            });

            animator.start();
            Animation iv2anim = AnimationUtils.loadAnimation(this, R.anim.level3anim);
            iv2.startAnimation(iv2anim);
            mSoundEffects.playGameOver();
            AlertDialog.Builder alert= new AlertDialog.Builder(this);
            alert.setMessage("Congratulations, you completed the level!");
            alert.setPositiveButton("Continue", (dialogInterface, i) -> {
                String email = sharedpreferences.getString(EMAIL, "");
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        ChildAccount t = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .dataOrgDAO()
                                .findChildByEmail(email);
                        if (t.getLevel3()==0){
                            t.setLevel3(1);
                            t.setLevelsCompleted(t.getLevelsCompleted()+1);
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .dataOrgDAO()
                                    .updateChild(t);
                        }

                    }
                }).start();
                startActivity(new Intent(BeginnerLevel3.this, LevelSelect.class));
            });
            alert.create().show();
        }
        else
            Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
    }

    private class MyStrtDrggngLstnr implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v) {
            WithDragShadow shadow= new WithDragShadow(v);
            if (v.getId()==R.id.upArrow)
                arrow = 1;
            if (v.getId()==R.id.downArrow)
                arrow = 2;
            if (v.getId()==R.id.leftArrow)
                arrow = 3;
            if (v.getId()==R.id.rightArrow)
                arrow = 4;
            ClipData data= ClipData.newPlainText("","");;
            v.startDrag(data,shadow,v,0);
            return false;
        }
    }

    private class MyEndDrgLstnr implements View.OnDragListener{
        @Override
        public boolean onDrag(View v, DragEvent event) {

            if(event.getAction()==DragEvent.ACTION_DROP){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (v.getId()==R.id.btn_input1){
                        move1 = arrow;
                    }
                    if (v.getId()==R.id.btn_input2){
                        move2 = arrow;
                    }
                    if (v.getId()==R.id.btn_input3){
                        move3 = arrow;
                    }
                    v.setBackground(((Button)event.getLocalState()).getBackground());
                }
            }
            return true;
        }
    }

    private class WithDragShadow extends View.DragShadowBuilder{

        public WithDragShadow(View v){ super(v);  }

        @Override public void onDrawShadow(Canvas canvas) { super.onDrawShadow(canvas);}

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this, R.raw.gameplay);
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

}