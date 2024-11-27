package com.android.server.timedetector;

/* loaded from: classes2.dex */
public final class ConfigurationInternal {
    private final boolean mAutoDetectionEnabledSetting;
    private final boolean mAutoDetectionSupported;
    private final java.time.Instant mAutoSuggestionLowerBound;
    private final java.time.Instant mManualSuggestionLowerBound;
    private final int[] mOriginPriorities;
    private final java.time.Instant mSuggestionUpperBound;
    private final int mSystemClockConfidenceThresholdMillis;
    private final int mSystemClockUpdateThresholdMillis;
    private final boolean mUserConfigAllowed;
    private final int mUserId;

    private ConfigurationInternal(com.android.server.timedetector.ConfigurationInternal.Builder builder) {
        this.mAutoDetectionSupported = builder.mAutoDetectionSupported;
        this.mSystemClockUpdateThresholdMillis = builder.mSystemClockUpdateThresholdMillis;
        this.mSystemClockConfidenceThresholdMillis = builder.mSystemClockConfidenceThresholdMillis;
        java.time.Instant instant = builder.mAutoSuggestionLowerBound;
        java.util.Objects.requireNonNull(instant);
        this.mAutoSuggestionLowerBound = instant;
        java.time.Instant instant2 = builder.mManualSuggestionLowerBound;
        java.util.Objects.requireNonNull(instant2);
        this.mManualSuggestionLowerBound = instant2;
        java.time.Instant instant3 = builder.mSuggestionUpperBound;
        java.util.Objects.requireNonNull(instant3);
        this.mSuggestionUpperBound = instant3;
        int[] iArr = builder.mOriginPriorities;
        java.util.Objects.requireNonNull(iArr);
        this.mOriginPriorities = iArr;
        this.mAutoDetectionEnabledSetting = builder.mAutoDetectionEnabledSetting;
        this.mUserId = builder.mUserId;
        this.mUserConfigAllowed = builder.mUserConfigAllowed;
    }

    public boolean isAutoDetectionSupported() {
        return this.mAutoDetectionSupported;
    }

    public int getSystemClockUpdateThresholdMillis() {
        return this.mSystemClockUpdateThresholdMillis;
    }

    public int getSystemClockConfidenceThresholdMillis() {
        return this.mSystemClockConfidenceThresholdMillis;
    }

    @android.annotation.NonNull
    public java.time.Instant getAutoSuggestionLowerBound() {
        return this.mAutoSuggestionLowerBound;
    }

    @android.annotation.NonNull
    public java.time.Instant getManualSuggestionLowerBound() {
        return this.mManualSuggestionLowerBound;
    }

    @android.annotation.NonNull
    public java.time.Instant getSuggestionUpperBound() {
        return this.mSuggestionUpperBound;
    }

    public int[] getAutoOriginPriorities() {
        return this.mOriginPriorities;
    }

    public boolean getAutoDetectionEnabledSetting() {
        return this.mAutoDetectionEnabledSetting;
    }

    public boolean getAutoDetectionEnabledBehavior() {
        return isAutoDetectionSupported() && this.mAutoDetectionEnabledSetting;
    }

    public int getUserId() {
        return this.mUserId;
    }

    @android.annotation.NonNull
    public android.os.UserHandle getUserHandle() {
        return android.os.UserHandle.of(this.mUserId);
    }

    public boolean isUserConfigAllowed() {
        return this.mUserConfigAllowed;
    }

    public android.app.time.TimeCapabilitiesAndConfig createCapabilitiesAndConfig(boolean z) {
        return new android.app.time.TimeCapabilitiesAndConfig(timeCapabilities(z), timeConfiguration());
    }

    private android.app.time.TimeCapabilities timeCapabilities(boolean z) {
        int i;
        android.app.time.TimeCapabilities.Builder builder = new android.app.time.TimeCapabilities.Builder(android.os.UserHandle.of(this.mUserId));
        boolean z2 = isUserConfigAllowed() || z;
        int i2 = 40;
        if (!isAutoDetectionSupported()) {
            i = 10;
        } else if (!z2) {
            i = 20;
        } else {
            i = 40;
        }
        builder.setConfigureAutoDetectionEnabledCapability(i);
        if (!z2) {
            i2 = 20;
        } else if (getAutoDetectionEnabledBehavior()) {
            i2 = 30;
        }
        builder.setSetManualTimeCapability(i2);
        return builder.build();
    }

    private android.app.time.TimeConfiguration timeConfiguration() {
        return new android.app.time.TimeConfiguration.Builder().setAutoDetectionEnabled(getAutoDetectionEnabledSetting()).build();
    }

    public com.android.server.timedetector.ConfigurationInternal merge(android.app.time.TimeConfiguration timeConfiguration) {
        com.android.server.timedetector.ConfigurationInternal.Builder builder = new com.android.server.timedetector.ConfigurationInternal.Builder(this);
        if (timeConfiguration.hasIsAutoDetectionEnabled()) {
            builder.setAutoDetectionEnabledSetting(timeConfiguration.isAutoDetectionEnabled());
        }
        return builder.build();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.timedetector.ConfigurationInternal)) {
            return false;
        }
        com.android.server.timedetector.ConfigurationInternal configurationInternal = (com.android.server.timedetector.ConfigurationInternal) obj;
        return this.mAutoDetectionSupported == configurationInternal.mAutoDetectionSupported && this.mAutoDetectionEnabledSetting == configurationInternal.mAutoDetectionEnabledSetting && this.mUserId == configurationInternal.mUserId && this.mUserConfigAllowed == configurationInternal.mUserConfigAllowed && this.mSystemClockUpdateThresholdMillis == configurationInternal.mSystemClockUpdateThresholdMillis && this.mSystemClockConfidenceThresholdMillis == configurationInternal.mSystemClockConfidenceThresholdMillis && this.mAutoSuggestionLowerBound.equals(configurationInternal.mAutoSuggestionLowerBound) && this.mManualSuggestionLowerBound.equals(configurationInternal.mManualSuggestionLowerBound) && this.mSuggestionUpperBound.equals(configurationInternal.mSuggestionUpperBound) && java.util.Arrays.equals(this.mOriginPriorities, configurationInternal.mOriginPriorities);
    }

    public int hashCode() {
        return (java.util.Objects.hash(java.lang.Boolean.valueOf(this.mAutoDetectionSupported), java.lang.Boolean.valueOf(this.mAutoDetectionEnabledSetting), java.lang.Integer.valueOf(this.mUserId), java.lang.Boolean.valueOf(this.mUserConfigAllowed), java.lang.Integer.valueOf(this.mSystemClockUpdateThresholdMillis), java.lang.Integer.valueOf(this.mSystemClockConfidenceThresholdMillis), this.mAutoSuggestionLowerBound, this.mManualSuggestionLowerBound, this.mSuggestionUpperBound) * 31) + java.util.Arrays.hashCode(this.mOriginPriorities);
    }

    public java.lang.String toString() {
        return "ConfigurationInternal{mAutoDetectionSupported=" + this.mAutoDetectionSupported + ", mSystemClockUpdateThresholdMillis=" + this.mSystemClockUpdateThresholdMillis + ", mSystemClockConfidenceThresholdMillis=" + this.mSystemClockConfidenceThresholdMillis + ", mAutoSuggestionLowerBound=" + this.mAutoSuggestionLowerBound + "(" + this.mAutoSuggestionLowerBound.toEpochMilli() + "), mManualSuggestionLowerBound=" + this.mManualSuggestionLowerBound + "(" + this.mManualSuggestionLowerBound.toEpochMilli() + "), mSuggestionUpperBound=" + this.mSuggestionUpperBound + "(" + this.mSuggestionUpperBound.toEpochMilli() + "), mOriginPriorities=" + ((java.lang.String) java.util.Arrays.stream(this.mOriginPriorities).mapToObj(new java.util.function.IntFunction() { // from class: com.android.server.timedetector.ConfigurationInternal$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                return com.android.server.timedetector.TimeDetectorStrategy.originToString(i);
            }
        }).collect(java.util.stream.Collectors.joining(",", "[", "]"))) + ", mAutoDetectionEnabled=" + this.mAutoDetectionEnabledSetting + ", mUserId=" + this.mUserId + ", mUserConfigAllowed=" + this.mUserConfigAllowed + '}';
    }

    static final class Builder {
        private boolean mAutoDetectionEnabledSetting;
        private boolean mAutoDetectionSupported;

        @android.annotation.NonNull
        private java.time.Instant mAutoSuggestionLowerBound;

        @android.annotation.NonNull
        private java.time.Instant mManualSuggestionLowerBound;

        @android.annotation.NonNull
        private int[] mOriginPriorities;

        @android.annotation.NonNull
        private java.time.Instant mSuggestionUpperBound;
        private int mSystemClockConfidenceThresholdMillis;
        private int mSystemClockUpdateThresholdMillis;
        private boolean mUserConfigAllowed;
        private final int mUserId;

        Builder(int i) {
            this.mUserId = i;
        }

        Builder(com.android.server.timedetector.ConfigurationInternal configurationInternal) {
            this.mUserId = configurationInternal.mUserId;
            this.mUserConfigAllowed = configurationInternal.mUserConfigAllowed;
            this.mAutoDetectionSupported = configurationInternal.mAutoDetectionSupported;
            this.mSystemClockUpdateThresholdMillis = configurationInternal.mSystemClockUpdateThresholdMillis;
            this.mAutoSuggestionLowerBound = configurationInternal.mAutoSuggestionLowerBound;
            this.mManualSuggestionLowerBound = configurationInternal.mManualSuggestionLowerBound;
            this.mSuggestionUpperBound = configurationInternal.mSuggestionUpperBound;
            this.mOriginPriorities = configurationInternal.mOriginPriorities;
            this.mAutoDetectionEnabledSetting = configurationInternal.mAutoDetectionEnabledSetting;
        }

        com.android.server.timedetector.ConfigurationInternal.Builder setUserConfigAllowed(boolean z) {
            this.mUserConfigAllowed = z;
            return this;
        }

        public com.android.server.timedetector.ConfigurationInternal.Builder setAutoDetectionSupported(boolean z) {
            this.mAutoDetectionSupported = z;
            return this;
        }

        public com.android.server.timedetector.ConfigurationInternal.Builder setSystemClockUpdateThresholdMillis(int i) {
            this.mSystemClockUpdateThresholdMillis = i;
            return this;
        }

        public com.android.server.timedetector.ConfigurationInternal.Builder setSystemClockConfidenceThresholdMillis(int i) {
            this.mSystemClockConfidenceThresholdMillis = i;
            return this;
        }

        public com.android.server.timedetector.ConfigurationInternal.Builder setAutoSuggestionLowerBound(@android.annotation.NonNull java.time.Instant instant) {
            java.util.Objects.requireNonNull(instant);
            this.mAutoSuggestionLowerBound = instant;
            return this;
        }

        public com.android.server.timedetector.ConfigurationInternal.Builder setManualSuggestionLowerBound(@android.annotation.NonNull java.time.Instant instant) {
            java.util.Objects.requireNonNull(instant);
            this.mManualSuggestionLowerBound = instant;
            return this;
        }

        public com.android.server.timedetector.ConfigurationInternal.Builder setSuggestionUpperBound(@android.annotation.NonNull java.time.Instant instant) {
            java.util.Objects.requireNonNull(instant);
            this.mSuggestionUpperBound = instant;
            return this;
        }

        public com.android.server.timedetector.ConfigurationInternal.Builder setOriginPriorities(@android.annotation.NonNull int... iArr) {
            java.util.Objects.requireNonNull(iArr);
            this.mOriginPriorities = iArr;
            return this;
        }

        com.android.server.timedetector.ConfigurationInternal.Builder setAutoDetectionEnabledSetting(boolean z) {
            this.mAutoDetectionEnabledSetting = z;
            return this;
        }

        @android.annotation.NonNull
        com.android.server.timedetector.ConfigurationInternal build() {
            return new com.android.server.timedetector.ConfigurationInternal(this);
        }
    }
}
