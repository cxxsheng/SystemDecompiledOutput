package com.android.server.display;

/* loaded from: classes.dex */
class DeviceStateToLayoutMap {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final java.lang.String CONFIG_FILE_PATH = "etc/displayconfig/display_layout_configuration.xml";
    private static final java.lang.String DATA_CONFIG_FILE_PATH = "system/displayconfig/display_layout_configuration.xml";
    private static final java.lang.String FRONT_STRING = "front";
    private static final int POSITION_FRONT = 0;
    private static final int POSITION_REAR = 1;
    private static final int POSITION_UNKNOWN = -1;
    private static final java.lang.String REAR_STRING = "rear";
    public static final int STATE_DEFAULT = -1;
    private static final java.lang.String TAG = "DeviceStateToLayoutMap";
    private final com.android.server.display.layout.DisplayIdProducer mIdProducer;
    private final boolean mIsPortInDisplayLayoutEnabled;
    private final android.util.SparseArray<com.android.server.display.layout.Layout> mLayoutMap;

    DeviceStateToLayoutMap(com.android.server.display.layout.DisplayIdProducer displayIdProducer, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this(displayIdProducer, displayManagerFlags, getConfigFile());
    }

    DeviceStateToLayoutMap(com.android.server.display.layout.DisplayIdProducer displayIdProducer, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, java.io.File file) {
        this.mLayoutMap = new android.util.SparseArray<>();
        this.mIsPortInDisplayLayoutEnabled = displayManagerFlags.isPortInDisplayLayoutEnabled();
        this.mIdProducer = displayIdProducer;
        loadLayoutsFromConfig(file);
        createLayout(-1);
    }

    private static java.io.File getConfigFile() {
        java.io.File buildPath = android.os.Environment.buildPath(android.os.Environment.getDataDirectory(), new java.lang.String[]{DATA_CONFIG_FILE_PATH});
        if (buildPath.exists()) {
            return buildPath;
        }
        return android.os.Environment.buildPath(android.os.Environment.getVendorDirectory(), new java.lang.String[]{CONFIG_FILE_PATH});
    }

    public void dumpLocked(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("DeviceStateToLayoutMap:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mIsPortInDisplayLayoutEnabled=" + this.mIsPortInDisplayLayoutEnabled);
        indentingPrintWriter.println("Registered Layouts:");
        for (int i = 0; i < this.mLayoutMap.size(); i++) {
            indentingPrintWriter.println("state(" + this.mLayoutMap.keyAt(i) + "): " + this.mLayoutMap.valueAt(i));
        }
    }

    com.android.server.display.layout.Layout get(int i) {
        com.android.server.display.layout.Layout layout = this.mLayoutMap.get(i);
        if (layout == null) {
            return this.mLayoutMap.get(-1);
        }
        return layout;
    }

    int size() {
        return this.mLayoutMap.size();
    }

    @com.android.internal.annotations.VisibleForTesting
    void loadLayoutsFromConfig(@android.annotation.NonNull java.io.File file) {
        if (!file.exists()) {
            return;
        }
        android.util.Slog.i(TAG, "Loading display layouts from " + file);
        try {
            java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
            try {
                com.android.server.display.config.layout.Layouts read = com.android.server.display.config.layout.XmlParser.read(bufferedInputStream);
                if (read == null) {
                    android.util.Slog.i(TAG, "Display layout config not found: " + file);
                    bufferedInputStream.close();
                    return;
                }
                for (com.android.server.display.config.layout.Layout layout : read.getLayout()) {
                    com.android.server.display.layout.Layout createLayout = createLayout(layout.getState().intValue());
                    for (com.android.server.display.config.layout.Display display : layout.getDisplay()) {
                        android.view.DisplayAddress displayAddressForLayoutDisplay = getDisplayAddressForLayoutDisplay(display);
                        int position = getPosition(display.getPosition());
                        java.math.BigInteger leadDisplayAddress = display.getLeadDisplayAddress();
                        createLayout.createDisplayLocked(displayAddressForLayoutDisplay, display.isDefaultDisplay(), display.isEnabled(), display.getDisplayGroup(), this.mIdProducer, position, leadDisplayAddress == null ? null : android.view.DisplayAddress.fromPhysicalDisplayId(leadDisplayAddress.longValue()), display.getBrightnessThrottlingMapId(), display.getRefreshRateZoneId(), display.getRefreshRateThermalThrottlingMapId(), display.getPowerThrottlingMapId());
                    }
                    createLayout.postProcessLocked();
                }
                bufferedInputStream.close();
            } finally {
            }
        } catch (java.io.IOException | javax.xml.datatype.DatatypeConfigurationException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Encountered an error while reading/parsing display layout config file: " + file, e);
        }
    }

    private android.view.DisplayAddress getDisplayAddressForLayoutDisplay(@android.annotation.NonNull com.android.server.display.config.layout.Display display) {
        java.math.BigInteger address_optional = display.getAddress_optional();
        if (address_optional != null) {
            return android.view.DisplayAddress.fromPhysicalDisplayId(address_optional.longValue());
        }
        if (!this.mIsPortInDisplayLayoutEnabled || display.getPort_optional() == null) {
            throw new java.lang.IllegalArgumentException("Must specify a display identifier in display layout configuration: " + display);
        }
        return android.view.DisplayAddress.fromPortAndModel((int) display.getPort_optional().longValue(), (java.lang.Long) null);
    }

    private int getPosition(@android.annotation.NonNull java.lang.String str) {
        if (FRONT_STRING.equals(str)) {
            return 0;
        }
        if (!REAR_STRING.equals(str)) {
            return -1;
        }
        return 1;
    }

    private com.android.server.display.layout.Layout createLayout(int i) {
        if (this.mLayoutMap.contains(i)) {
            android.util.Slog.e(TAG, "Attempted to create a second layout for state " + i);
            return null;
        }
        com.android.server.display.layout.Layout layout = new com.android.server.display.layout.Layout();
        this.mLayoutMap.append(i, layout);
        return layout;
    }
}
