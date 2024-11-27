package android.content.pm;

/* loaded from: classes.dex */
public class ServiceInfo extends android.content.pm.ComponentInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ServiceInfo>() { // from class: android.content.pm.ServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ServiceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ServiceInfo[] newArray(int i) {
            return new android.content.pm.ServiceInfo[i];
        }
    };
    public static final int FLAG_ALLOW_SHARED_ISOLATED_PROCESS = 16;
    public static final int FLAG_EXTERNAL_SERVICE = 4;
    public static final int FLAG_ISOLATED_PROCESS = 2;
    public static final int FLAG_SINGLE_USER = 1073741824;
    public static final int FLAG_STOP_WITH_TASK = 1;
    public static final int FLAG_SYSTEM_USER_ONLY = 536870912;
    public static final int FLAG_USE_APP_ZYGOTE = 8;
    public static final int FLAG_VISIBLE_TO_INSTANT_APP = 1048576;
    public static final int FOREGROUND_SERVICE_TYPES_MAX_INDEX = 30;
    public static final int FOREGROUND_SERVICE_TYPE_CAMERA = 64;
    public static final int FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE = 16;
    public static final int FOREGROUND_SERVICE_TYPE_DATA_SYNC = 1;
    public static final int FOREGROUND_SERVICE_TYPE_FILE_MANAGEMENT = 4096;
    public static final int FOREGROUND_SERVICE_TYPE_HEALTH = 256;
    public static final int FOREGROUND_SERVICE_TYPE_LOCATION = 8;
    public static final int FOREGROUND_SERVICE_TYPE_MANIFEST = -1;
    public static final int FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK = 2;
    public static final int FOREGROUND_SERVICE_TYPE_MEDIA_PROCESSING = 8192;
    public static final int FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION = 32;
    public static final int FOREGROUND_SERVICE_TYPE_MICROPHONE = 128;

    @java.lang.Deprecated
    public static final int FOREGROUND_SERVICE_TYPE_NONE = 0;
    public static final int FOREGROUND_SERVICE_TYPE_PHONE_CALL = 4;
    public static final int FOREGROUND_SERVICE_TYPE_REMOTE_MESSAGING = 512;
    public static final int FOREGROUND_SERVICE_TYPE_SHORT_SERVICE = 2048;
    public static final int FOREGROUND_SERVICE_TYPE_SPECIAL_USE = 1073741824;
    public static final int FOREGROUND_SERVICE_TYPE_SYSTEM_EXEMPTED = 1024;
    public int flags;
    public int mForegroundServiceType;
    public java.lang.String permission;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ForegroundServiceType {
    }

    public ServiceInfo() {
        this.mForegroundServiceType = 0;
    }

    public ServiceInfo(android.content.pm.ServiceInfo serviceInfo) {
        super(serviceInfo);
        this.mForegroundServiceType = 0;
        this.permission = serviceInfo.permission;
        this.flags = serviceInfo.flags;
        this.mForegroundServiceType = serviceInfo.mForegroundServiceType;
    }

    public int getForegroundServiceType() {
        return this.mForegroundServiceType;
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        dump(printer, str, 3);
    }

    void dump(android.util.Printer printer, java.lang.String str, int i) {
        super.dumpFront(printer, str);
        printer.println(str + "permission=" + this.permission);
        printer.println(str + "flags=0x" + java.lang.Integer.toHexString(this.flags));
        super.dumpBack(printer, str, i);
    }

    public java.lang.String toString() {
        return "ServiceInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.name + "}";
    }

    public static java.lang.String foregroundServiceTypeToLabel(int i) {
        switch (i) {
            case -1:
                return "manifest";
            case 0:
                return "none";
            case 1:
                return "dataSync";
            case 2:
                return "mediaPlayback";
            case 4:
                return "phoneCall";
            case 8:
                return "location";
            case 16:
                return "connectedDevice";
            case 32:
                return "mediaProjection";
            case 64:
                return android.content.Context.CAMERA_SERVICE;
            case 128:
                return "microphone";
            case 256:
                return android.os.BatteryManager.EXTRA_HEALTH;
            case 512:
                return "remoteMessaging";
            case 1024:
                return "systemExempted";
            case 2048:
                return "shortService";
            case 4096:
                return "fileManagement";
            case 8192:
                return "mediaProcessing";
            case 1073741824:
                return "specialUse";
            default:
                return "unknown";
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.ComponentInfo, android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.permission);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.mForegroundServiceType);
    }

    private ServiceInfo(android.os.Parcel parcel) {
        super(parcel);
        this.mForegroundServiceType = 0;
        this.permission = parcel.readString8();
        this.flags = parcel.readInt();
        this.mForegroundServiceType = parcel.readInt();
    }
}
