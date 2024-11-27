package android.app.admin;

/* loaded from: classes.dex */
public class SecurityLog {
    public static final int LEVEL_ERROR = 3;
    public static final int LEVEL_INFO = 1;
    public static final int LEVEL_WARNING = 2;
    private static final java.lang.String PROPERTY_LOGGING_ENABLED = "persist.logd.security";
    public static final int TAG_ADB_SHELL_CMD = 210002;
    public static final int TAG_ADB_SHELL_INTERACTIVE = 210001;
    public static final int TAG_APP_PROCESS_START = 210005;
    public static final int TAG_BACKUP_SERVICE_TOGGLED = 210044;
    public static final int TAG_BLUETOOTH_CONNECTION = 210039;
    public static final int TAG_BLUETOOTH_DISCONNECTION = 210040;
    public static final int TAG_CAMERA_POLICY_SET = 210034;
    public static final int TAG_CERT_AUTHORITY_INSTALLED = 210029;
    public static final int TAG_CERT_AUTHORITY_REMOVED = 210030;
    public static final int TAG_CERT_VALIDATION_FAILURE = 210033;
    public static final int TAG_CRYPTO_SELF_TEST_COMPLETED = 210031;
    public static final int TAG_KEYGUARD_DISABLED_FEATURES_SET = 210021;
    public static final int TAG_KEYGUARD_DISMISSED = 210006;
    public static final int TAG_KEYGUARD_DISMISS_AUTH_ATTEMPT = 210007;
    public static final int TAG_KEYGUARD_SECURED = 210008;
    public static final int TAG_KEY_DESTRUCTION = 210026;
    public static final int TAG_KEY_GENERATED = 210024;
    public static final int TAG_KEY_IMPORT = 210025;
    public static final int TAG_KEY_INTEGRITY_VIOLATION = 210032;
    public static final int TAG_LOGGING_STARTED = 210011;
    public static final int TAG_LOGGING_STOPPED = 210012;
    public static final int TAG_LOG_BUFFER_SIZE_CRITICAL = 210015;
    public static final int TAG_MAX_PASSWORD_ATTEMPTS_SET = 210020;
    public static final int TAG_MAX_SCREEN_LOCK_TIMEOUT_SET = 210019;
    public static final int TAG_MEDIA_MOUNT = 210013;
    public static final int TAG_MEDIA_UNMOUNT = 210014;
    public static final int TAG_OS_SHUTDOWN = 210010;
    public static final int TAG_OS_STARTUP = 210009;
    public static final int TAG_PACKAGE_INSTALLED = 210041;
    public static final int TAG_PACKAGE_UNINSTALLED = 210043;
    public static final int TAG_PACKAGE_UPDATED = 210042;
    public static final int TAG_PASSWORD_CHANGED = 210036;
    public static final int TAG_PASSWORD_COMPLEXITY_REQUIRED = 210035;
    public static final int TAG_PASSWORD_COMPLEXITY_SET = 210017;
    public static final int TAG_PASSWORD_EXPIRATION_SET = 210016;
    public static final int TAG_PASSWORD_HISTORY_LENGTH_SET = 210018;
    public static final int TAG_REMOTE_LOCK = 210022;
    public static final int TAG_SYNC_RECV_FILE = 210003;
    public static final int TAG_SYNC_SEND_FILE = 210004;
    public static final int TAG_USER_RESTRICTION_ADDED = 210027;
    public static final int TAG_USER_RESTRICTION_REMOVED = 210028;
    public static final int TAG_WIFI_CONNECTION = 210037;
    public static final int TAG_WIFI_DISCONNECTION = 210038;
    public static final int TAG_WIPE_FAILURE = 210023;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SecurityLogLevel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SecurityLogTag {
    }

    public static native boolean isLoggingEnabled();

    public static native void readEvents(java.util.Collection<android.app.admin.SecurityLog.SecurityEvent> collection) throws java.io.IOException;

    public static native void readEventsOnWrapping(long j, java.util.Collection<android.app.admin.SecurityLog.SecurityEvent> collection) throws java.io.IOException;

    public static native void readEventsSince(long j, java.util.Collection<android.app.admin.SecurityLog.SecurityEvent> collection) throws java.io.IOException;

    public static native void readPreviousEvents(java.util.Collection<android.app.admin.SecurityLog.SecurityEvent> collection) throws java.io.IOException;

    @android.annotation.SystemApi
    public static native int writeEvent(int i, java.lang.Object... objArr);

    public static void setLoggingEnabledProperty(boolean z) {
        android.os.SystemProperties.set(PROPERTY_LOGGING_ENABLED, z ? "true" : "false");
    }

    public static boolean getLoggingEnabledProperty() {
        return android.os.SystemProperties.getBoolean(PROPERTY_LOGGING_ENABLED, false);
    }

    public static final class SecurityEvent implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.admin.SecurityLog.SecurityEvent> CREATOR = new android.os.Parcelable.Creator<android.app.admin.SecurityLog.SecurityEvent>() { // from class: android.app.admin.SecurityLog.SecurityEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.admin.SecurityLog.SecurityEvent createFromParcel(android.os.Parcel parcel) {
                return new android.app.admin.SecurityLog.SecurityEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.admin.SecurityLog.SecurityEvent[] newArray(int i) {
                return new android.app.admin.SecurityLog.SecurityEvent[i];
            }
        };
        private android.util.EventLog.Event mEvent;
        private long mId;

        SecurityEvent(byte[] bArr) {
            this(0L, bArr);
        }

        SecurityEvent(android.os.Parcel parcel) {
            this(parcel.readLong(), parcel.createByteArray());
        }

        public SecurityEvent(long j, byte[] bArr) {
            this.mId = j;
            this.mEvent = android.util.EventLog.Event.fromBytes(bArr);
        }

        public long getTimeNanos() {
            return this.mEvent.getTimeNanos();
        }

        public int getTag() {
            return this.mEvent.getTag();
        }

        public java.lang.Object getData() {
            return this.mEvent.getData();
        }

        public int getIntegerData(int i) {
            return ((java.lang.Integer) ((java.lang.Object[]) this.mEvent.getData())[i]).intValue();
        }

        public java.lang.String getStringData(int i) {
            return (java.lang.String) ((java.lang.Object[]) this.mEvent.getData())[i];
        }

        public void setId(long j) {
            this.mId = j;
        }

        public long getId() {
            return this.mId;
        }

        public int getLogLevel() {
            switch (getTag()) {
                case 210001:
                case 210002:
                case 210003:
                case 210004:
                case 210005:
                case 210006:
                case 210008:
                case 210009:
                case 210010:
                case 210011:
                case 210012:
                case 210013:
                case 210014:
                case 210016:
                case 210017:
                case 210018:
                case 210019:
                case 210020:
                case 210027:
                case 210028:
                case 210034:
                case 210035:
                case 210036:
                    return 1;
                case 210007:
                case 210024:
                case 210025:
                case 210026:
                case 210029:
                    return getSuccess() ? 1 : 2;
                case 210015:
                case 210023:
                case 210032:
                    return 3;
                case 210021:
                case 210022:
                default:
                    return 1;
                case 210030:
                case 210031:
                    return getSuccess() ? 1 : 3;
                case 210033:
                    return 2;
            }
        }

        private boolean getSuccess() {
            java.lang.Object data = getData();
            if (data == null || !(data instanceof java.lang.Object[])) {
                return false;
            }
            java.lang.Object[] objArr = (java.lang.Object[]) data;
            return objArr.length >= 1 && (objArr[0] instanceof java.lang.Integer) && ((java.lang.Integer) objArr[0]).intValue() != 0;
        }

        public android.app.admin.SecurityLog.SecurityEvent redact(int i) {
            int userId;
            switch (getTag()) {
                case 210002:
                    return new android.app.admin.SecurityLog.SecurityEvent(getId(), this.mEvent.withNewData("").getBytes());
                case 210005:
                    try {
                        userId = android.os.UserHandle.getUserId(getIntegerData(2));
                        break;
                    } catch (java.lang.Exception e) {
                        return null;
                    }
                case 210013:
                case 210014:
                    try {
                        return new android.app.admin.SecurityLog.SecurityEvent(getId(), this.mEvent.withNewData(new java.lang.Object[]{getStringData(0), ""}).getBytes());
                    } catch (java.lang.Exception e2) {
                        return null;
                    }
                case 210024:
                case 210025:
                case 210026:
                    try {
                        userId = android.os.UserHandle.getUserId(getIntegerData(2));
                        break;
                    } catch (java.lang.Exception e3) {
                        return null;
                    }
                case 210029:
                case 210030:
                case 210041:
                case 210042:
                case 210043:
                    try {
                        userId = getIntegerData(2);
                        break;
                    } catch (java.lang.Exception e4) {
                        return null;
                    }
                case 210032:
                    try {
                        userId = android.os.UserHandle.getUserId(getIntegerData(1));
                        break;
                    } catch (java.lang.Exception e5) {
                        return null;
                    }
                case 210036:
                    try {
                        userId = getIntegerData(1);
                        break;
                    } catch (java.lang.Exception e6) {
                        return null;
                    }
                default:
                    userId = -10000;
                    break;
            }
            if (userId == -10000 || i == userId) {
                return this;
            }
            return null;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.mId);
            parcel.writeByteArray(this.mEvent.getBytes());
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.admin.SecurityLog.SecurityEvent securityEvent = (android.app.admin.SecurityLog.SecurityEvent) obj;
            if (this.mEvent.equals(securityEvent.mEvent) && this.mId == securityEvent.mId) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mEvent, java.lang.Long.valueOf(this.mId));
        }

        public boolean eventEquals(android.app.admin.SecurityLog.SecurityEvent securityEvent) {
            return securityEvent != null && this.mEvent.equals(securityEvent.mEvent);
        }
    }

    public static void redactEvents(java.util.ArrayList<android.app.admin.SecurityLog.SecurityEvent> arrayList, int i) {
        if (i == -1) {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            android.app.admin.SecurityLog.SecurityEvent redact = arrayList.get(i3).redact(i);
            if (redact != null) {
                arrayList.set(i2, redact);
                i2++;
            }
        }
        for (int size = arrayList.size() - 1; size >= i2; size--) {
            arrayList.remove(size);
        }
    }
}
