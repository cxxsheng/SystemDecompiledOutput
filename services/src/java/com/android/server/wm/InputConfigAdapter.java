package com.android.server.wm;

/* loaded from: classes3.dex */
class InputConfigAdapter {
    private static final java.util.List<com.android.server.wm.InputConfigAdapter.FlagMapping> INPUT_FEATURE_TO_CONFIG_MAP = java.util.List.of(new com.android.server.wm.InputConfigAdapter.FlagMapping(1, 1, false), new com.android.server.wm.InputConfigAdapter.FlagMapping(2, 2048, false), new com.android.server.wm.InputConfigAdapter.FlagMapping(4, 16384, false));
    private static final int INPUT_FEATURE_TO_CONFIG_MASK = computeMask(INPUT_FEATURE_TO_CONFIG_MAP);
    private static final java.util.List<com.android.server.wm.InputConfigAdapter.FlagMapping> LAYOUT_PARAM_FLAG_TO_CONFIG_MAP = java.util.List.of(new com.android.server.wm.InputConfigAdapter.FlagMapping(16, 8, false), new com.android.server.wm.InputConfigAdapter.FlagMapping(8388608, 16, true), new com.android.server.wm.InputConfigAdapter.FlagMapping(262144, 512, false), new com.android.server.wm.InputConfigAdapter.FlagMapping(536870912, 1024, false));
    private static final int LAYOUT_PARAM_FLAG_TO_CONFIG_MASK = computeMask(LAYOUT_PARAM_FLAG_TO_CONFIG_MAP);

    private InputConfigAdapter() {
    }

    private static class FlagMapping {
        final int mFlag;
        final int mInputConfig;
        final boolean mInverted;

        FlagMapping(int i, int i2, boolean z) {
            this.mFlag = i;
            this.mInputConfig = i2;
            this.mInverted = z;
        }
    }

    static int getMask() {
        return LAYOUT_PARAM_FLAG_TO_CONFIG_MASK | INPUT_FEATURE_TO_CONFIG_MASK | 64;
    }

    static int getInputConfigFromWindowParams(int i, int i2, int i3) {
        return (i == 2013 ? 64 : 0) | applyMapping(i2, LAYOUT_PARAM_FLAG_TO_CONFIG_MAP) | applyMapping(i3, INPUT_FEATURE_TO_CONFIG_MAP);
    }

    private static int applyMapping(int i, java.util.List<com.android.server.wm.InputConfigAdapter.FlagMapping> list) {
        int i2 = 0;
        for (com.android.server.wm.InputConfigAdapter.FlagMapping flagMapping : list) {
            if (((flagMapping.mFlag & i) != 0) != flagMapping.mInverted) {
                i2 |= flagMapping.mInputConfig;
            }
        }
        return i2;
    }

    private static int computeMask(java.util.List<com.android.server.wm.InputConfigAdapter.FlagMapping> list) {
        java.util.Iterator<com.android.server.wm.InputConfigAdapter.FlagMapping> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            i |= it.next().mInputConfig;
        }
        return i;
    }
}
