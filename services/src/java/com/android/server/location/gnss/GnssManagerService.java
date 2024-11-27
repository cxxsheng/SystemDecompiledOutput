package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class GnssManagerService {
    private static final java.lang.String ATTRIBUTION_ID = "GnssService";
    private final com.android.server.location.gnss.GnssManagerService.GnssCapabilitiesHalModule mCapabilitiesHalModule;
    final android.content.Context mContext;
    private final com.android.server.location.gnss.GnssManagerService.GnssGeofenceHalModule mGeofenceHalModule;
    private final com.android.server.location.gnss.GnssAntennaInfoProvider mGnssAntennaInfoProvider;
    private final android.location.IGpsGeofenceHardware mGnssGeofenceProxy;
    private final com.android.server.location.gnss.GnssLocationProvider mGnssLocationProvider;
    private final com.android.server.location.gnss.GnssMeasurementsProvider mGnssMeasurementsProvider;
    private final com.android.server.location.gnss.GnssMetrics mGnssMetrics;
    private final com.android.server.location.gnss.hal.GnssNative mGnssNative;
    private final com.android.server.location.gnss.GnssNavigationMessageProvider mGnssNavigationMessageProvider;
    private final com.android.server.location.gnss.GnssNmeaProvider mGnssNmeaProvider;
    private final com.android.server.location.gnss.GnssStatusProvider mGnssStatusProvider;
    public static final java.lang.String TAG = "GnssManager";
    public static final boolean D = android.util.Log.isLoggable(TAG, 3);

    public GnssManagerService(android.content.Context context, com.android.server.location.injector.Injector injector, com.android.server.location.gnss.hal.GnssNative gnssNative) {
        this.mContext = context.createAttributionContext(ATTRIBUTION_ID);
        this.mGnssNative = gnssNative;
        this.mGnssMetrics = new com.android.server.location.gnss.GnssMetrics(this.mContext, com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats")), this.mGnssNative);
        this.mGnssLocationProvider = new com.android.server.location.gnss.GnssLocationProvider(this.mContext, this.mGnssNative, this.mGnssMetrics);
        this.mGnssStatusProvider = new com.android.server.location.gnss.GnssStatusProvider(injector, this.mGnssNative);
        this.mGnssNmeaProvider = new com.android.server.location.gnss.GnssNmeaProvider(injector, this.mGnssNative);
        this.mGnssMeasurementsProvider = new com.android.server.location.gnss.GnssMeasurementsProvider(injector, this.mGnssNative);
        this.mGnssNavigationMessageProvider = new com.android.server.location.gnss.GnssNavigationMessageProvider(injector, this.mGnssNative);
        this.mGnssAntennaInfoProvider = new com.android.server.location.gnss.GnssAntennaInfoProvider(this.mGnssNative);
        this.mGnssGeofenceProxy = new com.android.server.location.gnss.GnssGeofenceProxy(this.mGnssNative);
        this.mGeofenceHalModule = new com.android.server.location.gnss.GnssManagerService.GnssGeofenceHalModule(this.mGnssNative);
        this.mCapabilitiesHalModule = new com.android.server.location.gnss.GnssManagerService.GnssCapabilitiesHalModule(this.mGnssNative);
        this.mGnssNative.register();
    }

    public void onSystemReady() {
        this.mGnssLocationProvider.onSystemReady();
    }

    public com.android.server.location.gnss.GnssLocationProvider getGnssLocationProvider() {
        return this.mGnssLocationProvider;
    }

    public void setAutomotiveGnssSuspended(boolean z) {
        this.mGnssLocationProvider.setAutomotiveGnssSuspended(z);
    }

    public boolean isAutomotiveGnssSuspended() {
        return this.mGnssLocationProvider.isAutomotiveGnssSuspended();
    }

    public android.location.IGpsGeofenceHardware getGnssGeofenceProxy() {
        return this.mGnssGeofenceProxy;
    }

    public int getGnssYearOfHardware() {
        return this.mGnssNative.getHardwareYear();
    }

    @android.annotation.Nullable
    public java.lang.String getGnssHardwareModelName() {
        return this.mGnssNative.getHardwareModelName();
    }

    public android.location.GnssCapabilities getGnssCapabilities() {
        return this.mGnssNative.getCapabilities();
    }

    @android.annotation.Nullable
    public java.util.List<android.location.GnssAntennaInfo> getGnssAntennaInfos() {
        return this.mGnssAntennaInfoProvider.getAntennaInfos();
    }

    public int getGnssBatchSize() {
        return this.mGnssLocationProvider.getBatchSize();
    }

    public void registerGnssStatusCallback(android.location.IGnssStatusListener iGnssStatusListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION", null);
        this.mGnssStatusProvider.addListener(android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str, str2, str3), iGnssStatusListener);
    }

    public void unregisterGnssStatusCallback(android.location.IGnssStatusListener iGnssStatusListener) {
        this.mGnssStatusProvider.removeListener(iGnssStatusListener);
    }

    public void registerGnssNmeaCallback(android.location.IGnssNmeaListener iGnssNmeaListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION", null);
        this.mGnssNmeaProvider.addListener(android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str, str2, str3), iGnssNmeaListener);
    }

    public void unregisterGnssNmeaCallback(android.location.IGnssNmeaListener iGnssNmeaListener) {
        this.mGnssNmeaProvider.removeListener(iGnssNmeaListener);
    }

    public void addGnssMeasurementsListener(android.location.GnssMeasurementRequest gnssMeasurementRequest, android.location.IGnssMeasurementsListener iGnssMeasurementsListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION", null);
        if (gnssMeasurementRequest.isCorrelationVectorOutputsEnabled()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.LOCATION_HARDWARE", null);
        }
        this.mGnssMeasurementsProvider.addListener(gnssMeasurementRequest, android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str, str2, str3), iGnssMeasurementsListener);
    }

    public void injectGnssMeasurementCorrections(android.location.GnssMeasurementCorrections gnssMeasurementCorrections) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.LOCATION_HARDWARE", null);
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION", null);
        if (!this.mGnssNative.injectMeasurementCorrections(gnssMeasurementCorrections)) {
            android.util.Log.w(TAG, "failed to inject GNSS measurement corrections");
        }
    }

    public void removeGnssMeasurementsListener(android.location.IGnssMeasurementsListener iGnssMeasurementsListener) {
        this.mGnssMeasurementsProvider.removeListener(iGnssMeasurementsListener);
    }

    public void addGnssNavigationMessageListener(android.location.IGnssNavigationMessageListener iGnssNavigationMessageListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION", null);
        this.mGnssNavigationMessageProvider.addListener(android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str, str2, str3), iGnssNavigationMessageListener);
    }

    public void removeGnssNavigationMessageListener(android.location.IGnssNavigationMessageListener iGnssNavigationMessageListener) {
        this.mGnssNavigationMessageProvider.removeListener(iGnssNavigationMessageListener);
    }

    public void addGnssAntennaInfoListener(android.location.IGnssAntennaInfoListener iGnssAntennaInfoListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        this.mGnssAntennaInfoProvider.addListener(android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str, str2, str3), iGnssAntennaInfoListener);
    }

    public void removeGnssAntennaInfoListener(android.location.IGnssAntennaInfoListener iGnssAntennaInfoListener) {
        this.mGnssAntennaInfoProvider.removeListener(iGnssAntennaInfoListener);
    }

    public void dump(java.io.FileDescriptor fileDescriptor, android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
        if (strArr.length > 0 && strArr[0].equals("--gnssmetrics")) {
            indentingPrintWriter.append(this.mGnssMetrics.dumpGnssMetricsAsProtoString());
            return;
        }
        indentingPrintWriter.println("Capabilities: " + this.mGnssNative.getCapabilities());
        indentingPrintWriter.println("GNSS Hardware Model Name: " + getGnssHardwareModelName());
        if (this.mGnssStatusProvider.isSupported()) {
            indentingPrintWriter.println("Status Provider:");
            indentingPrintWriter.increaseIndent();
            this.mGnssStatusProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
        }
        if (this.mGnssMeasurementsProvider.isSupported()) {
            indentingPrintWriter.println("Measurements Provider:");
            indentingPrintWriter.increaseIndent();
            this.mGnssMeasurementsProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
        }
        if (this.mGnssNavigationMessageProvider.isSupported()) {
            indentingPrintWriter.println("Navigation Message Provider:");
            indentingPrintWriter.increaseIndent();
            this.mGnssNavigationMessageProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
        }
        if (this.mGnssAntennaInfoProvider.isSupported()) {
            indentingPrintWriter.println("Antenna Info Provider:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Antenna Infos: " + this.mGnssAntennaInfoProvider.getAntennaInfos());
            this.mGnssAntennaInfoProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
        }
        com.android.server.location.gnss.GnssPowerStats powerStats = this.mGnssNative.getPowerStats();
        if (powerStats != null) {
            indentingPrintWriter.println("Last Power Stats:");
            indentingPrintWriter.increaseIndent();
            powerStats.dump(fileDescriptor, indentingPrintWriter, strArr, this.mGnssNative.getCapabilities());
            indentingPrintWriter.decreaseIndent();
        }
    }

    private class GnssCapabilitiesHalModule implements com.android.server.location.gnss.hal.GnssNative.BaseCallbacks {
        GnssCapabilitiesHalModule(com.android.server.location.gnss.hal.GnssNative gnssNative) {
            gnssNative.addBaseCallbacks(this);
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
        public void onHalRestarted() {
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
        public void onCapabilitiesChanged(android.location.GnssCapabilities gnssCapabilities, android.location.GnssCapabilities gnssCapabilities2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.location.gnss.GnssManagerService.this.mContext.sendBroadcastAsUser(new android.content.Intent("android.location.action.GNSS_CAPABILITIES_CHANGED").putExtra("android.location.extra.GNSS_CAPABILITIES", gnssCapabilities2).addFlags(1073741824).addFlags(268435456), android.os.UserHandle.ALL);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class GnssGeofenceHalModule implements com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks {
        private android.hardware.location.GeofenceHardwareImpl mGeofenceHardwareImpl;

        GnssGeofenceHalModule(com.android.server.location.gnss.hal.GnssNative gnssNative) {
            gnssNative.setGeofenceCallbacks(this);
        }

        private synchronized android.hardware.location.GeofenceHardwareImpl getGeofenceHardware() {
            try {
                if (this.mGeofenceHardwareImpl == null) {
                    this.mGeofenceHardwareImpl = android.hardware.location.GeofenceHardwareImpl.getInstance(com.android.server.location.gnss.GnssManagerService.this.mContext);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
            return this.mGeofenceHardwareImpl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReportGeofenceTransition$0(int i, android.location.Location location, int i2, long j) {
            getGeofenceHardware().reportGeofenceTransition(i, location, i2, j, 0, android.location.FusedBatchOptions.SourceTechnologies.GNSS);
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks
        public void onReportGeofenceTransition(final int i, final android.location.Location location, final int i2, final long j) {
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssManagerService.GnssGeofenceHalModule.this.lambda$onReportGeofenceTransition$0(i, location, i2, j);
                }
            });
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks
        public void onReportGeofenceStatus(final int i, final android.location.Location location) {
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssManagerService.GnssGeofenceHalModule.this.lambda$onReportGeofenceStatus$1(i, location);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReportGeofenceStatus$1(int i, android.location.Location location) {
            int i2;
            if (i != 2) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            getGeofenceHardware().reportGeofenceMonitorStatus(0, i2, location, android.location.FusedBatchOptions.SourceTechnologies.GNSS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReportGeofenceAddStatus$2(int i, int i2) {
            getGeofenceHardware().reportGeofenceAddStatus(i, translateGeofenceStatus(i2));
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks
        public void onReportGeofenceAddStatus(final int i, final int i2) {
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssManagerService.GnssGeofenceHalModule.this.lambda$onReportGeofenceAddStatus$2(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReportGeofenceRemoveStatus$3(int i, int i2) {
            getGeofenceHardware().reportGeofenceRemoveStatus(i, translateGeofenceStatus(i2));
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks
        public void onReportGeofenceRemoveStatus(final int i, final int i2) {
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssManagerService.GnssGeofenceHalModule.this.lambda$onReportGeofenceRemoveStatus$3(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReportGeofencePauseStatus$4(int i, int i2) {
            getGeofenceHardware().reportGeofencePauseStatus(i, translateGeofenceStatus(i2));
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks
        public void onReportGeofencePauseStatus(final int i, final int i2) {
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssManagerService.GnssGeofenceHalModule.this.lambda$onReportGeofencePauseStatus$4(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReportGeofenceResumeStatus$5(int i, int i2) {
            getGeofenceHardware().reportGeofenceResumeStatus(i, translateGeofenceStatus(i2));
        }

        @Override // com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks
        public void onReportGeofenceResumeStatus(final int i, final int i2) {
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssManagerService.GnssGeofenceHalModule.this.lambda$onReportGeofenceResumeStatus$5(i, i2);
                }
            });
        }

        private int translateGeofenceStatus(int i) {
            switch (i) {
                case com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks.GEOFENCE_STATUS_ERROR_GENERIC /* -149 */:
                    return 5;
                case com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks.GEOFENCE_STATUS_ERROR_INVALID_TRANSITION /* -103 */:
                    return 4;
                case com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks.GEOFENCE_STATUS_ERROR_ID_UNKNOWN /* -102 */:
                    return 3;
                case com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks.GEOFENCE_STATUS_ERROR_ID_EXISTS /* -101 */:
                    return 2;
                case 0:
                    return 0;
                case 100:
                    return 1;
                default:
                    return -1;
            }
        }
    }
}
