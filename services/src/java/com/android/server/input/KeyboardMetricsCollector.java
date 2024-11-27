package com.android.server.input;

/* loaded from: classes2.dex */
public final class KeyboardMetricsCollector {

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String DEFAULT_LANGUAGE_TAG = "None";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String DEFAULT_LAYOUT_NAME = "Default";
    private static final java.lang.String TAG = "KeyboardMetricCollector";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public enum KeyboardLogEvent {
        UNSPECIFIED(0, "INVALID_KEYBOARD_EVENT"),
        HOME(1, "HOME"),
        RECENT_APPS(2, "RECENT_APPS"),
        BACK(3, "BACK"),
        APP_SWITCH(4, "APP_SWITCH"),
        LAUNCH_ASSISTANT(5, "LAUNCH_ASSISTANT"),
        LAUNCH_VOICE_ASSISTANT(6, "LAUNCH_VOICE_ASSISTANT"),
        LAUNCH_SYSTEM_SETTINGS(7, "LAUNCH_SYSTEM_SETTINGS"),
        TOGGLE_NOTIFICATION_PANEL(8, "TOGGLE_NOTIFICATION_PANEL"),
        TOGGLE_TASKBAR(9, "TOGGLE_TASKBAR"),
        TAKE_SCREENSHOT(10, "TAKE_SCREENSHOT"),
        OPEN_SHORTCUT_HELPER(11, "OPEN_SHORTCUT_HELPER"),
        BRIGHTNESS_UP(12, "BRIGHTNESS_UP"),
        BRIGHTNESS_DOWN(13, "BRIGHTNESS_DOWN"),
        KEYBOARD_BACKLIGHT_UP(14, "KEYBOARD_BACKLIGHT_UP"),
        KEYBOARD_BACKLIGHT_DOWN(15, "KEYBOARD_BACKLIGHT_DOWN"),
        KEYBOARD_BACKLIGHT_TOGGLE(16, "KEYBOARD_BACKLIGHT_TOGGLE"),
        VOLUME_UP(17, "VOLUME_UP"),
        VOLUME_DOWN(18, "VOLUME_DOWN"),
        VOLUME_MUTE(19, "VOLUME_MUTE"),
        ALL_APPS(20, "ALL_APPS"),
        LAUNCH_SEARCH(21, "LAUNCH_SEARCH"),
        LANGUAGE_SWITCH(22, "LANGUAGE_SWITCH"),
        ACCESSIBILITY_ALL_APPS(23, "ACCESSIBILITY_ALL_APPS"),
        TOGGLE_CAPS_LOCK(24, "TOGGLE_CAPS_LOCK"),
        SYSTEM_MUTE(25, "SYSTEM_MUTE"),
        SPLIT_SCREEN_NAVIGATION(26, "SPLIT_SCREEN_NAVIGATION"),
        TRIGGER_BUG_REPORT(27, "TRIGGER_BUG_REPORT"),
        LOCK_SCREEN(28, "LOCK_SCREEN"),
        OPEN_NOTES(29, "OPEN_NOTES"),
        TOGGLE_POWER(30, "TOGGLE_POWER"),
        SYSTEM_NAVIGATION(31, "SYSTEM_NAVIGATION"),
        SLEEP(32, "SLEEP"),
        WAKEUP(33, "WAKEUP"),
        MEDIA_KEY(34, "MEDIA_KEY"),
        LAUNCH_DEFAULT_BROWSER(35, "LAUNCH_DEFAULT_BROWSER"),
        LAUNCH_DEFAULT_EMAIL(36, "LAUNCH_DEFAULT_EMAIL"),
        LAUNCH_DEFAULT_CONTACTS(37, "LAUNCH_DEFAULT_CONTACTS"),
        LAUNCH_DEFAULT_CALENDAR(38, "LAUNCH_DEFAULT_CALENDAR"),
        LAUNCH_DEFAULT_CALCULATOR(39, "LAUNCH_DEFAULT_CALCULATOR"),
        LAUNCH_DEFAULT_MUSIC(40, "LAUNCH_DEFAULT_MUSIC"),
        LAUNCH_DEFAULT_MAPS(41, "LAUNCH_DEFAULT_MAPS"),
        LAUNCH_DEFAULT_MESSAGING(42, "LAUNCH_DEFAULT_MESSAGING"),
        LAUNCH_DEFAULT_GALLERY(43, "LAUNCH_DEFAULT_GALLERY"),
        LAUNCH_DEFAULT_FILES(44, "LAUNCH_DEFAULT_FILES"),
        LAUNCH_DEFAULT_WEATHER(45, "LAUNCH_DEFAULT_WEATHER"),
        LAUNCH_DEFAULT_FITNESS(46, "LAUNCH_DEFAULT_FITNESS"),
        LAUNCH_APPLICATION_BY_PACKAGE_NAME(47, "LAUNCH_APPLICATION_BY_PACKAGE_NAME"),
        DESKTOP_MODE(48, "DESKTOP_MODE"),
        MULTI_WINDOW_NAVIGATION(49, "MULTIWINDOW_NAVIGATION");

        private static final android.util.SparseArray<com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent> VALUE_TO_ENUM_MAP = new android.util.SparseArray<>();
        private final java.lang.String mName;
        private final int mValue;

        static {
            for (com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent : values()) {
                VALUE_TO_ENUM_MAP.put(keyboardLogEvent.mValue, keyboardLogEvent);
            }
        }

        KeyboardLogEvent(int i, java.lang.String str) {
            this.mValue = i;
            this.mName = str;
        }

        public int getIntValue() {
            return this.mValue;
        }

        @android.annotation.Nullable
        public static com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent from(int i) {
            return VALUE_TO_ENUM_MAP.get(i);
        }

        @android.annotation.Nullable
        public static com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent getVolumeEvent(int i) {
            switch (i) {
                case 24:
                    return VOLUME_UP;
                case 25:
                    return VOLUME_DOWN;
                case 164:
                    return VOLUME_MUTE;
                default:
                    return null;
            }
        }

        @android.annotation.Nullable
        public static com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent getBrightnessEvent(int i) {
            switch (i) {
                case com.android.server.usb.descriptors.UsbDescriptor.CLASSID_DIAGNOSTIC /* 220 */:
                    return BRIGHTNESS_DOWN;
                case 221:
                    return BRIGHTNESS_UP;
                default:
                    return null;
            }
        }

        @android.annotation.Nullable
        public static com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent getLogEventFromIntent(android.content.Intent intent) {
            java.util.Set<java.lang.String> categories;
            android.content.Intent selector = intent.getSelector();
            if (selector != null && (categories = selector.getCategories()) != null && !categories.isEmpty()) {
                java.util.Iterator<java.lang.String> it = categories.iterator();
                while (it.hasNext()) {
                    com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent eventFromSelectorCategory = getEventFromSelectorCategory(it.next());
                    if (eventFromSelectorCategory != null) {
                        return eventFromSelectorCategory;
                    }
                }
            }
            java.lang.String stringExtra = intent.getStringExtra(com.android.server.policy.ModifierShortcutManager.EXTRA_ROLE);
            if (!android.text.TextUtils.isEmpty(stringExtra)) {
                return getLogEventFromRole(stringExtra);
            }
            java.util.Set<java.lang.String> categories2 = intent.getCategories();
            if (categories2 == null || categories2.isEmpty() || !categories2.contains("android.intent.category.LAUNCHER") || intent.getComponent() == null) {
                return null;
            }
            return LAUNCH_APPLICATION_BY_PACKAGE_NAME;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @android.annotation.Nullable
        private static com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent getEventFromSelectorCategory(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -2061526830:
                    if (str.equals("android.intent.category.APP_MAPS")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1895059731:
                    if (str.equals("android.intent.category.APP_BROWSER")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -822559815:
                    if (str.equals("android.intent.category.APP_WEATHER")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -551167607:
                    if (str.equals("android.intent.category.APP_MESSAGING")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 431052046:
                    if (str.equals("android.intent.category.APP_CONTACTS")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 510132385:
                    if (str.equals("android.intent.category.APP_EMAIL")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 510947196:
                    if (str.equals("android.intent.category.APP_FILES")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 517776170:
                    if (str.equals("android.intent.category.APP_MUSIC")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 645732605:
                    if (str.equals("android.intent.category.APP_CALCULATOR")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 820178937:
                    if (str.equals("android.intent.category.APP_CALENDAR")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1401629101:
                    if (str.equals("android.intent.category.APP_FITNESS")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 2052651799:
                    if (str.equals("android.intent.category.APP_GALLERY")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return LAUNCH_DEFAULT_BROWSER;
                case 1:
                    return LAUNCH_DEFAULT_EMAIL;
                case 2:
                    return LAUNCH_DEFAULT_CONTACTS;
                case 3:
                    return LAUNCH_DEFAULT_CALENDAR;
                case 4:
                    return LAUNCH_DEFAULT_CALCULATOR;
                case 5:
                    return LAUNCH_DEFAULT_MUSIC;
                case 6:
                    return LAUNCH_DEFAULT_MAPS;
                case 7:
                    return LAUNCH_DEFAULT_MESSAGING;
                case '\b':
                    return LAUNCH_DEFAULT_GALLERY;
                case '\t':
                    return LAUNCH_DEFAULT_FILES;
                case '\n':
                    return LAUNCH_DEFAULT_WEATHER;
                case 11:
                    return LAUNCH_DEFAULT_FITNESS;
                default:
                    return null;
            }
        }

        @android.annotation.Nullable
        private static com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent getLogEventFromRole(java.lang.String str) {
            if ("android.app.role.BROWSER".equals(str)) {
                return LAUNCH_DEFAULT_BROWSER;
            }
            if ("android.app.role.SMS".equals(str)) {
                return LAUNCH_DEFAULT_MESSAGING;
            }
            android.util.Log.w(com.android.server.input.KeyboardMetricsCollector.TAG, "Keyboard shortcut to launch " + str + " not supported for logging");
            return null;
        }
    }

    public static void logKeyboardSystemsEventReportedAtom(@android.annotation.Nullable android.view.InputDevice inputDevice, @android.annotation.Nullable com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent, int i, int... iArr) {
        if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
            return;
        }
        if (keyboardLogEvent == null) {
            android.util.Slog.w(TAG, "Invalid keyboard event logging, keycode = " + java.util.Arrays.toString(iArr) + ", modifier state = " + i);
            return;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.KEYBOARD_SYSTEMS_EVENT_REPORTED, inputDevice.getVendorId(), inputDevice.getProductId(), keyboardLogEvent.getIntValue(), iArr, i, inputDevice.getDeviceBus());
        if (DEBUG) {
            android.util.Slog.d(TAG, "Logging Keyboard system event: " + keyboardLogEvent.mName);
        }
    }

    public static void logKeyboardConfiguredAtom(com.android.server.input.KeyboardMetricsCollector.KeyboardConfigurationEvent keyboardConfigurationEvent) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        java.util.Iterator<com.android.server.input.KeyboardMetricsCollector.LayoutConfiguration> it = keyboardConfigurationEvent.getLayoutConfigurations().iterator();
        while (it.hasNext()) {
            addKeyboardLayoutConfigurationToProto(protoOutputStream, it.next());
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.KEYBOARD_CONFIGURED, keyboardConfigurationEvent.isFirstConfiguration(), keyboardConfigurationEvent.getVendorId(), keyboardConfigurationEvent.getProductId(), protoOutputStream.getBytes(), keyboardConfigurationEvent.getDeviceBus());
        if (DEBUG) {
            android.util.Slog.d(TAG, "Logging Keyboard configuration event: " + keyboardConfigurationEvent);
        }
    }

    private static void addKeyboardLayoutConfigurationToProto(android.util.proto.ProtoOutputStream protoOutputStream, com.android.server.input.KeyboardMetricsCollector.LayoutConfiguration layoutConfiguration) {
        long start = protoOutputStream.start(2246267895809L);
        protoOutputStream.write(1138166333442L, layoutConfiguration.keyboardLanguageTag);
        protoOutputStream.write(1120986464257L, layoutConfiguration.keyboardLayoutType);
        protoOutputStream.write(1138166333443L, layoutConfiguration.keyboardLayoutName);
        protoOutputStream.write(1120986464260L, layoutConfiguration.layoutSelectionCriteria);
        protoOutputStream.write(1138166333446L, layoutConfiguration.imeLanguageTag);
        protoOutputStream.write(1120986464261L, layoutConfiguration.imeLayoutType);
        protoOutputStream.end(start);
    }

    public static class KeyboardConfigurationEvent {
        private final android.view.InputDevice mInputDevice;
        private final boolean mIsFirstConfiguration;
        private final java.util.List<com.android.server.input.KeyboardMetricsCollector.LayoutConfiguration> mLayoutConfigurations;

        private KeyboardConfigurationEvent(android.view.InputDevice inputDevice, boolean z, java.util.List<com.android.server.input.KeyboardMetricsCollector.LayoutConfiguration> list) {
            this.mInputDevice = inputDevice;
            this.mIsFirstConfiguration = z;
            this.mLayoutConfigurations = list;
        }

        public int getVendorId() {
            return this.mInputDevice.getVendorId();
        }

        public int getProductId() {
            return this.mInputDevice.getProductId();
        }

        public int getDeviceBus() {
            return this.mInputDevice.getDeviceBus();
        }

        public boolean isFirstConfiguration() {
            return this.mIsFirstConfiguration;
        }

        public java.util.List<com.android.server.input.KeyboardMetricsCollector.LayoutConfiguration> getLayoutConfigurations() {
            return this.mLayoutConfigurations;
        }

        public java.lang.String toString() {
            return "InputDevice = {VendorId = " + java.lang.Integer.toHexString(getVendorId()) + ", ProductId = " + java.lang.Integer.toHexString(getProductId()) + ", Device Bus = " + java.lang.Integer.toHexString(getDeviceBus()) + "}, isFirstConfiguration = " + this.mIsFirstConfiguration + ", LayoutConfigurations = " + this.mLayoutConfigurations;
        }

        public static class Builder {

            @android.annotation.NonNull
            private final android.view.InputDevice mInputDevice;
            private boolean mIsFirstConfiguration;
            private final java.util.List<android.view.inputmethod.InputMethodSubtype> mImeSubtypeList = new java.util.ArrayList();
            private final java.util.List<java.lang.String> mSelectedLayoutList = new java.util.ArrayList();
            private final java.util.List<java.lang.Integer> mLayoutSelectionCriteriaList = new java.util.ArrayList();

            public Builder(@android.annotation.NonNull android.view.InputDevice inputDevice) {
                java.util.Objects.requireNonNull(inputDevice, "InputDevice provided should not be null");
                this.mInputDevice = inputDevice;
            }

            public com.android.server.input.KeyboardMetricsCollector.KeyboardConfigurationEvent.Builder setIsFirstTimeConfiguration(boolean z) {
                this.mIsFirstConfiguration = z;
                return this;
            }

            public com.android.server.input.KeyboardMetricsCollector.KeyboardConfigurationEvent.Builder addLayoutSelection(@android.annotation.NonNull android.view.inputmethod.InputMethodSubtype inputMethodSubtype, @android.annotation.Nullable java.lang.String str, int i) {
                java.util.Objects.requireNonNull(inputMethodSubtype, "IME subtype provided should not be null");
                if (!com.android.server.input.KeyboardMetricsCollector.isValidSelectionCriteria(i)) {
                    throw new java.lang.IllegalStateException("Invalid layout selection criteria");
                }
                this.mImeSubtypeList.add(inputMethodSubtype);
                this.mSelectedLayoutList.add(str);
                this.mLayoutSelectionCriteriaList.add(java.lang.Integer.valueOf(i));
                return this;
            }

            public com.android.server.input.KeyboardMetricsCollector.KeyboardConfigurationEvent build() {
                java.lang.String str;
                int size = this.mImeSubtypeList.size();
                if (size == 0) {
                    throw new java.lang.IllegalStateException("Should have at least one configuration");
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i = 0; i < size; i++) {
                    int intValue = this.mLayoutSelectionCriteriaList.get(i).intValue();
                    android.view.inputmethod.InputMethodSubtype inputMethodSubtype = this.mImeSubtypeList.get(i);
                    java.lang.String keyboardLanguageTag = this.mInputDevice.getKeyboardLanguageTag();
                    java.lang.String str2 = android.text.TextUtils.isEmpty(keyboardLanguageTag) ? com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : keyboardLanguageTag;
                    int layoutTypeEnumValue = android.hardware.input.KeyboardLayout.LayoutType.getLayoutTypeEnumValue(this.mInputDevice.getKeyboardLayoutType());
                    android.icu.util.ULocale physicalKeyboardHintLanguageTag = inputMethodSubtype.getPhysicalKeyboardHintLanguageTag();
                    java.lang.String languageTag = physicalKeyboardHintLanguageTag != null ? physicalKeyboardHintLanguageTag.toLanguageTag() : inputMethodSubtype.getCanonicalizedLanguageTag();
                    java.lang.String str3 = android.text.TextUtils.isEmpty(languageTag) ? com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : languageTag;
                    int layoutTypeEnumValue2 = android.hardware.input.KeyboardLayout.LayoutType.getLayoutTypeEnumValue(inputMethodSubtype.getPhysicalKeyboardHintLayoutType());
                    if (this.mSelectedLayoutList.get(i) == null) {
                        str = com.android.server.input.KeyboardMetricsCollector.DEFAULT_LAYOUT_NAME;
                    } else {
                        str = this.mSelectedLayoutList.get(i);
                    }
                    arrayList.add(new com.android.server.input.KeyboardMetricsCollector.LayoutConfiguration(layoutTypeEnumValue, str2, str, intValue, layoutTypeEnumValue2, str3));
                }
                return new com.android.server.input.KeyboardMetricsCollector.KeyboardConfigurationEvent(this.mInputDevice, this.mIsFirstConfiguration, arrayList);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class LayoutConfiguration {
        public final java.lang.String imeLanguageTag;
        public final int imeLayoutType;
        public final java.lang.String keyboardLanguageTag;
        public final java.lang.String keyboardLayoutName;
        public final int keyboardLayoutType;
        public final int layoutSelectionCriteria;

        private LayoutConfiguration(int i, java.lang.String str, java.lang.String str2, int i2, int i3, java.lang.String str3) {
            this.keyboardLayoutType = i;
            this.keyboardLanguageTag = str;
            this.keyboardLayoutName = str2;
            this.layoutSelectionCriteria = i2;
            this.imeLayoutType = i3;
            this.imeLanguageTag = str3;
        }

        public java.lang.String toString() {
            return "{keyboardLanguageTag = " + this.keyboardLanguageTag + " keyboardLayoutType = " + android.hardware.input.KeyboardLayout.LayoutType.getLayoutNameFromValue(this.keyboardLayoutType) + " keyboardLayoutName = " + this.keyboardLayoutName + " layoutSelectionCriteria = " + android.hardware.input.KeyboardLayoutSelectionResult.layoutSelectionCriteriaToString(this.layoutSelectionCriteria) + " imeLanguageTag = " + this.imeLanguageTag + " imeLayoutType = " + android.hardware.input.KeyboardLayout.LayoutType.getLayoutNameFromValue(this.imeLayoutType) + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidSelectionCriteria(int i) {
        return i == 1 || i == 2 || i == 3 || i == 4;
    }
}
