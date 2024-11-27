package com.android.internal.flags;

/* loaded from: classes4.dex */
public abstract class CoreFlags {
    private static final java.util.List<android.flags.SyncableFlag> sKnownFlags = new java.util.ArrayList();
    public static android.flags.BooleanFlag BOOL_FLAG = booleanFlag("core", "bool_flag", false);
    public static android.flags.FusedOffFlag OFF_FLAG = fusedOffFlag("core", "off_flag");
    public static android.flags.FusedOnFlag ON_FLAG = fusedOnFlag("core", "on_flag");
    public static android.flags.DynamicBooleanFlag DYN_FLAG = dynamicBooleanFlag("core", "dyn_flag", true);

    public static boolean isCoreFlag(android.flags.SyncableFlag syncableFlag) {
        for (android.flags.SyncableFlag syncableFlag2 : sKnownFlags) {
            if (syncableFlag2.getName().equals(syncableFlag.getName()) && syncableFlag2.getNamespace().equals(syncableFlag.getNamespace())) {
                return true;
            }
        }
        return false;
    }

    public static java.util.List<android.flags.SyncableFlag> getCoreFlags() {
        return sKnownFlags;
    }

    private static android.flags.BooleanFlag booleanFlag(java.lang.String str, java.lang.String str2, boolean z) {
        android.flags.BooleanFlag booleanFlag = android.flags.FeatureFlags.booleanFlag(str, str2, z);
        sKnownFlags.add(new android.flags.SyncableFlag(str, str2, java.lang.Boolean.toString(z), false));
        return booleanFlag;
    }

    private static android.flags.FusedOffFlag fusedOffFlag(java.lang.String str, java.lang.String str2) {
        android.flags.FusedOffFlag fusedOffFlag = android.flags.FeatureFlags.fusedOffFlag(str, str2);
        sKnownFlags.add(new android.flags.SyncableFlag(str, str2, "false", false));
        return fusedOffFlag;
    }

    private static android.flags.FusedOnFlag fusedOnFlag(java.lang.String str, java.lang.String str2) {
        android.flags.FusedOnFlag fusedOnFlag = android.flags.FeatureFlags.fusedOnFlag(str, str2);
        sKnownFlags.add(new android.flags.SyncableFlag(str, str2, "true", false));
        return fusedOnFlag;
    }

    private static android.flags.DynamicBooleanFlag dynamicBooleanFlag(java.lang.String str, java.lang.String str2, boolean z) {
        android.flags.DynamicBooleanFlag dynamicBooleanFlag = android.flags.FeatureFlags.dynamicBooleanFlag(str, str2, z);
        sKnownFlags.add(new android.flags.SyncableFlag(str, str2, java.lang.Boolean.toString(z), true));
        return dynamicBooleanFlag;
    }
}
