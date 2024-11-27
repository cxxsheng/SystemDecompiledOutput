package android.hardware;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class Camera {
    public static final java.lang.String ACTION_NEW_PICTURE = "android.hardware.action.NEW_PICTURE";
    public static final java.lang.String ACTION_NEW_VIDEO = "android.hardware.action.NEW_VIDEO";
    public static final int CAMERA_ERROR_DISABLED = 3;
    public static final int CAMERA_ERROR_EVICTED = 2;
    public static final int CAMERA_ERROR_SERVER_DIED = 100;
    public static final int CAMERA_ERROR_UNKNOWN = 1;
    private static final int CAMERA_FACE_DETECTION_HW = 0;
    private static final int CAMERA_FACE_DETECTION_SW = 1;
    public static final int CAMERA_HAL_API_VERSION_1_0 = 256;
    public static final int CAMERA_HAL_API_VERSION_3_0 = 768;
    private static final int CAMERA_MSG_COMPRESSED_IMAGE = 256;
    private static final int CAMERA_MSG_ERROR = 1;
    private static final int CAMERA_MSG_FOCUS = 4;
    private static final int CAMERA_MSG_FOCUS_MOVE = 2048;
    private static final int CAMERA_MSG_POSTVIEW_FRAME = 64;
    private static final int CAMERA_MSG_PREVIEW_FRAME = 16;
    private static final int CAMERA_MSG_PREVIEW_METADATA = 1024;
    private static final int CAMERA_MSG_RAW_IMAGE = 128;
    private static final int CAMERA_MSG_RAW_IMAGE_NOTIFY = 512;
    private static final int CAMERA_MSG_SHUTTER = 2;
    private static final int CAMERA_MSG_VIDEO_FRAME = 32;
    private static final int CAMERA_MSG_ZOOM = 8;
    private static final int NO_ERROR = 0;
    private static final java.lang.String TAG = "Camera";
    private com.android.internal.app.IAppOpsService mAppOps;
    private com.android.internal.app.IAppOpsCallback mAppOpsCallback;
    private android.hardware.Camera.AutoFocusCallback mAutoFocusCallback;
    private android.hardware.Camera.AutoFocusMoveCallback mAutoFocusMoveCallback;
    private android.hardware.Camera.ErrorCallback mDetailedErrorCallback;
    private android.hardware.Camera.ErrorCallback mErrorCallback;
    private android.hardware.Camera.EventHandler mEventHandler;
    private android.hardware.Camera.FaceDetectionListener mFaceListener;
    private android.hardware.Camera.PictureCallback mJpegCallback;
    private long mNativeContext;
    private boolean mOneShot;
    private android.hardware.Camera.PictureCallback mPostviewCallback;
    private android.hardware.Camera.PreviewCallback mPreviewCallback;
    private android.hardware.Camera.PictureCallback mRawImageCallback;
    private android.hardware.Camera.ShutterCallback mShutterCallback;
    private boolean mUsingPreviewAllocation;
    private boolean mWithBuffer;
    private android.hardware.Camera.OnZoomChangeListener mZoomListener;
    private boolean mFaceDetectionRunning = false;
    private final java.lang.Object mAutoFocusCallbackLock = new java.lang.Object();
    private final java.lang.Object mShutterSoundLock = new java.lang.Object();
    private boolean mHasAppOpsPlayAudio = true;
    private boolean mShutterSoundEnabledFromApp = true;

    @java.lang.Deprecated
    public interface AutoFocusCallback {
        void onAutoFocus(boolean z, android.hardware.Camera camera);
    }

    @java.lang.Deprecated
    public interface AutoFocusMoveCallback {
        void onAutoFocusMoving(boolean z, android.hardware.Camera camera);
    }

    @java.lang.Deprecated
    public static class CameraInfo {
        public static final int CAMERA_FACING_BACK = 0;
        public static final int CAMERA_FACING_FRONT = 1;
        public boolean canDisableShutterSound;
        public int facing;
        public int orientation;
    }

    @java.lang.Deprecated
    public interface ErrorCallback {
        void onError(int i, android.hardware.Camera camera);
    }

    @java.lang.Deprecated
    public static class Face {
        public android.graphics.Rect rect;
        public int score;
        public int id = -1;
        public android.graphics.Point leftEye = null;
        public android.graphics.Point rightEye = null;
        public android.graphics.Point mouth = null;
    }

    @java.lang.Deprecated
    public interface FaceDetectionListener {
        void onFaceDetection(android.hardware.Camera.Face[] faceArr, android.hardware.Camera camera);
    }

    @java.lang.Deprecated
    public interface OnZoomChangeListener {
        void onZoomChange(int i, boolean z, android.hardware.Camera camera);
    }

    @java.lang.Deprecated
    public interface PictureCallback {
        void onPictureTaken(byte[] bArr, android.hardware.Camera camera);
    }

    @java.lang.Deprecated
    public interface PreviewCallback {
        void onPreviewFrame(byte[] bArr, android.hardware.Camera camera);
    }

    @java.lang.Deprecated
    public interface ShutterCallback {
        void onShutter();
    }

    private final native void _addCallbackBuffer(byte[] bArr, int i);

    private final native boolean _enableShutterSound(boolean z);

    private static native void _getCameraInfo(int i, boolean z, android.hardware.Camera.CameraInfo cameraInfo);

    public static native int _getNumberOfCameras();

    private final native void _startFaceDetection(int i);

    private final native void _stopFaceDetection();

    private final native void _stopPreview();

    private native void enableFocusMoveCallback(int i);

    private final native void native_autoFocus();

    private final native void native_cancelAutoFocus();

    private final native java.lang.String native_getParameters();

    private final native void native_release();

    private final native void native_setParameters(java.lang.String str);

    private native int native_setup(java.lang.Object obj, int i, java.lang.String str, boolean z, boolean z2);

    private final native void native_takePicture(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void setHasPreviewCallback(boolean z, boolean z2);

    private final native void setPreviewCallbackSurface(android.view.Surface surface);

    public final native int getAudioRestriction();

    public final native void lock();

    public final native boolean previewEnabled();

    public final native void reconnect() throws java.io.IOException;

    public final native void setAudioRestriction(int i);

    public final native void setDisplayOrientation(int i);

    public final native void setPreviewSurface(android.view.Surface surface) throws java.io.IOException;

    public final native void setPreviewTexture(android.graphics.SurfaceTexture surfaceTexture) throws java.io.IOException;

    public final native void startPreview();

    public final native void startSmoothZoom(int i);

    public final native void stopSmoothZoom();

    public final native void unlock();

    public static boolean shouldExposeAuxCamera() {
        java.lang.String currentOpPackageName = android.app.ActivityThread.currentOpPackageName();
        if (currentOpPackageName == null) {
            return true;
        }
        return java.util.Arrays.asList(android.os.SystemProperties.get("vendor.camera.aux.packagelist", currentOpPackageName).split(",")).contains(currentOpPackageName) && !java.util.Arrays.asList(android.os.SystemProperties.get("vendor.camera.aux.packageexcludelist", "").split(",")).contains(currentOpPackageName);
    }

    public static int getNumberOfCameras() {
        int _getNumberOfCameras = _getNumberOfCameras();
        if (shouldExposeAuxCamera() || _getNumberOfCameras <= 2) {
            return _getNumberOfCameras;
        }
        return 2;
    }

    public static void getCameraInfo(int i, android.hardware.Camera.CameraInfo cameraInfo) {
        if (i >= getNumberOfCameras()) {
            throw new java.lang.RuntimeException("Unknown camera ID");
        }
        _getCameraInfo(i, android.hardware.camera2.CameraManager.shouldOverrideToPortrait(android.app.ActivityThread.currentApplication().getApplicationContext()), cameraInfo);
        try {
            if (android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio")).isCameraSoundForced()) {
                cameraInfo.canDisableShutterSound = false;
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Audio service is unavailable for queries");
        }
    }

    public static android.hardware.Camera open(int i) {
        return new android.hardware.Camera(i);
    }

    public static android.hardware.Camera open() {
        int numberOfCameras = getNumberOfCameras();
        android.hardware.Camera.CameraInfo cameraInfo = new android.hardware.Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 0) {
                return new android.hardware.Camera(i);
            }
        }
        return null;
    }

    public static android.hardware.Camera openLegacy(int i, int i2) {
        if (i2 < 768) {
            throw new java.lang.IllegalArgumentException("Unsupported HAL version " + i2);
        }
        return new android.hardware.Camera(i);
    }

    private int cameraInit(int i) {
        this.mShutterCallback = null;
        this.mRawImageCallback = null;
        this.mJpegCallback = null;
        this.mPreviewCallback = null;
        this.mPostviewCallback = null;
        this.mUsingPreviewAllocation = false;
        this.mZoomListener = null;
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler = new android.hardware.Camera.EventHandler(this, myLooper);
        } else {
            android.os.Looper mainLooper = android.os.Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new android.hardware.Camera.EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        return native_setup(new java.lang.ref.WeakReference(this), i, android.app.ActivityThread.currentOpPackageName(), android.hardware.camera2.CameraManager.shouldOverrideToPortrait(android.app.ActivityThread.currentApplication().getApplicationContext()), shouldForceSlowJpegMode());
    }

    private boolean shouldForceSlowJpegMode() {
        android.content.Context applicationContext = android.app.ActivityThread.currentApplication().getApplicationContext();
        java.lang.String[] stringArray = applicationContext.getResources().getStringArray(com.android.internal.R.array.config_forceSlowJpegModeList);
        java.lang.String packageName = applicationContext.getPackageName();
        for (java.lang.String str : stringArray) {
            if (android.text.TextUtils.equals(str, packageName)) {
                return true;
            }
        }
        return false;
    }

    Camera(int i) {
        if (i >= getNumberOfCameras()) {
            throw new java.lang.RuntimeException("Unknown camera ID");
        }
        int cameraInit = cameraInit(i);
        if (checkInitErrors(cameraInit)) {
            if (cameraInit == (-android.system.OsConstants.EACCES)) {
                throw new java.lang.RuntimeException("Fail to connect to camera service");
            }
            if (cameraInit == (-android.system.OsConstants.ENODEV)) {
                throw new java.lang.RuntimeException("Camera initialization failed");
            }
            throw new java.lang.RuntimeException("Unknown camera error");
        }
        initAppOps();
    }

    public static boolean checkInitErrors(int i) {
        return i != 0;
    }

    public static android.hardware.Camera openUninitialized() {
        return new android.hardware.Camera();
    }

    Camera() {
    }

    private void initAppOps() {
        this.mAppOps = com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.APP_OPS_SERVICE));
        updateAppOpsPlayAudio();
        this.mAppOpsCallback = new android.hardware.Camera.IAppOpsCallbackWrapper(this);
        try {
            this.mAppOps.startWatchingMode(28, android.app.ActivityThread.currentPackageName(), this.mAppOpsCallback);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error registering appOps callback", e);
            this.mHasAppOpsPlayAudio = false;
        }
    }

    private void releaseAppOps() {
        try {
            if (this.mAppOps != null) {
                this.mAppOps.stopWatchingMode(this.mAppOpsCallback);
            }
        } catch (java.lang.Exception e) {
        }
    }

    protected void finalize() {
        release();
    }

    public final void release() {
        native_release();
        this.mFaceDetectionRunning = false;
        releaseAppOps();
    }

    public final void setPreviewDisplay(android.view.SurfaceHolder surfaceHolder) throws java.io.IOException {
        if (surfaceHolder != null) {
            setPreviewSurface(surfaceHolder.getSurface());
        } else {
            setPreviewSurface(null);
        }
    }

    public final void stopPreview() {
        _stopPreview();
        this.mFaceDetectionRunning = false;
        this.mShutterCallback = null;
        this.mRawImageCallback = null;
        this.mPostviewCallback = null;
        this.mJpegCallback = null;
        synchronized (this.mAutoFocusCallbackLock) {
            this.mAutoFocusCallback = null;
        }
        this.mAutoFocusMoveCallback = null;
    }

    public final void setPreviewCallback(android.hardware.Camera.PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
        this.mOneShot = false;
        this.mWithBuffer = false;
        if (previewCallback != null) {
            this.mUsingPreviewAllocation = false;
        }
        setHasPreviewCallback(previewCallback != null, false);
    }

    public final void setOneShotPreviewCallback(android.hardware.Camera.PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
        boolean z = true;
        this.mOneShot = true;
        this.mWithBuffer = false;
        if (previewCallback != null) {
            this.mUsingPreviewAllocation = false;
        }
        if (previewCallback == null) {
            z = false;
        }
        setHasPreviewCallback(z, false);
    }

    public final void setPreviewCallbackWithBuffer(android.hardware.Camera.PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
        boolean z = false;
        this.mOneShot = false;
        this.mWithBuffer = true;
        if (previewCallback != null) {
            this.mUsingPreviewAllocation = false;
        }
        if (previewCallback != null) {
            z = true;
        }
        setHasPreviewCallback(z, true);
    }

    public final void addCallbackBuffer(byte[] bArr) {
        _addCallbackBuffer(bArr, 16);
    }

    public final void addRawImageCallbackBuffer(byte[] bArr) {
        addCallbackBuffer(bArr, 128);
    }

    private final void addCallbackBuffer(byte[] bArr, int i) {
        if (i != 16 && i != 128) {
            throw new java.lang.IllegalArgumentException("Unsupported message type: " + i);
        }
        _addCallbackBuffer(bArr, i);
    }

    private class EventHandler extends android.os.Handler {
        private final android.hardware.Camera mCamera;

        public EventHandler(android.hardware.Camera camera, android.os.Looper looper) {
            super(looper);
            this.mCamera = camera;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.hardware.Camera.AutoFocusCallback autoFocusCallback;
            switch (message.what) {
                case 1:
                    android.util.Log.e(android.hardware.Camera.TAG, "Error " + message.arg1);
                    if (android.hardware.Camera.this.mDetailedErrorCallback != null) {
                        android.hardware.Camera.this.mDetailedErrorCallback.onError(message.arg1, this.mCamera);
                        return;
                    } else {
                        if (android.hardware.Camera.this.mErrorCallback != null) {
                            if (message.arg1 == 3) {
                                android.hardware.Camera.this.mErrorCallback.onError(2, this.mCamera);
                                return;
                            } else {
                                android.hardware.Camera.this.mErrorCallback.onError(message.arg1, this.mCamera);
                                return;
                            }
                        }
                        return;
                    }
                case 2:
                    if (android.hardware.Camera.this.mShutterCallback != null) {
                        android.hardware.Camera.this.mShutterCallback.onShutter();
                        return;
                    }
                    return;
                case 4:
                    synchronized (android.hardware.Camera.this.mAutoFocusCallbackLock) {
                        autoFocusCallback = android.hardware.Camera.this.mAutoFocusCallback;
                    }
                    if (autoFocusCallback != null) {
                        autoFocusCallback.onAutoFocus(message.arg1 != 0, this.mCamera);
                        return;
                    }
                    return;
                case 8:
                    if (android.hardware.Camera.this.mZoomListener != null) {
                        android.hardware.Camera.this.mZoomListener.onZoomChange(message.arg1, message.arg2 != 0, this.mCamera);
                        return;
                    }
                    return;
                case 16:
                    android.hardware.Camera.PreviewCallback previewCallback = android.hardware.Camera.this.mPreviewCallback;
                    if (previewCallback != null) {
                        if (android.hardware.Camera.this.mOneShot) {
                            android.hardware.Camera.this.mPreviewCallback = null;
                        } else if (!android.hardware.Camera.this.mWithBuffer) {
                            android.hardware.Camera.this.setHasPreviewCallback(true, false);
                        }
                        previewCallback.onPreviewFrame((byte[]) message.obj, this.mCamera);
                        return;
                    }
                    return;
                case 64:
                    if (android.hardware.Camera.this.mPostviewCallback != null) {
                        android.hardware.Camera.this.mPostviewCallback.onPictureTaken((byte[]) message.obj, this.mCamera);
                        return;
                    }
                    return;
                case 128:
                    if (android.hardware.Camera.this.mRawImageCallback != null) {
                        android.hardware.Camera.this.mRawImageCallback.onPictureTaken((byte[]) message.obj, this.mCamera);
                        return;
                    }
                    return;
                case 256:
                    if (android.hardware.Camera.this.mJpegCallback != null) {
                        android.hardware.Camera.this.mJpegCallback.onPictureTaken((byte[]) message.obj, this.mCamera);
                        return;
                    }
                    return;
                case 1024:
                    if (android.hardware.Camera.this.mFaceListener != null) {
                        android.hardware.Camera.this.mFaceListener.onFaceDetection((android.hardware.Camera.Face[]) message.obj, this.mCamera);
                        return;
                    }
                    return;
                case 2048:
                    if (android.hardware.Camera.this.mAutoFocusMoveCallback != null) {
                        android.hardware.Camera.this.mAutoFocusMoveCallback.onAutoFocusMoving(message.arg1 != 0, this.mCamera);
                        return;
                    }
                    return;
                default:
                    android.util.Log.e(android.hardware.Camera.TAG, "Unknown message type " + message.what);
                    return;
            }
        }
    }

    private static void postEventFromNative(java.lang.Object obj, int i, int i2, int i3, java.lang.Object obj2) {
        android.hardware.Camera camera = (android.hardware.Camera) ((java.lang.ref.WeakReference) obj).get();
        if (camera != null && camera.mEventHandler != null) {
            camera.mEventHandler.sendMessage(camera.mEventHandler.obtainMessage(i, i2, i3, obj2));
        }
    }

    public final void autoFocus(android.hardware.Camera.AutoFocusCallback autoFocusCallback) {
        synchronized (this.mAutoFocusCallbackLock) {
            this.mAutoFocusCallback = autoFocusCallback;
        }
        native_autoFocus();
    }

    public final void cancelAutoFocus() {
        synchronized (this.mAutoFocusCallbackLock) {
            this.mAutoFocusCallback = null;
        }
        native_cancelAutoFocus();
        this.mEventHandler.removeMessages(4);
    }

    public void setAutoFocusMoveCallback(android.hardware.Camera.AutoFocusMoveCallback autoFocusMoveCallback) {
        this.mAutoFocusMoveCallback = autoFocusMoveCallback;
        enableFocusMoveCallback(this.mAutoFocusMoveCallback != null ? 1 : 0);
    }

    public final void takePicture(android.hardware.Camera.ShutterCallback shutterCallback, android.hardware.Camera.PictureCallback pictureCallback, android.hardware.Camera.PictureCallback pictureCallback2) {
        takePicture(shutterCallback, pictureCallback, null, pictureCallback2);
    }

    public final void takePicture(android.hardware.Camera.ShutterCallback shutterCallback, android.hardware.Camera.PictureCallback pictureCallback, android.hardware.Camera.PictureCallback pictureCallback2, android.hardware.Camera.PictureCallback pictureCallback3) {
        int i;
        this.mShutterCallback = shutterCallback;
        this.mRawImageCallback = pictureCallback;
        this.mPostviewCallback = pictureCallback2;
        this.mJpegCallback = pictureCallback3;
        if (this.mShutterCallback == null) {
            i = 0;
        } else {
            i = 2;
        }
        if (this.mRawImageCallback != null) {
            i |= 128;
        }
        if (this.mPostviewCallback != null) {
            i |= 64;
        }
        if (this.mJpegCallback != null) {
            i |= 256;
        }
        native_takePicture(i);
        this.mFaceDetectionRunning = false;
    }

    public final boolean enableShutterSound(boolean z) {
        boolean _enableShutterSound;
        boolean z2 = true;
        try {
            if (android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio")).isCameraSoundForced()) {
                z2 = false;
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Audio service is unavailable for queries");
        }
        if (!z && !z2) {
            return false;
        }
        synchronized (this.mShutterSoundLock) {
            this.mShutterSoundEnabledFromApp = z;
            _enableShutterSound = _enableShutterSound(z);
            if (z && !this.mHasAppOpsPlayAudio) {
                android.util.Log.i(TAG, "Shutter sound is not allowed by AppOpsManager");
                if (z2) {
                    _enableShutterSound(false);
                }
            }
        }
        return _enableShutterSound;
    }

    public final boolean disableShutterSound() {
        return _enableShutterSound(false);
    }

    private static class IAppOpsCallbackWrapper extends com.android.internal.app.IAppOpsCallback.Stub {
        private final java.lang.ref.WeakReference<android.hardware.Camera> mWeakCamera;

        IAppOpsCallbackWrapper(android.hardware.Camera camera) {
            this.mWeakCamera = new java.lang.ref.WeakReference<>(camera);
        }

        @Override // com.android.internal.app.IAppOpsCallback
        public void opChanged(int i, int i2, java.lang.String str, java.lang.String str2) {
            android.hardware.Camera camera;
            if (i == 28 && (camera = this.mWeakCamera.get()) != null) {
                camera.updateAppOpsPlayAudio();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppOpsPlayAudio() {
        int i;
        synchronized (this.mShutterSoundLock) {
            boolean z = this.mHasAppOpsPlayAudio;
            try {
                boolean z2 = true;
                if (this.mAppOps == null) {
                    i = 1;
                } else {
                    i = this.mAppOps.checkAudioOperation(28, 13, android.os.Process.myUid(), android.app.ActivityThread.currentPackageName());
                }
                if (i != 0) {
                    z2 = false;
                }
                this.mHasAppOpsPlayAudio = z2;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "AppOpsService check audio operation failed");
                this.mHasAppOpsPlayAudio = false;
            }
            if (z != this.mHasAppOpsPlayAudio) {
                if (!this.mHasAppOpsPlayAudio) {
                    try {
                    } catch (android.os.RemoteException e2) {
                        android.util.Log.e(TAG, "Audio service is unavailable for queries");
                    }
                    if (android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio")).isCameraSoundForced()) {
                    } else {
                        _enableShutterSound(false);
                    }
                } else {
                    enableShutterSound(this.mShutterSoundEnabledFromApp);
                }
            }
        }
    }

    public final void setZoomChangeListener(android.hardware.Camera.OnZoomChangeListener onZoomChangeListener) {
        this.mZoomListener = onZoomChangeListener;
    }

    public final void setFaceDetectionListener(android.hardware.Camera.FaceDetectionListener faceDetectionListener) {
        this.mFaceListener = faceDetectionListener;
    }

    public final void startFaceDetection() {
        if (this.mFaceDetectionRunning) {
            throw new java.lang.RuntimeException("Face detection is already running");
        }
        _startFaceDetection(0);
        this.mFaceDetectionRunning = true;
    }

    public final void stopFaceDetection() {
        _stopFaceDetection();
        this.mFaceDetectionRunning = false;
    }

    public final void setErrorCallback(android.hardware.Camera.ErrorCallback errorCallback) {
        this.mErrorCallback = errorCallback;
    }

    public final void setDetailedErrorCallback(android.hardware.Camera.ErrorCallback errorCallback) {
        this.mDetailedErrorCallback = errorCallback;
    }

    public void setParameters(android.hardware.Camera.Parameters parameters) {
        if (this.mUsingPreviewAllocation) {
            android.hardware.Camera.Size previewSize = parameters.getPreviewSize();
            android.hardware.Camera.Size previewSize2 = getParameters().getPreviewSize();
            if (previewSize.width != previewSize2.width || previewSize.height != previewSize2.height) {
                throw new java.lang.IllegalStateException("Cannot change preview size while a preview allocation is configured.");
            }
        }
        native_setParameters(parameters.flatten());
    }

    public android.hardware.Camera.Parameters getParameters() {
        android.hardware.Camera.Parameters parameters = new android.hardware.Camera.Parameters();
        parameters.unflatten(native_getParameters());
        return parameters;
    }

    public static android.hardware.Camera.Parameters getEmptyParameters() {
        android.hardware.Camera camera = new android.hardware.Camera();
        java.util.Objects.requireNonNull(camera);
        return new android.hardware.Camera.Parameters();
    }

    public static android.hardware.Camera.Parameters getParametersCopy(android.hardware.Camera.Parameters parameters) {
        if (parameters == null) {
            throw new java.lang.NullPointerException("parameters must not be null");
        }
        android.hardware.Camera outer = parameters.getOuter();
        java.util.Objects.requireNonNull(outer);
        android.hardware.Camera.Parameters parameters2 = new android.hardware.Camera.Parameters();
        parameters2.copyFrom(parameters);
        return parameters2;
    }

    @java.lang.Deprecated
    public class Size {
        public int height;
        public int width;

        public Size(int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.hardware.Camera.Size)) {
                return false;
            }
            android.hardware.Camera.Size size = (android.hardware.Camera.Size) obj;
            return this.width == size.width && this.height == size.height;
        }

        public int hashCode() {
            return (this.width * 32713) + this.height;
        }
    }

    @java.lang.Deprecated
    public static class Area {
        public android.graphics.Rect rect;
        public int weight;

        public Area(android.graphics.Rect rect, int i) {
            this.rect = rect;
            this.weight = i;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.hardware.Camera.Area)) {
                return false;
            }
            android.hardware.Camera.Area area = (android.hardware.Camera.Area) obj;
            if (this.rect == null) {
                if (area.rect != null) {
                    return false;
                }
            } else if (!this.rect.equals(area.rect)) {
                return false;
            }
            return this.weight == area.weight;
        }
    }

    @java.lang.Deprecated
    public class Parameters {
        public static final java.lang.String ANTIBANDING_50HZ = "50hz";
        public static final java.lang.String ANTIBANDING_60HZ = "60hz";
        public static final java.lang.String ANTIBANDING_AUTO = "auto";
        public static final java.lang.String ANTIBANDING_OFF = "off";
        public static final java.lang.String EFFECT_AQUA = "aqua";
        public static final java.lang.String EFFECT_BLACKBOARD = "blackboard";
        public static final java.lang.String EFFECT_MONO = "mono";
        public static final java.lang.String EFFECT_NEGATIVE = "negative";
        public static final java.lang.String EFFECT_NONE = "none";
        public static final java.lang.String EFFECT_POSTERIZE = "posterize";
        public static final java.lang.String EFFECT_SEPIA = "sepia";
        public static final java.lang.String EFFECT_SOLARIZE = "solarize";
        public static final java.lang.String EFFECT_WHITEBOARD = "whiteboard";
        private static final java.lang.String FALSE = "false";
        public static final java.lang.String FLASH_MODE_AUTO = "auto";
        public static final java.lang.String FLASH_MODE_OFF = "off";
        public static final java.lang.String FLASH_MODE_ON = "on";
        public static final java.lang.String FLASH_MODE_RED_EYE = "red-eye";
        public static final java.lang.String FLASH_MODE_TORCH = "torch";
        public static final int FOCUS_DISTANCE_FAR_INDEX = 2;
        public static final int FOCUS_DISTANCE_NEAR_INDEX = 0;
        public static final int FOCUS_DISTANCE_OPTIMAL_INDEX = 1;
        public static final java.lang.String FOCUS_MODE_AUTO = "auto";
        public static final java.lang.String FOCUS_MODE_CONTINUOUS_PICTURE = "continuous-picture";
        public static final java.lang.String FOCUS_MODE_CONTINUOUS_VIDEO = "continuous-video";
        public static final java.lang.String FOCUS_MODE_EDOF = "edof";
        public static final java.lang.String FOCUS_MODE_FIXED = "fixed";
        public static final java.lang.String FOCUS_MODE_INFINITY = "infinity";
        public static final java.lang.String FOCUS_MODE_MACRO = "macro";
        private static final java.lang.String KEY_ANTIBANDING = "antibanding";
        private static final java.lang.String KEY_AUTO_EXPOSURE_LOCK = "auto-exposure-lock";
        private static final java.lang.String KEY_AUTO_EXPOSURE_LOCK_SUPPORTED = "auto-exposure-lock-supported";
        private static final java.lang.String KEY_AUTO_WHITEBALANCE_LOCK = "auto-whitebalance-lock";
        private static final java.lang.String KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED = "auto-whitebalance-lock-supported";
        private static final java.lang.String KEY_EFFECT = "effect";
        private static final java.lang.String KEY_EXPOSURE_COMPENSATION = "exposure-compensation";
        private static final java.lang.String KEY_EXPOSURE_COMPENSATION_STEP = "exposure-compensation-step";
        private static final java.lang.String KEY_FLASH_MODE = "flash-mode";
        private static final java.lang.String KEY_FOCAL_LENGTH = "focal-length";
        private static final java.lang.String KEY_FOCUS_AREAS = "focus-areas";
        private static final java.lang.String KEY_FOCUS_DISTANCES = "focus-distances";
        private static final java.lang.String KEY_FOCUS_MODE = "focus-mode";
        private static final java.lang.String KEY_GPS_ALTITUDE = "gps-altitude";
        private static final java.lang.String KEY_GPS_LATITUDE = "gps-latitude";
        private static final java.lang.String KEY_GPS_LONGITUDE = "gps-longitude";
        private static final java.lang.String KEY_GPS_PROCESSING_METHOD = "gps-processing-method";
        private static final java.lang.String KEY_GPS_TIMESTAMP = "gps-timestamp";
        private static final java.lang.String KEY_HORIZONTAL_VIEW_ANGLE = "horizontal-view-angle";
        private static final java.lang.String KEY_JPEG_QUALITY = "jpeg-quality";
        private static final java.lang.String KEY_JPEG_THUMBNAIL_HEIGHT = "jpeg-thumbnail-height";
        private static final java.lang.String KEY_JPEG_THUMBNAIL_QUALITY = "jpeg-thumbnail-quality";
        private static final java.lang.String KEY_JPEG_THUMBNAIL_SIZE = "jpeg-thumbnail-size";
        private static final java.lang.String KEY_JPEG_THUMBNAIL_WIDTH = "jpeg-thumbnail-width";
        private static final java.lang.String KEY_MAX_EXPOSURE_COMPENSATION = "max-exposure-compensation";
        private static final java.lang.String KEY_MAX_NUM_DETECTED_FACES_HW = "max-num-detected-faces-hw";
        private static final java.lang.String KEY_MAX_NUM_DETECTED_FACES_SW = "max-num-detected-faces-sw";
        private static final java.lang.String KEY_MAX_NUM_FOCUS_AREAS = "max-num-focus-areas";
        private static final java.lang.String KEY_MAX_NUM_METERING_AREAS = "max-num-metering-areas";
        private static final java.lang.String KEY_MAX_ZOOM = "max-zoom";
        private static final java.lang.String KEY_METERING_AREAS = "metering-areas";
        private static final java.lang.String KEY_MIN_EXPOSURE_COMPENSATION = "min-exposure-compensation";
        private static final java.lang.String KEY_PICTURE_FORMAT = "picture-format";
        private static final java.lang.String KEY_PICTURE_SIZE = "picture-size";
        private static final java.lang.String KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO = "preferred-preview-size-for-video";
        private static final java.lang.String KEY_PREVIEW_FORMAT = "preview-format";
        private static final java.lang.String KEY_PREVIEW_FPS_RANGE = "preview-fps-range";
        private static final java.lang.String KEY_PREVIEW_FRAME_RATE = "preview-frame-rate";
        private static final java.lang.String KEY_PREVIEW_SIZE = "preview-size";
        private static final java.lang.String KEY_RECORDING_HINT = "recording-hint";
        private static final java.lang.String KEY_ROTATION = "rotation";
        private static final java.lang.String KEY_SCENE_MODE = "scene-mode";
        private static final java.lang.String KEY_SMOOTH_ZOOM_SUPPORTED = "smooth-zoom-supported";
        private static final java.lang.String KEY_VERTICAL_VIEW_ANGLE = "vertical-view-angle";
        private static final java.lang.String KEY_VIDEO_SIZE = "video-size";
        private static final java.lang.String KEY_VIDEO_SNAPSHOT_SUPPORTED = "video-snapshot-supported";
        private static final java.lang.String KEY_VIDEO_STABILIZATION = "video-stabilization";
        private static final java.lang.String KEY_VIDEO_STABILIZATION_SUPPORTED = "video-stabilization-supported";
        private static final java.lang.String KEY_WHITE_BALANCE = "whitebalance";
        private static final java.lang.String KEY_ZOOM = "zoom";
        private static final java.lang.String KEY_ZOOM_RATIOS = "zoom-ratios";
        private static final java.lang.String KEY_ZOOM_SUPPORTED = "zoom-supported";
        private static final java.lang.String PIXEL_FORMAT_BAYER_RGGB = "bayer-rggb";
        private static final java.lang.String PIXEL_FORMAT_JPEG = "jpeg";
        private static final java.lang.String PIXEL_FORMAT_RGB565 = "rgb565";
        private static final java.lang.String PIXEL_FORMAT_YUV420P = "yuv420p";
        private static final java.lang.String PIXEL_FORMAT_YUV420SP = "yuv420sp";
        private static final java.lang.String PIXEL_FORMAT_YUV422I = "yuv422i-yuyv";
        private static final java.lang.String PIXEL_FORMAT_YUV422SP = "yuv422sp";
        public static final int PREVIEW_FPS_MAX_INDEX = 1;
        public static final int PREVIEW_FPS_MIN_INDEX = 0;
        public static final java.lang.String SCENE_MODE_ACTION = "action";
        public static final java.lang.String SCENE_MODE_AUTO = "auto";
        public static final java.lang.String SCENE_MODE_BARCODE = "barcode";
        public static final java.lang.String SCENE_MODE_BEACH = "beach";
        public static final java.lang.String SCENE_MODE_CANDLELIGHT = "candlelight";
        public static final java.lang.String SCENE_MODE_FIREWORKS = "fireworks";
        public static final java.lang.String SCENE_MODE_HDR = "hdr";
        public static final java.lang.String SCENE_MODE_LANDSCAPE = "landscape";
        public static final java.lang.String SCENE_MODE_NIGHT = "night";
        public static final java.lang.String SCENE_MODE_NIGHT_PORTRAIT = "night-portrait";
        public static final java.lang.String SCENE_MODE_PARTY = "party";
        public static final java.lang.String SCENE_MODE_PORTRAIT = "portrait";
        public static final java.lang.String SCENE_MODE_SNOW = "snow";
        public static final java.lang.String SCENE_MODE_SPORTS = "sports";
        public static final java.lang.String SCENE_MODE_STEADYPHOTO = "steadyphoto";
        public static final java.lang.String SCENE_MODE_SUNSET = "sunset";
        public static final java.lang.String SCENE_MODE_THEATRE = "theatre";
        private static final java.lang.String SUPPORTED_VALUES_SUFFIX = "-values";
        private static final java.lang.String TRUE = "true";
        public static final java.lang.String WHITE_BALANCE_AUTO = "auto";
        public static final java.lang.String WHITE_BALANCE_CLOUDY_DAYLIGHT = "cloudy-daylight";
        public static final java.lang.String WHITE_BALANCE_DAYLIGHT = "daylight";
        public static final java.lang.String WHITE_BALANCE_FLUORESCENT = "fluorescent";
        public static final java.lang.String WHITE_BALANCE_INCANDESCENT = "incandescent";
        public static final java.lang.String WHITE_BALANCE_SHADE = "shade";
        public static final java.lang.String WHITE_BALANCE_TWILIGHT = "twilight";
        public static final java.lang.String WHITE_BALANCE_WARM_FLUORESCENT = "warm-fluorescent";
        private final java.util.LinkedHashMap<java.lang.String, java.lang.String> mMap;

        private Parameters() {
            this.mMap = new java.util.LinkedHashMap<>(64);
        }

        public void copyFrom(android.hardware.Camera.Parameters parameters) {
            if (parameters == null) {
                throw new java.lang.NullPointerException("other must not be null");
            }
            this.mMap.putAll(parameters.mMap);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.hardware.Camera getOuter() {
            return android.hardware.Camera.this;
        }

        public boolean same(android.hardware.Camera.Parameters parameters) {
            if (this == parameters) {
                return true;
            }
            return parameters != null && this.mMap.equals(parameters.mMap);
        }

        @java.lang.Deprecated
        public void dump() {
            android.util.Log.e(android.hardware.Camera.TAG, "dump: size=" + this.mMap.size());
            for (java.lang.String str : this.mMap.keySet()) {
                android.util.Log.e(android.hardware.Camera.TAG, "dump: " + str + "=" + this.mMap.get(str));
            }
        }

        public java.lang.String flatten() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            for (java.lang.String str : this.mMap.keySet()) {
                sb.append(str);
                sb.append("=");
                sb.append(this.mMap.get(str));
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        public void unflatten(java.lang.String str) {
            this.mMap.clear();
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(';');
            simpleStringSplitter.setString(str);
            for (java.lang.String str2 : simpleStringSplitter) {
                int indexOf = str2.indexOf(61);
                if (indexOf != -1) {
                    this.mMap.put(str2.substring(0, indexOf), str2.substring(indexOf + 1));
                }
            }
        }

        public void remove(java.lang.String str) {
            this.mMap.remove(str);
        }

        public void set(java.lang.String str, java.lang.String str2) {
            if (str.indexOf(61) != -1 || str.indexOf(59) != -1 || str.indexOf(0) != -1) {
                android.util.Log.e(android.hardware.Camera.TAG, "Key \"" + str + "\" contains invalid character (= or ; or \\0)");
            } else if (str2.indexOf(61) != -1 || str2.indexOf(59) != -1 || str2.indexOf(0) != -1) {
                android.util.Log.e(android.hardware.Camera.TAG, "Value \"" + str2 + "\" contains invalid character (= or ; or \\0)");
            } else {
                put(str, str2);
            }
        }

        public void set(java.lang.String str, int i) {
            put(str, java.lang.Integer.toString(i));
        }

        private void put(java.lang.String str, java.lang.String str2) {
            this.mMap.remove(str);
            this.mMap.put(str, str2);
        }

        private void set(java.lang.String str, java.util.List<android.hardware.Camera.Area> list) {
            if (list == null) {
                set(str, "(0,0,0,0,0)");
                return;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                android.hardware.Camera.Area area = list.get(i);
                android.graphics.Rect rect = area.rect;
                sb.append('(');
                sb.append(rect.left);
                sb.append(',');
                sb.append(rect.top);
                sb.append(',');
                sb.append(rect.right);
                sb.append(',');
                sb.append(rect.bottom);
                sb.append(',');
                sb.append(area.weight);
                sb.append(')');
                if (i != list.size() - 1) {
                    sb.append(',');
                }
            }
            set(str, sb.toString());
        }

        public java.lang.String get(java.lang.String str) {
            return this.mMap.get(str);
        }

        public int getInt(java.lang.String str) {
            return java.lang.Integer.parseInt(this.mMap.get(str));
        }

        public void setPreviewSize(int i, int i2) {
            set(KEY_PREVIEW_SIZE, java.lang.Integer.toString(i) + "x" + java.lang.Integer.toString(i2));
        }

        public android.hardware.Camera.Size getPreviewSize() {
            return strToSize(get(KEY_PREVIEW_SIZE));
        }

        public java.util.List<android.hardware.Camera.Size> getSupportedPreviewSizes() {
            return splitSize(get("preview-size-values"));
        }

        public java.util.List<android.hardware.Camera.Size> getSupportedVideoSizes() {
            return splitSize(get("video-size-values"));
        }

        public android.hardware.Camera.Size getPreferredPreviewSizeForVideo() {
            return strToSize(get(KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO));
        }

        public void setJpegThumbnailSize(int i, int i2) {
            set(KEY_JPEG_THUMBNAIL_WIDTH, i);
            set(KEY_JPEG_THUMBNAIL_HEIGHT, i2);
        }

        public android.hardware.Camera.Size getJpegThumbnailSize() {
            return android.hardware.Camera.this.new Size(getInt(KEY_JPEG_THUMBNAIL_WIDTH), getInt(KEY_JPEG_THUMBNAIL_HEIGHT));
        }

        public java.util.List<android.hardware.Camera.Size> getSupportedJpegThumbnailSizes() {
            return splitSize(get("jpeg-thumbnail-size-values"));
        }

        public void setJpegThumbnailQuality(int i) {
            set(KEY_JPEG_THUMBNAIL_QUALITY, i);
        }

        public int getJpegThumbnailQuality() {
            return getInt(KEY_JPEG_THUMBNAIL_QUALITY);
        }

        public void setJpegQuality(int i) {
            set(KEY_JPEG_QUALITY, i);
        }

        public int getJpegQuality() {
            return getInt(KEY_JPEG_QUALITY);
        }

        @java.lang.Deprecated
        public void setPreviewFrameRate(int i) {
            set(KEY_PREVIEW_FRAME_RATE, i);
        }

        @java.lang.Deprecated
        public int getPreviewFrameRate() {
            return getInt(KEY_PREVIEW_FRAME_RATE);
        }

        @java.lang.Deprecated
        public java.util.List<java.lang.Integer> getSupportedPreviewFrameRates() {
            return splitInt(get("preview-frame-rate-values"));
        }

        public void setPreviewFpsRange(int i, int i2) {
            set(KEY_PREVIEW_FPS_RANGE, "" + i + "," + i2);
        }

        public void getPreviewFpsRange(int[] iArr) {
            if (iArr == null || iArr.length != 2) {
                throw new java.lang.IllegalArgumentException("range must be an array with two elements.");
            }
            splitInt(get(KEY_PREVIEW_FPS_RANGE), iArr);
        }

        public java.util.List<int[]> getSupportedPreviewFpsRange() {
            return splitRange(get("preview-fps-range-values"));
        }

        public void setPreviewFormat(int i) {
            java.lang.String cameraFormatForPixelFormat = cameraFormatForPixelFormat(i);
            if (cameraFormatForPixelFormat == null) {
                throw new java.lang.IllegalArgumentException("Invalid pixel_format=" + i);
            }
            set(KEY_PREVIEW_FORMAT, cameraFormatForPixelFormat);
        }

        public int getPreviewFormat() {
            return pixelFormatForCameraFormat(get(KEY_PREVIEW_FORMAT));
        }

        public java.util.List<java.lang.Integer> getSupportedPreviewFormats() {
            java.lang.String str = get("preview-format-values");
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<java.lang.String> it = split(str).iterator();
            while (it.hasNext()) {
                int pixelFormatForCameraFormat = pixelFormatForCameraFormat(it.next());
                if (pixelFormatForCameraFormat != 0) {
                    arrayList.add(java.lang.Integer.valueOf(pixelFormatForCameraFormat));
                }
            }
            return arrayList;
        }

        public void setPictureSize(int i, int i2) {
            set(KEY_PICTURE_SIZE, java.lang.Integer.toString(i) + "x" + java.lang.Integer.toString(i2));
        }

        public android.hardware.Camera.Size getPictureSize() {
            return strToSize(get(KEY_PICTURE_SIZE));
        }

        public java.util.List<android.hardware.Camera.Size> getSupportedPictureSizes() {
            return splitSize(get("picture-size-values"));
        }

        public void setPictureFormat(int i) {
            java.lang.String cameraFormatForPixelFormat = cameraFormatForPixelFormat(i);
            if (cameraFormatForPixelFormat == null) {
                throw new java.lang.IllegalArgumentException("Invalid pixel_format=" + i);
            }
            set(KEY_PICTURE_FORMAT, cameraFormatForPixelFormat);
        }

        public int getPictureFormat() {
            return pixelFormatForCameraFormat(get(KEY_PICTURE_FORMAT));
        }

        public java.util.List<java.lang.Integer> getSupportedPictureFormats() {
            java.lang.String str = get("picture-format-values");
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<java.lang.String> it = split(str).iterator();
            while (it.hasNext()) {
                int pixelFormatForCameraFormat = pixelFormatForCameraFormat(it.next());
                if (pixelFormatForCameraFormat != 0) {
                    arrayList.add(java.lang.Integer.valueOf(pixelFormatForCameraFormat));
                }
            }
            return arrayList;
        }

        private java.lang.String cameraFormatForPixelFormat(int i) {
            switch (i) {
                case 4:
                    return PIXEL_FORMAT_RGB565;
                case 16:
                    return PIXEL_FORMAT_YUV422SP;
                case 17:
                    return PIXEL_FORMAT_YUV420SP;
                case 20:
                    return PIXEL_FORMAT_YUV422I;
                case 256:
                    return PIXEL_FORMAT_JPEG;
                case 842094169:
                    return PIXEL_FORMAT_YUV420P;
                default:
                    return null;
            }
        }

        private int pixelFormatForCameraFormat(java.lang.String str) {
            if (str == null) {
                return 0;
            }
            if (str.equals(PIXEL_FORMAT_YUV422SP)) {
                return 16;
            }
            if (str.equals(PIXEL_FORMAT_YUV420SP)) {
                return 17;
            }
            if (str.equals(PIXEL_FORMAT_YUV422I)) {
                return 20;
            }
            if (str.equals(PIXEL_FORMAT_YUV420P)) {
                return 842094169;
            }
            if (str.equals(PIXEL_FORMAT_RGB565)) {
                return 4;
            }
            if (!str.equals(PIXEL_FORMAT_JPEG)) {
                return 0;
            }
            return 256;
        }

        public void setRotation(int i) {
            if (i == 0 || i == 90 || i == 180 || i == 270) {
                set(KEY_ROTATION, java.lang.Integer.toString(i));
                return;
            }
            throw new java.lang.IllegalArgumentException("Invalid rotation=" + i);
        }

        public void setGpsLatitude(double d) {
            set(KEY_GPS_LATITUDE, java.lang.Double.toString(d));
        }

        public void setGpsLongitude(double d) {
            set(KEY_GPS_LONGITUDE, java.lang.Double.toString(d));
        }

        public void setGpsAltitude(double d) {
            set(KEY_GPS_ALTITUDE, java.lang.Double.toString(d));
        }

        public void setGpsTimestamp(long j) {
            set(KEY_GPS_TIMESTAMP, java.lang.Long.toString(j));
        }

        public void setGpsProcessingMethod(java.lang.String str) {
            set(KEY_GPS_PROCESSING_METHOD, str);
        }

        public void removeGpsData() {
            remove(KEY_GPS_LATITUDE);
            remove(KEY_GPS_LONGITUDE);
            remove(KEY_GPS_ALTITUDE);
            remove(KEY_GPS_TIMESTAMP);
            remove(KEY_GPS_PROCESSING_METHOD);
        }

        public java.lang.String getWhiteBalance() {
            return get(KEY_WHITE_BALANCE);
        }

        public void setWhiteBalance(java.lang.String str) {
            if (same(str, get(KEY_WHITE_BALANCE))) {
                return;
            }
            set(KEY_WHITE_BALANCE, str);
            set(KEY_AUTO_WHITEBALANCE_LOCK, FALSE);
        }

        public java.util.List<java.lang.String> getSupportedWhiteBalance() {
            return split(get("whitebalance-values"));
        }

        public java.lang.String getColorEffect() {
            return get(KEY_EFFECT);
        }

        public void setColorEffect(java.lang.String str) {
            set(KEY_EFFECT, str);
        }

        public java.util.List<java.lang.String> getSupportedColorEffects() {
            return split(get("effect-values"));
        }

        public java.lang.String getAntibanding() {
            return get(KEY_ANTIBANDING);
        }

        public void setAntibanding(java.lang.String str) {
            set(KEY_ANTIBANDING, str);
        }

        public java.util.List<java.lang.String> getSupportedAntibanding() {
            return split(get("antibanding-values"));
        }

        public java.lang.String getSceneMode() {
            return get(KEY_SCENE_MODE);
        }

        public void setSceneMode(java.lang.String str) {
            set(KEY_SCENE_MODE, str);
        }

        public java.util.List<java.lang.String> getSupportedSceneModes() {
            return split(get("scene-mode-values"));
        }

        public java.lang.String getFlashMode() {
            return get(KEY_FLASH_MODE);
        }

        public void setFlashMode(java.lang.String str) {
            set(KEY_FLASH_MODE, str);
        }

        public java.util.List<java.lang.String> getSupportedFlashModes() {
            return split(get("flash-mode-values"));
        }

        public java.lang.String getFocusMode() {
            return get(KEY_FOCUS_MODE);
        }

        public void setFocusMode(java.lang.String str) {
            set(KEY_FOCUS_MODE, str);
        }

        public java.util.List<java.lang.String> getSupportedFocusModes() {
            return split(get("focus-mode-values"));
        }

        public float getFocalLength() {
            return java.lang.Float.parseFloat(get(KEY_FOCAL_LENGTH));
        }

        public float getHorizontalViewAngle() {
            return java.lang.Float.parseFloat(get(KEY_HORIZONTAL_VIEW_ANGLE));
        }

        public float getVerticalViewAngle() {
            return java.lang.Float.parseFloat(get(KEY_VERTICAL_VIEW_ANGLE));
        }

        public int getExposureCompensation() {
            return getInt(KEY_EXPOSURE_COMPENSATION, 0);
        }

        public void setExposureCompensation(int i) {
            set(KEY_EXPOSURE_COMPENSATION, i);
        }

        public int getMaxExposureCompensation() {
            return getInt(KEY_MAX_EXPOSURE_COMPENSATION, 0);
        }

        public int getMinExposureCompensation() {
            return getInt(KEY_MIN_EXPOSURE_COMPENSATION, 0);
        }

        public float getExposureCompensationStep() {
            return getFloat(KEY_EXPOSURE_COMPENSATION_STEP, 0.0f);
        }

        public void setAutoExposureLock(boolean z) {
            set(KEY_AUTO_EXPOSURE_LOCK, z ? TRUE : FALSE);
        }

        public boolean getAutoExposureLock() {
            return TRUE.equals(get(KEY_AUTO_EXPOSURE_LOCK));
        }

        public boolean isAutoExposureLockSupported() {
            return TRUE.equals(get(KEY_AUTO_EXPOSURE_LOCK_SUPPORTED));
        }

        public void setAutoWhiteBalanceLock(boolean z) {
            set(KEY_AUTO_WHITEBALANCE_LOCK, z ? TRUE : FALSE);
        }

        public boolean getAutoWhiteBalanceLock() {
            return TRUE.equals(get(KEY_AUTO_WHITEBALANCE_LOCK));
        }

        public boolean isAutoWhiteBalanceLockSupported() {
            return TRUE.equals(get(KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED));
        }

        public int getZoom() {
            return getInt(KEY_ZOOM, 0);
        }

        public void setZoom(int i) {
            set(KEY_ZOOM, i);
        }

        public boolean isZoomSupported() {
            return TRUE.equals(get(KEY_ZOOM_SUPPORTED));
        }

        public int getMaxZoom() {
            return getInt(KEY_MAX_ZOOM, 0);
        }

        public java.util.List<java.lang.Integer> getZoomRatios() {
            return splitInt(get(KEY_ZOOM_RATIOS));
        }

        public boolean isSmoothZoomSupported() {
            return TRUE.equals(get(KEY_SMOOTH_ZOOM_SUPPORTED));
        }

        public void getFocusDistances(float[] fArr) {
            if (fArr == null || fArr.length != 3) {
                throw new java.lang.IllegalArgumentException("output must be a float array with three elements.");
            }
            splitFloat(get(KEY_FOCUS_DISTANCES), fArr);
        }

        public int getMaxNumFocusAreas() {
            return getInt(KEY_MAX_NUM_FOCUS_AREAS, 0);
        }

        public java.util.List<android.hardware.Camera.Area> getFocusAreas() {
            return splitArea(get(KEY_FOCUS_AREAS));
        }

        public void setFocusAreas(java.util.List<android.hardware.Camera.Area> list) {
            set(KEY_FOCUS_AREAS, list);
        }

        public int getMaxNumMeteringAreas() {
            return getInt(KEY_MAX_NUM_METERING_AREAS, 0);
        }

        public java.util.List<android.hardware.Camera.Area> getMeteringAreas() {
            return splitArea(get(KEY_METERING_AREAS));
        }

        public void setMeteringAreas(java.util.List<android.hardware.Camera.Area> list) {
            set(KEY_METERING_AREAS, list);
        }

        public int getMaxNumDetectedFaces() {
            return getInt(KEY_MAX_NUM_DETECTED_FACES_HW, 0);
        }

        public void setRecordingHint(boolean z) {
            set(KEY_RECORDING_HINT, z ? TRUE : FALSE);
        }

        public boolean isVideoSnapshotSupported() {
            return TRUE.equals(get(KEY_VIDEO_SNAPSHOT_SUPPORTED));
        }

        public void setVideoStabilization(boolean z) {
            set(KEY_VIDEO_STABILIZATION, z ? TRUE : FALSE);
        }

        public boolean getVideoStabilization() {
            return TRUE.equals(get(KEY_VIDEO_STABILIZATION));
        }

        public boolean isVideoStabilizationSupported() {
            return TRUE.equals(get(KEY_VIDEO_STABILIZATION_SUPPORTED));
        }

        private java.util.ArrayList<java.lang.String> split(java.lang.String str) {
            if (str == null) {
                return null;
            }
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
            java.util.Iterator<java.lang.String> it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
            return arrayList;
        }

        private java.util.ArrayList<java.lang.Integer> splitInt(java.lang.String str) {
            if (str == null) {
                return null;
            }
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>();
            java.util.Iterator<java.lang.String> it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                arrayList.add(java.lang.Integer.valueOf(java.lang.Integer.parseInt(it.next())));
            }
            if (arrayList.size() == 0) {
                return null;
            }
            return arrayList;
        }

        private void splitInt(java.lang.String str, int[] iArr) {
            if (str == null) {
                return;
            }
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            java.util.Iterator<java.lang.String> it = simpleStringSplitter.iterator();
            int i = 0;
            while (it.hasNext()) {
                iArr[i] = java.lang.Integer.parseInt(it.next());
                i++;
            }
        }

        private void splitFloat(java.lang.String str, float[] fArr) {
            if (str == null) {
                return;
            }
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            java.util.Iterator<java.lang.String> it = simpleStringSplitter.iterator();
            int i = 0;
            while (it.hasNext()) {
                fArr[i] = java.lang.Float.parseFloat(it.next());
                i++;
            }
        }

        private float getFloat(java.lang.String str, float f) {
            try {
                return java.lang.Float.parseFloat(this.mMap.get(str));
            } catch (java.lang.NumberFormatException e) {
                return f;
            }
        }

        private int getInt(java.lang.String str, int i) {
            try {
                return java.lang.Integer.parseInt(this.mMap.get(str));
            } catch (java.lang.NumberFormatException e) {
                return i;
            }
        }

        private java.util.ArrayList<android.hardware.Camera.Size> splitSize(java.lang.String str) {
            if (str == null) {
                return null;
            }
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            java.util.ArrayList<android.hardware.Camera.Size> arrayList = new java.util.ArrayList<>();
            java.util.Iterator<java.lang.String> it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                android.hardware.Camera.Size strToSize = strToSize(it.next());
                if (strToSize != null) {
                    arrayList.add(strToSize);
                }
            }
            if (arrayList.size() == 0) {
                return null;
            }
            return arrayList;
        }

        private android.hardware.Camera.Size strToSize(java.lang.String str) {
            if (str == null) {
                return null;
            }
            int indexOf = str.indexOf(120);
            if (indexOf != -1) {
                return android.hardware.Camera.this.new Size(java.lang.Integer.parseInt(str.substring(0, indexOf)), java.lang.Integer.parseInt(str.substring(indexOf + 1)));
            }
            android.util.Log.e(android.hardware.Camera.TAG, "Invalid size parameter string=" + str);
            return null;
        }

        private java.util.ArrayList<int[]> splitRange(java.lang.String str) {
            int indexOf;
            if (str == null || str.charAt(0) != '(' || str.charAt(str.length() - 1) != ')') {
                android.util.Log.e(android.hardware.Camera.TAG, "Invalid range list string=" + str);
                return null;
            }
            java.util.ArrayList<int[]> arrayList = new java.util.ArrayList<>();
            int i = 1;
            do {
                int[] iArr = new int[2];
                indexOf = str.indexOf("),(", i);
                if (indexOf == -1) {
                    indexOf = str.length() - 1;
                }
                splitInt(str.substring(i, indexOf), iArr);
                arrayList.add(iArr);
                i = indexOf + 3;
            } while (indexOf != str.length() - 1);
            if (arrayList.size() == 0) {
                return null;
            }
            return arrayList;
        }

        private java.util.ArrayList<android.hardware.Camera.Area> splitArea(java.lang.String str) {
            int indexOf;
            if (str == null || str.charAt(0) != '(' || str.charAt(str.length() - 1) != ')') {
                android.util.Log.e(android.hardware.Camera.TAG, "Invalid area string=" + str);
                return null;
            }
            java.util.ArrayList<android.hardware.Camera.Area> arrayList = new java.util.ArrayList<>();
            int[] iArr = new int[5];
            int i = 1;
            do {
                indexOf = str.indexOf("),(", i);
                if (indexOf == -1) {
                    indexOf = str.length() - 1;
                }
                splitInt(str.substring(i, indexOf), iArr);
                arrayList.add(new android.hardware.Camera.Area(new android.graphics.Rect(iArr[0], iArr[1], iArr[2], iArr[3]), iArr[4]));
                i = indexOf + 3;
            } while (indexOf != str.length() - 1);
            if (arrayList.size() == 0) {
                return null;
            }
            if (arrayList.size() == 1) {
                android.hardware.Camera.Area area = arrayList.get(0);
                android.graphics.Rect rect = area.rect;
                if (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0 && area.weight == 0) {
                    return null;
                }
            }
            return arrayList;
        }

        private boolean same(java.lang.String str, java.lang.String str2) {
            if (str == null && str2 == null) {
                return true;
            }
            if (str != null && str.equals(str2)) {
                return true;
            }
            return false;
        }
    }
}
