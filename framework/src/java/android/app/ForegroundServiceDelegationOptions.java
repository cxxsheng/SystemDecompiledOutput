package android.app;

/* loaded from: classes.dex */
public class ForegroundServiceDelegationOptions {
    public static final int DELEGATION_SERVICE_CAMERA = 7;
    public static final int DELEGATION_SERVICE_CONNECTED_DEVICE = 5;
    public static final int DELEGATION_SERVICE_DATA_SYNC = 1;
    public static final int DELEGATION_SERVICE_DEFAULT = 0;
    public static final int DELEGATION_SERVICE_HEALTH = 9;
    public static final int DELEGATION_SERVICE_LOCATION = 4;
    public static final int DELEGATION_SERVICE_MEDIA_PLAYBACK = 2;
    public static final int DELEGATION_SERVICE_MEDIA_PROJECTION = 6;
    public static final int DELEGATION_SERVICE_MICROPHONE = 8;
    public static final int DELEGATION_SERVICE_PHONE_CALL = 3;
    public static final int DELEGATION_SERVICE_REMOTE_MESSAGING = 10;
    public static final int DELEGATION_SERVICE_SPECIAL_USE = 12;
    public static final int DELEGATION_SERVICE_SYSTEM_EXEMPTED = 11;
    public final android.app.IApplicationThread mClientAppThread;
    public java.lang.String mClientInstanceName;
    public final java.lang.String mClientPackageName;
    public final int mClientPid;
    public final int mClientUid;
    public final int mDelegationService;
    public final int mForegroundServiceTypes;
    public final boolean mSticky;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DelegationService {
    }

    public ForegroundServiceDelegationOptions(int i, int i2, java.lang.String str, android.app.IApplicationThread iApplicationThread, boolean z, java.lang.String str2, int i3, int i4) {
        this.mClientPid = i;
        this.mClientUid = i2;
        this.mClientPackageName = str;
        this.mClientAppThread = iApplicationThread;
        this.mSticky = z;
        this.mClientInstanceName = str2;
        this.mForegroundServiceTypes = i3;
        this.mDelegationService = i4;
    }

    public boolean isSameDelegate(android.app.ForegroundServiceDelegationOptions foregroundServiceDelegationOptions) {
        return this.mDelegationService == foregroundServiceDelegationOptions.mDelegationService && this.mClientUid == foregroundServiceDelegationOptions.mClientUid && this.mClientPid == foregroundServiceDelegationOptions.mClientPid && this.mClientInstanceName.equals(foregroundServiceDelegationOptions.mClientInstanceName);
    }

    public android.content.ComponentName getComponentName() {
        return new android.content.ComponentName(this.mClientPackageName, serviceCodeToString(this.mDelegationService) + ":" + this.mClientInstanceName);
    }

    public java.lang.String getDescription() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ForegroundServiceDelegate{").append("package:").append(this.mClientPackageName).append(",").append("service:").append(serviceCodeToString(this.mDelegationService)).append(",").append("uid:").append(this.mClientUid).append(",").append("pid:").append(this.mClientPid).append(",").append("instance:").append(this.mClientInstanceName).append("}");
        return sb.toString();
    }

    public static java.lang.String serviceCodeToString(int i) {
        switch (i) {
            case 0:
                return "DEFAULT";
            case 1:
                return "DATA_SYNC";
            case 2:
                return "MEDIA_PLAYBACK";
            case 3:
                return "PHONE_CALL";
            case 4:
                return "LOCATION";
            case 5:
                return "CONNECTED_DEVICE";
            case 6:
                return "MEDIA_PROJECTION";
            case 7:
                return "CAMERA";
            case 8:
                return "MICROPHONE";
            case 9:
                return "HEALTH";
            case 10:
                return "REMOTE_MESSAGING";
            case 11:
                return "SYSTEM_EXEMPTED";
            case 12:
                return "SPECIAL_USE";
            default:
                return "(unknown:" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static class Builder {
        android.app.IApplicationThread mClientAppThread;
        java.lang.String mClientInstanceName;
        int mClientNotificationId;
        java.lang.String mClientPackageName;
        int mClientPid;
        int mClientUid;
        int mDelegationService;
        int mForegroundServiceTypes;
        boolean mSticky;

        public android.app.ForegroundServiceDelegationOptions.Builder setClientPid(int i) {
            this.mClientPid = i;
            return this;
        }

        public android.app.ForegroundServiceDelegationOptions.Builder setClientUid(int i) {
            this.mClientUid = i;
            return this;
        }

        public android.app.ForegroundServiceDelegationOptions.Builder setClientPackageName(java.lang.String str) {
            this.mClientPackageName = str;
            return this;
        }

        public android.app.ForegroundServiceDelegationOptions.Builder setClientNotificationId(int i) {
            this.mClientNotificationId = i;
            return this;
        }

        public android.app.ForegroundServiceDelegationOptions.Builder setClientAppThread(android.app.IApplicationThread iApplicationThread) {
            this.mClientAppThread = iApplicationThread;
            return this;
        }

        public android.app.ForegroundServiceDelegationOptions.Builder setClientInstanceName(java.lang.String str) {
            this.mClientInstanceName = str;
            return this;
        }

        public android.app.ForegroundServiceDelegationOptions.Builder setSticky(boolean z) {
            this.mSticky = z;
            return this;
        }

        public android.app.ForegroundServiceDelegationOptions.Builder setForegroundServiceTypes(int i) {
            this.mForegroundServiceTypes = i;
            return this;
        }

        public android.app.ForegroundServiceDelegationOptions.Builder setDelegationService(int i) {
            this.mDelegationService = i;
            return this;
        }

        public android.app.ForegroundServiceDelegationOptions build() {
            return new android.app.ForegroundServiceDelegationOptions(this.mClientPid, this.mClientUid, this.mClientPackageName, this.mClientAppThread, this.mSticky, this.mClientInstanceName, this.mForegroundServiceTypes, this.mDelegationService);
        }
    }
}
