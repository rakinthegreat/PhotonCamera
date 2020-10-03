package com.eszdman.photoncamera.ui.camera.binding;

import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;

import com.eszdman.photoncamera.R;
import com.eszdman.photoncamera.ui.camera.model.CameraFragmentModel;
import com.manual.KnobView;
import com.manual.Rotation;

/**
 * class to handel custom bindings that should get applied when a model change
 */
public class CustomBinding {

    //handel the rotation that should get applied when the CameraFragmentModels rotation change
    //the view item must add bindRotate="@{uimodel}"/>
    @BindingAdapter("bindRotate")
    public static void rotatetView(View view, CameraFragmentModel model)
    {
        if (model != null)
            view.animate().rotation(model.getOrientation()).setDuration(model.getDuration()).start();
    }

    //handel the rotation that should get applied to "@+id/buttons_container" when the CameraFragmentModels rotation change
    //the ui item must add bindChildsRotate="@{uimodel}"/>
    @BindingAdapter("bindChildsRotate")
    public static void rotatetKnobView(KnobView view, CameraFragmentModel model)
    {
        if (model != null) {
            int orientation = model.getOrientation();
            KnobView defaultKnobView = view;
            defaultKnobView.setKnobItemsRotation(Rotation.fromDeviceOrientation(orientation));
        }
    }
}
