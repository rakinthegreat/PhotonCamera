package com.eszdman.photoncamera.Control;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.widget.SeekBar;
import android.widget.TextView;
import com.eszdman.photoncamera.Parameters.ExposureIndex;
import com.eszdman.photoncamera.Parameters.IsoExpoSelector;
import com.eszdman.photoncamera.R;
import com.eszdman.photoncamera.api.CameraReflectionApi;
import com.eszdman.photoncamera.api.Interface;
import com.eszdman.photoncamera.ui.CameraFragment;

public class Manual {
    public double expvalue = 1.0/20;
    public int isovalue = 1600;
    @SuppressLint("NewApi")
    public void Init() {
        SeekBar isoSlider = Interface.i.mainActivity.findViewById(R.id.isoSlider);
        TextView isoValue = Interface.i.mainActivity.findViewById(R.id.isoValue);
        int miniso = IsoExpoSelector.getISOLOWExt();
        isoSlider.setMin(1);
        isoSlider.setMax(IsoExpoSelector.getISOHIGHExt()/miniso);
        isoSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                isovalue = progress * miniso;
                isoValue.setText(String.valueOf(isovalue));
                try{
                    //Interface.i.camera.mPreviewRequestBuilder.set(CaptureRequest.SENSOR_SENSITIVITY,isovalue);
                    CameraReflectionApi.set(Interface.i.camera.mPreviewRequest,CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_OFF);
                    CameraReflectionApi.set(Interface.i.camera.mPreviewRequest,CaptureRequest.SENSOR_SENSITIVITY,(int)(isovalue/IsoExpoSelector.getMPY()));
                    CameraReflectionApi.set(Interface.i.camera.mPreviewRequest,CaptureRequest.SENSOR_EXPOSURE_TIME,Interface.i.camera.mPreviewExposuretime);
                    Interface.i.camera.rebuildPreview();
                } catch (Exception ignored){}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        isoSlider.setProgress(isoSlider.getMin());
        isoSlider.setProgress(isoSlider.getMax()/2);
        SeekBar expSlider = Interface.i.mainActivity.findViewById(R.id.expSlider);
        TextView expValue = Interface.i.mainActivity.findViewById(R.id.expValue);
        long minexp = IsoExpoSelector.getEXPLOW();
        long maxexp = IsoExpoSelector.getEXPHIGH();
        expSlider.setMin((int)(Math.log((double)(minexp)/ ExposureIndex.sec)/Math.log(2))-1);
        expSlider.setMax((int)(Math.log((double)(maxexp)/ ExposureIndex.sec)/Math.log(2))+1);
        expSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                expvalue = Math.pow(2,expSlider.getProgress());
                if(expSlider.getProgress() == expSlider.getMax()) expvalue = ((double)maxexp)/ExposureIndex.sec;
                if(expSlider.getProgress() == expSlider.getMin()) expvalue = ((double)minexp)/ExposureIndex.sec;
                if(expvalue < 1.0) {
                    expValue.setText("1/"+(int)(1.0 / expvalue));
                    try{
                        //Interface.i.camera.mPreviewRequestBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME,ExposureIndex.sec2time(expvalue));
                        CameraReflectionApi.set(Interface.i.camera.mPreviewRequest,CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_OFF);
                        CameraReflectionApi.set(Interface.i.camera.mPreviewRequest,CaptureRequest.SENSOR_EXPOSURE_TIME,ExposureIndex.sec2time(expvalue));
                        CameraReflectionApi.set(Interface.i.camera.mPreviewRequest,CaptureRequest.SENSOR_SENSITIVITY,Interface.i.camera.mPreviewIso);
                        Interface.i.camera.rebuildPreview();
                    } catch (Exception ignored){}
                } else expValue.setText(String.valueOf((int)expvalue));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        expSlider.setProgress(expSlider.getMin());
        expSlider.setProgress(-5);
        SeekBar focusSlider = Interface.i.mainActivity.findViewById(R.id.focusSlider);
        TextView focusValue = Interface.i.mainActivity.findViewById(R.id.focusValue);
        float min = CameraFragment.mCameraCharacteristics.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
        float max = CameraFragment.mCameraCharacteristics.get(CameraCharacteristics.LENS_INFO_HYPERFOCAL_DISTANCE);
        focusSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Float progressFloat = new Float(progress);
                if(progressFloat == 1000f) {
                    focusValue.setText("INF");
                }
                else if(progressFloat <= 100f) {
                    focusValue.setText(progressFloat + "cm");
                }
                else {
                    focusValue.setText(progressFloat / 100 + "m");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
}
