package android.companion;

/* loaded from: classes.dex */
public interface DeviceFilter<D extends android.os.Parcelable> extends android.os.Parcelable {
    public static final int MEDIUM_TYPE_BLUETOOTH = 0;
    public static final int MEDIUM_TYPE_BLUETOOTH_LE = 1;
    public static final int MEDIUM_TYPE_WIFI = 2;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MediumType {
    }

    java.lang.String getDeviceDisplayName(D d);

    int getMediumType();

    boolean matches(D d);

    static <D extends android.os.Parcelable> boolean matches(android.companion.DeviceFilter<D> deviceFilter, D d) {
        return deviceFilter == null || deviceFilter.matches(d);
    }
}
