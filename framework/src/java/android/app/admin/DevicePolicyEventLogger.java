package android.app.admin;

/* loaded from: classes.dex */
public class DevicePolicyEventLogger {
    private java.lang.String mAdminPackageName;
    private boolean mBooleanValue;
    private final int mEventId;
    private int mIntValue;
    private java.lang.String[] mStringArrayValue;
    private long mTimePeriodMs;

    private DevicePolicyEventLogger(int i) {
        this.mEventId = i;
    }

    public static android.app.admin.DevicePolicyEventLogger createEvent(int i) {
        return new android.app.admin.DevicePolicyEventLogger(i);
    }

    public int getEventId() {
        return this.mEventId;
    }

    public android.app.admin.DevicePolicyEventLogger setInt(int i) {
        this.mIntValue = i;
        return this;
    }

    public int getInt() {
        return this.mIntValue;
    }

    public android.app.admin.DevicePolicyEventLogger setBoolean(boolean z) {
        this.mBooleanValue = z;
        return this;
    }

    public boolean getBoolean() {
        return this.mBooleanValue;
    }

    public android.app.admin.DevicePolicyEventLogger setTimePeriod(long j) {
        this.mTimePeriodMs = j;
        return this;
    }

    public long getTimePeriod() {
        return this.mTimePeriodMs;
    }

    public android.app.admin.DevicePolicyEventLogger setStrings(java.lang.String... strArr) {
        this.mStringArrayValue = strArr;
        return this;
    }

    public android.app.admin.DevicePolicyEventLogger setStrings(java.lang.String str, java.lang.String[] strArr) {
        java.util.Objects.requireNonNull(strArr, "values parameter cannot be null");
        this.mStringArrayValue = new java.lang.String[strArr.length + 1];
        this.mStringArrayValue[0] = str;
        java.lang.System.arraycopy(strArr, 0, this.mStringArrayValue, 1, strArr.length);
        return this;
    }

    public android.app.admin.DevicePolicyEventLogger setStrings(java.lang.String str, java.lang.String str2, java.lang.String[] strArr) {
        java.util.Objects.requireNonNull(strArr, "values parameter cannot be null");
        this.mStringArrayValue = new java.lang.String[strArr.length + 2];
        this.mStringArrayValue[0] = str;
        this.mStringArrayValue[1] = str2;
        java.lang.System.arraycopy(strArr, 0, this.mStringArrayValue, 2, strArr.length);
        return this;
    }

    public java.lang.String[] getStringArray() {
        if (this.mStringArrayValue == null) {
            return null;
        }
        return (java.lang.String[]) java.util.Arrays.copyOf(this.mStringArrayValue, this.mStringArrayValue.length);
    }

    public android.app.admin.DevicePolicyEventLogger setAdmin(java.lang.String str) {
        this.mAdminPackageName = str;
        return this;
    }

    public android.app.admin.DevicePolicyEventLogger setAdmin(android.content.ComponentName componentName) {
        this.mAdminPackageName = componentName != null ? componentName.getPackageName() : null;
        return this;
    }

    public java.lang.String getAdminPackageName() {
        return this.mAdminPackageName;
    }

    public void write() {
        com.android.internal.util.FrameworkStatsLog.write(103, this.mEventId, this.mAdminPackageName, this.mIntValue, this.mBooleanValue, this.mTimePeriodMs, stringArrayValueToBytes(this.mStringArrayValue));
    }

    private static byte[] stringArrayValueToBytes(java.lang.String[] strArr) {
        if (strArr == null) {
            return null;
        }
        android.stats.devicepolicy.nano.StringList stringList = new android.stats.devicepolicy.nano.StringList();
        stringList.stringValue = strArr;
        return com.android.framework.protobuf.nano.MessageNano.toByteArray(stringList);
    }
}
