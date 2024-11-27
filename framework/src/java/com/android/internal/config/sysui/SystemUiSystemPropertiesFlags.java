package com.android.internal.config.sysui;

/* loaded from: classes4.dex */
public class SystemUiSystemPropertiesFlags {
    private static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver MAIN_RESOLVER;
    public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag TEAMFOOD = devFlag("persist.sysui.teamfood");
    public static com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver TEST_RESOLVER;

    public interface FlagResolver {
        int getIntValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag);

        java.lang.String getStringValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag);

        boolean isEnabled(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag);
    }

    public static final class NotificationFlags {
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag LOG_DND_STATE_EVENTS = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.releasedFlag("persist.sysui.notification.log_dnd_state_events");
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag RANKING_UPDATE_ASHMEM = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.devFlag("persist.sysui.notification.ranking_update_ashmem");
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag PROPAGATE_CHANNEL_UPDATES_TO_CONVERSATIONS = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.releasedFlag("persist.sysui.notification.propagate_channel_updates_to_conversations");
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag NOTIF_COOLDOWN_T1 = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.devFlag("persist.debug.sysui.notification.notif_cooldown_t1", 60000);
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag NOTIF_COOLDOWN_T2 = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.devFlag("persist.debug.sysui.notification.notif_cooldown_t2", 10000);
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag NOTIF_VOLUME1 = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.devFlag("persist.debug.sysui.notification.notif_volume1", 30);
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag NOTIF_VOLUME2 = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.devFlag("persist.debug.sysui.notification.notif_volume2", 0);
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag NOTIF_COOLDOWN_COUNTER_RESET = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.devFlag("persist.debug.sysui.notification.notif_cooldown_counter_reset", 10);
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag NOTIF_AVALANCHE_TIMEOUT = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.devFlag("persist.debug.sysui.notification.notif_avalanche_timeout", 120000);
        public static final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag DEBUG_SHORT_BITMAP_DURATION = com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.devFlag("persist.sysui.notification.debug_short_bitmap_duration");
    }

    static {
        MAIN_RESOLVER = android.os.Build.IS_DEBUGGABLE ? new com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.DebugResolver() : new com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.ProdResolver();
        TEST_RESOLVER = null;
    }

    public static com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver getResolver() {
        if (android.os.Build.IS_DEBUGGABLE && TEST_RESOLVER != null) {
            android.util.Log.i("SystemUiSystemPropertiesFlags", "Returning debug resolver " + TEST_RESOLVER);
            return TEST_RESOLVER;
        }
        return MAIN_RESOLVER;
    }

    public static com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag devFlag(java.lang.String str) {
        return new com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag(str, false, (com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag) null);
    }

    public static com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag devFlag(java.lang.String str, int i) {
        return new com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag(str, i, (com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag) null);
    }

    public static com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag devFlag(java.lang.String str, java.lang.String str2) {
        return new com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag(str, str2, (com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag) null);
    }

    public static com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag teamfoodFlag(java.lang.String str) {
        return new com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag(str, false, TEAMFOOD);
    }

    public static com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag releasedFlag(java.lang.String str) {
        return new com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag(str, true, (com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag) null);
    }

    public static final class Flag {
        public final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag mDebugDefault;
        public final int mDefaultIntValue;
        public final java.lang.String mDefaultStringValue;
        public final boolean mDefaultValue;
        public final java.lang.String mSysPropKey;

        public Flag(java.lang.String str, boolean z, com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag) {
            this.mSysPropKey = str;
            this.mDefaultValue = z;
            this.mDebugDefault = flag;
            this.mDefaultIntValue = 0;
            this.mDefaultStringValue = null;
        }

        public Flag(java.lang.String str, int i, com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag) {
            this.mSysPropKey = str;
            this.mDefaultIntValue = i;
            this.mDebugDefault = flag;
            this.mDefaultValue = false;
            this.mDefaultStringValue = null;
        }

        public Flag(java.lang.String str, java.lang.String str2, com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag) {
            this.mSysPropKey = str;
            this.mDefaultStringValue = str2;
            this.mDebugDefault = flag;
            this.mDefaultValue = false;
            this.mDefaultIntValue = 0;
        }
    }

    public static final class ProdResolver implements com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver {
        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public boolean isEnabled(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag) {
            return flag.mDefaultValue;
        }

        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public int getIntValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag) {
            return flag.mDefaultIntValue;
        }

        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public java.lang.String getStringValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag) {
            return flag.mDefaultStringValue;
        }
    }

    public static class DebugResolver implements com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver {
        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public final boolean isEnabled(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag) {
            if (flag.mDebugDefault == null) {
                return getBoolean(flag.mSysPropKey, flag.mDefaultValue);
            }
            return getBoolean(flag.mSysPropKey, isEnabled(flag.mDebugDefault));
        }

        public boolean getBoolean(java.lang.String str, boolean z) {
            return android.os.SystemProperties.getBoolean(str, z);
        }

        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public int getIntValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag) {
            if (flag.mDebugDefault == null) {
                return android.os.SystemProperties.getInt(flag.mSysPropKey, flag.mDefaultIntValue);
            }
            return android.os.SystemProperties.getInt(flag.mSysPropKey, getIntValue(flag.mDebugDefault));
        }

        @Override // com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver
        public java.lang.String getStringValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.Flag flag) {
            if (flag.mDebugDefault == null) {
                return android.os.SystemProperties.get(flag.mSysPropKey, flag.mDefaultStringValue);
            }
            return android.os.SystemProperties.get(flag.mSysPropKey, getStringValue(flag.mDebugDefault));
        }
    }
}
