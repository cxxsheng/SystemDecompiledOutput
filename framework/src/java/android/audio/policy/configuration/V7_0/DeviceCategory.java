package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum DeviceCategory {
    DEVICE_CATEGORY_HEADSET("DEVICE_CATEGORY_HEADSET"),
    DEVICE_CATEGORY_SPEAKER("DEVICE_CATEGORY_SPEAKER"),
    DEVICE_CATEGORY_EARPIECE("DEVICE_CATEGORY_EARPIECE"),
    DEVICE_CATEGORY_EXT_MEDIA("DEVICE_CATEGORY_EXT_MEDIA"),
    DEVICE_CATEGORY_HEARING_AID("DEVICE_CATEGORY_HEARING_AID");

    private final java.lang.String rawName;

    DeviceCategory(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.DeviceCategory fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.DeviceCategory deviceCategory : values()) {
            if (deviceCategory.getRawName().equals(str)) {
                return deviceCategory;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
