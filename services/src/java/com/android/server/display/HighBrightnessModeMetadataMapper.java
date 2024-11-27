package com.android.server.display;

/* loaded from: classes.dex */
class HighBrightnessModeMetadataMapper {
    private static final java.lang.String TAG = "HighBrightnessModeMetadataMapper";
    private final android.util.ArrayMap<java.lang.String, com.android.server.display.HighBrightnessModeMetadata> mHighBrightnessModeMetadataMap = new android.util.ArrayMap<>();

    HighBrightnessModeMetadataMapper() {
    }

    com.android.server.display.HighBrightnessModeMetadata getHighBrightnessModeMetadataLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
        if (primaryDisplayDeviceLocked == null) {
            android.util.Slog.wtf(TAG, "Display Device is null in DisplayPowerController for display: " + logicalDisplay.getDisplayIdLocked());
            return null;
        }
        if (primaryDisplayDeviceLocked.getDisplayDeviceConfig().getHighBrightnessModeData() == null) {
            return null;
        }
        java.lang.String uniqueId = primaryDisplayDeviceLocked.getUniqueId();
        if (this.mHighBrightnessModeMetadataMap.containsKey(uniqueId)) {
            return this.mHighBrightnessModeMetadataMap.get(uniqueId);
        }
        com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata = new com.android.server.display.HighBrightnessModeMetadata();
        this.mHighBrightnessModeMetadataMap.put(uniqueId, highBrightnessModeMetadata);
        return highBrightnessModeMetadata;
    }
}
