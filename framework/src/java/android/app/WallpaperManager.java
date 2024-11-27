package android.app;

/* loaded from: classes.dex */
public class WallpaperManager {
    public static final java.lang.String ACTION_CHANGE_LIVE_WALLPAPER = "android.service.wallpaper.CHANGE_LIVE_WALLPAPER";
    public static final java.lang.String ACTION_CROP_AND_SET_WALLPAPER = "android.service.wallpaper.CROP_AND_SET_WALLPAPER";
    public static final java.lang.String ACTION_LIVE_WALLPAPER_CHOOSER = "android.service.wallpaper.LIVE_WALLPAPER_CHOOSER";
    public static final java.lang.String COMMAND_DISPLAY_SWITCH = "android.wallpaper.displayswitch";
    public static final java.lang.String COMMAND_DROP = "android.home.drop";
    public static final java.lang.String COMMAND_FREEZE = "android.wallpaper.freeze";
    public static final java.lang.String COMMAND_GOING_TO_SLEEP = "android.wallpaper.goingtosleep";
    public static final java.lang.String COMMAND_KEYGUARD_GOING_AWAY = "android.wallpaper.keyguardgoingaway";
    public static final java.lang.String COMMAND_REAPPLY = "android.wallpaper.reapply";
    public static final java.lang.String COMMAND_SECONDARY_TAP = "android.wallpaper.secondaryTap";
    public static final java.lang.String COMMAND_TAP = "android.wallpaper.tap";
    public static final java.lang.String COMMAND_UNFREEZE = "android.wallpaper.unfreeze";
    public static final java.lang.String COMMAND_WAKING_UP = "android.wallpaper.wakingup";
    private static final boolean DEBUG = false;
    public static final java.lang.String EXTRA_FROM_FOREGROUND_APP = "android.service.wallpaper.extra.FROM_FOREGROUND_APP";
    public static final java.lang.String EXTRA_LIVE_WALLPAPER_COMPONENT = "android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT";
    public static final java.lang.String EXTRA_NEW_WALLPAPER_ID = "android.service.wallpaper.extra.ID";
    public static final int FLAG_LOCK = 2;
    public static final int FLAG_SYSTEM = 1;
    public static final int LANDSCAPE = 1;
    public static final int ORIENTATION_UNKNOWN = -1;
    public static final int PORTRAIT = 0;
    private static final java.lang.String PROP_LOCK_WALLPAPER = "ro.config.lock_wallpaper";
    private static final java.lang.String PROP_WALLPAPER = "ro.config.wallpaper";
    private static final java.lang.String PROP_WALLPAPER_COMPONENT = "ro.config.wallpaper_component";
    static final long RETURN_DEFAULT_ON_SECURITY_EXCEPTION = 239784307;
    public static final int SQUARE_LANDSCAPE = 3;
    public static final int SQUARE_PORTRAIT = 2;
    static final long THROW_ON_SECURITY_EXCEPTION = 237508058;
    private static final java.lang.String WALLPAPER_CMF_PATH = "/wallpaper/image/";
    public static final java.lang.String WALLPAPER_PREVIEW_META_DATA = "android.wallpaper.preview";
    private static android.app.WallpaperManager.Globals sGlobals;
    private final android.app.WallpaperManager.ColorManagementProxy mCmProxy;
    private final android.content.Context mContext;
    private float mWallpaperXStep;
    private float mWallpaperYStep;
    private final boolean mWcgEnabled;
    private static java.lang.String TAG = "WallpaperManager";
    private static final android.graphics.RectF LOCAL_COLOR_BOUNDS = new android.graphics.RectF(0.0f, 0.0f, 1.0f, 1.0f);
    private static final java.lang.String VALUE_CMF_COLOR = android.os.SystemProperties.get("ro.boot.hardware.color");
    private static final java.lang.Object sSync = new java.lang.Object[0];
    private static java.lang.Boolean sIsMultiCropEnabled = null;

    public interface LocalWallpaperColorConsumer {
        void onColorsChanged(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScreenOrientation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SetWallpaperFlags {
    }

    public static int getOrientation(android.graphics.Point point) {
        float f = point.x / point.y;
        if (f >= 1.3333334f) {
            return 1;
        }
        if (f > 1.0f) {
            return 3;
        }
        return f > 0.75f ? 2 : 0;
    }

    public static int getRotatedOrientation(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            case 3:
                return 2;
            default:
                return -1;
        }
    }

    static class FastBitmapDrawable extends android.graphics.drawable.Drawable {
        private final android.graphics.Bitmap mBitmap;
        private int mDrawLeft;
        private int mDrawTop;
        private final int mHeight;
        private final android.graphics.Paint mPaint;
        private final int mWidth;

        private FastBitmapDrawable(android.graphics.Bitmap bitmap) {
            this.mBitmap = bitmap;
            this.mWidth = bitmap.getWidth();
            this.mHeight = bitmap.getHeight();
            setBounds(0, 0, this.mWidth, this.mHeight);
            this.mPaint = new android.graphics.Paint();
            this.mPaint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC));
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(android.graphics.Canvas canvas) {
            canvas.drawBitmap(this.mBitmap, this.mDrawLeft, this.mDrawTop, this.mPaint);
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -1;
        }

        @Override // android.graphics.drawable.Drawable
        public void setBounds(int i, int i2, int i3, int i4) {
            this.mDrawLeft = i + (((i3 - i) - this.mWidth) / 2);
            this.mDrawTop = i2 + (((i4 - i2) - this.mHeight) / 2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            throw new java.lang.UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(android.graphics.ColorFilter colorFilter) {
            throw new java.lang.UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public void setDither(boolean z) {
            throw new java.lang.UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public void setFilterBitmap(boolean z) {
            throw new java.lang.UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.mWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.mHeight;
        }

        @Override // android.graphics.drawable.Drawable
        public int getMinimumWidth() {
            return this.mWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public int getMinimumHeight() {
            return this.mHeight;
        }
    }

    private static class CachedWallpaper {
        final android.graphics.Bitmap mCachedWallpaper;
        final int mCachedWallpaperUserId;
        final int mWhich;

        CachedWallpaper(android.graphics.Bitmap bitmap, int i, int i2) {
            this.mCachedWallpaper = bitmap;
            this.mCachedWallpaperUserId = i;
            this.mWhich = i2;
        }

        boolean isValid(int i, int i2) {
            return i == this.mCachedWallpaperUserId && i2 == this.mWhich && !this.mCachedWallpaper.isRecycled();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Globals extends android.app.IWallpaperManagerCallback.Stub {
        private android.app.WallpaperManager.CachedWallpaper mCachedWallpaper;
        private boolean mColorCallbackRegistered;
        private android.graphics.Bitmap mDefaultWallpaper;
        private android.os.Handler mMainLooperHandler;
        private final android.app.IWallpaperManager mService;
        private final java.util.ArrayList<android.util.Pair<android.app.WallpaperManager.OnColorsChangedListener, android.os.Handler>> mColorListeners = new java.util.ArrayList<>();
        private android.util.ArrayMap<android.app.WallpaperManager.LocalWallpaperColorConsumer, android.util.ArraySet<android.graphics.RectF>> mLocalColorCallbackAreas = new android.util.ArrayMap<>();
        private android.app.ILocalWallpaperColorConsumer mLocalColorCallback = new android.app.ILocalWallpaperColorConsumer.Stub() { // from class: android.app.WallpaperManager.Globals.1
            @Override // android.app.ILocalWallpaperColorConsumer
            public void onColorsChanged(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors) {
                for (android.app.WallpaperManager.LocalWallpaperColorConsumer localWallpaperColorConsumer : android.app.WallpaperManager.Globals.this.mLocalColorCallbackAreas.keySet()) {
                    android.util.ArraySet arraySet = (android.util.ArraySet) android.app.WallpaperManager.Globals.this.mLocalColorCallbackAreas.get(localWallpaperColorConsumer);
                    if (arraySet != null && arraySet.contains(rectF)) {
                        localWallpaperColorConsumer.onColorsChanged(rectF, wallpaperColors);
                    }
                }
            }
        };

        Globals(android.app.IWallpaperManager iWallpaperManager, android.os.Looper looper) {
            this.mService = iWallpaperManager;
            this.mMainLooperHandler = new android.os.Handler(looper);
            forgetLoadedWallpaper();
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperChanged() {
            forgetLoadedWallpaper();
        }

        public void addOnColorsChangedListener(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener, android.os.Handler handler, int i, int i2) {
            synchronized (this) {
                if (!this.mColorCallbackRegistered) {
                    try {
                        this.mService.registerWallpaperColorsCallback(this, i, i2);
                        this.mColorCallbackRegistered = true;
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.app.WallpaperManager.TAG, "Can't register for color updates", e);
                    }
                }
                this.mColorListeners.add(new android.util.Pair<>(onColorsChangedListener, handler));
            }
        }

        public void addOnColorsChangedListener(android.app.WallpaperManager.LocalWallpaperColorConsumer localWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i, int i2, int i3) {
            synchronized (this) {
                for (android.graphics.RectF rectF : list) {
                    android.util.ArraySet<android.graphics.RectF> arraySet = this.mLocalColorCallbackAreas.get(localWallpaperColorConsumer);
                    if (arraySet == null) {
                        arraySet = new android.util.ArraySet<>();
                        this.mLocalColorCallbackAreas.put(localWallpaperColorConsumer, arraySet);
                    }
                    arraySet.add(rectF);
                }
                try {
                    this.mService.addOnLocalColorsChangedListener(this.mLocalColorCallback, list, i, i2, i3);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.app.WallpaperManager.TAG, "Can't register for local color updates", e);
                }
            }
        }

        public void removeOnColorsChangedListener(android.app.WallpaperManager.LocalWallpaperColorConsumer localWallpaperColorConsumer, int i, int i2, int i3) {
            synchronized (this) {
                android.util.ArraySet<android.graphics.RectF> remove = this.mLocalColorCallbackAreas.remove(localWallpaperColorConsumer);
                if (remove != null && remove.size() != 0) {
                    for (android.app.WallpaperManager.LocalWallpaperColorConsumer localWallpaperColorConsumer2 : this.mLocalColorCallbackAreas.keySet()) {
                        android.util.ArraySet<android.graphics.RectF> arraySet = this.mLocalColorCallbackAreas.get(localWallpaperColorConsumer2);
                        if (arraySet != null && localWallpaperColorConsumer2 != localWallpaperColorConsumer) {
                            remove.removeAll((android.util.ArraySet<? extends android.graphics.RectF>) arraySet);
                        }
                    }
                    try {
                        if (remove.size() > 0) {
                            this.mService.removeOnLocalColorsChangedListener(this.mLocalColorCallback, new java.util.ArrayList(remove), i, i2, i3);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.app.WallpaperManager.TAG, "Can't unregister for local color updates", e);
                    }
                }
            }
        }

        public void removeOnColorsChangedListener(final android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener, int i, int i2) {
            synchronized (this) {
                this.mColorListeners.removeIf(new java.util.function.Predicate() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        return android.app.WallpaperManager.Globals.lambda$removeOnColorsChangedListener$0(android.app.WallpaperManager.OnColorsChangedListener.this, (android.util.Pair) obj);
                    }
                });
                if (this.mColorListeners.size() == 0 && this.mColorCallbackRegistered) {
                    this.mColorCallbackRegistered = false;
                    try {
                        this.mService.unregisterWallpaperColorsCallback(this, i, i2);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.app.WallpaperManager.TAG, "Can't unregister color updates", e);
                    }
                }
            }
        }

        static /* synthetic */ boolean lambda$removeOnColorsChangedListener$0(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener, android.util.Pair pair) {
            return pair.first == onColorsChangedListener;
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperColorsChanged(final android.app.WallpaperColors wallpaperColors, final int i, final int i2) {
            synchronized (this) {
                java.util.Iterator<android.util.Pair<android.app.WallpaperManager.OnColorsChangedListener, android.os.Handler>> it = this.mColorListeners.iterator();
                while (it.hasNext()) {
                    final android.util.Pair<android.app.WallpaperManager.OnColorsChangedListener, android.os.Handler> next = it.next();
                    android.os.Handler handler = next.second;
                    if (next.second == null) {
                        handler = this.mMainLooperHandler;
                    }
                    handler.post(new java.lang.Runnable() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.WallpaperManager.Globals.this.lambda$onWallpaperColorsChanged$1(next, wallpaperColors, i, i2);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWallpaperColorsChanged$1(android.util.Pair pair, android.app.WallpaperColors wallpaperColors, int i, int i2) {
            boolean contains;
            synchronized (android.app.WallpaperManager.sGlobals) {
                contains = this.mColorListeners.contains(pair);
            }
            if (contains) {
                ((android.app.WallpaperManager.OnColorsChangedListener) pair.first).onColorsChanged(wallpaperColors, i, i2);
            }
        }

        android.app.WallpaperColors getWallpaperColors(int i, int i2, int i3) {
            android.app.WallpaperManager.checkExactlyOneWallpaperFlagSet(i);
            try {
                return this.mService.getWallpaperColors(i, i2, i3);
            } catch (android.os.RemoteException e) {
                return null;
            }
        }

        public android.graphics.Bitmap peekWallpaperBitmap(android.content.Context context, boolean z, int i, android.app.WallpaperManager.ColorManagementProxy colorManagementProxy) {
            return peekWallpaperBitmap(context, z, i, context.getUserId(), false, colorManagementProxy);
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x00bc A[Catch: all -> 0x00dc, TryCatch #3 {, blocks: (B:5:0x002c, B:7:0x0030, B:9:0x0038, B:11:0x0040, B:12:0x0044, B:15:0x0046, B:18:0x0058, B:20:0x00bc, B:21:0x00c3, B:23:0x00c5, B:34:0x00d8, B:35:0x00db, B:46:0x0080, B:47:0x0083, B:38:0x00b5, B:17:0x0049, B:41:0x0063, B:43:0x0069, B:45:0x0072, B:49:0x0085, B:51:0x008f, B:52:0x0099, B:37:0x009b), top: B:4:0x002c, inners: #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:23:0x00c5 A[Catch: all -> 0x00dc, DONT_GENERATE, TRY_LEAVE, TryCatch #3 {, blocks: (B:5:0x002c, B:7:0x0030, B:9:0x0038, B:11:0x0040, B:12:0x0044, B:15:0x0046, B:18:0x0058, B:20:0x00bc, B:21:0x00c3, B:23:0x00c5, B:34:0x00d8, B:35:0x00db, B:46:0x0080, B:47:0x0083, B:38:0x00b5, B:17:0x0049, B:41:0x0063, B:43:0x0069, B:45:0x0072, B:49:0x0085, B:51:0x008f, B:52:0x0099, B:37:0x009b), top: B:4:0x002c, inners: #2 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public android.graphics.Bitmap peekWallpaperBitmap(android.content.Context context, boolean z, int i, int i2, boolean z2, android.app.WallpaperManager.ColorManagementProxy colorManagementProxy) {
            android.graphics.Bitmap bitmap;
            if (this.mService != null) {
                try {
                    try {
                        android.os.Trace.beginSection("WPMS.isWallpaperSupported");
                        if (!this.mService.isWallpaperSupported(context.getOpPackageName())) {
                            return null;
                        }
                    } finally {
                        android.os.Trace.endSection();
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            synchronized (this) {
                if (this.mCachedWallpaper != null && this.mCachedWallpaper.isValid(i2, i) && context.checkSelfPermission(android.Manifest.permission.READ_WALLPAPER_INTERNAL) == 0) {
                    return this.mCachedWallpaper.mCachedWallpaper;
                }
                this.mCachedWallpaper = null;
                try {
                    try {
                        android.os.Trace.beginSection("WPMS.getCurrentWallpaperLocked");
                        bitmap = getCurrentWallpaperLocked(context, i, i2, z2, colorManagementProxy);
                    } catch (java.lang.OutOfMemoryError e2) {
                        android.util.Log.w(android.app.WallpaperManager.TAG, "Out of memory loading the current wallpaper: " + e2);
                        android.os.Trace.endSection();
                        bitmap = null;
                        if (bitmap != null) {
                        }
                    } catch (java.lang.SecurityException e3) {
                        if (android.app.compat.CompatChanges.isChangeEnabled(android.app.WallpaperManager.RETURN_DEFAULT_ON_SECURITY_EXCEPTION) && !android.app.compat.CompatChanges.isChangeEnabled(android.app.WallpaperManager.THROW_ON_SECURITY_EXCEPTION)) {
                            android.util.Log.w(android.app.WallpaperManager.TAG, "No permission to access wallpaper, returning default wallpaper to avoid crashing legacy app.");
                            return getDefaultWallpaper(context, 1);
                        }
                        if (context.getApplicationInfo().targetSdkVersion >= 27) {
                            throw e3;
                        }
                        android.util.Log.w(android.app.WallpaperManager.TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
                        android.os.Trace.endSection();
                        bitmap = null;
                        if (bitmap != null) {
                        }
                    }
                    if (bitmap != null) {
                        this.mCachedWallpaper = new android.app.WallpaperManager.CachedWallpaper(bitmap, i2, i);
                        return bitmap;
                    }
                    if (z || (i == 2 && isStaticWallpaper(2))) {
                        return getDefaultWallpaper(context, i);
                    }
                    return null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.graphics.Rect peekWallpaperDimensions(android.content.Context context, boolean z, int i, int i2) {
            android.graphics.Rect rect;
            java.io.InputStream openDefaultWallpaper;
            if (this.mService != null) {
                try {
                    if (!this.mService.isWallpaperSupported(context.getOpPackageName())) {
                        return new android.graphics.Rect();
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            synchronized (this) {
                try {
                    android.os.ParcelFileDescriptor wallpaperWithFeature = this.mService.getWallpaperWithFeature(context.getOpPackageName(), context.getAttributionTag(), this, i, new android.os.Bundle(), i2, true);
                    if (wallpaperWithFeature == null) {
                        rect = null;
                    } else {
                        try {
                            android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            android.graphics.BitmapFactory.decodeFileDescriptor(wallpaperWithFeature.getFileDescriptor(), null, options);
                            rect = new android.graphics.Rect(0, 0, options.outWidth, options.outHeight);
                        } finally {
                        }
                    }
                    if (wallpaperWithFeature != null) {
                        try {
                            wallpaperWithFeature.close();
                        } catch (android.os.RemoteException e2) {
                            e = e2;
                            android.util.Log.w(android.app.WallpaperManager.TAG, "peek wallpaper dimensions failed", e);
                        } catch (java.io.IOException e3) {
                        }
                    }
                } catch (android.os.RemoteException e4) {
                    e = e4;
                    rect = null;
                } catch (java.io.IOException e5) {
                    rect = null;
                }
            }
            if ((rect == null || rect.width() == 0 || rect.height() == 0) && ((z || (i == 2 && isStaticWallpaper(2))) && (openDefaultWallpaper = android.app.WallpaperManager.openDefaultWallpaper(context, i)) != null)) {
                try {
                    android.graphics.BitmapFactory.Options options2 = new android.graphics.BitmapFactory.Options();
                    options2.inJustDecodeBounds = true;
                    android.graphics.BitmapFactory.decodeStream(openDefaultWallpaper, null, options2);
                    rect = new android.graphics.Rect(0, 0, options2.outWidth, options2.outHeight);
                } finally {
                    libcore.io.IoUtils.closeQuietly(openDefaultWallpaper);
                }
            }
            return rect;
        }

        void forgetLoadedWallpaper() {
            synchronized (this) {
                this.mCachedWallpaper = null;
                this.mDefaultWallpaper = null;
            }
        }

        private android.graphics.Bitmap getCurrentWallpaperLocked(android.content.Context context, int i, int i2, final boolean z, final android.app.WallpaperManager.ColorManagementProxy colorManagementProxy) {
            if (this.mService == null) {
                android.util.Log.w(android.app.WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                android.os.Trace.beginSection("WPMS.getWallpaperWithFeature_" + i);
                android.os.ParcelFileDescriptor wallpaperWithFeature = this.mService.getWallpaperWithFeature(context.getOpPackageName(), context.getAttributionTag(), this, i, bundle, i2, true);
                android.os.Trace.endSection();
                if (wallpaperWithFeature == null) {
                    return null;
                }
                try {
                    android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(wallpaperWithFeature);
                    try {
                        android.graphics.Bitmap decodeBitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(context.getResources(), autoCloseInputStream), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda0
                            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                            public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                                android.app.WallpaperManager.Globals.lambda$getCurrentWallpaperLocked$2(z, colorManagementProxy, imageDecoder, imageInfo, source);
                            }
                        });
                        autoCloseInputStream.close();
                        return decodeBitmap;
                    } catch (java.lang.Throwable th) {
                        try {
                            autoCloseInputStream.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (java.io.IOException | java.lang.OutOfMemoryError e) {
                    android.util.Log.w(android.app.WallpaperManager.TAG, "Can't decode file", e);
                    return null;
                }
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        static /* synthetic */ void lambda$getCurrentWallpaperLocked$2(boolean z, android.app.WallpaperManager.ColorManagementProxy colorManagementProxy, android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
            imageDecoder.setMutableRequired(!z);
            if (colorManagementProxy != null) {
                colorManagementProxy.doColorManagement(imageDecoder, imageInfo);
            }
        }

        private android.graphics.Bitmap getDefaultWallpaper(android.content.Context context, int i) {
            android.os.Trace.beginSection("WPMS.getDefaultWallpaper_" + i);
            android.graphics.Bitmap bitmap = this.mDefaultWallpaper;
            if (bitmap == null || bitmap.isRecycled()) {
                android.os.Trace.beginSection("WPMS.openDefaultWallpaper");
                bitmap = null;
                try {
                    java.io.InputStream openDefaultWallpaper = android.app.WallpaperManager.openDefaultWallpaper(context, i);
                    try {
                        android.os.Trace.endSection();
                        if (openDefaultWallpaper != null) {
                            android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
                            android.os.Trace.beginSection("WPMS.decodeStream");
                            bitmap = android.graphics.BitmapFactory.decodeStream(openDefaultWallpaper, null, options);
                            android.os.Trace.endSection();
                        }
                        if (openDefaultWallpaper != null) {
                            openDefaultWallpaper.close();
                        }
                    } catch (java.lang.Throwable th) {
                        if (openDefaultWallpaper != null) {
                            try {
                                openDefaultWallpaper.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException | java.lang.OutOfMemoryError e) {
                    android.util.Log.w(android.app.WallpaperManager.TAG, "Can't decode stream", e);
                }
            }
            synchronized (this) {
                this.mDefaultWallpaper = bitmap;
            }
            android.os.Trace.endSection();
            return bitmap;
        }

        private boolean isStaticWallpaper(int i) {
            if (this.mService == null) {
                android.util.Log.w(android.app.WallpaperManager.TAG, "WallpaperService not running");
                throw new java.lang.RuntimeException(new android.os.DeadSystemException());
            }
            try {
                return this.mService.isStaticWallpaper(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    static void initGlobals(android.app.IWallpaperManager iWallpaperManager, android.os.Looper looper) {
        synchronized (sSync) {
            if (sGlobals == null) {
                sGlobals = new android.app.WallpaperManager.Globals(iWallpaperManager, looper);
            }
        }
    }

    WallpaperManager(android.app.IWallpaperManager iWallpaperManager, android.content.Context context, android.os.Handler handler) {
        this.mWallpaperXStep = -1.0f;
        this.mWallpaperYStep = -1.0f;
        this.mContext = context;
        if (iWallpaperManager != null) {
            initGlobals(iWallpaperManager, context.getMainLooper());
        }
        this.mWcgEnabled = context.getResources().getConfiguration().isScreenWideColorGamut() && context.getResources().getBoolean(com.android.internal.R.bool.config_enableWcgMode);
        this.mCmProxy = new android.app.WallpaperManager.ColorManagementProxy(context);
    }

    WallpaperManager() {
        this.mWallpaperXStep = -1.0f;
        this.mWallpaperYStep = -1.0f;
        this.mContext = null;
        this.mCmProxy = null;
        this.mWcgEnabled = false;
    }

    public static android.app.WallpaperManager getInstance(android.content.Context context) {
        return (android.app.WallpaperManager) context.getSystemService(android.content.Context.WALLPAPER_SERVICE);
    }

    public android.app.IWallpaperManager getIWallpaperManager() {
        return sGlobals.mService;
    }

    public boolean isLockscreenLiveWallpaperEnabled() {
        return true;
    }

    public static boolean isMultiCropEnabled() {
        if (sIsMultiCropEnabled == null) {
            sIsMultiCropEnabled = java.lang.Boolean.valueOf(com.android.window.flags.Flags.multiCrop());
        }
        return sIsMultiCropEnabled.booleanValue();
    }

    public boolean shouldEnableWideColorGamut() {
        return this.mWcgEnabled;
    }

    public android.graphics.drawable.Drawable getDrawable() {
        return getDrawable(1);
    }

    public android.graphics.drawable.Drawable getDrawable(int i) {
        android.graphics.Bitmap peekWallpaperBitmap = sGlobals.peekWallpaperBitmap(this.mContext, i != 2, i, getColorManagementProxy());
        if (peekWallpaperBitmap != null) {
            android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), peekWallpaperBitmap);
            bitmapDrawable.setDither(false);
            return bitmapDrawable;
        }
        return null;
    }

    public android.graphics.drawable.Drawable getBuiltInDrawable() {
        return getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, 1);
    }

    public android.graphics.drawable.Drawable getBuiltInDrawable(int i) {
        return getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, i);
    }

    public android.graphics.drawable.Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2) {
        return getBuiltInDrawable(i, i2, z, f, f2, 1);
    }

    public android.graphics.drawable.Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2, int i3) {
        android.graphics.RectF rectF;
        android.graphics.BitmapRegionDecoder bitmapRegionDecoder;
        android.graphics.Bitmap bitmap;
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        checkExactlyOneWallpaperFlagSet(i3);
        android.content.res.Resources resources = this.mContext.getResources();
        float max = java.lang.Math.max(0.0f, java.lang.Math.min(1.0f, f));
        float max2 = java.lang.Math.max(0.0f, java.lang.Math.min(1.0f, f2));
        java.io.InputStream openDefaultWallpaper = openDefaultWallpaper(this.mContext, i3);
        if (openDefaultWallpaper == null) {
            return null;
        }
        java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(openDefaultWallpaper);
        if (i <= 0 || i2 <= 0) {
            return new android.graphics.drawable.BitmapDrawable(resources, android.graphics.BitmapFactory.decodeStream(bufferedInputStream, null, null));
        }
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        android.graphics.BitmapFactory.decodeStream(bufferedInputStream, null, options);
        if (options.outWidth != 0 && options.outHeight != 0) {
            int i4 = options.outWidth;
            int i5 = options.outHeight;
            java.io.BufferedInputStream bufferedInputStream2 = new java.io.BufferedInputStream(openDefaultWallpaper(this.mContext, i3));
            int min = java.lang.Math.min(i4, i);
            int min2 = java.lang.Math.min(i5, i2);
            if (z) {
                rectF = getMaxCropRect(i4, i5, min, min2, max, max2);
            } else {
                float f3 = (i4 - min) * max;
                float f4 = (i5 - min2) * max2;
                rectF = new android.graphics.RectF(f3, f4, min + f3, min2 + f4);
            }
            android.graphics.Rect rect = new android.graphics.Rect();
            rectF.roundOut(rect);
            if (rect.width() <= 0 || rect.height() <= 0) {
                android.util.Log.w(TAG, "crop has bad values for full size image");
                return null;
            }
            int min3 = java.lang.Math.min(rect.width() / min, rect.height() / min2);
            try {
                bitmapRegionDecoder = android.graphics.BitmapRegionDecoder.newInstance((java.io.InputStream) bufferedInputStream2, true);
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "cannot open region decoder for default wallpaper");
                bitmapRegionDecoder = null;
            }
            if (bitmapRegionDecoder == null) {
                bitmap = null;
            } else {
                android.graphics.BitmapFactory.Options options2 = new android.graphics.BitmapFactory.Options();
                if (min3 > 1) {
                    options2.inSampleSize = min3;
                }
                bitmap = bitmapRegionDecoder.decodeRegion(rect, options2);
                bitmapRegionDecoder.recycle();
            }
            if (bitmap == null) {
                java.io.BufferedInputStream bufferedInputStream3 = new java.io.BufferedInputStream(openDefaultWallpaper(this.mContext, i3));
                android.graphics.BitmapFactory.Options options3 = new android.graphics.BitmapFactory.Options();
                if (min3 > 1) {
                    options3.inSampleSize = min3;
                }
                android.graphics.Bitmap decodeStream = android.graphics.BitmapFactory.decodeStream(bufferedInputStream3, null, options3);
                if (decodeStream != null) {
                    bitmap = android.graphics.Bitmap.createBitmap(decodeStream, rect.left, rect.top, rect.width(), rect.height());
                }
            }
            if (bitmap == null) {
                android.util.Log.w(TAG, "cannot decode default wallpaper");
                return null;
            }
            if (min > 0 && min2 > 0 && (bitmap.getWidth() != min || bitmap.getHeight() != min2)) {
                android.graphics.Matrix matrix = new android.graphics.Matrix();
                android.graphics.RectF rectF2 = new android.graphics.RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
                android.graphics.RectF rectF3 = new android.graphics.RectF(0.0f, 0.0f, min, min2);
                matrix.setRectToRect(rectF2, rectF3, android.graphics.Matrix.ScaleToFit.FILL);
                android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap((int) rectF3.width(), (int) rectF3.height(), android.graphics.Bitmap.Config.ARGB_8888);
                if (createBitmap != null) {
                    android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
                    android.graphics.Paint paint = new android.graphics.Paint();
                    paint.setFilterBitmap(true);
                    canvas.drawBitmap(bitmap, matrix, paint);
                    bitmap = createBitmap;
                }
            }
            return new android.graphics.drawable.BitmapDrawable(resources, bitmap);
        }
        android.util.Log.e(TAG, "default wallpaper dimensions are 0");
        return null;
    }

    private static android.graphics.RectF getMaxCropRect(int i, int i2, int i3, int i4, float f, float f2) {
        android.graphics.RectF rectF = new android.graphics.RectF();
        float f3 = i;
        float f4 = i2;
        float f5 = i3;
        float f6 = i4;
        if (f3 / f4 > f5 / f6) {
            rectF.top = 0.0f;
            rectF.bottom = f4;
            float f7 = f5 * (f4 / f6);
            rectF.left = (f3 - f7) * f;
            rectF.right = rectF.left + f7;
        } else {
            rectF.left = 0.0f;
            rectF.right = f3;
            float f8 = f6 * (f3 / f5);
            rectF.top = (f4 - f8) * f2;
            rectF.bottom = rectF.top + f8;
        }
        return rectF;
    }

    public android.graphics.drawable.Drawable peekDrawable() {
        return peekDrawable(1);
    }

    public android.graphics.drawable.Drawable peekDrawable(int i) {
        return getDrawable(i);
    }

    public android.graphics.drawable.Drawable getFastDrawable() {
        return getFastDrawable(1);
    }

    public android.graphics.drawable.Drawable getFastDrawable(int i) {
        android.graphics.Bitmap peekWallpaperBitmap = sGlobals.peekWallpaperBitmap(this.mContext, i != 2, i, getColorManagementProxy());
        if (peekWallpaperBitmap == null) {
            return null;
        }
        return new android.app.WallpaperManager.FastBitmapDrawable(peekWallpaperBitmap);
    }

    public android.graphics.drawable.Drawable peekFastDrawable() {
        return peekFastDrawable(1);
    }

    public android.graphics.drawable.Drawable peekFastDrawable(int i) {
        return getFastDrawable(i);
    }

    public boolean wallpaperSupportsWcg(int i) {
        android.app.WallpaperManager.ColorManagementProxy colorManagementProxy;
        android.graphics.Bitmap peekWallpaperBitmap;
        return (!shouldEnableWideColorGamut() || (peekWallpaperBitmap = sGlobals.peekWallpaperBitmap(this.mContext, false, i, (colorManagementProxy = getColorManagementProxy()))) == null || peekWallpaperBitmap.getColorSpace() == null || peekWallpaperBitmap.getColorSpace() == android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB) || !colorManagementProxy.isSupportedColorSpace(peekWallpaperBitmap.getColorSpace())) ? false : true;
    }

    public android.graphics.Bitmap getBitmap() {
        return getBitmap(false);
    }

    public android.graphics.Bitmap getBitmap(boolean z) {
        return getBitmapAsUser(this.mContext.getUserId(), z);
    }

    public android.graphics.Bitmap getBitmap(boolean z, int i) {
        return getBitmapAsUser(this.mContext.getUserId(), z, i);
    }

    public android.graphics.Bitmap getBitmapAsUser(int i, boolean z) {
        return sGlobals.peekWallpaperBitmap(this.mContext, true, 1, i, z, getColorManagementProxy());
    }

    public android.graphics.Bitmap getBitmapAsUser(int i, boolean z, int i2) {
        return getBitmapAsUser(i, z, i2, i2 != 2);
    }

    public android.graphics.Bitmap getBitmapAsUser(int i, boolean z, int i2, boolean z2) {
        return sGlobals.peekWallpaperBitmap(this.mContext, z2, i2, i, z, getColorManagementProxy());
    }

    public android.graphics.Rect peekBitmapDimensions() {
        return peekBitmapDimensions(1);
    }

    public android.graphics.Rect peekBitmapDimensions(int i) {
        return peekBitmapDimensions(i, i != 2);
    }

    public android.graphics.Rect peekBitmapDimensions(int i, boolean z) {
        checkExactlyOneWallpaperFlagSet(i);
        return sGlobals.peekWallpaperDimensions(this.mContext, z, i, this.mContext.getUserId());
    }

    public java.util.List<android.graphics.Rect> getBitmapCrops(java.util.List<android.graphics.Point> list, int i, boolean z) {
        checkExactlyOneWallpaperFlagSet(i);
        try {
            return sGlobals.mService.getBitmapCrops(list, i, z, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.graphics.Rect> getBitmapCrops(android.graphics.Point point, java.util.List<android.graphics.Point> list, java.util.Map<android.graphics.Point, android.graphics.Rect> map) {
        if (map == null) {
            try {
                map = java.util.Map.of();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        java.util.Set<java.util.Map.Entry<android.graphics.Point, android.graphics.Rect>> entrySet = map.entrySet();
        return sGlobals.mService.getFutureBitmapCrops(point, list, entrySet.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int orientation;
                orientation = android.app.WallpaperManager.getOrientation((android.graphics.Point) ((java.util.Map.Entry) obj).getKey());
                return orientation;
            }
        }).toArray(), entrySet.stream().map(new android.app.WallpaperManager$$ExternalSyntheticLambda3()).toList());
    }

    public android.app.WallpaperColors getWallpaperColors(android.graphics.Bitmap bitmap, java.util.Map<android.graphics.Point, android.graphics.Rect> map) {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        if (map == null) {
            try {
                map = java.util.Map.of();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        java.util.Set<java.util.Map.Entry<android.graphics.Point, android.graphics.Rect>> entrySet = map.entrySet();
        int[] array = entrySet.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int orientation;
                orientation = android.app.WallpaperManager.getOrientation((android.graphics.Point) ((java.util.Map.Entry) obj).getKey());
                return orientation;
            }
        }).toArray();
        java.util.List<android.graphics.Rect> list = entrySet.stream().map(new android.app.WallpaperManager$$ExternalSyntheticLambda3()).toList();
        android.graphics.Rect bitmapCrop = sGlobals.mService.getBitmapCrop(new android.graphics.Point(bitmap.getWidth(), bitmap.getHeight()), array, list);
        return android.app.WallpaperColors.fromBitmap(android.graphics.Bitmap.createBitmap(bitmap, bitmapCrop.left, bitmapCrop.top, bitmapCrop.width(), bitmapCrop.height()), getWallpaperDimAmount());
    }

    public android.os.ParcelFileDescriptor getWallpaperFile(int i) {
        return getWallpaperFile(i, this.mContext.getUserId());
    }

    public void addOnColorsChangedListener(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener, android.os.Handler handler) {
        addOnColorsChangedListener(onColorsChangedListener, handler, this.mContext.getUserId());
    }

    public void addOnColorsChangedListener(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener, android.os.Handler handler, int i) {
        sGlobals.addOnColorsChangedListener(onColorsChangedListener, handler, i, this.mContext.getDisplayId());
    }

    public void removeOnColorsChangedListener(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener) {
        removeOnColorsChangedListener(onColorsChangedListener, this.mContext.getUserId());
    }

    public void removeOnColorsChangedListener(android.app.WallpaperManager.OnColorsChangedListener onColorsChangedListener, int i) {
        sGlobals.removeOnColorsChangedListener(onColorsChangedListener, i, this.mContext.getDisplayId());
    }

    public android.app.WallpaperColors getWallpaperColors(int i) {
        return getWallpaperColors(i, this.mContext.getUserId());
    }

    public android.app.WallpaperColors getWallpaperColors(int i, int i2) {
        android.os.StrictMode.assertUiContext(this.mContext, "getWallpaperColors");
        return sGlobals.getWallpaperColors(i, i2, this.mContext.getDisplayId());
    }

    public void addOnColorsChangedListener(android.app.WallpaperManager.LocalWallpaperColorConsumer localWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i) throws java.lang.IllegalArgumentException {
        java.util.Iterator<android.graphics.RectF> it = list.iterator();
        while (it.hasNext()) {
            if (!LOCAL_COLOR_BOUNDS.contains(it.next())) {
                throw new java.lang.IllegalArgumentException("Regions must be within bounds " + LOCAL_COLOR_BOUNDS);
            }
        }
        sGlobals.addOnColorsChangedListener(localWallpaperColorConsumer, list, i, this.mContext.getUserId(), this.mContext.getDisplayId());
    }

    public void removeOnColorsChangedListener(android.app.WallpaperManager.LocalWallpaperColorConsumer localWallpaperColorConsumer) {
        sGlobals.removeOnColorsChangedListener(localWallpaperColorConsumer, 1, this.mContext.getUserId(), this.mContext.getDisplayId());
    }

    public android.os.ParcelFileDescriptor getWallpaperFile(int i, int i2) {
        return getWallpaperFile(i, i2, true);
    }

    public android.os.ParcelFileDescriptor getWallpaperFile(int i, boolean z) {
        return getWallpaperFile(i, this.mContext.getUserId(), z);
    }

    private android.os.ParcelFileDescriptor getWallpaperFile(int i, int i2, boolean z) {
        checkExactlyOneWallpaperFlagSet(i);
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperWithFeature(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), null, i, new android.os.Bundle(), i2, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.SecurityException e2) {
            if (android.app.compat.CompatChanges.isChangeEnabled(RETURN_DEFAULT_ON_SECURITY_EXCEPTION) && !android.app.compat.CompatChanges.isChangeEnabled(THROW_ON_SECURITY_EXCEPTION)) {
                android.util.Log.w(TAG, "No permission to access wallpaper, returning default wallpaper file to avoid crashing legacy app.");
                return getDefaultSystemWallpaperFile();
            }
            if (this.mContext.getApplicationInfo().targetSdkVersion < 27) {
                android.util.Log.w(TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
                return null;
            }
            throw e2;
        }
    }

    public void forgetLoadedWallpaper() {
        sGlobals.forgetLoadedWallpaper();
    }

    public android.app.WallpaperInfo getWallpaperInfo() {
        return getWallpaperInfoForUser(this.mContext.getUserId());
    }

    public android.app.WallpaperInfo getWallpaperInfoForUser(int i) {
        return getWallpaperInfo(1, i);
    }

    public android.app.WallpaperInfo getWallpaperInfo(int i) {
        return getWallpaperInfo(i, this.mContext.getUserId());
    }

    public android.app.WallpaperInfo getWallpaperInfo(int i, int i2) {
        checkExactlyOneWallpaperFlagSet(i);
        try {
            if (sGlobals.mService == null) {
                android.util.Log.w(TAG, "WallpaperService not running");
                throw new java.lang.RuntimeException(new android.os.DeadSystemException());
            }
            return sGlobals.mService.getWallpaperInfoWithFlags(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.ParcelFileDescriptor getWallpaperInfoFile() {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperInfoFile(this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWallpaperId(int i) {
        return getWallpaperIdForUser(i, this.mContext.getUserId());
    }

    public int getWallpaperIdForUser(int i, int i2) {
        try {
            if (sGlobals.mService == null) {
                android.util.Log.w(TAG, "WallpaperService not running");
                throw new java.lang.RuntimeException(new android.os.DeadSystemException());
            }
            return sGlobals.mService.getWallpaperIdForUser(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.Intent getCropAndSetWallpaperIntent(android.net.Uri uri) {
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("Image URI must not be null");
        }
        if (!"content".equals(uri.getScheme())) {
            throw new java.lang.IllegalArgumentException("Image URI must be of the content scheme type");
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.Intent intent = new android.content.Intent(ACTION_CROP_AND_SET_WALLPAPER, uri);
        intent.addFlags(1);
        android.content.pm.ResolveInfo resolveActivity = packageManager.resolveActivity(new android.content.Intent(android.content.Intent.ACTION_MAIN).addCategory(android.content.Intent.CATEGORY_HOME), 65536);
        if (resolveActivity != null) {
            intent.setPackage(resolveActivity.activityInfo.packageName);
            if (packageManager.queryIntentActivities(intent, 0).size() > 0) {
                return intent;
            }
        }
        intent.setPackage(this.mContext.getString(com.android.internal.R.string.config_wallpaperCropperPackage));
        if (packageManager.queryIntentActivities(intent, 0).size() > 0) {
            return intent;
        }
        throw new java.lang.IllegalArgumentException("Cannot use passed URI to set wallpaper; check that the type returned by ContentProvider matches image/*");
    }

    public void setResource(int i) throws java.io.IOException {
        setResource(i, 3);
    }

    public int setResource(int i, int i2) throws java.io.IOException {
        android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        android.os.Bundle bundle = new android.os.Bundle();
        android.app.WallpaperManager.WallpaperSetCompletion wallpaperSetCompletion = new android.app.WallpaperManager.WallpaperSetCompletion();
        try {
            android.content.res.Resources resources = this.mContext.getResources();
            android.os.ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper("res:" + resources.getResourceName(i), this.mContext.getOpPackageName(), null, null, false, bundle, i2, wallpaperSetCompletion, this.mContext.getUserId());
            if (wallpaper != null) {
                android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = null;
                try {
                    autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                } catch (java.lang.Throwable th) {
                    th = th;
                }
                try {
                    copyStreamToWallpaperFile(resources.openRawResource(i), autoCloseOutputStream);
                    autoCloseOutputStream.close();
                    wallpaperSetCompletion.waitForCompletion();
                    libcore.io.IoUtils.closeQuietly(autoCloseOutputStream);
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    autoCloseOutputStream2 = autoCloseOutputStream;
                    libcore.io.IoUtils.closeQuietly(autoCloseOutputStream2);
                    throw th;
                }
            }
            return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBitmap(android.graphics.Bitmap bitmap) throws java.io.IOException {
        setBitmap(bitmap, null, true);
    }

    public int setBitmap(android.graphics.Bitmap bitmap, android.graphics.Rect rect, boolean z) throws java.io.IOException {
        return setBitmap(bitmap, rect, z, 3);
    }

    public int setBitmap(android.graphics.Bitmap bitmap, android.graphics.Rect rect, boolean z, int i) throws java.io.IOException {
        return setBitmap(bitmap, rect, z, i, this.mContext.getUserId());
    }

    public int setBitmap(android.graphics.Bitmap bitmap, android.graphics.Rect rect, boolean z, int i, int i2) throws java.io.IOException {
        android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        if (com.android.window.flags.Flags.multiCrop()) {
            android.util.SparseArray<android.graphics.Rect> sparseArray = new android.util.SparseArray<>();
            if (rect != null) {
                sparseArray.put(-1, rect);
            }
            return setBitmapWithCrops(bitmap, sparseArray, z, i, i2);
        }
        validateRect(rect);
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        android.os.Bundle bundle = new android.os.Bundle();
        android.app.WallpaperManager.WallpaperSetCompletion wallpaperSetCompletion = new android.app.WallpaperManager.WallpaperSetCompletion();
        android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = null;
        try {
            android.os.ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), null, rect == null ? null : java.util.List.of(rect), z, bundle, i, wallpaperSetCompletion, i2);
            if (wallpaper != null) {
                try {
                    autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                } catch (java.lang.Throwable th) {
                    th = th;
                }
                try {
                    bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 90, autoCloseOutputStream);
                    autoCloseOutputStream.close();
                    wallpaperSetCompletion.waitForCompletion();
                    libcore.io.IoUtils.closeQuietly(autoCloseOutputStream);
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    autoCloseOutputStream2 = autoCloseOutputStream;
                    libcore.io.IoUtils.closeQuietly(autoCloseOutputStream2);
                    throw th;
                }
            }
            return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setBitmapWithCrops(android.graphics.Bitmap bitmap, java.util.Map<android.graphics.Point, android.graphics.Rect> map, boolean z, int i) throws java.io.IOException {
        final android.util.SparseArray<android.graphics.Rect> sparseArray = new android.util.SparseArray<>();
        map.forEach(new java.util.function.BiConsumer() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.util.SparseArray.this.put(android.app.WallpaperManager.getOrientation((android.graphics.Point) obj), (android.graphics.Rect) obj2);
            }
        });
        return setBitmapWithCrops(bitmap, sparseArray, z, i, this.mContext.getUserId());
    }

    private int setBitmapWithCrops(android.graphics.Bitmap bitmap, android.util.SparseArray<android.graphics.Rect> sparseArray, boolean z, int i, int i2) throws java.io.IOException {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        int size = sparseArray.size();
        int[] iArr = new int[size];
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i3 = 0; i3 < size; i3++) {
            iArr[i3] = sparseArray.keyAt(i3);
            android.graphics.Rect valueAt = sparseArray.valueAt(i3);
            validateRect(valueAt);
            arrayList.add(valueAt);
        }
        android.os.Bundle bundle = new android.os.Bundle();
        android.app.WallpaperManager.WallpaperSetCompletion wallpaperSetCompletion = new android.app.WallpaperManager.WallpaperSetCompletion();
        try {
            android.os.ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), iArr, arrayList, z, bundle, i, wallpaperSetCompletion, i2);
            if (wallpaper != null) {
                android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = null;
                try {
                    android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                    try {
                        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 90, autoCloseOutputStream2);
                        autoCloseOutputStream2.close();
                        wallpaperSetCompletion.waitForCompletion();
                        libcore.io.IoUtils.closeQuietly(autoCloseOutputStream2);
                    } catch (java.lang.Throwable th) {
                        th = th;
                        autoCloseOutputStream = autoCloseOutputStream2;
                        libcore.io.IoUtils.closeQuietly(autoCloseOutputStream);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                }
            }
            return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private final void validateRect(android.graphics.Rect rect) {
        if (rect != null && rect.isEmpty()) {
            throw new java.lang.IllegalArgumentException("visibleCrop rectangle must be valid and non-empty");
        }
    }

    public void setStream(java.io.InputStream inputStream) throws java.io.IOException {
        setStream(inputStream, null, true);
    }

    private void copyStreamToWallpaperFile(java.io.InputStream inputStream, java.io.FileOutputStream fileOutputStream) throws java.io.IOException {
        android.os.FileUtils.copy(inputStream, fileOutputStream);
    }

    public int setStream(java.io.InputStream inputStream, android.graphics.Rect rect, boolean z) throws java.io.IOException {
        return setStream(inputStream, rect, z, 3);
    }

    public int setStream(java.io.InputStream inputStream, android.graphics.Rect rect, boolean z, int i) throws java.io.IOException {
        android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        if (!com.android.window.flags.Flags.multiCrop()) {
            validateRect(rect);
            if (sGlobals.mService == null) {
                android.util.Log.w(TAG, "WallpaperService not running");
                throw new java.lang.RuntimeException(new android.os.DeadSystemException());
            }
            android.os.Bundle bundle = new android.os.Bundle();
            android.app.WallpaperManager.WallpaperSetCompletion wallpaperSetCompletion = new android.app.WallpaperManager.WallpaperSetCompletion();
            android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = null;
            try {
                android.os.ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), null, rect == null ? null : java.util.List.of(rect), z, bundle, i, wallpaperSetCompletion, this.mContext.getUserId());
                if (wallpaper != null) {
                    try {
                        autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                    } catch (java.lang.Throwable th) {
                        th = th;
                    }
                    try {
                        copyStreamToWallpaperFile(inputStream, autoCloseOutputStream);
                        autoCloseOutputStream.close();
                        wallpaperSetCompletion.waitForCompletion();
                        libcore.io.IoUtils.closeQuietly(autoCloseOutputStream);
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        autoCloseOutputStream2 = autoCloseOutputStream;
                        libcore.io.IoUtils.closeQuietly(autoCloseOutputStream2);
                        throw th;
                    }
                }
                return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.SparseArray<android.graphics.Rect> sparseArray = new android.util.SparseArray<>();
        if (rect != null) {
            sparseArray.put(-1, rect);
        }
        return setStreamWithCrops(inputStream, sparseArray, z, i);
    }

    public int setStreamWithCrops(java.io.InputStream inputStream, java.util.Map<android.graphics.Point, android.graphics.Rect> map, boolean z, int i) throws java.io.IOException {
        final android.util.SparseArray<android.graphics.Rect> sparseArray = new android.util.SparseArray<>();
        map.forEach(new java.util.function.BiConsumer() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.util.SparseArray.this.put(android.app.WallpaperManager.getOrientation((android.graphics.Point) obj), (android.graphics.Rect) obj2);
            }
        });
        return setStreamWithCrops(inputStream, sparseArray, z, i);
    }

    public int setStreamWithCrops(java.io.InputStream inputStream, android.util.SparseArray<android.graphics.Rect> sparseArray, boolean z, int i) throws java.io.IOException {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        int size = sparseArray.size();
        int[] iArr = new int[size];
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = sparseArray.keyAt(i2);
            android.graphics.Rect valueAt = sparseArray.valueAt(i2);
            validateRect(valueAt);
            arrayList.add(valueAt);
        }
        android.os.Bundle bundle = new android.os.Bundle();
        android.app.WallpaperManager.WallpaperSetCompletion wallpaperSetCompletion = new android.app.WallpaperManager.WallpaperSetCompletion();
        try {
            android.os.ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), iArr, arrayList, z, bundle, i, wallpaperSetCompletion, this.mContext.getUserId());
            if (wallpaper != null) {
                android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = null;
                try {
                    android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                    try {
                        copyStreamToWallpaperFile(inputStream, autoCloseOutputStream2);
                        autoCloseOutputStream2.close();
                        wallpaperSetCompletion.waitForCompletion();
                        libcore.io.IoUtils.closeQuietly(autoCloseOutputStream2);
                    } catch (java.lang.Throwable th) {
                        th = th;
                        autoCloseOutputStream = autoCloseOutputStream2;
                        libcore.io.IoUtils.closeQuietly(autoCloseOutputStream);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                }
            }
            return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasResourceWallpaper(int i) {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.hasNamedWallpaper("res:" + this.mContext.getResources().getResourceName(i));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDesiredMinimumWidth() {
        android.os.StrictMode.assertUiContext(this.mContext, "getDesiredMinimumWidth");
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.getWidthHint(this.mContext.getDisplayId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDesiredMinimumHeight() {
        android.os.StrictMode.assertUiContext(this.mContext, "getDesiredMinimumHeight");
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.getHeightHint(this.mContext.getDisplayId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void suggestDesiredDimensions(int i, int i2) {
        android.os.StrictMode.assertUiContext(this.mContext, "suggestDesiredDimensions");
        int i3 = 0;
        try {
            try {
                i3 = android.os.SystemProperties.getInt("sys.max_texture_size", 0);
            } catch (java.lang.Exception e) {
            }
            if (i3 > 0 && (i > i3 || i2 > i3)) {
                float f = i2 / i;
                if (i > i2) {
                    i2 = (int) ((i3 * f) + 0.5d);
                    i = i3;
                } else {
                    i = (int) ((i3 / f) + 0.5d);
                    i2 = i3;
                }
            }
            if (sGlobals.mService == null) {
                android.util.Log.w(TAG, "WallpaperService not running");
                throw new java.lang.RuntimeException(new android.os.DeadSystemException());
            }
            sGlobals.mService.setDimensionHints(i, i2, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void setDisplayPadding(android.graphics.Rect rect) {
        android.os.StrictMode.assertUiContext(this.mContext, "setDisplayPadding");
        try {
            if (sGlobals.mService == null) {
                android.util.Log.w(TAG, "WallpaperService not running");
                throw new java.lang.RuntimeException(new android.os.DeadSystemException());
            }
            sGlobals.mService.setDisplayPadding(rect, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setDisplayOffset(android.os.IBinder iBinder, int i, int i2) {
        try {
            android.view.WindowManagerGlobal.getWindowSession().setWallpaperDisplayOffset(iBinder, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearWallpaper() {
        clearWallpaper(3, this.mContext.getUserId());
    }

    @android.annotation.SystemApi
    public void clearWallpaper(int i, int i2) {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            sGlobals.mService.clearWallpaper(this.mContext.getOpPackageName(), i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean setWallpaperComponent(android.content.ComponentName componentName) {
        return setWallpaperComponent(componentName, this.mContext.getUserId());
    }

    @android.annotation.SystemApi
    public void setWallpaperDimAmount(float f) {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            sGlobals.mService.setWallpaperDimAmount(android.util.MathUtils.saturate(f));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public float getWallpaperDimAmount() {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperDimAmount();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean lockScreenWallpaperExists() {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.lockScreenWallpaperExists();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setWallpaperComponent(android.content.ComponentName componentName, int i) {
        return setWallpaperComponentWithFlags(componentName, 3, i);
    }

    @android.annotation.SystemApi
    public boolean setWallpaperComponentWithFlags(android.content.ComponentName componentName, int i) {
        return setWallpaperComponentWithFlags(componentName, i, this.mContext.getUserId());
    }

    public boolean setWallpaperComponentWithFlags(android.content.ComponentName componentName, int i, int i2) {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperManagerService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            sGlobals.mService.setWallpaperComponentChecked(componentName, this.mContext.getOpPackageName(), i, i2);
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperOffsets(android.os.IBinder iBinder, float f, float f2) {
        try {
            android.view.WindowManagerGlobal.getWindowSession().setWallpaperPosition(iBinder, f, f2, this.mWallpaperXStep, this.mWallpaperYStep);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperOffsetSteps(float f, float f2) {
        this.mWallpaperXStep = f;
        this.mWallpaperYStep = f2;
    }

    public void sendWallpaperCommand(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle) {
        try {
            android.view.WindowManagerGlobal.getWindowSession().sendWallpaperCommand(iBinder, str, i, i2, i3, bundle, false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperZoomOut(android.os.IBinder iBinder, float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new java.lang.IllegalArgumentException("zoom must be between 0 and 1: " + f);
        }
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("windowToken must not be null");
        }
        try {
            android.view.WindowManagerGlobal.getWindowSession().setWallpaperZoomOut(iBinder, f);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWallpaperSupported() {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperSupported(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSetWallpaperAllowed() {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.isSetWallpaperAllowed(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearWallpaperOffsets(android.os.IBinder iBinder) {
        try {
            android.view.WindowManagerGlobal.getWindowSession().setWallpaperPosition(iBinder, -1.0f, -1.0f, -1.0f, -1.0f);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clear() throws java.io.IOException {
        clear(3);
    }

    public void clear(int i) throws java.io.IOException {
        clearWallpaper(i, this.mContext.getUserId());
    }

    public static java.io.InputStream openDefaultWallpaper(android.content.Context context, int i) {
        java.io.InputStream wallpaperInputStream = getWallpaperInputStream(android.os.SystemProperties.get(PROP_WALLPAPER));
        if (wallpaperInputStream != null) {
            return wallpaperInputStream;
        }
        java.io.InputStream wallpaperInputStream2 = getWallpaperInputStream(getCmfWallpaperPath());
        if (wallpaperInputStream2 != null) {
            return wallpaperInputStream2;
        }
        try {
            return context.getResources().openRawResource(com.android.internal.R.drawable.default_wallpaper);
        } catch (android.content.res.Resources.NotFoundException e) {
            return null;
        }
    }

    private static android.os.ParcelFileDescriptor getDefaultSystemWallpaperFile() {
        java.util.Iterator<java.lang.String> it = getDefaultSystemWallpaperPaths().iterator();
        while (it.hasNext()) {
            java.io.File file = new java.io.File(it.next());
            if (file.exists()) {
                try {
                    return android.os.ParcelFileDescriptor.open(file, 268435456);
                } catch (java.io.FileNotFoundException e) {
                }
            }
        }
        return null;
    }

    private static java.io.InputStream getWallpaperInputStream(java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(str)) {
            java.io.File file = new java.io.File(str);
            if (file.exists()) {
                try {
                    return new java.io.FileInputStream(file);
                } catch (java.io.IOException e) {
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    private static java.util.List<java.lang.String> getDefaultSystemWallpaperPaths() {
        return java.util.List.of(android.os.SystemProperties.get(PROP_WALLPAPER), getCmfWallpaperPath());
    }

    private static java.lang.String getCmfWallpaperPath() {
        return android.os.Environment.getProductDirectory() + WALLPAPER_CMF_PATH + "default_wallpaper_" + VALUE_CMF_COLOR;
    }

    public static android.content.ComponentName getDefaultWallpaperComponent(android.content.Context context) {
        android.content.ComponentName componentName;
        java.lang.String str = android.os.SystemProperties.get(PROP_WALLPAPER_COMPONENT);
        if (android.text.TextUtils.isEmpty(str)) {
            componentName = null;
        } else {
            componentName = android.content.ComponentName.unflattenFromString(str);
        }
        if (componentName == null) {
            java.lang.String string = context.getString(com.android.internal.R.string.default_wallpaper_component);
            if (!android.text.TextUtils.isEmpty(string)) {
                componentName = android.content.ComponentName.unflattenFromString(string);
            }
        }
        if (isComponentExist(context, componentName)) {
            return componentName;
        }
        return null;
    }

    public static android.content.ComponentName getCmfDefaultWallpaperComponent(android.content.Context context) {
        android.content.ComponentName componentName;
        java.lang.String[] split;
        java.lang.String[] stringArray = context.getResources().getStringArray(com.android.internal.R.array.default_wallpaper_component_per_device_color);
        android.content.ComponentName componentName2 = null;
        if (stringArray != null && stringArray.length > 0) {
            for (java.lang.String str : stringArray) {
                if (!android.text.TextUtils.isEmpty(str) && (split = str.split(",")) != null && split.length == 2 && VALUE_CMF_COLOR.equals(split[0]) && !android.text.TextUtils.isEmpty(split[1])) {
                    componentName = android.content.ComponentName.unflattenFromString(split[1]);
                    break;
                }
            }
        }
        componentName = null;
        if (isComponentExist(context, componentName)) {
            componentName2 = componentName;
        }
        return componentName2 == null ? getDefaultWallpaperComponent(context) : componentName2;
    }

    private static boolean isComponentExist(android.content.Context context, android.content.ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(componentName.getPackageName(), 786432);
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean isWallpaperBackupEligible(int i) {
        if (sGlobals.mService == null) {
            android.util.Log.w(TAG, "WallpaperService not running");
            throw new java.lang.RuntimeException(new android.os.DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperBackupEligible(i, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception querying wallpaper backup eligibility: " + e.getMessage());
            return false;
        }
    }

    public android.app.WallpaperManager.ColorManagementProxy getColorManagementProxy() {
        return this.mCmProxy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkExactlyOneWallpaperFlagSet(int i) {
        if (i == 1 || i == 2) {
        } else {
            throw new java.lang.IllegalArgumentException("Must specify exactly one kind of wallpaper");
        }
    }

    public static class ColorManagementProxy {
        private final java.util.Set<android.graphics.ColorSpace> mSupportedColorSpaces = new java.util.HashSet();

        public ColorManagementProxy(android.content.Context context) {
            android.view.Display displayNoVerify = context.getDisplayNoVerify();
            if (displayNoVerify != null) {
                this.mSupportedColorSpaces.addAll(java.util.Arrays.asList(displayNoVerify.getSupportedWideColorGamut()));
            }
        }

        public java.util.Set<android.graphics.ColorSpace> getSupportedColorSpaces() {
            return this.mSupportedColorSpaces;
        }

        boolean isSupportedColorSpace(android.graphics.ColorSpace colorSpace) {
            return colorSpace != null && (colorSpace == android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB) || getSupportedColorSpaces().contains(colorSpace));
        }

        void doColorManagement(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo) {
            if (!isSupportedColorSpace(imageInfo.getColorSpace())) {
                imageDecoder.setTargetColorSpace(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
                android.util.Log.w(android.app.WallpaperManager.TAG, "Not supported color space: " + imageInfo.getColorSpace());
            }
        }
    }

    private class WallpaperSetCompletion extends android.app.IWallpaperManagerCallback.Stub {
        final java.util.concurrent.CountDownLatch mLatch = new java.util.concurrent.CountDownLatch(1);

        public WallpaperSetCompletion() {
        }

        public void waitForCompletion() {
            try {
                if (this.mLatch.await(30L, java.util.concurrent.TimeUnit.SECONDS)) {
                    android.util.Log.d(android.app.WallpaperManager.TAG, "Wallpaper set completion.");
                } else {
                    android.util.Log.d(android.app.WallpaperManager.TAG, "Timeout waiting for wallpaper set completion!");
                }
            } catch (java.lang.InterruptedException e) {
            }
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperChanged() throws android.os.RemoteException {
            this.mLatch.countDown();
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperColorsChanged(android.app.WallpaperColors wallpaperColors, int i, int i2) throws android.os.RemoteException {
            android.app.WallpaperManager.sGlobals.onWallpaperColorsChanged(wallpaperColors, i, i2);
        }
    }

    public interface OnColorsChangedListener {
        void onColorsChanged(android.app.WallpaperColors wallpaperColors, int i);

        default void onColorsChanged(android.app.WallpaperColors wallpaperColors, int i, int i2) {
            onColorsChanged(wallpaperColors, i);
        }
    }
}
