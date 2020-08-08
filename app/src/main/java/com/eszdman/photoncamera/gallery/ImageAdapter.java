package com.eszdman.photoncamera.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.io.File;
import java.util.Arrays;

import com.bumptech.glide.Glide;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

public class ImageAdapter extends PagerAdapter {
    private Context context;
    private File[] file;

    ImageAdapter(Context context, File[] file) {
        this.context = context;
        this.file = file;
    }
    @Override
    public int getCount() {
        return file.length;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Arrays.sort(file, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        ImageView imageView = new ImageView(context);
        Glide
                .with(context)
                .load(file[position])
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}


