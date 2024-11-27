package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
final class LocationTimeZoneManagerServiceState {
    private final java.lang.String mControllerState;

    @android.annotation.NonNull
    private final java.util.List<java.lang.String> mControllerStates;

    @android.annotation.Nullable
    private final com.android.server.timezonedetector.LocationAlgorithmEvent mLastEvent;

    @android.annotation.NonNull
    private final java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> mPrimaryProviderStates;

    @android.annotation.NonNull
    private final java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> mSecondaryProviderStates;

    LocationTimeZoneManagerServiceState(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState.Builder builder) {
        this.mControllerState = builder.mControllerState;
        this.mLastEvent = builder.mLastEvent;
        java.util.List<java.lang.String> list = builder.mControllerStates;
        java.util.Objects.requireNonNull(list);
        this.mControllerStates = list;
        java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> list2 = builder.mPrimaryProviderStates;
        java.util.Objects.requireNonNull(list2);
        this.mPrimaryProviderStates = list2;
        java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> list3 = builder.mSecondaryProviderStates;
        java.util.Objects.requireNonNull(list3);
        this.mSecondaryProviderStates = list3;
    }

    public java.lang.String getControllerState() {
        return this.mControllerState;
    }

    @android.annotation.Nullable
    public com.android.server.timezonedetector.LocationAlgorithmEvent getLastEvent() {
        return this.mLastEvent;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> getControllerStates() {
        return this.mControllerStates;
    }

    @android.annotation.NonNull
    public java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> getPrimaryProviderStates() {
        return java.util.Collections.unmodifiableList(this.mPrimaryProviderStates);
    }

    @android.annotation.NonNull
    public java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> getSecondaryProviderStates() {
        return java.util.Collections.unmodifiableList(this.mSecondaryProviderStates);
    }

    public java.lang.String toString() {
        return "LocationTimeZoneManagerServiceState{mControllerState=" + this.mControllerState + ", mLastEvent=" + this.mLastEvent + ", mControllerStates=" + this.mControllerStates + ", mPrimaryProviderStates=" + this.mPrimaryProviderStates + ", mSecondaryProviderStates=" + this.mSecondaryProviderStates + '}';
    }

    static final class Builder {
        private java.lang.String mControllerState;
        private java.util.List<java.lang.String> mControllerStates;
        private com.android.server.timezonedetector.LocationAlgorithmEvent mLastEvent;
        private java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> mPrimaryProviderStates;
        private java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> mSecondaryProviderStates;

        Builder() {
        }

        @android.annotation.NonNull
        public com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState.Builder setControllerState(java.lang.String str) {
            this.mControllerState = str;
            return this;
        }

        @android.annotation.NonNull
        com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState.Builder setLastEvent(@android.annotation.NonNull com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
            java.util.Objects.requireNonNull(locationAlgorithmEvent);
            this.mLastEvent = locationAlgorithmEvent;
            return this;
        }

        @android.annotation.NonNull
        public com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState.Builder setStateChanges(@android.annotation.NonNull java.util.List<java.lang.String> list) {
            this.mControllerStates = new java.util.ArrayList(list);
            return this;
        }

        @android.annotation.NonNull
        com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState.Builder setPrimaryProviderStateChanges(@android.annotation.NonNull java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> list) {
            this.mPrimaryProviderStates = new java.util.ArrayList(list);
            return this;
        }

        @android.annotation.NonNull
        com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState.Builder setSecondaryProviderStateChanges(@android.annotation.NonNull java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> list) {
            this.mSecondaryProviderStates = new java.util.ArrayList(list);
            return this;
        }

        @android.annotation.NonNull
        com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState build() {
            return new com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState(this);
        }
    }
}
