package android.mtp;

/* loaded from: classes2.dex */
public class MtpServer implements java.lang.Runnable {
    private static final int sID_LEN_BYTES = 16;
    private static final int sID_LEN_STR = 32;
    private final android.content.Context mContext;
    private final android.mtp.MtpDatabase mDatabase;
    private long mNativeContext;
    private final java.lang.Runnable mOnTerminate;

    private final native void native_add_storage(android.mtp.MtpStorage mtpStorage);

    private final native void native_cleanup();

    private final native void native_remove_storage(int i);

    private final native void native_run();

    private final native void native_send_device_property_changed(int i);

    private final native void native_send_object_added(int i);

    private final native void native_send_object_info_changed(int i);

    private final native void native_send_object_removed(int i);

    private final native void native_setup(android.mtp.MtpDatabase mtpDatabase, java.io.FileDescriptor fileDescriptor, boolean z, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4);

    static {
        java.lang.System.loadLibrary("media_jni");
    }

    public MtpServer(android.mtp.MtpDatabase mtpDatabase, java.io.FileDescriptor fileDescriptor, boolean z, java.lang.Runnable runnable, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.lang.String str4;
        this.mDatabase = (android.mtp.MtpDatabase) com.android.internal.util.Preconditions.checkNotNull(mtpDatabase);
        this.mOnTerminate = (java.lang.Runnable) com.android.internal.util.Preconditions.checkNotNull(runnable);
        this.mContext = this.mDatabase.getContext();
        int i = 0;
        android.content.SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("mtp-cfg", 0);
        java.lang.String str5 = null;
        if (sharedPreferences.contains("mtp-id")) {
            java.lang.String string = sharedPreferences.getString("mtp-id", null);
            if (string.length() == 32) {
                while (true) {
                    if (i >= string.length()) {
                        str5 = string;
                        break;
                    } else if (java.lang.Character.digit(string.charAt(i), 16) == -1) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        if (str5 != null) {
            str4 = str5;
        } else {
            java.lang.String randId = getRandId();
            sharedPreferences.edit().putString("mtp-id", randId).apply();
            str4 = randId;
        }
        native_setup(mtpDatabase, fileDescriptor, z, str, str2, str3, str4);
        mtpDatabase.setServer(this);
    }

    private java.lang.String getRandId() {
        byte[] bArr = new byte[16];
        new java.util.Random().nextBytes(bArr);
        return libcore.util.HexEncoding.encodeToString(bArr);
    }

    public void start() {
        new java.lang.Thread(this, "MtpServer").start();
    }

    @Override // java.lang.Runnable
    public void run() {
        native_run();
        native_cleanup();
        this.mDatabase.close();
        this.mOnTerminate.run();
    }

    public void sendObjectAdded(int i) {
        native_send_object_added(i);
    }

    public void sendObjectRemoved(int i) {
        native_send_object_removed(i);
    }

    public void sendObjectInfoChanged(int i) {
        native_send_object_info_changed(i);
    }

    public void sendDevicePropertyChanged(int i) {
        native_send_device_property_changed(i);
    }

    public void addStorage(android.mtp.MtpStorage mtpStorage) {
        native_add_storage(mtpStorage);
    }

    public void removeStorage(android.mtp.MtpStorage mtpStorage) {
        native_remove_storage(mtpStorage.getStorageId());
    }
}
