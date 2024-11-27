package android.service.wallpaper;

/* loaded from: classes3.dex */
public class EngineWindowPage {
    private android.graphics.Bitmap mScreenShot;
    private volatile long mLastUpdateTime = 0;
    private java.util.Set<android.graphics.RectF> mCallbackAreas = new android.util.ArraySet();
    private java.util.Map<android.graphics.RectF, android.app.WallpaperColors> mRectFColors = new android.util.ArrayMap();

    public void addArea(android.graphics.RectF rectF) {
        this.mCallbackAreas.add(rectF);
    }

    public void addWallpaperColors(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors) {
        this.mCallbackAreas.add(rectF);
        this.mRectFColors.put(rectF, wallpaperColors);
    }

    public android.graphics.Bitmap getBitmap() {
        if (this.mScreenShot == null || this.mScreenShot.isRecycled()) {
            return null;
        }
        return this.mScreenShot;
    }

    public void removeArea(android.graphics.RectF rectF) {
        this.mCallbackAreas.remove(rectF);
        this.mRectFColors.remove(rectF);
    }

    public void setLastUpdateTime(long j) {
        this.mLastUpdateTime = j;
    }

    public long getLastUpdateTime() {
        return this.mLastUpdateTime;
    }

    public android.app.WallpaperColors getColors(android.graphics.RectF rectF) {
        return this.mRectFColors.get(rectF);
    }

    public void setBitmap(android.graphics.Bitmap bitmap) {
        this.mScreenShot = bitmap;
    }

    public java.util.Set<android.graphics.RectF> getAreas() {
        return this.mCallbackAreas;
    }

    public void removeColor(android.graphics.RectF rectF) {
        this.mRectFColors.remove(rectF);
    }
}
