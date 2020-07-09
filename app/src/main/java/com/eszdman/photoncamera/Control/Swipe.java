package com.eszdman.photoncamera.Control;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.eszdman.photoncamera.R;
import com.eszdman.photoncamera.api.CameraReflectionApi;
import com.eszdman.photoncamera.api.Interface;

public class Swipe {
    private static String TAG = "Swipe";
    private GestureDetector gestureDetector;
    private View.OnTouchListener touchListener;
    @SuppressLint("ClickableViewAccessibility")
    public void RunDetection(){
        Log.d(TAG,"SwipeDetection - ON");
        ConstraintLayout manualmode = Interface.i.mainActivity.findViewById(R.id.manual_mode);
        gestureDetector = new GestureDetector(Interface.i.mainActivity, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Animation slideUp = AnimationUtils.loadAnimation(Interface.i.mainActivity, R.anim.slide_up);
                Animation slideDown = AnimationUtils.loadAnimation(Interface.i.mainActivity, R.anim.animate_slide_down_exit);
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            Log.d(TAG, "Right");
                        } else {
                            Log.d(TAG, "Left");
                        }
                        return true;
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        Log.d(TAG, "Bottom");//it swipes from top to bottom
                        if(Interface.i.settings.ManualMode) manualmode.startAnimation(slideDown);
                        Interface.i.settings.ManualMode = false;
                        CameraReflectionApi.set(Interface.i.camera.mPreviewRequest,CaptureRequest.CONTROL_AE_MODE,Interface.i.settings.aeModeOn);
                        Interface.i.camera.rebuildPreview();
                        manualmode.setVisibility(View.GONE);
                    } else {
                        Log.d(TAG, "Top");//it swipes from bottom to top
                        if(!Interface.i.settings.ManualMode) manualmode.startAnimation(slideUp);
                        Interface.i.settings.ManualMode = true;
                        Interface.i.camera.rebuildPreview();
                        manualmode.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
                return false;
            }
        });
        touchListener = (view, motionEvent) -> gestureDetector.onTouchEvent(motionEvent);
        View holder = Interface.i.mainActivity.findViewById(R.id.textureHolder);
        Log.d(TAG,"input:"+holder);
        if(holder != null) holder.setOnTouchListener(touchListener);
    }
}
