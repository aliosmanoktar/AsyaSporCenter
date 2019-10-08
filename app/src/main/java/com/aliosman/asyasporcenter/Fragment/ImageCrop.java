package com.aliosman.asyasporcenter.Fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.ISendImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ImageCrop extends BottomSheetDialogFragment {
    String TAG = getClass().getName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.layout_image_crop,container,false);
        final CropImageView ImageView = view.findViewById(R.id.cropImageView);
        final LinearLayout layout = view.findViewById(R.id.linear);
        final Button btn_cop=view.findViewById(R.id.crop);
        //ImageView.setBackgroundColor(getResources().getColor(R.color.background));
        ImageView.setAspectRatio(1,1);
        final ISendImage send =(ISendImage) getArguments().getSerializable("send");
        Uri bmp = (Uri)getArguments().getParcelable("uri");
        ImageView.setImageUriAsync(bmp);
        btn_cop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap crop=ImageView.getCroppedImage();
                send.SendBitmap(crop);
                dismiss();
            }
        });

        ImageView.post(new Runnable() {
            @Override
            public void run() {
                DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
                int height = displayMetrics.heightPixels;
                int maxHeight = (int) (height*0.55);
                if (ImageView.getHeight()>maxHeight){
                    ImageView.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,maxHeight));
                }
            }
        });

        return view;
    }
}
