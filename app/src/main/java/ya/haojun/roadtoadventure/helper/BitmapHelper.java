package ya.haojun.roadtoadventure.helper;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class BitmapHelper {

    public static Bitmap resize(Bitmap bmp, int maxW, int maxH) {
        if (bmp == null)
            return null;
        // old w , h
        int oldWidth = bmp.getWidth();
        int oldHeight = bmp.getHeight();
        // conform upload format
        if (oldWidth <= maxW && oldHeight <= maxH)
            return bmp;
        // new format
        int newWidth = oldWidth;
        int newHeight = oldHeight;
        do {
            newWidth *= 0.9;
            newHeight *= 0.9;
        } while (newWidth > maxW || newHeight > maxH);
        // scale %
        float scaleWidth = ((float) newWidth) / oldWidth;
        float scaleHeight = ((float) newHeight) / oldHeight;
        // scale matrix params
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bmp, 0, 0, oldWidth, oldHeight, matrix, true);
    }

    public static Bitmap viewToBitmap(View view) {
        // Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        // Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        // Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            // has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            // does not have background drawable, then draw white background on
            // the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        // return the bitmap
        return returnedBitmap;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

}
