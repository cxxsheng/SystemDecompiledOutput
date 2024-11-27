package com.android.server.display.layout;

/* loaded from: classes.dex */
public class Layout {
    public static final java.lang.String DEFAULT_DISPLAY_GROUP_NAME = "";
    public static final int NO_LEAD_DISPLAY = -1;
    private static final java.lang.String TAG = "Layout";
    private final java.util.List<com.android.server.display.layout.Layout.Display> mDisplays = new java.util.ArrayList(2);

    public java.lang.String toString() {
        return this.mDisplays.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.server.display.layout.Layout)) {
            return false;
        }
        return this.mDisplays.equals(((com.android.server.display.layout.Layout) obj).mDisplays);
    }

    public int hashCode() {
        return this.mDisplays.hashCode();
    }

    public void createDefaultDisplayLocked(@android.annotation.NonNull android.view.DisplayAddress displayAddress, com.android.server.display.layout.DisplayIdProducer displayIdProducer) {
        createDisplayLocked(displayAddress, true, true, "", displayIdProducer, -1, null, null, null, null, null);
    }

    public void createDisplayLocked(@android.annotation.NonNull android.view.DisplayAddress displayAddress, boolean z, boolean z2, java.lang.String str, com.android.server.display.layout.DisplayIdProducer displayIdProducer, int i, @android.annotation.Nullable android.view.DisplayAddress displayAddress2, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, @android.annotation.Nullable java.lang.String str4, @android.annotation.Nullable java.lang.String str5) {
        if (contains(displayAddress)) {
            android.util.Slog.w(TAG, "Attempting to add second definition for display-device: " + displayAddress);
            return;
        }
        if (z && getById(0) != null) {
            android.util.Slog.w(TAG, "Ignoring attempt to add a second default display: " + displayAddress);
            return;
        }
        java.lang.String str6 = str == null ? "" : str;
        if (z && !str6.equals("")) {
            throw new java.lang.IllegalArgumentException("Default display should own DEFAULT_DISPLAY_GROUP");
        }
        if (z && displayAddress2 != null) {
            throw new java.lang.IllegalArgumentException("Default display cannot have a lead display");
        }
        if (displayAddress.equals(displayAddress2)) {
            throw new java.lang.IllegalArgumentException("Lead display address cannot be the same as display  address");
        }
        this.mDisplays.add(new com.android.server.display.layout.Layout.Display(displayAddress, displayIdProducer.getId(z), z2, str6, str2, i, displayAddress2, str3, str4, str5));
    }

    public void removeDisplayLocked(int i) {
        com.android.server.display.layout.Layout.Display byId = getById(i);
        if (byId != null) {
            this.mDisplays.remove(byId);
        }
    }

    public void postProcessLocked() {
        for (int i = 0; i < this.mDisplays.size(); i++) {
            com.android.server.display.layout.Layout.Display display = this.mDisplays.get(i);
            if (display.getLogicalDisplayId() == 0) {
                display.setLeadDisplayId(-1);
            } else {
                android.view.DisplayAddress leadDisplayAddress = display.getLeadDisplayAddress();
                if (leadDisplayAddress == null) {
                    display.setLeadDisplayId(-1);
                } else {
                    com.android.server.display.layout.Layout.Display byAddress = getByAddress(leadDisplayAddress);
                    if (byAddress == null) {
                        throw new java.lang.IllegalArgumentException("Cannot find a lead display whose address is " + leadDisplayAddress);
                    }
                    if (!android.text.TextUtils.equals(display.getDisplayGroupName(), byAddress.getDisplayGroupName())) {
                        throw new java.lang.IllegalArgumentException("Lead display(" + byAddress + ") should be in the same display group of the display(" + display + ")");
                    }
                    if (hasCyclicLeadDisplay(display)) {
                        throw new java.lang.IllegalArgumentException("Display(" + display + ") has a cyclic lead display");
                    }
                    display.setLeadDisplayId(byAddress.getLogicalDisplayId());
                }
            }
        }
    }

    public boolean contains(@android.annotation.NonNull android.view.DisplayAddress displayAddress) {
        return getByAddress(displayAddress) != null;
    }

    @android.annotation.Nullable
    public com.android.server.display.layout.Layout.Display getById(int i) {
        for (int i2 = 0; i2 < this.mDisplays.size(); i2++) {
            com.android.server.display.layout.Layout.Display display = this.mDisplays.get(i2);
            if (i == display.getLogicalDisplayId()) {
                return display;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    public com.android.server.display.layout.Layout.Display getByAddress(@android.annotation.NonNull android.view.DisplayAddress displayAddress) {
        for (int i = 0; i < this.mDisplays.size(); i++) {
            com.android.server.display.layout.Layout.Display display = this.mDisplays.get(i);
            if (displayAddress.equals(display.getAddress())) {
                return display;
            }
            if (android.view.DisplayAddress.Physical.isPortMatch(displayAddress, display.getAddress())) {
                return display;
            }
        }
        return null;
    }

    public com.android.server.display.layout.Layout.Display getAt(int i) {
        return this.mDisplays.get(i);
    }

    public int size() {
        return this.mDisplays.size();
    }

    private boolean hasCyclicLeadDisplay(com.android.server.display.layout.Layout.Display display) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        while (display != null) {
            if (arraySet.contains(display)) {
                return true;
            }
            arraySet.add(display);
            android.view.DisplayAddress leadDisplayAddress = display.getLeadDisplayAddress();
            display = leadDisplayAddress == null ? null : getByAddress(leadDisplayAddress);
        }
        return false;
    }

    public static class Display {
        public static final int POSITION_FRONT = 0;
        public static final int POSITION_REAR = 1;
        public static final int POSITION_UNKNOWN = -1;
        private final android.view.DisplayAddress mAddress;
        private final java.lang.String mDisplayGroupName;
        private final boolean mIsEnabled;

        @android.annotation.Nullable
        private final android.view.DisplayAddress mLeadDisplayAddress;
        private int mLeadDisplayId;
        private final int mLogicalDisplayId;
        private final int mPosition;

        @android.annotation.Nullable
        private final java.lang.String mPowerThrottlingMapId;

        @android.annotation.Nullable
        private final java.lang.String mRefreshRateZoneId;

        @android.annotation.Nullable
        private final java.lang.String mThermalBrightnessThrottlingMapId;

        @android.annotation.Nullable
        private final java.lang.String mThermalRefreshRateThrottlingMapId;

        private Display(@android.annotation.NonNull android.view.DisplayAddress displayAddress, int i, boolean z, @android.annotation.NonNull java.lang.String str, java.lang.String str2, int i2, @android.annotation.Nullable android.view.DisplayAddress displayAddress2, @android.annotation.Nullable java.lang.String str3, @android.annotation.Nullable java.lang.String str4, @android.annotation.Nullable java.lang.String str5) {
            this.mAddress = displayAddress;
            this.mLogicalDisplayId = i;
            this.mIsEnabled = z;
            this.mDisplayGroupName = str;
            this.mPosition = i2;
            this.mThermalBrightnessThrottlingMapId = str2;
            this.mLeadDisplayAddress = displayAddress2;
            this.mRefreshRateZoneId = str3;
            this.mThermalRefreshRateThrottlingMapId = str4;
            this.mPowerThrottlingMapId = str5;
            this.mLeadDisplayId = -1;
        }

        public java.lang.String toString() {
            java.lang.String str;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{dispId: ");
            sb.append(this.mLogicalDisplayId);
            sb.append("(");
            sb.append(this.mIsEnabled ? "ON" : "OFF");
            sb.append("), displayGroupName: ");
            sb.append(this.mDisplayGroupName);
            sb.append(", addr: ");
            sb.append(this.mAddress);
            if (this.mPosition == -1) {
                str = "";
            } else {
                str = ", position: " + this.mPosition;
            }
            sb.append(str);
            sb.append(", mThermalBrightnessThrottlingMapId: ");
            sb.append(this.mThermalBrightnessThrottlingMapId);
            sb.append(", mRefreshRateZoneId: ");
            sb.append(this.mRefreshRateZoneId);
            sb.append(", mLeadDisplayId: ");
            sb.append(this.mLeadDisplayId);
            sb.append(", mLeadDisplayAddress: ");
            sb.append(this.mLeadDisplayAddress);
            sb.append(", mThermalRefreshRateThrottlingMapId: ");
            sb.append(this.mThermalRefreshRateThrottlingMapId);
            sb.append(", mPowerThrottlingMapId: ");
            sb.append(this.mPowerThrottlingMapId);
            sb.append("}");
            return sb.toString();
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.display.layout.Layout.Display)) {
                return false;
            }
            com.android.server.display.layout.Layout.Display display = (com.android.server.display.layout.Layout.Display) obj;
            return display.mIsEnabled == this.mIsEnabled && display.mPosition == this.mPosition && display.mLogicalDisplayId == this.mLogicalDisplayId && this.mDisplayGroupName.equals(display.mDisplayGroupName) && this.mAddress.equals(display.mAddress) && java.util.Objects.equals(this.mThermalBrightnessThrottlingMapId, display.mThermalBrightnessThrottlingMapId) && java.util.Objects.equals(display.mRefreshRateZoneId, this.mRefreshRateZoneId) && this.mLeadDisplayId == display.mLeadDisplayId && java.util.Objects.equals(this.mLeadDisplayAddress, display.mLeadDisplayAddress) && java.util.Objects.equals(this.mThermalRefreshRateThrottlingMapId, display.mThermalRefreshRateThrottlingMapId) && java.util.Objects.equals(this.mPowerThrottlingMapId, display.mPowerThrottlingMapId);
        }

        public int hashCode() {
            return ((((((((((((((((((((java.lang.Boolean.hashCode(this.mIsEnabled) + 31) * 31) + this.mPosition) * 31) + this.mLogicalDisplayId) * 31) + this.mDisplayGroupName.hashCode()) * 31) + this.mAddress.hashCode()) * 31) + java.util.Objects.hashCode(this.mThermalBrightnessThrottlingMapId)) * 31) + java.util.Objects.hashCode(this.mRefreshRateZoneId)) * 31) + this.mLeadDisplayId) * 31) + java.util.Objects.hashCode(this.mLeadDisplayAddress)) * 31) + java.util.Objects.hashCode(this.mThermalRefreshRateThrottlingMapId)) * 31) + java.util.Objects.hashCode(this.mPowerThrottlingMapId);
        }

        public android.view.DisplayAddress getAddress() {
            return this.mAddress;
        }

        public int getLogicalDisplayId() {
            return this.mLogicalDisplayId;
        }

        public boolean isEnabled() {
            return this.mIsEnabled;
        }

        public java.lang.String getDisplayGroupName() {
            return this.mDisplayGroupName;
        }

        @android.annotation.Nullable
        public java.lang.String getRefreshRateZoneId() {
            return this.mRefreshRateZoneId;
        }

        public java.lang.String getThermalBrightnessThrottlingMapId() {
            return this.mThermalBrightnessThrottlingMapId;
        }

        public int getPosition() {
            return this.mPosition;
        }

        public int getLeadDisplayId() {
            return this.mLeadDisplayId;
        }

        @android.annotation.Nullable
        public android.view.DisplayAddress getLeadDisplayAddress() {
            return this.mLeadDisplayAddress;
        }

        public java.lang.String getRefreshRateThermalThrottlingMapId() {
            return this.mThermalRefreshRateThrottlingMapId;
        }

        public java.lang.String getPowerThrottlingMapId() {
            return this.mPowerThrottlingMapId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLeadDisplayId(int i) {
            this.mLeadDisplayId = i;
        }
    }
}
