package com.android.server.input;

/* loaded from: classes2.dex */
class InputSettingsObserver extends android.database.ContentObserver {
    private static final java.lang.String DEEP_PRESS_ENABLED = "deep_press_enabled";
    static final java.lang.String TAG = "InputManager";
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final com.android.server.input.NativeInputManagerService mNative;
    private final java.util.Map<android.net.Uri, java.util.function.Consumer<java.lang.String>> mObservers;
    private final com.android.server.input.InputManagerService mService;

    InputSettingsObserver(android.content.Context context, android.os.Handler handler, com.android.server.input.InputManagerService inputManagerService, com.android.server.input.NativeInputManagerService nativeInputManagerService) {
        super(handler);
        this.mContext = context;
        this.mHandler = handler;
        this.mService = inputManagerService;
        this.mNative = nativeInputManagerService;
        this.mObservers = java.util.Map.ofEntries(java.util.Map.entry(android.provider.Settings.System.getUriFor("pointer_speed"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$0((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.System.getUriFor("touchpad_pointer_speed"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$1((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.System.getUriFor("touchpad_natural_scrolling"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$2((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.System.getUriFor("touchpad_tap_to_click"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$3((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.System.getUriFor("touchpad_tap_dragging"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$4((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.System.getUriFor("touchpad_right_click_zone"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$5((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.System.getUriFor("show_touches"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$6((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.System.getUriFor("pointer_location"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$7((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.Secure.getUriFor("accessibility_large_pointer_icon"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$8((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.Secure.getUriFor("long_press_timeout"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$9((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.Global.getUriFor("maximum_obscuring_opacity_for_touch"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$10((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.System.getUriFor("show_key_presses"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$11((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.Secure.getUriFor("key_repeat_timeout"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$12((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.Secure.getUriFor("key_repeat_delay"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$13((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.System.getUriFor("show_rotary_input"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$14((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.Secure.getUriFor("accessibility_bounce_keys"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$15((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.Secure.getUriFor("accessibility_slow_keys"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$16((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.Secure.getUriFor("accessibility_sticky_keys"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$17((java.lang.String) obj);
            }
        }), java.util.Map.entry(android.provider.Settings.Secure.getUriFor("stylus_pointer_icon_enabled"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$18((java.lang.String) obj);
            }
        }), java.util.Map.entry(lineageos.providers.LineageSettings.System.getUriFor("swap_volume_keys_on_rotation"), new java.util.function.Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.InputSettingsObserver.this.lambda$new$19((java.lang.String) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.lang.String str) {
        updateMousePointerSpeed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(java.lang.String str) {
        updateTouchpadPointerSpeed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(java.lang.String str) {
        updateTouchpadNaturalScrollingEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(java.lang.String str) {
        updateTouchpadTapToClickEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(java.lang.String str) {
        updateTouchpadTapDraggingEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(java.lang.String str) {
        updateTouchpadRightClickZoneEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(java.lang.String str) {
        updateShowTouches();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(java.lang.String str) {
        updatePointerLocation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(java.lang.String str) {
        updateAccessibilityLargePointer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10(java.lang.String str) {
        updateMaximumObscuringOpacityForTouch();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$11(java.lang.String str) {
        updateShowKeyPresses();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$12(java.lang.String str) {
        updateKeyRepeatInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$13(java.lang.String str) {
        updateKeyRepeatInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$14(java.lang.String str) {
        updateShowRotaryInput();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$15(java.lang.String str) {
        updateAccessibilityBounceKeys();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$16(java.lang.String str) {
        updateAccessibilitySlowKeys();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$17(java.lang.String str) {
        updateAccessibilityStickyKeys();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$18(java.lang.String str) {
        updateStylusPointerIconEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$19(java.lang.String str) {
        updateVolumeKeysRotation();
    }

    public void registerAndUpdate() {
        java.util.Iterator<android.net.Uri> it = this.mObservers.keySet().iterator();
        while (it.hasNext()) {
            this.mContext.getContentResolver().registerContentObserver(it.next(), true, this, -1);
        }
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.input.InputSettingsObserver.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                java.util.Iterator it2 = com.android.server.input.InputSettingsObserver.this.mObservers.values().iterator();
                while (it2.hasNext()) {
                    ((java.util.function.Consumer) it2.next()).accept("user switched");
                }
            }
        }, new android.content.IntentFilter("android.intent.action.USER_SWITCHED"), null, this.mHandler);
        java.util.Iterator<java.util.function.Consumer<java.lang.String>> it2 = this.mObservers.values().iterator();
        while (it2.hasNext()) {
            it2.next().accept("just booted");
        }
        configureUserActivityPokeInterval();
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, android.net.Uri uri) {
        this.mObservers.get(uri).accept("setting changed");
    }

    private boolean getBoolean(java.lang.String str, boolean z) {
        return android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), str, z ? 1 : 0, -2) != 0;
    }

    private int constrainPointerSpeedValue(int i) {
        return java.lang.Math.min(java.lang.Math.max(i, -7), 7);
    }

    private void updateMousePointerSpeed() {
        this.mNative.setPointerSpeed(constrainPointerSpeedValue(android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), "pointer_speed", 0, -2)));
    }

    private void updateTouchpadPointerSpeed() {
        this.mNative.setTouchpadPointerSpeed(constrainPointerSpeedValue(android.hardware.input.InputSettings.getTouchpadPointerSpeed(this.mContext)));
    }

    private void updateTouchpadNaturalScrollingEnabled() {
        this.mNative.setTouchpadNaturalScrollingEnabled(android.hardware.input.InputSettings.useTouchpadNaturalScrolling(this.mContext));
    }

    private void updateTouchpadTapToClickEnabled() {
        this.mNative.setTouchpadTapToClickEnabled(android.hardware.input.InputSettings.useTouchpadTapToClick(this.mContext));
    }

    private void updateTouchpadTapDraggingEnabled() {
        this.mNative.setTouchpadTapDraggingEnabled(android.hardware.input.InputSettings.useTouchpadTapDragging(this.mContext));
    }

    private void updateTouchpadRightClickZoneEnabled() {
        this.mNative.setTouchpadRightClickZoneEnabled(android.hardware.input.InputSettings.useTouchpadRightClickZone(this.mContext));
    }

    private void updateShowTouches() {
        this.mNative.setShowTouches(getBoolean("show_touches", false));
    }

    private void updatePointerLocation() {
        this.mService.updatePointerLocationEnabled(getBoolean("pointer_location", false));
    }

    private void updateShowKeyPresses() {
        this.mService.updateShowKeyPresses(getBoolean("show_key_presses", false));
    }

    private void updateShowRotaryInput() {
        this.mService.updateShowRotaryInput(getBoolean("show_rotary_input", false));
    }

    private void updateVolumeKeysRotation() {
        this.mNative.setVolumeKeysRotation(lineageos.providers.LineageSettings.System.getIntForUser(this.mContext.getContentResolver(), "swap_volume_keys_on_rotation", 0, -2));
    }

    private void updateAccessibilityLargePointer() {
        this.mService.setUseLargePointerIcons(android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_large_pointer_icon", 0, -2) == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateLongPressTimeout, reason: merged with bridge method [inline-methods] */
    public void lambda$new$9(java.lang.String str) {
        int intForUser = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "long_press_timeout", 400, -2);
        boolean z = android.provider.DeviceConfig.getBoolean("input_native_boot", DEEP_PRESS_ENABLED, true);
        boolean z2 = z && intForUser <= 400;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(z2 ? "Enabling" : "Disabling");
        sb.append(" motion classifier because ");
        sb.append(str);
        sb.append(": feature ");
        sb.append(z ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
        sb.append(", long press timeout = ");
        sb.append(intForUser);
        sb.append(" ms");
        android.util.Log.i(TAG, sb.toString());
        this.mNative.setMotionClassifierEnabled(z2);
    }

    private void updateKeyRepeatInfo() {
        this.mNative.setKeyRepeatConfiguration(android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "key_repeat_timeout", android.view.ViewConfiguration.getKeyRepeatTimeout(), -2), android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "key_repeat_delay", android.view.ViewConfiguration.getKeyRepeatDelay(), -2));
    }

    private void updateMaximumObscuringOpacityForTouch() {
        float maximumObscuringOpacityForTouch = android.hardware.input.InputSettings.getMaximumObscuringOpacityForTouch(this.mContext);
        if (maximumObscuringOpacityForTouch < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || maximumObscuringOpacityForTouch > 1.0f) {
            android.util.Log.e(TAG, "Invalid maximum obscuring opacity " + maximumObscuringOpacityForTouch + ", it should be >= 0 and <= 1, rejecting update.");
            return;
        }
        this.mNative.setMaximumObscuringOpacityForTouch(maximumObscuringOpacityForTouch);
    }

    private void updateAccessibilityBounceKeys() {
        this.mService.setAccessibilityBounceKeysThreshold(android.hardware.input.InputSettings.getAccessibilityBounceKeysThreshold(this.mContext));
    }

    private void updateAccessibilitySlowKeys() {
        this.mService.setAccessibilitySlowKeysThreshold(android.hardware.input.InputSettings.getAccessibilitySlowKeysThreshold(this.mContext));
    }

    private void updateAccessibilityStickyKeys() {
        this.mService.setAccessibilityStickyKeysEnabled(android.hardware.input.InputSettings.isAccessibilityStickyKeysEnabled(this.mContext));
    }

    private void configureUserActivityPokeInterval() {
        if (com.android.input.flags.Flags.rateLimitUserActivityPokeInDispatcher()) {
            int integer = this.mContext.getResources().getInteger(android.R.integer.config_mediaOutputSwitchDialogVersion);
            android.util.Log.i(TAG, "Setting user activity interval (ms) of " + integer);
            this.mNative.setMinTimeBetweenUserActivityPokes((long) integer);
        }
    }

    private void updateStylusPointerIconEnabled() {
        this.mNative.setStylusPointerIconEnabled(android.hardware.input.InputSettings.isStylusPointerIconEnabled(this.mContext));
    }
}
