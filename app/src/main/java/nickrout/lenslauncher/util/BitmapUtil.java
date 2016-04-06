package nickrout.lenslauncher.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

/**
 * Created by nickrout on 2016/04/02.
 */
public class BitmapUtil {

    // Source: http://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bitmapDrawable.setAntiAlias(true);
            bitmapDrawable.setDither(true);
            bitmapDrawable.setTargetDensity(DisplayMetrics.DENSITY_XXXHIGH);
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap resIdToBitmap(Resources res, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPreferQualityOverSpeed = true;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        return bitmap;
    }

    public static Bitmap packageNameToBitmap(PackageManager packageManager, String packageName) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            Resources resources = packageManager.getResourcesForApplication(applicationInfo);
            int appIconResId = applicationInfo.icon;
            return resIdToBitmap(resources, appIconResId);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}