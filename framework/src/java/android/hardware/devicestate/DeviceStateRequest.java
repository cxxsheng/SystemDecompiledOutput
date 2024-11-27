package android.hardware.devicestate;

/* loaded from: classes2.dex */
public final class DeviceStateRequest {
    public static final int FLAG_CANCEL_WHEN_BASE_CHANGES = 1;
    private final int mFlags;
    private final int mRequestedState;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestFlags {
    }

    public static android.hardware.devicestate.DeviceStateRequest.Builder newBuilder(int i) {
        return new android.hardware.devicestate.DeviceStateRequest.Builder(i);
    }

    public static final class Builder {
        private int mFlags;
        private final int mRequestedState;

        private Builder(int i) {
            this.mRequestedState = i;
        }

        public android.hardware.devicestate.DeviceStateRequest.Builder setFlags(int i) {
            this.mFlags = i | this.mFlags;
            return this;
        }

        public android.hardware.devicestate.DeviceStateRequest build() {
            return new android.hardware.devicestate.DeviceStateRequest(this.mRequestedState, this.mFlags);
        }
    }

    public interface Callback {
        default void onRequestActivated(android.hardware.devicestate.DeviceStateRequest deviceStateRequest) {
        }

        default void onRequestSuspended(android.hardware.devicestate.DeviceStateRequest deviceStateRequest) {
        }

        default void onRequestCanceled(android.hardware.devicestate.DeviceStateRequest deviceStateRequest) {
        }
    }

    private DeviceStateRequest(int i, int i2) {
        this.mRequestedState = i;
        this.mFlags = i2;
    }

    public int getState() {
        return this.mRequestedState;
    }

    public int getFlags() {
        return this.mFlags;
    }
}
