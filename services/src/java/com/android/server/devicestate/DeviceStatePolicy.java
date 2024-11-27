package com.android.server.devicestate;

/* loaded from: classes.dex */
public abstract class DeviceStatePolicy implements android.util.Dumpable {
    protected final android.content.Context mContext;

    public abstract void configureDeviceForState(int i, @android.annotation.NonNull java.lang.Runnable runnable);

    public abstract com.android.server.devicestate.DeviceStateProvider getDeviceStateProvider();

    protected DeviceStatePolicy(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
    }

    static final class DefaultProvider implements com.android.server.devicestate.DeviceStatePolicy.Provider {
        DefaultProvider() {
        }

        @Override // com.android.server.devicestate.DeviceStatePolicy.Provider
        public com.android.server.devicestate.DeviceStatePolicy instantiate(@android.annotation.NonNull android.content.Context context) {
            return new com.android.server.policy.DeviceStatePolicyImpl(context);
        }
    }

    public interface Provider {
        com.android.server.devicestate.DeviceStatePolicy instantiate(@android.annotation.NonNull android.content.Context context);

        static com.android.server.devicestate.DeviceStatePolicy.Provider fromResources(@android.annotation.NonNull android.content.res.Resources resources) {
            java.lang.String string = resources.getString(android.R.string.config_defaultWearableSensingConsentComponent);
            if (android.text.TextUtils.isEmpty(string)) {
                return new com.android.server.devicestate.DeviceStatePolicy.DefaultProvider();
            }
            try {
                return (com.android.server.devicestate.DeviceStatePolicy.Provider) java.lang.Class.forName(string).newInstance();
            } catch (java.lang.ClassCastException | java.lang.ReflectiveOperationException e) {
                throw new java.lang.IllegalStateException("Couldn't instantiate class " + string + " for config_deviceSpecificDeviceStatePolicyProvider: make sure it has a public zero-argument constructor and implements DeviceStatePolicy.Provider", e);
            }
        }
    }
}
