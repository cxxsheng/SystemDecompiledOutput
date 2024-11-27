package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmManagerClient implements java.lang.AutoCloseable {
    private static final int ACTION_PROCESS_DRM_INFO = 1002;
    private static final int ACTION_REMOVE_ALL_RIGHTS = 1001;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_UNKNOWN = -2000;
    public static final int INVALID_SESSION = -1;
    private static final java.lang.String TAG = "DrmManagerClient";
    private android.content.Context mContext;
    private android.drm.DrmManagerClient.EventHandler mEventHandler;
    android.os.HandlerThread mEventThread;
    private android.drm.DrmManagerClient.InfoHandler mInfoHandler;
    android.os.HandlerThread mInfoThread;
    private long mNativeContext;
    private android.drm.DrmManagerClient.OnErrorListener mOnErrorListener;
    private android.drm.DrmManagerClient.OnEventListener mOnEventListener;
    private android.drm.DrmManagerClient.OnInfoListener mOnInfoListener;
    private int mUniqueId;
    private final java.util.concurrent.atomic.AtomicBoolean mClosed = new java.util.concurrent.atomic.AtomicBoolean();
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();

    public interface OnErrorListener {
        void onError(android.drm.DrmManagerClient drmManagerClient, android.drm.DrmErrorEvent drmErrorEvent);
    }

    public interface OnEventListener {
        void onEvent(android.drm.DrmManagerClient drmManagerClient, android.drm.DrmEvent drmEvent);
    }

    public interface OnInfoListener {
        void onInfo(android.drm.DrmManagerClient drmManagerClient, android.drm.DrmInfoEvent drmInfoEvent);
    }

    private native android.drm.DrmInfo _acquireDrmInfo(int i, android.drm.DrmInfoRequest drmInfoRequest);

    private native boolean _canHandle(int i, java.lang.String str, java.lang.String str2);

    private native int _checkRightsStatus(int i, java.lang.String str, int i2);

    private native android.drm.DrmConvertedStatus _closeConvertSession(int i, int i2);

    private native android.drm.DrmConvertedStatus _convertData(int i, int i2, byte[] bArr);

    private native android.drm.DrmSupportInfo[] _getAllSupportInfo(int i);

    private native android.content.ContentValues _getConstraints(int i, java.lang.String str, int i2);

    private native int _getDrmObjectType(int i, java.lang.String str, java.lang.String str2);

    private native android.content.ContentValues _getMetadata(int i, java.lang.String str);

    private native java.lang.String _getOriginalMimeType(int i, java.lang.String str, java.io.FileDescriptor fileDescriptor);

    private native int _initialize();

    private native void _installDrmEngine(int i, java.lang.String str);

    private native int _openConvertSession(int i, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native android.drm.DrmInfoStatus _processDrmInfo(int i, android.drm.DrmInfo drmInfo);

    private native void _release(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native int _removeAllRights(int i);

    private native int _removeRights(int i, java.lang.String str);

    private native int _saveRights(int i, android.drm.DrmRights drmRights, java.lang.String str, java.lang.String str2);

    private native void _setListeners(int i, java.lang.Object obj);

    static {
        java.lang.System.loadLibrary("drmframework_jni");
    }

    private class EventHandler extends android.os.Handler {
        public EventHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.drm.DrmErrorEvent drmErrorEvent;
            java.util.HashMap hashMap = new java.util.HashMap();
            android.drm.DrmEvent drmEvent = null;
            switch (message.what) {
                case 1001:
                    if (android.drm.DrmManagerClient.this._removeAllRights(android.drm.DrmManagerClient.this.mUniqueId) == 0) {
                        drmEvent = new android.drm.DrmEvent(android.drm.DrmManagerClient.this.mUniqueId, 1001, null);
                        drmErrorEvent = null;
                        break;
                    } else {
                        drmErrorEvent = new android.drm.DrmErrorEvent(android.drm.DrmManagerClient.this.mUniqueId, 2007, null);
                        break;
                    }
                case 1002:
                    android.drm.DrmInfo drmInfo = (android.drm.DrmInfo) message.obj;
                    android.drm.DrmInfoStatus _processDrmInfo = android.drm.DrmManagerClient.this._processDrmInfo(android.drm.DrmManagerClient.this.mUniqueId, drmInfo);
                    hashMap.put(android.drm.DrmEvent.DRM_INFO_STATUS_OBJECT, _processDrmInfo);
                    hashMap.put(android.drm.DrmEvent.DRM_INFO_OBJECT, drmInfo);
                    if (_processDrmInfo != null && 1 == _processDrmInfo.statusCode) {
                        drmEvent = new android.drm.DrmEvent(android.drm.DrmManagerClient.this.mUniqueId, android.drm.DrmManagerClient.this.getEventType(_processDrmInfo.infoType), null, hashMap);
                        drmErrorEvent = null;
                        break;
                    } else {
                        drmErrorEvent = new android.drm.DrmErrorEvent(android.drm.DrmManagerClient.this.mUniqueId, android.drm.DrmManagerClient.this.getErrorType(_processDrmInfo != null ? _processDrmInfo.infoType : drmInfo.getInfoType()), null, hashMap);
                        break;
                    }
                    break;
                default:
                    android.util.Log.e(android.drm.DrmManagerClient.TAG, "Unknown message type " + message.what);
                    return;
            }
            if (android.drm.DrmManagerClient.this.mOnEventListener != null && drmEvent != null) {
                android.drm.DrmManagerClient.this.mOnEventListener.onEvent(android.drm.DrmManagerClient.this, drmEvent);
            }
            if (android.drm.DrmManagerClient.this.mOnErrorListener != null && drmErrorEvent != null) {
                android.drm.DrmManagerClient.this.mOnErrorListener.onError(android.drm.DrmManagerClient.this, drmErrorEvent);
            }
        }
    }

    public static void notify(java.lang.Object obj, int i, int i2, java.lang.String str) {
        android.drm.DrmManagerClient drmManagerClient = (android.drm.DrmManagerClient) ((java.lang.ref.WeakReference) obj).get();
        if (drmManagerClient != null && drmManagerClient.mInfoHandler != null) {
            drmManagerClient.mInfoHandler.sendMessage(drmManagerClient.mInfoHandler.obtainMessage(1, i, i2, str));
        }
    }

    private class InfoHandler extends android.os.Handler {
        public static final int INFO_EVENT_TYPE = 1;

        public InfoHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.drm.DrmErrorEvent drmErrorEvent;
            switch (message.what) {
                case 1:
                    int i = message.arg1;
                    int i2 = message.arg2;
                    java.lang.String obj = message.obj.toString();
                    android.drm.DrmInfoEvent drmInfoEvent = null;
                    switch (i2) {
                        case 1:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            drmErrorEvent = null;
                            drmInfoEvent = new android.drm.DrmInfoEvent(i, i2, obj);
                            break;
                        case 2:
                            try {
                                android.drm.DrmUtils.removeFile(obj);
                            } catch (java.io.IOException e) {
                                e.printStackTrace();
                            }
                            drmErrorEvent = null;
                            drmInfoEvent = new android.drm.DrmInfoEvent(i, i2, obj);
                            break;
                        default:
                            drmErrorEvent = new android.drm.DrmErrorEvent(i, i2, obj);
                            break;
                    }
                    if (android.drm.DrmManagerClient.this.mOnInfoListener != null && drmInfoEvent != null) {
                        android.drm.DrmManagerClient.this.mOnInfoListener.onInfo(android.drm.DrmManagerClient.this, drmInfoEvent);
                    }
                    if (android.drm.DrmManagerClient.this.mOnErrorListener != null && drmErrorEvent != null) {
                        android.drm.DrmManagerClient.this.mOnErrorListener.onError(android.drm.DrmManagerClient.this, drmErrorEvent);
                        break;
                    }
                    break;
                default:
                    android.util.Log.e(android.drm.DrmManagerClient.TAG, "Unknown message type " + message.what);
                    break;
            }
        }
    }

    public DrmManagerClient(android.content.Context context) {
        this.mContext = context;
        createEventThreads();
        this.mUniqueId = _initialize();
        this.mCloseGuard.open("release");
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            if (this.mEventHandler != null) {
                this.mEventThread.quit();
                this.mEventThread = null;
            }
            if (this.mInfoHandler != null) {
                this.mInfoThread.quit();
                this.mInfoThread = null;
            }
            this.mEventHandler = null;
            this.mInfoHandler = null;
            this.mOnEventListener = null;
            this.mOnInfoListener = null;
            this.mOnErrorListener = null;
            _release(this.mUniqueId);
        }
    }

    @java.lang.Deprecated
    public void release() {
        close();
    }

    public synchronized void setOnInfoListener(android.drm.DrmManagerClient.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
        if (onInfoListener != null) {
            createListeners();
        }
    }

    public synchronized void setOnEventListener(android.drm.DrmManagerClient.OnEventListener onEventListener) {
        this.mOnEventListener = onEventListener;
        if (onEventListener != null) {
            createListeners();
        }
    }

    public synchronized void setOnErrorListener(android.drm.DrmManagerClient.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
        if (onErrorListener != null) {
            createListeners();
        }
    }

    public java.lang.String[] getAvailableDrmEngines() {
        android.drm.DrmSupportInfo[] _getAllSupportInfo = _getAllSupportInfo(this.mUniqueId);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.drm.DrmSupportInfo drmSupportInfo : _getAllSupportInfo) {
            arrayList.add(drmSupportInfo.getDescriprition());
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
    }

    public java.util.Collection<android.drm.DrmSupportInfo> getAvailableDrmSupportInfo() {
        return java.util.Arrays.asList(_getAllSupportInfo(this.mUniqueId));
    }

    public android.content.ContentValues getConstraints(java.lang.String str, int i) {
        if (str == null || str.equals("") || !android.drm.DrmStore.Action.isValid(i)) {
            throw new java.lang.IllegalArgumentException("Given usage or path is invalid/null");
        }
        return _getConstraints(this.mUniqueId, str, i);
    }

    public android.content.ContentValues getMetadata(java.lang.String str) {
        if (str == null || str.equals("")) {
            throw new java.lang.IllegalArgumentException("Given path is invalid/null");
        }
        return _getMetadata(this.mUniqueId, str);
    }

    public android.content.ContentValues getConstraints(android.net.Uri uri, int i) {
        if (uri == null || android.net.Uri.EMPTY == uri) {
            throw new java.lang.IllegalArgumentException("Uri should be non null");
        }
        return getConstraints(convertUriToPath(uri), i);
    }

    public android.content.ContentValues getMetadata(android.net.Uri uri) {
        if (uri == null || android.net.Uri.EMPTY == uri) {
            throw new java.lang.IllegalArgumentException("Uri should be non null");
        }
        return getMetadata(convertUriToPath(uri));
    }

    public int saveRights(android.drm.DrmRights drmRights, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        if (drmRights == null || !drmRights.isValid()) {
            throw new java.lang.IllegalArgumentException("Given drmRights or contentPath is not valid");
        }
        if (str != null && !str.equals("")) {
            android.drm.DrmUtils.writeToFile(str, drmRights.getData());
        }
        return _saveRights(this.mUniqueId, drmRights, str, str2);
    }

    public void installDrmEngine(java.lang.String str) {
        if (str == null || str.equals("")) {
            throw new java.lang.IllegalArgumentException("Given engineFilePath: " + str + "is not valid");
        }
        _installDrmEngine(this.mUniqueId, str);
    }

    public boolean canHandle(java.lang.String str, java.lang.String str2) {
        if ((str == null || str.equals("")) && (str2 == null || str2.equals(""))) {
            throw new java.lang.IllegalArgumentException("Path or the mimetype should be non null");
        }
        return _canHandle(this.mUniqueId, str, str2);
    }

    public boolean canHandle(android.net.Uri uri, java.lang.String str) {
        if ((uri == null || android.net.Uri.EMPTY == uri) && (str == null || str.equals(""))) {
            throw new java.lang.IllegalArgumentException("Uri or the mimetype should be non null");
        }
        return canHandle(convertUriToPath(uri), str);
    }

    public int processDrmInfo(android.drm.DrmInfo drmInfo) {
        if (drmInfo == null || !drmInfo.isValid()) {
            throw new java.lang.IllegalArgumentException("Given drmInfo is invalid/null");
        }
        if (this.mEventHandler == null) {
            return ERROR_UNKNOWN;
        }
        if (this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(1002, drmInfo))) {
            return 0;
        }
        return ERROR_UNKNOWN;
    }

    public android.drm.DrmInfo acquireDrmInfo(android.drm.DrmInfoRequest drmInfoRequest) {
        if (drmInfoRequest == null || !drmInfoRequest.isValid()) {
            throw new java.lang.IllegalArgumentException("Given drmInfoRequest is invalid/null");
        }
        return _acquireDrmInfo(this.mUniqueId, drmInfoRequest);
    }

    public int acquireRights(android.drm.DrmInfoRequest drmInfoRequest) {
        android.drm.DrmInfo acquireDrmInfo = acquireDrmInfo(drmInfoRequest);
        if (acquireDrmInfo == null) {
            return ERROR_UNKNOWN;
        }
        return processDrmInfo(acquireDrmInfo);
    }

    public int getDrmObjectType(java.lang.String str, java.lang.String str2) {
        if ((str == null || str.equals("")) && (str2 == null || str2.equals(""))) {
            throw new java.lang.IllegalArgumentException("Path or the mimetype should be non null");
        }
        return _getDrmObjectType(this.mUniqueId, str, str2);
    }

    public int getDrmObjectType(android.net.Uri uri, java.lang.String str) {
        java.lang.String str2 = "";
        if ((uri == null || android.net.Uri.EMPTY == uri) && (str == null || str.equals(""))) {
            throw new java.lang.IllegalArgumentException("Uri or the mimetype should be non null");
        }
        try {
            str2 = convertUriToPath(uri);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Given Uri could not be found in media store");
        }
        return getDrmObjectType(str2, str);
    }

    public java.lang.String getOriginalMimeType(java.lang.String str) {
        java.io.FileInputStream fileInputStream;
        java.io.FileDescriptor fileDescriptor;
        if (str == null || str.equals("")) {
            throw new java.lang.IllegalArgumentException("Given path should be non null");
        }
        java.lang.String str2 = null;
        str2 = null;
        str2 = null;
        java.io.FileInputStream fileInputStream2 = null;
        try {
            try {
                java.io.File file = new java.io.File(str);
                if (file.exists()) {
                    fileInputStream = new java.io.FileInputStream(file);
                    try {
                        fileDescriptor = fileInputStream.getFD();
                    } catch (java.io.IOException e) {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return str2;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (java.io.IOException e2) {
                            }
                        }
                        throw th;
                    }
                } else {
                    fileDescriptor = null;
                    fileInputStream = null;
                }
                str2 = _getOriginalMimeType(this.mUniqueId, str, fileDescriptor);
            } catch (java.io.IOException e3) {
                fileInputStream = null;
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (java.io.IOException e4) {
        }
        return str2;
    }

    public java.lang.String getOriginalMimeType(android.net.Uri uri) {
        if (uri == null || android.net.Uri.EMPTY == uri) {
            throw new java.lang.IllegalArgumentException("Given uri is not valid");
        }
        return getOriginalMimeType(convertUriToPath(uri));
    }

    public int checkRightsStatus(java.lang.String str) {
        return checkRightsStatus(str, 0);
    }

    public int checkRightsStatus(android.net.Uri uri) {
        if (uri == null || android.net.Uri.EMPTY == uri) {
            throw new java.lang.IllegalArgumentException("Given uri is not valid");
        }
        return checkRightsStatus(convertUriToPath(uri));
    }

    public int checkRightsStatus(java.lang.String str, int i) {
        if (str == null || str.equals("") || !android.drm.DrmStore.Action.isValid(i)) {
            throw new java.lang.IllegalArgumentException("Given path or action is not valid");
        }
        return _checkRightsStatus(this.mUniqueId, str, i);
    }

    public int checkRightsStatus(android.net.Uri uri, int i) {
        if (uri == null || android.net.Uri.EMPTY == uri) {
            throw new java.lang.IllegalArgumentException("Given uri is not valid");
        }
        return checkRightsStatus(convertUriToPath(uri), i);
    }

    public int removeRights(java.lang.String str) {
        if (str == null || str.equals("")) {
            throw new java.lang.IllegalArgumentException("Given path should be non null");
        }
        return _removeRights(this.mUniqueId, str);
    }

    public int removeRights(android.net.Uri uri) {
        if (uri == null || android.net.Uri.EMPTY == uri) {
            throw new java.lang.IllegalArgumentException("Given uri is not valid");
        }
        return removeRights(convertUriToPath(uri));
    }

    public int removeAllRights() {
        if (this.mEventHandler == null) {
            return ERROR_UNKNOWN;
        }
        if (this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(1001))) {
            return 0;
        }
        return ERROR_UNKNOWN;
    }

    public int openConvertSession(java.lang.String str) {
        if (str == null || str.equals("")) {
            throw new java.lang.IllegalArgumentException("Path or the mimeType should be non null");
        }
        return _openConvertSession(this.mUniqueId, str);
    }

    public android.drm.DrmConvertedStatus convertData(int i, byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            throw new java.lang.IllegalArgumentException("Given inputData should be non null");
        }
        return _convertData(this.mUniqueId, i, bArr);
    }

    public android.drm.DrmConvertedStatus closeConvertSession(int i) {
        return _closeConvertSession(this.mUniqueId, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getEventType(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
                return 1002;
            default:
                return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getErrorType(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
                return 2006;
            default:
                return -1;
        }
    }

    private java.lang.String convertUriToPath(android.net.Uri uri) {
        java.lang.AutoCloseable autoCloseable = null;
        if (uri == null) {
            return null;
        }
        java.lang.String scheme = uri.getScheme();
        if (scheme == null || scheme.equals("") || scheme.equals("file")) {
            return uri.getPath();
        }
        if (scheme.equals(android.content.IntentFilter.SCHEME_HTTP) || scheme.equals(android.content.IntentFilter.SCHEME_HTTPS)) {
            return uri.toString();
        }
        if (scheme.equals("content")) {
            try {
                try {
                    android.database.Cursor query = this.mContext.getContentResolver().query(uri, new java.lang.String[]{"_data"}, null, null, null);
                    if (query == null || query.getCount() == 0 || !query.moveToFirst()) {
                        throw new java.lang.IllegalArgumentException("Given Uri could not be found in media store");
                    }
                    java.lang.String string = query.getString(query.getColumnIndexOrThrow("_data"));
                    if (query != null) {
                        query.close();
                    }
                    return string;
                } catch (android.database.sqlite.SQLiteException e) {
                    throw new java.lang.IllegalArgumentException("Given Uri is not formatted in a way so that it can be found in media store.");
                }
            } catch (java.lang.Throwable th) {
                if (0 != 0) {
                    autoCloseable.close();
                }
                throw th;
            }
        }
        throw new java.lang.IllegalArgumentException("Given Uri scheme is not supported");
    }

    private void createEventThreads() {
        if (this.mEventHandler == null && this.mInfoHandler == null) {
            this.mInfoThread = new android.os.HandlerThread("DrmManagerClient.InfoHandler");
            this.mInfoThread.start();
            this.mInfoHandler = new android.drm.DrmManagerClient.InfoHandler(this.mInfoThread.getLooper());
            this.mEventThread = new android.os.HandlerThread("DrmManagerClient.EventHandler");
            this.mEventThread.start();
            this.mEventHandler = new android.drm.DrmManagerClient.EventHandler(this.mEventThread.getLooper());
        }
    }

    private void createListeners() {
        _setListeners(this.mUniqueId, new java.lang.ref.WeakReference(this));
    }
}
