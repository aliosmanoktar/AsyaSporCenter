package com.aliosman.asyasporcenter.Values;

import android.graphics.Bitmap;

import java.io.Serializable;

public interface ISendImage extends Serializable {
    void SendBitmap(Bitmap image);
}
