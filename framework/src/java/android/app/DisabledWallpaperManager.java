package android.app;

/* loaded from: classes.dex */
final class DisabledWallpaperManager extends android.app.WallpaperManager {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = android.app.DisabledWallpaperManager.class.getSimpleName();
    private static android.app.DisabledWallpaperManager sInstance;

    static android.app.DisabledWallpaperManager getInstance() {
        if (sInstance == null) {
            sInstance = new android.app.DisabledWallpaperManager();
        }
        return sInstance;
    }

    private DisabledWallpaperManager() {
    }

    @Override // android.app.WallpaperManager
    public boolean isWallpaperSupported() {
        return false;
    }

    @Override // android.app.WallpaperManager
    public boolean isSetWallpaperAllowed() {
        return false;
    }

    private static <T> T unsupported() {
        return null;
    }

    private static boolean unsupportedBoolean() {
        return false;
    }

    private static int unsupportedInt() {
        return -1;
    }

    @Override // android.app.WallpaperManager
    public android.graphics.drawable.Drawable getDrawable() {
        return (android.graphics.drawable.Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.drawable.Drawable getBuiltInDrawable() {
        return (android.graphics.drawable.Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.drawable.Drawable getBuiltInDrawable(int i) {
        return (android.graphics.drawable.Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.drawable.Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2) {
        return (android.graphics.drawable.Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.drawable.Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2, int i3) {
        return (android.graphics.drawable.Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.drawable.Drawable peekDrawable() {
        return (android.graphics.drawable.Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.drawable.Drawable getFastDrawable() {
        return (android.graphics.drawable.Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.drawable.Drawable peekFastDrawable() {
        return (android.graphics.drawable.Drawable) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.Bitmap getBitmap() {
        return (android.graphics.Bitmap) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.Bitmap getBitmap(boolean z) {
        return (android.graphics.Bitmap) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.graphics.Bitmap getBitmapAsUser(int i, boolean z) {
        return (android.graphics.Bitmap) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.os.ParcelFileDescriptor getWallpaperFile(int i) {
        return (android.os.ParcelFileDescriptor) unsupported();
    }

    @Override // android.app.WallpaperManager
    public void addOnColorsChangedListener(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener, android.os.Handler handler) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void addOnColorsChangedListener(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener, android.os.Handler handler, int i) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void removeOnColorsChangedListener(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void removeOnColorsChangedListener(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener, int i) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.app.WallpaperColors getWallpaperColors(int i) {
        return (android.app.WallpaperColors) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.app.WallpaperColors getWallpaperColors(int i, int i2) {
        return (android.app.WallpaperColors) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.os.ParcelFileDescriptor getWallpaperFile(int i, int i2) {
        return (android.os.ParcelFileDescriptor) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.os.ParcelFileDescriptor getWallpaperFile(int i, boolean z) {
        return (android.os.ParcelFileDescriptor) unsupported();
    }

    @Override // android.app.WallpaperManager
    public void forgetLoadedWallpaper() {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.app.WallpaperInfo getWallpaperInfo() {
        return (android.app.WallpaperInfo) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.os.ParcelFileDescriptor getWallpaperInfoFile() {
        return (android.os.ParcelFileDescriptor) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.app.WallpaperInfo getWallpaperInfoForUser(int i) {
        return (android.app.WallpaperInfo) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.app.WallpaperInfo getWallpaperInfo(int i) {
        return (android.app.WallpaperInfo) unsupported();
    }

    @Override // android.app.WallpaperManager
    public android.app.WallpaperInfo getWallpaperInfo(int i, int i2) {
        return (android.app.WallpaperInfo) unsupported();
    }

    @Override // android.app.WallpaperManager
    public int getWallpaperId(int i) {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public int getWallpaperIdForUser(int i, int i2) {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public android.content.Intent getCropAndSetWallpaperIntent(android.net.Uri uri) {
        return (android.content.Intent) unsupported();
    }

    @Override // android.app.WallpaperManager
    public void setResource(int i) throws java.io.IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public int setResource(int i, int i2) throws java.io.IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public void setBitmap(android.graphics.Bitmap bitmap) throws java.io.IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public int setBitmap(android.graphics.Bitmap bitmap, android.graphics.Rect rect, boolean z) throws java.io.IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public int setBitmap(android.graphics.Bitmap bitmap, android.graphics.Rect rect, boolean z, int i) throws java.io.IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public int setBitmap(android.graphics.Bitmap bitmap, android.graphics.Rect rect, boolean z, int i, int i2) throws java.io.IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public void setStream(java.io.InputStream inputStream) throws java.io.IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public int setStream(java.io.InputStream inputStream, android.graphics.Rect rect, boolean z) throws java.io.IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public int setStream(java.io.InputStream inputStream, android.graphics.Rect rect, boolean z, int i) throws java.io.IOException {
        unsupported();
        return 0;
    }

    @Override // android.app.WallpaperManager
    public boolean hasResourceWallpaper(int i) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public int getDesiredMinimumWidth() {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public int getDesiredMinimumHeight() {
        return unsupportedInt();
    }

    @Override // android.app.WallpaperManager
    public void suggestDesiredDimensions(int i, int i2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void setDisplayPadding(android.graphics.Rect rect) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void setDisplayOffset(android.os.IBinder iBinder, int i, int i2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clearWallpaper() {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clearWallpaper(int i, int i2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public boolean setWallpaperComponent(android.content.ComponentName componentName) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public boolean setWallpaperComponent(android.content.ComponentName componentName, int i) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public void setWallpaperOffsets(android.os.IBinder iBinder, float f, float f2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void setWallpaperOffsetSteps(float f, float f2) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void sendWallpaperCommand(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clearWallpaperOffsets(android.os.IBinder iBinder) {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clear() throws java.io.IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public void clear(int i) throws java.io.IOException {
        unsupported();
    }

    @Override // android.app.WallpaperManager
    public boolean isWallpaperBackupEligible(int i) {
        return unsupportedBoolean();
    }

    @Override // android.app.WallpaperManager
    public boolean wallpaperSupportsWcg(int i) {
        return unsupportedBoolean();
    }
}
