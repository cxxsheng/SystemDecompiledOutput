package android.hardware.devicestate;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class DeviceState {

    @java.lang.Deprecated
    public static final int FLAG_APP_INACCESSIBLE = 2;

    @java.lang.Deprecated
    public static final int FLAG_CANCEL_OVERRIDE_REQUESTS = 1;

    @java.lang.Deprecated
    public static final int FLAG_CANCEL_WHEN_REQUESTER_NOT_ON_TOP = 8;

    @java.lang.Deprecated
    public static final int FLAG_EMULATED_ONLY = 4;

    @java.lang.Deprecated
    public static final int FLAG_UNSUPPORTED_WHEN_POWER_SAVE_MODE = 32;

    @java.lang.Deprecated
    public static final int FLAG_UNSUPPORTED_WHEN_THERMAL_STATUS_CRITICAL = 16;
    public static final int PROPERTY_APP_INACCESSIBLE = 9;
    public static final int PROPERTY_EMULATED_ONLY = 10;
    public static final int PROPERTY_EXTENDED_DEVICE_STATE_EXTERNAL_DISPLAY = 15;
    public static final int PROPERTY_FEATURE_DUAL_DISPLAY_INTERNAL_DEFAULT = 17;
    public static final int PROPERTY_FEATURE_REAR_DISPLAY = 16;
    public static final int PROPERTY_FOLDABLE_DISPLAY_CONFIGURATION_INNER_PRIMARY = 12;
    public static final int PROPERTY_FOLDABLE_DISPLAY_CONFIGURATION_OUTER_PRIMARY = 11;
    public static final int PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_CLOSED = 1;
    public static final int PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_HALF_OPEN = 2;
    public static final int PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_OPEN = 3;
    public static final int PROPERTY_POLICY_AVAILABLE_FOR_APP_REQUEST = 8;
    public static final int PROPERTY_POLICY_CANCEL_OVERRIDE_REQUESTS = 4;
    public static final int PROPERTY_POLICY_CANCEL_WHEN_REQUESTER_NOT_ON_TOP = 5;
    public static final int PROPERTY_POLICY_UNSUPPORTED_WHEN_POWER_SAVE_MODE = 7;
    public static final int PROPERTY_POLICY_UNSUPPORTED_WHEN_THERMAL_STATUS_CRITICAL = 6;
    public static final int PROPERTY_POWER_CONFIGURATION_TRIGGER_SLEEP = 13;
    public static final int PROPERTY_POWER_CONFIGURATION_TRIGGER_WAKE = 14;
    private final int mFlags;
    private final int mIdentifier;
    private final java.lang.String mName;
    private final java.util.Set<java.lang.Integer> mProperties;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @java.lang.Deprecated
    public @interface DeviceStateFlags {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceStateProperties {
    }

    @java.lang.Deprecated
    public DeviceState(int i, java.lang.String str, int i2) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 10000, "identifier");
        this.mIdentifier = i;
        this.mName = str;
        this.mFlags = i2;
        this.mProperties = java.util.Collections.emptySet();
    }

    public DeviceState(int i, java.lang.String str, java.util.Set<java.lang.Integer> set) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 10000, "identifier");
        this.mIdentifier = i;
        this.mName = str;
        this.mProperties = java.util.Set.copyOf(set);
        this.mFlags = 0;
    }

    public int getIdentifier() {
        return this.mIdentifier;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    @java.lang.Deprecated
    public int getFlags() {
        return this.mFlags;
    }

    public java.lang.String toString() {
        boolean z = true;
        java.lang.StringBuilder append = new java.lang.StringBuilder().append("DeviceState{identifier=").append(this.mIdentifier).append(", name='").append(this.mName).append(android.text.format.DateFormat.QUOTE).append(", app_accessible=").append((hasProperty(9) || hasFlag(2)) ? false : true).append(", cancel_when_requester_not_on_top=");
        if (!hasProperty(5) && !hasFlag(8)) {
            z = false;
        }
        return append.append(z).append("}").toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.hardware.devicestate.DeviceState deviceState = (android.hardware.devicestate.DeviceState) obj;
        if (this.mIdentifier == deviceState.mIdentifier && java.util.Objects.equals(this.mName, deviceState.mName) && java.util.Objects.equals(this.mProperties, deviceState.mProperties)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mIdentifier), this.mName, this.mProperties);
    }

    @java.lang.Deprecated
    public boolean hasFlag(int i) {
        return (this.mFlags & i) == i;
    }

    public boolean hasProperty(int i) {
        return this.mProperties.contains(java.lang.Integer.valueOf(i));
    }

    public boolean hasProperties(int... iArr) {
        for (int i : iArr) {
            if (this.mProperties.contains(java.lang.Integer.valueOf(i))) {
                return false;
            }
        }
        return true;
    }
}
