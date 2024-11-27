package com.android.internal.accessibility.common;

/* loaded from: classes4.dex */
public final class ShortcutConstants {
    public static final java.lang.String CHOOSER_PACKAGE_NAME = "android";
    public static final char SERVICES_SEPARATOR = ':';
    public static final int[] USER_SHORTCUT_TYPES = {1, 2, 4};

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AccessibilityFragmentType {
        public static final int INVISIBLE_TOGGLE = 1;
        public static final int LAUNCH_ACTIVITY = 3;
        public static final int TOGGLE = 2;
        public static final int VOLUME_SHORTCUT_TOGGLE = 0;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShortcutMenuMode {
        public static final int EDIT = 1;
        public static final int LAUNCH = 0;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserShortcutType {
        public static final int DEFAULT = 0;
        public static final int HARDWARE = 2;
        public static final int SOFTWARE = 1;
        public static final int TRIPLETAP = 4;
    }

    private ShortcutConstants() {
    }
}
