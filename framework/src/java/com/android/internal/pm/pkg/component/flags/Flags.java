package com.android.internal.pm.pkg.component.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.internal.pm.pkg.component.flags.FeatureFlags FEATURE_FLAGS = new com.android.internal.pm.pkg.component.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_PER_PROCESS_USE_EMBEDDED_DEX_ATTR = "com.android.internal.pm.pkg.component.flags.enable_per_process_use_embedded_dex_attr";

    public static boolean enablePerProcessUseEmbeddedDexAttr() {
        return FEATURE_FLAGS.enablePerProcessUseEmbeddedDexAttr();
    }
}
