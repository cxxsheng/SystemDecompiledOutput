package com.android.server.wm;

/* loaded from: classes3.dex */
class DisplayWindowSettings {

    @android.annotation.NonNull
    private final com.android.server.wm.WindowManagerService mService;

    @android.annotation.NonNull
    private final com.android.server.wm.DisplayWindowSettings.SettingsProvider mSettingsProvider;

    DisplayWindowSettings(@android.annotation.NonNull com.android.server.wm.WindowManagerService windowManagerService, @android.annotation.NonNull com.android.server.wm.DisplayWindowSettings.SettingsProvider settingsProvider) {
        this.mService = windowManagerService;
        this.mSettingsProvider = settingsProvider;
    }

    void setUserRotation(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, int i, int i2) {
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mUserRotationMode = java.lang.Integer.valueOf(i);
        overrideSettings.mUserRotation = java.lang.Integer.valueOf(i2);
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    void setForcedSize(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, int i, int i2) {
        java.lang.String str;
        if (displayContent.isDefaultDisplay) {
            if (i == 0 || i2 == 0) {
                str = "";
            } else {
                str = i + "," + i2;
            }
            android.provider.Settings.Global.putString(this.mService.mContext.getContentResolver(), "display_size_forced", str);
        }
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mForcedWidth = i;
        overrideSettings.mForcedHeight = i2;
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    void setForcedDensity(@android.annotation.NonNull android.view.DisplayInfo displayInfo, int i, int i2) {
        if (displayInfo.displayId == 0) {
            android.provider.Settings.Secure.putStringForUser(this.mService.mContext.getContentResolver(), "display_density_forced", i == 0 ? "" : java.lang.Integer.toString(i), i2);
        }
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mForcedDensity = i;
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    void setForcedScalingMode(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, int i) {
        if (displayContent.isDefaultDisplay) {
            android.provider.Settings.Global.putInt(this.mService.mContext.getContentResolver(), "display_scaling_force", i);
        }
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mForcedScalingMode = java.lang.Integer.valueOf(i);
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    void setFixedToUserRotation(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, int i) {
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mFixedToUserRotation = java.lang.Integer.valueOf(i);
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    void setIgnoreOrientationRequest(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, boolean z) {
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mIgnoreOrientationRequest = java.lang.Boolean.valueOf(z);
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    private int getWindowingModeLocked(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry, @android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        int i = settingsEntry.mWindowingMode;
        if (i == 5 && !this.mService.mAtmService.mSupportsFreeformWindowManagement) {
            return 1;
        }
        if (i == 0) {
            return (this.mService.mAtmService.mSupportsFreeformWindowManagement && (this.mService.mIsPc || displayContent.forceDesktopMode())) ? 5 : 1;
        }
        return i;
    }

    int getWindowingModeLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        return getWindowingModeLocked(this.mSettingsProvider.getSettings(displayContent.getDisplayInfo()), displayContent);
    }

    void setWindowingModeLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, int i) {
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mWindowingMode = i;
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
        if (defaultTaskDisplayArea != null) {
            defaultTaskDisplayArea.setWindowingMode(i);
        }
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    int getRemoveContentModeLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settings = this.mSettingsProvider.getSettings(displayContent.getDisplayInfo());
        if (settings.mRemoveContentMode == 0) {
            if (displayContent.isPrivate()) {
                return 2;
            }
            return 1;
        }
        return settings.mRemoveContentMode;
    }

    void setRemoveContentModeLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, int i) {
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mRemoveContentMode = i;
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    boolean shouldShowWithInsecureKeyguardLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settings = this.mSettingsProvider.getSettings(displayContent.getDisplayInfo());
        if (settings.mShouldShowWithInsecureKeyguard != null) {
            return settings.mShouldShowWithInsecureKeyguard.booleanValue();
        }
        return false;
    }

    void setShouldShowWithInsecureKeyguardLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, boolean z) {
        if (!displayContent.isPrivate() && z) {
            throw new java.lang.IllegalArgumentException("Public display can't be allowed to show content when locked");
        }
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mShouldShowWithInsecureKeyguard = java.lang.Boolean.valueOf(z);
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    void setDontMoveToTop(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, boolean z) {
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settings = this.mSettingsProvider.getSettings(displayInfo);
        settings.mDontMoveToTop = java.lang.Boolean.valueOf(z);
        this.mSettingsProvider.updateOverrideSettings(displayInfo, settings);
    }

    boolean shouldShowSystemDecorsLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        if (displayContent.getDisplayId() == 0) {
            return true;
        }
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settings = this.mSettingsProvider.getSettings(displayContent.getDisplayInfo());
        if (settings.mShouldShowSystemDecors != null) {
            return settings.mShouldShowSystemDecors.booleanValue();
        }
        return false;
    }

    void setShouldShowSystemDecorsLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, boolean z) {
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mShouldShowSystemDecors = java.lang.Boolean.valueOf(z);
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    boolean isHomeSupportedLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        if (displayContent.getDisplayId() == 0) {
            return true;
        }
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settings = this.mSettingsProvider.getSettings(displayContent.getDisplayInfo());
        if (settings.mIsHomeSupported != null) {
            return settings.mIsHomeSupported.booleanValue();
        }
        return shouldShowSystemDecorsLocked(displayContent);
    }

    void setHomeSupportedOnDisplayLocked(@android.annotation.NonNull java.lang.String str, int i, boolean z) {
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
        displayInfo.uniqueId = str;
        displayInfo.type = i;
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mIsHomeSupported = java.lang.Boolean.valueOf(z);
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    void clearDisplaySettings(@android.annotation.NonNull java.lang.String str, int i) {
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
        displayInfo.uniqueId = str;
        displayInfo.type = i;
        this.mSettingsProvider.clearDisplaySettings(displayInfo);
    }

    int getImePolicyLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        if (displayContent.getDisplayId() == 0) {
            return 0;
        }
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settings = this.mSettingsProvider.getSettings(displayContent.getDisplayInfo());
        if (settings.mImePolicy != null) {
            return settings.mImePolicy.intValue();
        }
        return 1;
    }

    void setDisplayImePolicy(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, int i) {
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry overrideSettings = this.mSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mImePolicy = java.lang.Integer.valueOf(i);
        this.mSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    void applySettingsToDisplayLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        applySettingsToDisplayLocked(displayContent, true);
    }

    void applySettingsToDisplayLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, boolean z) {
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settings = this.mSettingsProvider.getSettings(displayContent.getDisplayInfo());
        int windowingModeLocked = getWindowingModeLocked(settings, displayContent);
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
        if (defaultTaskDisplayArea != null) {
            defaultTaskDisplayArea.setWindowingMode(windowingModeLocked);
        }
        displayContent.getDisplayRotation().restoreSettings(settings.mUserRotationMode != null ? settings.mUserRotationMode.intValue() : 0, settings.mUserRotation != null ? settings.mUserRotation.intValue() : 0, settings.mFixedToUserRotation != null ? settings.mFixedToUserRotation.intValue() : 0);
        boolean z2 = settings.mForcedDensity != 0;
        boolean z3 = (settings.mForcedWidth == 0 || settings.mForcedHeight == 0) ? false : true;
        displayContent.mIsDensityForced = z2;
        displayContent.mIsSizeForced = z3;
        displayContent.mIgnoreDisplayCutout = settings.mIgnoreDisplayCutout != null ? settings.mIgnoreDisplayCutout.booleanValue() : false;
        displayContent.updateBaseDisplayMetrics(z3 ? settings.mForcedWidth : displayContent.mInitialDisplayWidth, z3 ? settings.mForcedHeight : displayContent.mInitialDisplayHeight, z2 ? settings.mForcedDensity : displayContent.getInitialDisplayDensity(), displayContent.mBaseDisplayPhysicalXDpi, displayContent.mBaseDisplayPhysicalYDpi);
        displayContent.mDisplayScalingDisabled = (settings.mForcedScalingMode != null ? settings.mForcedScalingMode.intValue() : 0) == 1;
        displayContent.mDontMoveToTop = !displayContent.canStealTopFocus() || (settings.mDontMoveToTop != null ? settings.mDontMoveToTop.booleanValue() : false);
        if (z) {
            applyRotationSettingsToDisplayLocked(displayContent);
        }
    }

    void applyRotationSettingsToDisplayLocked(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settings = this.mSettingsProvider.getSettings(displayContent.getDisplayInfo());
        displayContent.setIgnoreOrientationRequest(settings.mIgnoreOrientationRequest != null ? settings.mIgnoreOrientationRequest.booleanValue() : false);
        displayContent.getDisplayRotation().resetAllowAllRotations();
    }

    boolean updateSettingsForDisplay(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
        if (defaultTaskDisplayArea != null && defaultTaskDisplayArea.getWindowingMode() != getWindowingModeLocked(displayContent)) {
            defaultTaskDisplayArea.setWindowingMode(getWindowingModeLocked(displayContent));
            return true;
        }
        return false;
    }

    void onDisplayRemoved(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        this.mSettingsProvider.onDisplayRemoved(displayContent.getDisplayInfo());
    }

    interface SettingsProvider {
        void clearDisplaySettings(@android.annotation.NonNull android.view.DisplayInfo displayInfo);

        @android.annotation.NonNull
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry getOverrideSettings(@android.annotation.NonNull android.view.DisplayInfo displayInfo);

        @android.annotation.NonNull
        com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry getSettings(@android.annotation.NonNull android.view.DisplayInfo displayInfo);

        void onDisplayRemoved(@android.annotation.NonNull android.view.DisplayInfo displayInfo);

        void updateOverrideSettings(@android.annotation.NonNull android.view.DisplayInfo displayInfo, @android.annotation.NonNull com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry);

        public static class SettingsEntry {

            @android.annotation.Nullable
            java.lang.Boolean mDontMoveToTop;

            @android.annotation.Nullable
            java.lang.Integer mFixedToUserRotation;
            int mForcedDensity;
            int mForcedHeight;

            @android.annotation.Nullable
            java.lang.Integer mForcedScalingMode;
            int mForcedWidth;

            @android.annotation.Nullable
            java.lang.Boolean mIgnoreDisplayCutout;

            @android.annotation.Nullable
            java.lang.Boolean mIgnoreOrientationRequest;

            @android.annotation.Nullable
            java.lang.Integer mImePolicy;

            @android.annotation.Nullable
            java.lang.Boolean mIsHomeSupported;

            @android.annotation.Nullable
            java.lang.Boolean mShouldShowSystemDecors;

            @android.annotation.Nullable
            java.lang.Boolean mShouldShowWithInsecureKeyguard;

            @android.annotation.Nullable
            java.lang.Integer mUserRotation;

            @android.annotation.Nullable
            java.lang.Integer mUserRotationMode;
            int mWindowingMode = 0;
            int mRemoveContentMode = 0;

            SettingsEntry() {
            }

            SettingsEntry(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry) {
                setTo(settingsEntry);
            }

            boolean setTo(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry) {
                boolean z;
                if (settingsEntry.mWindowingMode == this.mWindowingMode) {
                    z = false;
                } else {
                    this.mWindowingMode = settingsEntry.mWindowingMode;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mUserRotationMode, this.mUserRotationMode)) {
                    this.mUserRotationMode = settingsEntry.mUserRotationMode;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mUserRotation, this.mUserRotation)) {
                    this.mUserRotation = settingsEntry.mUserRotation;
                    z = true;
                }
                if (settingsEntry.mForcedWidth != this.mForcedWidth) {
                    this.mForcedWidth = settingsEntry.mForcedWidth;
                    z = true;
                }
                if (settingsEntry.mForcedHeight != this.mForcedHeight) {
                    this.mForcedHeight = settingsEntry.mForcedHeight;
                    z = true;
                }
                if (settingsEntry.mForcedDensity != this.mForcedDensity) {
                    this.mForcedDensity = settingsEntry.mForcedDensity;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mForcedScalingMode, this.mForcedScalingMode)) {
                    this.mForcedScalingMode = settingsEntry.mForcedScalingMode;
                    z = true;
                }
                if (settingsEntry.mRemoveContentMode != this.mRemoveContentMode) {
                    this.mRemoveContentMode = settingsEntry.mRemoveContentMode;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mShouldShowWithInsecureKeyguard, this.mShouldShowWithInsecureKeyguard)) {
                    this.mShouldShowWithInsecureKeyguard = settingsEntry.mShouldShowWithInsecureKeyguard;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mShouldShowSystemDecors, this.mShouldShowSystemDecors)) {
                    this.mShouldShowSystemDecors = settingsEntry.mShouldShowSystemDecors;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mIsHomeSupported, this.mIsHomeSupported)) {
                    this.mIsHomeSupported = settingsEntry.mIsHomeSupported;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mImePolicy, this.mImePolicy)) {
                    this.mImePolicy = settingsEntry.mImePolicy;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mFixedToUserRotation, this.mFixedToUserRotation)) {
                    this.mFixedToUserRotation = settingsEntry.mFixedToUserRotation;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mIgnoreOrientationRequest, this.mIgnoreOrientationRequest)) {
                    this.mIgnoreOrientationRequest = settingsEntry.mIgnoreOrientationRequest;
                    z = true;
                }
                if (!java.util.Objects.equals(settingsEntry.mIgnoreDisplayCutout, this.mIgnoreDisplayCutout)) {
                    this.mIgnoreDisplayCutout = settingsEntry.mIgnoreDisplayCutout;
                    z = true;
                }
                if (java.util.Objects.equals(settingsEntry.mDontMoveToTop, this.mDontMoveToTop)) {
                    return z;
                }
                this.mDontMoveToTop = settingsEntry.mDontMoveToTop;
                return true;
            }

            boolean updateFrom(@android.annotation.NonNull com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry) {
                boolean z;
                if (settingsEntry.mWindowingMode != 0 && settingsEntry.mWindowingMode != this.mWindowingMode) {
                    this.mWindowingMode = settingsEntry.mWindowingMode;
                    z = true;
                } else {
                    z = false;
                }
                if (settingsEntry.mUserRotationMode != null && !java.util.Objects.equals(settingsEntry.mUserRotationMode, this.mUserRotationMode)) {
                    this.mUserRotationMode = settingsEntry.mUserRotationMode;
                    z = true;
                }
                if (settingsEntry.mUserRotation != null && !java.util.Objects.equals(settingsEntry.mUserRotation, this.mUserRotation)) {
                    this.mUserRotation = settingsEntry.mUserRotation;
                    z = true;
                }
                if (settingsEntry.mForcedWidth != 0 && settingsEntry.mForcedWidth != this.mForcedWidth) {
                    this.mForcedWidth = settingsEntry.mForcedWidth;
                    z = true;
                }
                if (settingsEntry.mForcedHeight != 0 && settingsEntry.mForcedHeight != this.mForcedHeight) {
                    this.mForcedHeight = settingsEntry.mForcedHeight;
                    z = true;
                }
                if (settingsEntry.mForcedDensity != 0 && settingsEntry.mForcedDensity != this.mForcedDensity) {
                    this.mForcedDensity = settingsEntry.mForcedDensity;
                    z = true;
                }
                if (settingsEntry.mForcedScalingMode != null && !java.util.Objects.equals(settingsEntry.mForcedScalingMode, this.mForcedScalingMode)) {
                    this.mForcedScalingMode = settingsEntry.mForcedScalingMode;
                    z = true;
                }
                if (settingsEntry.mRemoveContentMode != 0 && settingsEntry.mRemoveContentMode != this.mRemoveContentMode) {
                    this.mRemoveContentMode = settingsEntry.mRemoveContentMode;
                    z = true;
                }
                if (settingsEntry.mShouldShowWithInsecureKeyguard != null && !java.util.Objects.equals(settingsEntry.mShouldShowWithInsecureKeyguard, this.mShouldShowWithInsecureKeyguard)) {
                    this.mShouldShowWithInsecureKeyguard = settingsEntry.mShouldShowWithInsecureKeyguard;
                    z = true;
                }
                if (settingsEntry.mShouldShowSystemDecors != null && !java.util.Objects.equals(settingsEntry.mShouldShowSystemDecors, this.mShouldShowSystemDecors)) {
                    this.mShouldShowSystemDecors = settingsEntry.mShouldShowSystemDecors;
                    z = true;
                }
                if (settingsEntry.mIsHomeSupported != null && !java.util.Objects.equals(settingsEntry.mIsHomeSupported, this.mIsHomeSupported)) {
                    this.mIsHomeSupported = settingsEntry.mIsHomeSupported;
                    z = true;
                }
                if (settingsEntry.mImePolicy != null && !java.util.Objects.equals(settingsEntry.mImePolicy, this.mImePolicy)) {
                    this.mImePolicy = settingsEntry.mImePolicy;
                    z = true;
                }
                if (settingsEntry.mFixedToUserRotation != null && !java.util.Objects.equals(settingsEntry.mFixedToUserRotation, this.mFixedToUserRotation)) {
                    this.mFixedToUserRotation = settingsEntry.mFixedToUserRotation;
                    z = true;
                }
                if (settingsEntry.mIgnoreOrientationRequest != null && !java.util.Objects.equals(settingsEntry.mIgnoreOrientationRequest, this.mIgnoreOrientationRequest)) {
                    this.mIgnoreOrientationRequest = settingsEntry.mIgnoreOrientationRequest;
                    z = true;
                }
                if (settingsEntry.mIgnoreDisplayCutout != null && !java.util.Objects.equals(settingsEntry.mIgnoreDisplayCutout, this.mIgnoreDisplayCutout)) {
                    this.mIgnoreDisplayCutout = settingsEntry.mIgnoreDisplayCutout;
                    z = true;
                }
                if (settingsEntry.mDontMoveToTop != null && !java.util.Objects.equals(settingsEntry.mDontMoveToTop, this.mDontMoveToTop)) {
                    this.mDontMoveToTop = settingsEntry.mDontMoveToTop;
                    return true;
                }
                return z;
            }

            boolean isEmpty() {
                return this.mWindowingMode == 0 && this.mUserRotationMode == null && this.mUserRotation == null && this.mForcedWidth == 0 && this.mForcedHeight == 0 && this.mForcedDensity == 0 && this.mForcedScalingMode == null && this.mRemoveContentMode == 0 && this.mShouldShowWithInsecureKeyguard == null && this.mShouldShowSystemDecors == null && this.mIsHomeSupported == null && this.mImePolicy == null && this.mFixedToUserRotation == null && this.mIgnoreOrientationRequest == null && this.mIgnoreDisplayCutout == null && this.mDontMoveToTop == null;
            }

            public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry = (com.android.server.wm.DisplayWindowSettings.SettingsProvider.SettingsEntry) obj;
                if (this.mWindowingMode == settingsEntry.mWindowingMode && this.mForcedWidth == settingsEntry.mForcedWidth && this.mForcedHeight == settingsEntry.mForcedHeight && this.mForcedDensity == settingsEntry.mForcedDensity && this.mRemoveContentMode == settingsEntry.mRemoveContentMode && java.util.Objects.equals(this.mUserRotationMode, settingsEntry.mUserRotationMode) && java.util.Objects.equals(this.mUserRotation, settingsEntry.mUserRotation) && java.util.Objects.equals(this.mForcedScalingMode, settingsEntry.mForcedScalingMode) && java.util.Objects.equals(this.mShouldShowWithInsecureKeyguard, settingsEntry.mShouldShowWithInsecureKeyguard) && java.util.Objects.equals(this.mShouldShowSystemDecors, settingsEntry.mShouldShowSystemDecors) && java.util.Objects.equals(this.mIsHomeSupported, settingsEntry.mIsHomeSupported) && java.util.Objects.equals(this.mImePolicy, settingsEntry.mImePolicy) && java.util.Objects.equals(this.mFixedToUserRotation, settingsEntry.mFixedToUserRotation) && java.util.Objects.equals(this.mIgnoreOrientationRequest, settingsEntry.mIgnoreOrientationRequest) && java.util.Objects.equals(this.mIgnoreDisplayCutout, settingsEntry.mIgnoreDisplayCutout) && java.util.Objects.equals(this.mDontMoveToTop, settingsEntry.mDontMoveToTop)) {
                    return true;
                }
                return false;
            }

            public int hashCode() {
                return java.util.Objects.hash(java.lang.Integer.valueOf(this.mWindowingMode), this.mUserRotationMode, this.mUserRotation, java.lang.Integer.valueOf(this.mForcedWidth), java.lang.Integer.valueOf(this.mForcedHeight), java.lang.Integer.valueOf(this.mForcedDensity), this.mForcedScalingMode, java.lang.Integer.valueOf(this.mRemoveContentMode), this.mShouldShowWithInsecureKeyguard, this.mShouldShowSystemDecors, this.mIsHomeSupported, this.mImePolicy, this.mFixedToUserRotation, this.mIgnoreOrientationRequest, this.mIgnoreDisplayCutout, this.mDontMoveToTop);
            }

            public java.lang.String toString() {
                return "SettingsEntry{mWindowingMode=" + this.mWindowingMode + ", mUserRotationMode=" + this.mUserRotationMode + ", mUserRotation=" + this.mUserRotation + ", mForcedWidth=" + this.mForcedWidth + ", mForcedHeight=" + this.mForcedHeight + ", mForcedDensity=" + this.mForcedDensity + ", mForcedScalingMode=" + this.mForcedScalingMode + ", mRemoveContentMode=" + this.mRemoveContentMode + ", mShouldShowWithInsecureKeyguard=" + this.mShouldShowWithInsecureKeyguard + ", mShouldShowSystemDecors=" + this.mShouldShowSystemDecors + ", mIsHomeSupported=" + this.mIsHomeSupported + ", mShouldShowIme=" + this.mImePolicy + ", mFixedToUserRotation=" + this.mFixedToUserRotation + ", mIgnoreOrientationRequest=" + this.mIgnoreOrientationRequest + ", mIgnoreDisplayCutout=" + this.mIgnoreDisplayCutout + ", mDontMoveToTop=" + this.mDontMoveToTop + '}';
            }
        }
    }
}
