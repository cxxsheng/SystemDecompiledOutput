package android.app;

/* loaded from: classes.dex */
public class NativeActivity extends android.app.Activity implements android.view.SurfaceHolder.Callback2, android.view.InputQueue.Callback, android.view.ViewTreeObserver.OnGlobalLayoutListener {
    private static final java.lang.String KEY_NATIVE_SAVED_STATE = "android:native_state";
    public static final java.lang.String META_DATA_FUNC_NAME = "android.app.func_name";
    public static final java.lang.String META_DATA_LIB_NAME = "android.app.lib_name";
    private android.view.InputQueue mCurInputQueue;
    private android.view.SurfaceHolder mCurSurfaceHolder;
    private boolean mDestroyed;
    private boolean mDispatchingUnhandledKey;
    private android.view.inputmethod.InputMethodManager mIMM;
    int mLastContentHeight;
    int mLastContentWidth;
    int mLastContentX;
    int mLastContentY;
    final int[] mLocation = new int[2];
    private android.app.NativeActivity.NativeContentView mNativeContentView;
    private long mNativeHandle;

    private native java.lang.String getDlError();

    private native long loadNativeCode(java.lang.String str, java.lang.String str2, android.os.MessageQueue messageQueue, java.lang.String str3, java.lang.String str4, java.lang.String str5, int i, android.content.res.AssetManager assetManager, byte[] bArr, java.lang.ClassLoader classLoader, java.lang.String str6);

    private native void onConfigurationChangedNative(long j);

    private native void onContentRectChangedNative(long j, int i, int i2, int i3, int i4);

    private native void onInputQueueCreatedNative(long j, long j2);

    private native void onInputQueueDestroyedNative(long j, long j2);

    private native void onLowMemoryNative(long j);

    private native void onPauseNative(long j);

    private native void onResumeNative(long j);

    private native byte[] onSaveInstanceStateNative(long j);

    private native void onStartNative(long j);

    private native void onStopNative(long j);

    private native void onSurfaceChangedNative(long j, android.view.Surface surface, int i, int i2, int i3);

    private native void onSurfaceCreatedNative(long j, android.view.Surface surface);

    private native void onSurfaceDestroyedNative(long j);

    private native void onSurfaceRedrawNeededNative(long j, android.view.Surface surface);

    private native void onWindowFocusChangedNative(long j, boolean z);

    private native void unloadNativeCode(long j);

    static class NativeContentView extends android.view.View {
        android.app.NativeActivity mActivity;

        public NativeContentView(android.content.Context context) {
            super(context);
        }

        public NativeContentView(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        java.lang.String str;
        byte[] bArr;
        this.mIMM = (android.view.inputmethod.InputMethodManager) getSystemService(android.view.inputmethod.InputMethodManager.class);
        getWindow().takeSurface(this);
        getWindow().takeInputQueue(this);
        getWindow().setFormat(4);
        getWindow().setSoftInputMode(16);
        this.mNativeContentView = new android.app.NativeActivity.NativeContentView(this);
        this.mNativeContentView.mActivity = this;
        setContentView(this.mNativeContentView);
        this.mNativeContentView.requestFocus();
        this.mNativeContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        try {
            android.content.pm.ActivityInfo activityInfo = getPackageManager().getActivityInfo(getIntent().getComponent(), 128);
            str = "main";
            java.lang.String str2 = "ANativeActivity_onCreate";
            if (activityInfo.metaData != null) {
                java.lang.String string = activityInfo.metaData.getString(META_DATA_LIB_NAME);
                str = string != null ? string : "main";
                java.lang.String string2 = activityInfo.metaData.getString(META_DATA_FUNC_NAME);
                if (string2 != null) {
                    str2 = string2;
                }
            }
            dalvik.system.BaseDexClassLoader baseDexClassLoader = (dalvik.system.BaseDexClassLoader) getClassLoader();
            java.lang.String findLibrary = baseDexClassLoader.findLibrary(str);
            if (findLibrary != null) {
                if (bundle == null) {
                    bArr = null;
                } else {
                    bArr = bundle.getByteArray(KEY_NATIVE_SAVED_STATE);
                }
                this.mNativeHandle = loadNativeCode(findLibrary, str2, android.os.Looper.myQueue(), getAbsolutePath(getFilesDir()), getAbsolutePath(getObbDir()), getAbsolutePath(getExternalFilesDir(null)), android.os.Build.VERSION.SDK_INT, getAssets(), bArr, baseDexClassLoader, baseDexClassLoader.getLdLibraryPath());
                if (this.mNativeHandle == 0) {
                    throw new java.lang.UnsatisfiedLinkError("Unable to load native library \"" + findLibrary + "\": " + getDlError());
                }
                super.onCreate(bundle);
                return;
            }
            throw new java.lang.IllegalArgumentException("Unable to find native library " + str + " using classloader: " + baseDexClassLoader.toString());
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.RuntimeException("Error getting activity info", e);
        }
    }

    private static java.lang.String getAbsolutePath(java.io.File file) {
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mDestroyed = true;
        if (this.mCurSurfaceHolder != null) {
            onSurfaceDestroyedNative(this.mNativeHandle);
            this.mCurSurfaceHolder = null;
        }
        if (this.mCurInputQueue != null) {
            onInputQueueDestroyedNative(this.mNativeHandle, this.mCurInputQueue.getNativePtr());
            this.mCurInputQueue = null;
        }
        unloadNativeCode(this.mNativeHandle);
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        onPauseNative(this.mNativeHandle);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        onResumeNative(this.mNativeHandle);
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        byte[] onSaveInstanceStateNative = onSaveInstanceStateNative(this.mNativeHandle);
        if (onSaveInstanceStateNative != null) {
            bundle.putByteArray(KEY_NATIVE_SAVED_STATE, onSaveInstanceStateNative);
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        onStartNative(this.mNativeHandle);
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        onStopNative(this.mNativeHandle);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!this.mDestroyed) {
            onConfigurationChangedNative(this.mNativeHandle);
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        if (!this.mDestroyed) {
            onLowMemoryNative(this.mNativeHandle);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (!this.mDestroyed) {
            onWindowFocusChangedNative(this.mNativeHandle, z);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(android.view.SurfaceHolder surfaceHolder) {
        if (!this.mDestroyed) {
            this.mCurSurfaceHolder = surfaceHolder;
            onSurfaceCreatedNative(this.mNativeHandle, surfaceHolder.getSurface());
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (!this.mDestroyed) {
            this.mCurSurfaceHolder = surfaceHolder;
            onSurfaceChangedNative(this.mNativeHandle, surfaceHolder.getSurface(), i, i2, i3);
        }
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeeded(android.view.SurfaceHolder surfaceHolder) {
        if (!this.mDestroyed) {
            this.mCurSurfaceHolder = surfaceHolder;
            onSurfaceRedrawNeededNative(this.mNativeHandle, surfaceHolder.getSurface());
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
        this.mCurSurfaceHolder = null;
        if (!this.mDestroyed) {
            onSurfaceDestroyedNative(this.mNativeHandle);
        }
    }

    @Override // android.view.InputQueue.Callback
    public void onInputQueueCreated(android.view.InputQueue inputQueue) {
        if (!this.mDestroyed) {
            this.mCurInputQueue = inputQueue;
            onInputQueueCreatedNative(this.mNativeHandle, inputQueue.getNativePtr());
        }
    }

    @Override // android.view.InputQueue.Callback
    public void onInputQueueDestroyed(android.view.InputQueue inputQueue) {
        if (!this.mDestroyed) {
            onInputQueueDestroyedNative(this.mNativeHandle, inputQueue.getNativePtr());
            this.mCurInputQueue = null;
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        this.mNativeContentView.getLocationInWindow(this.mLocation);
        int width = this.mNativeContentView.getWidth();
        int height = this.mNativeContentView.getHeight();
        if (this.mLocation[0] != this.mLastContentX || this.mLocation[1] != this.mLastContentY || width != this.mLastContentWidth || height != this.mLastContentHeight) {
            this.mLastContentX = this.mLocation[0];
            this.mLastContentY = this.mLocation[1];
            this.mLastContentWidth = width;
            this.mLastContentHeight = height;
            if (!this.mDestroyed) {
                onContentRectChangedNative(this.mNativeHandle, this.mLastContentX, this.mLastContentY, this.mLastContentWidth, this.mLastContentHeight);
            }
        }
    }

    void setWindowFlags(int i, int i2) {
        getWindow().setFlags(i, i2);
    }

    void setWindowFormat(int i) {
        getWindow().setFormat(i);
    }

    void showIme(int i) {
        this.mIMM.showSoftInput(this.mNativeContentView, i);
    }

    void hideIme(int i) {
        this.mIMM.hideSoftInputFromWindow(this.mNativeContentView.getWindowToken(), i);
    }
}
