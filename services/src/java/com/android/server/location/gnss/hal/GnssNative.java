package com.android.server.location.gnss.hal;

/* loaded from: classes2.dex */
public class GnssNative {
    public static final int AGPS_REF_LOCATION_TYPE_GSM_CELLID = 1;
    public static final int AGPS_REF_LOCATION_TYPE_LTE_CELLID = 4;
    public static final int AGPS_REF_LOCATION_TYPE_NR_CELLID = 8;
    public static final int AGPS_REF_LOCATION_TYPE_UMTS_CELLID = 2;
    public static final int AGPS_SETID_TYPE_IMSI = 1;
    public static final int AGPS_SETID_TYPE_MSISDN = 2;
    public static final int AGPS_SETID_TYPE_NONE = 0;
    public static final int GNSS_AIDING_TYPE_ALL = 65535;
    public static final int GNSS_AIDING_TYPE_ALMANAC = 2;
    public static final int GNSS_AIDING_TYPE_CELLDB_INFO = 32768;
    public static final int GNSS_AIDING_TYPE_EPHEMERIS = 1;
    public static final int GNSS_AIDING_TYPE_HEALTH = 64;
    public static final int GNSS_AIDING_TYPE_IONO = 16;
    public static final int GNSS_AIDING_TYPE_POSITION = 4;
    public static final int GNSS_AIDING_TYPE_RTI = 1024;
    public static final int GNSS_AIDING_TYPE_SADATA = 512;
    public static final int GNSS_AIDING_TYPE_SVDIR = 128;
    public static final int GNSS_AIDING_TYPE_SVSTEER = 256;
    public static final int GNSS_AIDING_TYPE_TIME = 8;
    public static final int GNSS_AIDING_TYPE_UTC = 32;
    public static final int GNSS_LOCATION_HAS_ALTITUDE = 2;
    public static final int GNSS_LOCATION_HAS_BEARING = 8;
    public static final int GNSS_LOCATION_HAS_BEARING_ACCURACY = 128;
    public static final int GNSS_LOCATION_HAS_HORIZONTAL_ACCURACY = 16;
    public static final int GNSS_LOCATION_HAS_LAT_LONG = 1;
    public static final int GNSS_LOCATION_HAS_SPEED = 4;
    public static final int GNSS_LOCATION_HAS_SPEED_ACCURACY = 64;
    public static final int GNSS_LOCATION_HAS_VERTICAL_ACCURACY = 32;
    public static final int GNSS_POSITION_MODE_MS_ASSISTED = 2;
    public static final int GNSS_POSITION_MODE_MS_BASED = 1;
    public static final int GNSS_POSITION_MODE_STANDALONE = 0;
    public static final int GNSS_POSITION_RECURRENCE_PERIODIC = 0;
    public static final int GNSS_POSITION_RECURRENCE_SINGLE = 1;
    public static final int GNSS_REALTIME_HAS_TIMESTAMP_NS = 1;
    public static final int GNSS_REALTIME_HAS_TIME_UNCERTAINTY_NS = 2;
    private static final float ITAR_SPEED_LIMIT_METERS_PER_SECOND = 400.0f;

    @com.android.internal.annotations.GuardedBy({"GnssNative.class"})
    private static com.android.server.location.gnss.hal.GnssNative.GnssHal sGnssHal;

    @com.android.internal.annotations.GuardedBy({"GnssNative.class"})
    private static boolean sGnssHalInitialized;

    @com.android.internal.annotations.GuardedBy({"GnssNative.class"})
    private static com.android.server.location.gnss.hal.GnssNative sInstance;
    private com.android.server.location.gnss.hal.GnssNative.AGpsCallbacks mAGpsCallbacks;
    private final com.android.server.location.gnss.GnssConfiguration mConfiguration;
    private final com.android.server.location.injector.EmergencyHelper mEmergencyHelper;
    private com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks mGeofenceCallbacks;
    private final com.android.server.location.gnss.hal.GnssNative.GnssHal mGnssHal;
    private volatile boolean mItarSpeedLimitExceeded;
    private com.android.server.location.gnss.hal.GnssNative.LocationRequestCallbacks mLocationRequestCallbacks;
    private com.android.server.location.gnss.hal.GnssNative.NotificationCallbacks mNotificationCallbacks;
    private com.android.server.location.gnss.hal.GnssNative.PsdsCallbacks mPsdsCallbacks;
    private boolean mRegistered;
    private com.android.server.location.gnss.hal.GnssNative.TimeCallbacks mTimeCallbacks;
    private int mTopFlags;
    private com.android.server.location.gnss.hal.GnssNative.BaseCallbacks[] mBaseCallbacks = new com.android.server.location.gnss.hal.GnssNative.BaseCallbacks[0];
    private com.android.server.location.gnss.hal.GnssNative.StatusCallbacks[] mStatusCallbacks = new com.android.server.location.gnss.hal.GnssNative.StatusCallbacks[0];
    private com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks[] mSvStatusCallbacks = new com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks[0];
    private com.android.server.location.gnss.hal.GnssNative.NmeaCallbacks[] mNmeaCallbacks = new com.android.server.location.gnss.hal.GnssNative.NmeaCallbacks[0];
    private com.android.server.location.gnss.hal.GnssNative.LocationCallbacks[] mLocationCallbacks = new com.android.server.location.gnss.hal.GnssNative.LocationCallbacks[0];
    private com.android.server.location.gnss.hal.GnssNative.MeasurementCallbacks[] mMeasurementCallbacks = new com.android.server.location.gnss.hal.GnssNative.MeasurementCallbacks[0];
    private com.android.server.location.gnss.hal.GnssNative.AntennaInfoCallbacks[] mAntennaInfoCallbacks = new com.android.server.location.gnss.hal.GnssNative.AntennaInfoCallbacks[0];
    private com.android.server.location.gnss.hal.GnssNative.NavigationMessageCallbacks[] mNavigationMessageCallbacks = new com.android.server.location.gnss.hal.GnssNative.NavigationMessageCallbacks[0];
    private android.location.GnssCapabilities mCapabilities = new android.location.GnssCapabilities.Builder().build();

    @android.annotation.Nullable
    private com.android.server.location.gnss.GnssPowerStats mPowerStats = null;
    private int mHardwareYear = 0;

    @android.annotation.Nullable
    private java.lang.String mHardwareModelName = null;
    private long mStartRealtimeMs = 0;
    private boolean mHasFirstFix = false;

    public interface AGpsCallbacks {
        public static final int AGPS_REQUEST_SETID_IMSI = 1;
        public static final int AGPS_REQUEST_SETID_MSISDN = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface AgpsSetIdFlags {
        }

        void onReportAGpsStatus(int i, int i2, byte[] bArr);

        void onRequestSetID(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AgpsReferenceLocationType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AgpsSetIdType {
    }

    public interface AntennaInfoCallbacks {
        void onReportAntennaInfo(java.util.List<android.location.GnssAntennaInfo> list);
    }

    public interface GeofenceCallbacks {
        public static final int GEOFENCE_AVAILABILITY_AVAILABLE = 2;
        public static final int GEOFENCE_AVAILABILITY_UNAVAILABLE = 1;
        public static final int GEOFENCE_STATUS_ERROR_GENERIC = -149;
        public static final int GEOFENCE_STATUS_ERROR_ID_EXISTS = -101;
        public static final int GEOFENCE_STATUS_ERROR_ID_UNKNOWN = -102;
        public static final int GEOFENCE_STATUS_ERROR_INVALID_TRANSITION = -103;
        public static final int GEOFENCE_STATUS_ERROR_TOO_MANY_GEOFENCES = 100;
        public static final int GEOFENCE_STATUS_OPERATION_SUCCESS = 0;
        public static final int GEOFENCE_TRANSITION_ENTERED = 1;
        public static final int GEOFENCE_TRANSITION_EXITED = 2;
        public static final int GEOFENCE_TRANSITION_UNCERTAIN = 4;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface GeofenceAvailability {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface GeofenceStatus {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface GeofenceTransition {
        }

        void onReportGeofenceAddStatus(int i, int i2);

        void onReportGeofencePauseStatus(int i, int i2);

        void onReportGeofenceRemoveStatus(int i, int i2);

        void onReportGeofenceResumeStatus(int i, int i2);

        void onReportGeofenceStatus(int i, android.location.Location location);

        void onReportGeofenceTransition(int i, android.location.Location location, int i2, long j);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GnssAidingTypeFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GnssLocationFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GnssPositionMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GnssPositionRecurrence {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GnssRealtimeFlags {
    }

    public interface LocationCallbacks {
        void onReportLocation(boolean z, android.location.Location location);

        void onReportLocations(android.location.Location[] locationArr);
    }

    public interface LocationRequestCallbacks {
        void onRequestLocation(boolean z, boolean z2);

        void onRequestRefLocation();
    }

    public interface MeasurementCallbacks {
        void onReportMeasurements(android.location.GnssMeasurementsEvent gnssMeasurementsEvent);
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface NativeEntryPoint {
    }

    public interface NavigationMessageCallbacks {
        void onReportNavigationMessage(android.location.GnssNavigationMessage gnssNavigationMessage);
    }

    public interface NmeaCallbacks {
        void onReportNmea(long j);
    }

    public interface NotificationCallbacks {
        void onReportNfwNotification(java.lang.String str, byte b, java.lang.String str2, byte b2, java.lang.String str3, byte b3, boolean z, boolean z2);
    }

    public interface PsdsCallbacks {
        void onRequestPsdsDownload(int i);
    }

    public interface StatusCallbacks {
        public static final int GNSS_STATUS_ENGINE_OFF = 4;
        public static final int GNSS_STATUS_ENGINE_ON = 3;
        public static final int GNSS_STATUS_NONE = 0;
        public static final int GNSS_STATUS_SESSION_BEGIN = 1;
        public static final int GNSS_STATUS_SESSION_END = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface GnssStatusValue {
        }

        void onReportFirstFix(int i);

        void onReportStatus(int i);
    }

    public interface SvStatusCallbacks {
        void onReportSvStatus(android.location.GnssStatus gnssStatus);
    }

    public interface TimeCallbacks {
        void onRequestUtcTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_add_geofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_agps_set_id(int i, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_agps_set_ref_location_cellid(int i, int i2, int i3, int i4, long j, int i5, int i6, int i7);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_class_init_once();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_cleanup();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_cleanup_batching();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_delete_aiding_data(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_flush_batch();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int native_get_batch_size();

    /* JADX INFO: Access modifiers changed from: private */
    public static native java.lang.String native_get_internal_state();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_init();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_init_batching();

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_init_once(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_best_location(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_location(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_inject_measurement_corrections(android.location.GnssMeasurementCorrections gnssMeasurementCorrections);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_ni_supl_message_data(byte[] bArr, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_psds_data(byte[] bArr, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_inject_time(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_antenna_info_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_geofence_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_gnss_visibility_control_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_measurement_corrections_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_measurement_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_navigation_message_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_is_supported();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_pause_geofence(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int native_read_nmea(byte[] bArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_remove_geofence(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_request_power_stats();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_resume_geofence(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void native_set_agps_server(int i, java.lang.String str, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_set_position_mode(int i, int i2, int i3, int i4, int i5, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_antenna_info_listening();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_batch(long j, float f, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_measurement_collection(boolean z, boolean z2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_navigation_message_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_nmea_message_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_start_sv_status_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_antenna_info_listening();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_batch();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_measurement_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_navigation_message_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_nmea_message_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_stop_sv_status_collection();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_supports_psds();

    public interface BaseCallbacks {
        void onHalRestarted();

        default void onHalStarted() {
        }

        default void onCapabilitiesChanged(android.location.GnssCapabilities gnssCapabilities, android.location.GnssCapabilities gnssCapabilities2) {
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static synchronized void setGnssHalForTest(com.android.server.location.gnss.hal.GnssNative.GnssHal gnssHal) {
        synchronized (com.android.server.location.gnss.hal.GnssNative.class) {
            java.util.Objects.requireNonNull(gnssHal);
            com.android.server.location.gnss.hal.GnssNative.GnssHal gnssHal2 = gnssHal;
            sGnssHal = gnssHal;
            sGnssHalInitialized = false;
            sInstance = null;
        }
    }

    private static synchronized void initializeHal() {
        synchronized (com.android.server.location.gnss.hal.GnssNative.class) {
            try {
                if (!sGnssHalInitialized) {
                    if (sGnssHal == null) {
                        sGnssHal = new com.android.server.location.gnss.hal.GnssNative.GnssHal();
                    }
                    sGnssHal.classInitOnce();
                    sGnssHalInitialized = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public static synchronized boolean isSupported() {
        boolean isSupported;
        synchronized (com.android.server.location.gnss.hal.GnssNative.class) {
            initializeHal();
            isSupported = sGnssHal.isSupported();
        }
        return isSupported;
    }

    public static synchronized com.android.server.location.gnss.hal.GnssNative create(com.android.server.location.injector.Injector injector, com.android.server.location.gnss.GnssConfiguration gnssConfiguration) {
        com.android.server.location.gnss.hal.GnssNative gnssNative;
        synchronized (com.android.server.location.gnss.hal.GnssNative.class) {
            com.android.internal.util.Preconditions.checkState(isSupported());
            com.android.internal.util.Preconditions.checkState(sInstance == null);
            gnssNative = new com.android.server.location.gnss.hal.GnssNative(sGnssHal, injector, gnssConfiguration);
            sInstance = gnssNative;
        }
        return gnssNative;
    }

    private GnssNative(com.android.server.location.gnss.hal.GnssNative.GnssHal gnssHal, com.android.server.location.injector.Injector injector, com.android.server.location.gnss.GnssConfiguration gnssConfiguration) {
        java.util.Objects.requireNonNull(gnssHal);
        this.mGnssHal = gnssHal;
        this.mEmergencyHelper = injector.getEmergencyHelper();
        this.mConfiguration = gnssConfiguration;
    }

    public void addBaseCallbacks(com.android.server.location.gnss.hal.GnssNative.BaseCallbacks baseCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mBaseCallbacks = (com.android.server.location.gnss.hal.GnssNative.BaseCallbacks[]) com.android.internal.util.ArrayUtils.appendElement(com.android.server.location.gnss.hal.GnssNative.BaseCallbacks.class, this.mBaseCallbacks, baseCallbacks);
    }

    public void addStatusCallbacks(com.android.server.location.gnss.hal.GnssNative.StatusCallbacks statusCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mStatusCallbacks = (com.android.server.location.gnss.hal.GnssNative.StatusCallbacks[]) com.android.internal.util.ArrayUtils.appendElement(com.android.server.location.gnss.hal.GnssNative.StatusCallbacks.class, this.mStatusCallbacks, statusCallbacks);
    }

    public void addSvStatusCallbacks(com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks svStatusCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mSvStatusCallbacks = (com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks[]) com.android.internal.util.ArrayUtils.appendElement(com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks.class, this.mSvStatusCallbacks, svStatusCallbacks);
    }

    public void addNmeaCallbacks(com.android.server.location.gnss.hal.GnssNative.NmeaCallbacks nmeaCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mNmeaCallbacks = (com.android.server.location.gnss.hal.GnssNative.NmeaCallbacks[]) com.android.internal.util.ArrayUtils.appendElement(com.android.server.location.gnss.hal.GnssNative.NmeaCallbacks.class, this.mNmeaCallbacks, nmeaCallbacks);
    }

    public void addLocationCallbacks(com.android.server.location.gnss.hal.GnssNative.LocationCallbacks locationCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mLocationCallbacks = (com.android.server.location.gnss.hal.GnssNative.LocationCallbacks[]) com.android.internal.util.ArrayUtils.appendElement(com.android.server.location.gnss.hal.GnssNative.LocationCallbacks.class, this.mLocationCallbacks, locationCallbacks);
    }

    public void addMeasurementCallbacks(com.android.server.location.gnss.hal.GnssNative.MeasurementCallbacks measurementCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mMeasurementCallbacks = (com.android.server.location.gnss.hal.GnssNative.MeasurementCallbacks[]) com.android.internal.util.ArrayUtils.appendElement(com.android.server.location.gnss.hal.GnssNative.MeasurementCallbacks.class, this.mMeasurementCallbacks, measurementCallbacks);
    }

    public void addAntennaInfoCallbacks(com.android.server.location.gnss.hal.GnssNative.AntennaInfoCallbacks antennaInfoCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mAntennaInfoCallbacks = (com.android.server.location.gnss.hal.GnssNative.AntennaInfoCallbacks[]) com.android.internal.util.ArrayUtils.appendElement(com.android.server.location.gnss.hal.GnssNative.AntennaInfoCallbacks.class, this.mAntennaInfoCallbacks, antennaInfoCallbacks);
    }

    public void addNavigationMessageCallbacks(com.android.server.location.gnss.hal.GnssNative.NavigationMessageCallbacks navigationMessageCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mNavigationMessageCallbacks = (com.android.server.location.gnss.hal.GnssNative.NavigationMessageCallbacks[]) com.android.internal.util.ArrayUtils.appendElement(com.android.server.location.gnss.hal.GnssNative.NavigationMessageCallbacks.class, this.mNavigationMessageCallbacks, navigationMessageCallbacks);
    }

    public void setGeofenceCallbacks(com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks geofenceCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        com.android.internal.util.Preconditions.checkState(this.mGeofenceCallbacks == null);
        java.util.Objects.requireNonNull(geofenceCallbacks);
        this.mGeofenceCallbacks = geofenceCallbacks;
    }

    public void setTimeCallbacks(com.android.server.location.gnss.hal.GnssNative.TimeCallbacks timeCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        com.android.internal.util.Preconditions.checkState(this.mTimeCallbacks == null);
        java.util.Objects.requireNonNull(timeCallbacks);
        this.mTimeCallbacks = timeCallbacks;
    }

    public void setLocationRequestCallbacks(com.android.server.location.gnss.hal.GnssNative.LocationRequestCallbacks locationRequestCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        com.android.internal.util.Preconditions.checkState(this.mLocationRequestCallbacks == null);
        java.util.Objects.requireNonNull(locationRequestCallbacks);
        this.mLocationRequestCallbacks = locationRequestCallbacks;
    }

    public void setPsdsCallbacks(com.android.server.location.gnss.hal.GnssNative.PsdsCallbacks psdsCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        com.android.internal.util.Preconditions.checkState(this.mPsdsCallbacks == null);
        java.util.Objects.requireNonNull(psdsCallbacks);
        this.mPsdsCallbacks = psdsCallbacks;
    }

    public void setAGpsCallbacks(com.android.server.location.gnss.hal.GnssNative.AGpsCallbacks aGpsCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        com.android.internal.util.Preconditions.checkState(this.mAGpsCallbacks == null);
        java.util.Objects.requireNonNull(aGpsCallbacks);
        this.mAGpsCallbacks = aGpsCallbacks;
    }

    public void setNotificationCallbacks(com.android.server.location.gnss.hal.GnssNative.NotificationCallbacks notificationCallbacks) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        com.android.internal.util.Preconditions.checkState(this.mNotificationCallbacks == null);
        java.util.Objects.requireNonNull(notificationCallbacks);
        this.mNotificationCallbacks = notificationCallbacks;
    }

    public void register() {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered);
        this.mRegistered = true;
        initializeGnss(false);
        android.util.Log.i(com.android.server.location.gnss.GnssManagerService.TAG, "gnss hal started");
        for (int i = 0; i < this.mBaseCallbacks.length; i++) {
            this.mBaseCallbacks[i].onHalStarted();
        }
    }

    private void initializeGnss(boolean z) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mTopFlags = 0;
        this.mGnssHal.initOnce(this, z);
        if (this.mGnssHal.init()) {
            this.mGnssHal.cleanup();
            android.util.Log.i(com.android.server.location.gnss.GnssManagerService.TAG, "gnss hal initialized");
        } else {
            android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "gnss hal initialization failed");
        }
    }

    public com.android.server.location.gnss.GnssConfiguration getConfiguration() {
        return this.mConfiguration;
    }

    public boolean init() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.init();
    }

    public void cleanup() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.cleanup();
    }

    @android.annotation.Nullable
    public com.android.server.location.gnss.GnssPowerStats getPowerStats() {
        return this.mPowerStats;
    }

    public android.location.GnssCapabilities getCapabilities() {
        return this.mCapabilities;
    }

    public int getHardwareYear() {
        return this.mHardwareYear;
    }

    @android.annotation.Nullable
    public java.lang.String getHardwareModelName() {
        return this.mHardwareModelName;
    }

    public boolean isItarSpeedLimitExceeded() {
        return this.mItarSpeedLimitExceeded;
    }

    public boolean start() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mStartRealtimeMs = android.os.SystemClock.elapsedRealtime();
        this.mHasFirstFix = false;
        return this.mGnssHal.start();
    }

    public boolean stop() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stop();
    }

    public boolean setPositionMode(int i, int i2, int i3, int i4, int i5, boolean z) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.setPositionMode(i, i2, i3, i4, i5, z);
    }

    public java.lang.String getInternalState() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.getInternalState();
    }

    public void deleteAidingData(int i) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.deleteAidingData(i);
    }

    public int readNmea(byte[] bArr, int i) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.readNmea(bArr, i);
    }

    public void injectLocation(android.location.Location location) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        if (location.hasAccuracy()) {
            int i = (location.hasAltitude() ? 2 : 0) | 1 | (location.hasSpeed() ? 4 : 0) | (location.hasBearing() ? 8 : 0) | (location.hasAccuracy() ? 16 : 0) | (location.hasVerticalAccuracy() ? 32 : 0) | (location.hasSpeedAccuracy() ? 64 : 0) | (location.hasBearingAccuracy() ? 128 : 0);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            double altitude = location.getAltitude();
            float speed = location.getSpeed();
            float bearing = location.getBearing();
            float accuracy = location.getAccuracy();
            float verticalAccuracyMeters = location.getVerticalAccuracyMeters();
            float speedAccuracyMetersPerSecond = location.getSpeedAccuracyMetersPerSecond();
            float bearingAccuracyDegrees = location.getBearingAccuracyDegrees();
            long time = location.getTime();
            int i2 = location.hasElapsedRealtimeUncertaintyNanos() ? 2 : 0;
            this.mGnssHal.injectLocation(i, latitude, longitude, altitude, speed, bearing, accuracy, verticalAccuracyMeters, speedAccuracyMetersPerSecond, bearingAccuracyDegrees, time, i2 | 1, location.getElapsedRealtimeNanos(), location.getElapsedRealtimeUncertaintyNanos());
        }
    }

    public void injectBestLocation(android.location.Location location) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        int i = (location.hasAltitude() ? 2 : 0) | 1 | (location.hasSpeed() ? 4 : 0) | (location.hasBearing() ? 8 : 0) | (location.hasAccuracy() ? 16 : 0) | (location.hasVerticalAccuracy() ? 32 : 0) | (location.hasSpeedAccuracy() ? 64 : 0) | (location.hasBearingAccuracy() ? 128 : 0);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();
        float speed = location.getSpeed();
        float bearing = location.getBearing();
        float accuracy = location.getAccuracy();
        float verticalAccuracyMeters = location.getVerticalAccuracyMeters();
        float speedAccuracyMetersPerSecond = location.getSpeedAccuracyMetersPerSecond();
        float bearingAccuracyDegrees = location.getBearingAccuracyDegrees();
        long time = location.getTime();
        int i2 = location.hasElapsedRealtimeUncertaintyNanos() ? 2 : 0;
        this.mGnssHal.injectBestLocation(i, latitude, longitude, altitude, speed, bearing, accuracy, verticalAccuracyMeters, speedAccuracyMetersPerSecond, bearingAccuracyDegrees, time, i2 | 1, location.getElapsedRealtimeNanos(), location.getElapsedRealtimeUncertaintyNanos());
    }

    public void injectTime(long j, long j2, int i) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectTime(j, j2, i);
    }

    public boolean isNavigationMessageCollectionSupported() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isNavigationMessageCollectionSupported();
    }

    public boolean startNavigationMessageCollection() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startNavigationMessageCollection();
    }

    public boolean stopNavigationMessageCollection() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopNavigationMessageCollection();
    }

    public boolean isAntennaInfoSupported() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isAntennaInfoSupported();
    }

    public boolean startAntennaInfoListening() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startAntennaInfoListening();
    }

    public boolean stopAntennaInfoListening() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopAntennaInfoListening();
    }

    public boolean isMeasurementSupported() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isMeasurementSupported();
    }

    public boolean startMeasurementCollection(boolean z, boolean z2, int i) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startMeasurementCollection(z, z2, i);
    }

    public boolean stopMeasurementCollection() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopMeasurementCollection();
    }

    public boolean startSvStatusCollection() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startSvStatusCollection();
    }

    public boolean stopSvStatusCollection() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopSvStatusCollection();
    }

    public boolean startNmeaMessageCollection() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startNmeaMessageCollection();
    }

    public boolean stopNmeaMessageCollection() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.stopNmeaMessageCollection();
    }

    public boolean isMeasurementCorrectionsSupported() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isMeasurementCorrectionsSupported();
    }

    public boolean injectMeasurementCorrections(android.location.GnssMeasurementCorrections gnssMeasurementCorrections) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.injectMeasurementCorrections(gnssMeasurementCorrections);
    }

    public boolean initBatching() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.initBatching();
    }

    public void cleanupBatching() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.cleanupBatching();
    }

    public boolean startBatch(long j, float f, boolean z) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.startBatch(j, f, z);
    }

    public void flushBatch() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.flushBatch();
    }

    public void stopBatch() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.stopBatch();
    }

    public int getBatchSize() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.getBatchSize();
    }

    public boolean isGeofencingSupported() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isGeofencingSupported();
    }

    public boolean addGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.addGeofence(i, d, d2, d3, i2, i3, i4, i5);
    }

    public boolean resumeGeofence(int i, int i2) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.resumeGeofence(i, i2);
    }

    public boolean pauseGeofence(int i) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.pauseGeofence(i);
    }

    public boolean removeGeofence(int i) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.removeGeofence(i);
    }

    public boolean isGnssVisibilityControlSupported() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isGnssVisibilityControlSupported();
    }

    public void requestPowerStats() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.requestPowerStats();
    }

    public void setAgpsServer(int i, java.lang.String str, int i2) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.setAgpsServer(i, str, i2);
    }

    public void setAgpsSetId(int i, java.lang.String str) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.setAgpsSetId(i, str);
    }

    public void setAgpsReferenceLocationCellId(int i, int i2, int i3, int i4, long j, int i5, int i6, int i7) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.setAgpsReferenceLocationCellId(i, i2, i3, i4, j, i5, i6, i7);
    }

    public boolean isPsdsSupported() {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        return this.mGnssHal.isPsdsSupported();
    }

    public void injectPsdsData(byte[] bArr, int i, int i2) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectPsdsData(bArr, i, i2);
    }

    public void injectNiSuplMessageData(byte[] bArr, int i, int i2) {
        com.android.internal.util.Preconditions.checkState(this.mRegistered);
        this.mGnssHal.injectNiSuplMessageData(bArr, i, i2);
    }

    void reportGnssServiceDied() {
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "gnss hal died - restarting shortly...");
        com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.hal.GnssNative.this.restartHal();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    void restartHal() {
        initializeGnss(true);
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "gnss hal restarted");
        for (int i = 0; i < this.mBaseCallbacks.length; i++) {
            this.mBaseCallbacks[i].onHalRestarted();
        }
    }

    void reportLocation(final boolean z, final android.location.Location location) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda20
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportLocation$0(z, location);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportLocation$0(boolean z, android.location.Location location) throws java.lang.Exception {
        if (z && !this.mHasFirstFix) {
            this.mHasFirstFix = true;
            int elapsedRealtime = (int) (android.os.SystemClock.elapsedRealtime() - this.mStartRealtimeMs);
            for (int i = 0; i < this.mStatusCallbacks.length; i++) {
                this.mStatusCallbacks[i].onReportFirstFix(elapsedRealtime);
            }
        }
        if (location.hasSpeed()) {
            boolean z2 = location.getSpeed() > ITAR_SPEED_LIMIT_METERS_PER_SECOND;
            if (!this.mItarSpeedLimitExceeded && z2) {
                android.util.Log.w(com.android.server.location.gnss.GnssManagerService.TAG, "speed nearing ITAR threshold - blocking further GNSS output");
            } else if (this.mItarSpeedLimitExceeded && !z2) {
                android.util.Log.w(com.android.server.location.gnss.GnssManagerService.TAG, "speed leaving ITAR threshold - allowing further GNSS output");
            }
            this.mItarSpeedLimitExceeded = z2;
        }
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        for (int i2 = 0; i2 < this.mLocationCallbacks.length; i2++) {
            this.mLocationCallbacks[i2].onReportLocation(z, location);
        }
    }

    void reportStatus(final int i) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportStatus$1(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportStatus$1(int i) throws java.lang.Exception {
        for (int i2 = 0; i2 < this.mStatusCallbacks.length; i2++) {
            this.mStatusCallbacks[i2].onReportStatus(i);
        }
    }

    void reportSvStatus(final int i, final int[] iArr, final float[] fArr, final float[] fArr2, final float[] fArr3, final float[] fArr4, final float[] fArr5) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda9
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportSvStatus$2(i, iArr, fArr, fArr2, fArr3, fArr4, fArr5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportSvStatus$2(int i, int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) throws java.lang.Exception {
        android.location.GnssStatus wrap = android.location.GnssStatus.wrap(i, iArr, fArr, fArr2, fArr3, fArr4, fArr5);
        for (int i2 = 0; i2 < this.mSvStatusCallbacks.length; i2++) {
            this.mSvStatusCallbacks[i2].onReportSvStatus(wrap);
        }
    }

    void reportAGpsStatus(final int i, final int i2, final byte[] bArr) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportAGpsStatus$3(i, i2, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportAGpsStatus$3(int i, int i2, byte[] bArr) throws java.lang.Exception {
        this.mAGpsCallbacks.onReportAGpsStatus(i, i2, bArr);
    }

    void reportNmea(final long j) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda19
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportNmea$4(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportNmea$4(long j) throws java.lang.Exception {
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        for (int i = 0; i < this.mNmeaCallbacks.length; i++) {
            this.mNmeaCallbacks[i].onReportNmea(j);
        }
    }

    void reportMeasurementData(final android.location.GnssMeasurementsEvent gnssMeasurementsEvent) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda12
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportMeasurementData$5(gnssMeasurementsEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportMeasurementData$5(android.location.GnssMeasurementsEvent gnssMeasurementsEvent) throws java.lang.Exception {
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        for (int i = 0; i < this.mMeasurementCallbacks.length; i++) {
            this.mMeasurementCallbacks[i].onReportMeasurements(gnssMeasurementsEvent);
        }
    }

    void reportAntennaInfo(final java.util.List<android.location.GnssAntennaInfo> list) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda18
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportAntennaInfo$6(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportAntennaInfo$6(java.util.List list) throws java.lang.Exception {
        for (int i = 0; i < this.mAntennaInfoCallbacks.length; i++) {
            this.mAntennaInfoCallbacks[i].onReportAntennaInfo(list);
        }
    }

    void reportNavigationMessage(final android.location.GnssNavigationMessage gnssNavigationMessage) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda15
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportNavigationMessage$7(gnssNavigationMessage);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportNavigationMessage$7(android.location.GnssNavigationMessage gnssNavigationMessage) throws java.lang.Exception {
        if (this.mItarSpeedLimitExceeded) {
            return;
        }
        for (int i = 0; i < this.mNavigationMessageCallbacks.length; i++) {
            this.mNavigationMessageCallbacks[i].onReportNavigationMessage(gnssNavigationMessage);
        }
    }

    void setTopHalCapabilities(int i, boolean z) {
        this.mTopFlags = i | this.mTopFlags;
        android.location.GnssCapabilities gnssCapabilities = this.mCapabilities;
        this.mCapabilities = gnssCapabilities.withTopHalFlags(this.mTopFlags, z);
        onCapabilitiesChanged(gnssCapabilities, this.mCapabilities);
    }

    void setSubHalMeasurementCorrectionsCapabilities(int i) {
        android.location.GnssCapabilities gnssCapabilities = this.mCapabilities;
        this.mCapabilities = gnssCapabilities.withSubHalMeasurementCorrectionsFlags(i);
        onCapabilitiesChanged(gnssCapabilities, this.mCapabilities);
    }

    void setSubHalPowerIndicationCapabilities(int i) {
        android.location.GnssCapabilities gnssCapabilities = this.mCapabilities;
        this.mCapabilities = gnssCapabilities.withSubHalPowerFlags(i);
        onCapabilitiesChanged(gnssCapabilities, this.mCapabilities);
    }

    void setSignalTypeCapabilities(java.util.List<android.location.GnssSignalType> list) {
        android.location.GnssCapabilities gnssCapabilities = this.mCapabilities;
        this.mCapabilities = gnssCapabilities.withSignalTypes(list);
        onCapabilitiesChanged(gnssCapabilities, this.mCapabilities);
    }

    private void onCapabilitiesChanged(final android.location.GnssCapabilities gnssCapabilities, final android.location.GnssCapabilities gnssCapabilities2) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda16
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$onCapabilitiesChanged$8(gnssCapabilities2, gnssCapabilities);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCapabilitiesChanged$8(android.location.GnssCapabilities gnssCapabilities, android.location.GnssCapabilities gnssCapabilities2) throws java.lang.Exception {
        if (gnssCapabilities.equals(gnssCapabilities2)) {
            return;
        }
        android.util.Log.i(com.android.server.location.gnss.GnssManagerService.TAG, "gnss capabilities changed to " + gnssCapabilities);
        for (int i = 0; i < this.mBaseCallbacks.length; i++) {
            this.mBaseCallbacks[i].onCapabilitiesChanged(gnssCapabilities2, gnssCapabilities);
        }
    }

    void reportGnssPowerStats(com.android.server.location.gnss.GnssPowerStats gnssPowerStats) {
        this.mPowerStats = gnssPowerStats;
    }

    void setGnssYearOfHardware(int i) {
        this.mHardwareYear = i;
    }

    private void setGnssHardwareModelName(java.lang.String str) {
        this.mHardwareModelName = str;
    }

    void reportLocationBatch(final android.location.Location[] locationArr) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda6
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportLocationBatch$9(locationArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportLocationBatch$9(android.location.Location[] locationArr) throws java.lang.Exception {
        for (int i = 0; i < this.mLocationCallbacks.length; i++) {
            this.mLocationCallbacks[i].onReportLocations(locationArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$psdsDownloadRequest$10(int i) throws java.lang.Exception {
        this.mPsdsCallbacks.onRequestPsdsDownload(i);
    }

    void psdsDownloadRequest(final int i) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda10
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$psdsDownloadRequest$10(i);
            }
        });
    }

    void reportGeofenceTransition(final int i, final android.location.Location location, final int i2, final long j) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda8
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportGeofenceTransition$11(i, location, i2, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceTransition$11(int i, android.location.Location location, int i2, long j) throws java.lang.Exception {
        this.mGeofenceCallbacks.onReportGeofenceTransition(i, location, i2, j);
    }

    void reportGeofenceStatus(final int i, final android.location.Location location) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportGeofenceStatus$12(i, location);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceStatus$12(int i, android.location.Location location) throws java.lang.Exception {
        this.mGeofenceCallbacks.onReportGeofenceStatus(i, location);
    }

    void reportGeofenceAddStatus(final int i, final int i2) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda7
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportGeofenceAddStatus$13(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceAddStatus$13(int i, int i2) throws java.lang.Exception {
        this.mGeofenceCallbacks.onReportGeofenceAddStatus(i, i2);
    }

    void reportGeofenceRemoveStatus(final int i, final int i2) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda17
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportGeofenceRemoveStatus$14(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceRemoveStatus$14(int i, int i2) throws java.lang.Exception {
        this.mGeofenceCallbacks.onReportGeofenceRemoveStatus(i, i2);
    }

    void reportGeofencePauseStatus(final int i, final int i2) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportGeofencePauseStatus$15(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofencePauseStatus$15(int i, int i2) throws java.lang.Exception {
        this.mGeofenceCallbacks.onReportGeofencePauseStatus(i, i2);
    }

    void reportGeofenceResumeStatus(final int i, final int i2) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda5
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportGeofenceResumeStatus$16(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportGeofenceResumeStatus$16(int i, int i2) throws java.lang.Exception {
        this.mGeofenceCallbacks.onReportGeofenceResumeStatus(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestSetID$17(int i) throws java.lang.Exception {
        this.mAGpsCallbacks.onRequestSetID(i);
    }

    void requestSetID(final int i) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda23
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$requestSetID$17(i);
            }
        });
    }

    void requestLocation(final boolean z, final boolean z2) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda11
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$requestLocation$18(z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestLocation$18(boolean z, boolean z2) throws java.lang.Exception {
        this.mLocationRequestCallbacks.onRequestLocation(z, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestUtcTime$19() throws java.lang.Exception {
        this.mTimeCallbacks.onRequestUtcTime();
    }

    void requestUtcTime() {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda14
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$requestUtcTime$19();
            }
        });
    }

    void requestRefLocation() {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda21
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$requestRefLocation$20();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestRefLocation$20() throws java.lang.Exception {
        this.mLocationRequestCallbacks.onRequestRefLocation();
    }

    void reportNfwNotification(final java.lang.String str, final byte b, final java.lang.String str2, final byte b2, final java.lang.String str3, final byte b3, final boolean z, final boolean z2) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda22
            public final void runOrThrow() {
                com.android.server.location.gnss.hal.GnssNative.this.lambda$reportNfwNotification$21(str, b, str2, b2, str3, b3, z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportNfwNotification$21(java.lang.String str, byte b, java.lang.String str2, byte b2, java.lang.String str3, byte b3, boolean z, boolean z2) throws java.lang.Exception {
        this.mNotificationCallbacks.onReportNfwNotification(str, b, str2, b2, str3, b3, z, z2);
    }

    public boolean isInEmergencySession() {
        return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.location.gnss.hal.GnssNative$$ExternalSyntheticLambda2
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$isInEmergencySession$22;
                lambda$isInEmergencySession$22 = com.android.server.location.gnss.hal.GnssNative.this.lambda$isInEmergencySession$22();
                return lambda$isInEmergencySession$22;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$isInEmergencySession$22() throws java.lang.Exception {
        return java.lang.Boolean.valueOf(this.mEmergencyHelper.isInEmergency(java.util.concurrent.TimeUnit.SECONDS.toMillis(this.mConfiguration.getEsExtensionSec())));
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class GnssHal {
        protected GnssHal() {
        }

        protected void classInitOnce() {
            com.android.server.location.gnss.hal.GnssNative.native_class_init_once();
        }

        protected boolean isSupported() {
            return com.android.server.location.gnss.hal.GnssNative.native_is_supported();
        }

        protected void initOnce(com.android.server.location.gnss.hal.GnssNative gnssNative, boolean z) {
            gnssNative.native_init_once(z);
        }

        protected boolean init() {
            return com.android.server.location.gnss.hal.GnssNative.native_init();
        }

        protected void cleanup() {
            com.android.server.location.gnss.hal.GnssNative.native_cleanup();
        }

        protected boolean start() {
            return com.android.server.location.gnss.hal.GnssNative.native_start();
        }

        protected boolean stop() {
            return com.android.server.location.gnss.hal.GnssNative.native_stop();
        }

        protected boolean setPositionMode(int i, int i2, int i3, int i4, int i5, boolean z) {
            return com.android.server.location.gnss.hal.GnssNative.native_set_position_mode(i, i2, i3, i4, i5, z);
        }

        protected java.lang.String getInternalState() {
            return com.android.server.location.gnss.hal.GnssNative.native_get_internal_state();
        }

        protected void deleteAidingData(int i) {
            com.android.server.location.gnss.hal.GnssNative.native_delete_aiding_data(i);
        }

        protected int readNmea(byte[] bArr, int i) {
            return com.android.server.location.gnss.hal.GnssNative.native_read_nmea(bArr, i);
        }

        protected void injectLocation(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4) {
            com.android.server.location.gnss.hal.GnssNative.native_inject_location(i, d, d2, d3, f, f2, f3, f4, f5, f6, j, i2, j2, d4);
        }

        protected void injectBestLocation(int i, double d, double d2, double d3, float f, float f2, float f3, float f4, float f5, float f6, long j, int i2, long j2, double d4) {
            com.android.server.location.gnss.hal.GnssNative.native_inject_best_location(i, d, d2, d3, f, f2, f3, f4, f5, f6, j, i2, j2, d4);
        }

        protected void injectTime(long j, long j2, int i) {
            com.android.server.location.gnss.hal.GnssNative.native_inject_time(j, j2, i);
        }

        protected boolean isNavigationMessageCollectionSupported() {
            return com.android.server.location.gnss.hal.GnssNative.native_is_navigation_message_supported();
        }

        protected boolean startNavigationMessageCollection() {
            return com.android.server.location.gnss.hal.GnssNative.native_start_navigation_message_collection();
        }

        protected boolean stopNavigationMessageCollection() {
            return com.android.server.location.gnss.hal.GnssNative.native_stop_navigation_message_collection();
        }

        protected boolean isAntennaInfoSupported() {
            return com.android.server.location.gnss.hal.GnssNative.native_is_antenna_info_supported();
        }

        protected boolean startAntennaInfoListening() {
            return com.android.server.location.gnss.hal.GnssNative.native_start_antenna_info_listening();
        }

        protected boolean stopAntennaInfoListening() {
            return com.android.server.location.gnss.hal.GnssNative.native_stop_antenna_info_listening();
        }

        protected boolean isMeasurementSupported() {
            return com.android.server.location.gnss.hal.GnssNative.native_is_measurement_supported();
        }

        protected boolean startMeasurementCollection(boolean z, boolean z2, int i) {
            return com.android.server.location.gnss.hal.GnssNative.native_start_measurement_collection(z, z2, i);
        }

        protected boolean stopMeasurementCollection() {
            return com.android.server.location.gnss.hal.GnssNative.native_stop_measurement_collection();
        }

        protected boolean isMeasurementCorrectionsSupported() {
            return com.android.server.location.gnss.hal.GnssNative.native_is_measurement_corrections_supported();
        }

        protected boolean injectMeasurementCorrections(android.location.GnssMeasurementCorrections gnssMeasurementCorrections) {
            return com.android.server.location.gnss.hal.GnssNative.native_inject_measurement_corrections(gnssMeasurementCorrections);
        }

        protected boolean startSvStatusCollection() {
            return com.android.server.location.gnss.hal.GnssNative.native_start_sv_status_collection();
        }

        protected boolean stopSvStatusCollection() {
            return com.android.server.location.gnss.hal.GnssNative.native_stop_sv_status_collection();
        }

        protected boolean startNmeaMessageCollection() {
            return com.android.server.location.gnss.hal.GnssNative.native_start_nmea_message_collection();
        }

        protected boolean stopNmeaMessageCollection() {
            return com.android.server.location.gnss.hal.GnssNative.native_stop_nmea_message_collection();
        }

        protected int getBatchSize() {
            return com.android.server.location.gnss.hal.GnssNative.native_get_batch_size();
        }

        protected boolean initBatching() {
            return com.android.server.location.gnss.hal.GnssNative.native_init_batching();
        }

        protected void cleanupBatching() {
            com.android.server.location.gnss.hal.GnssNative.native_cleanup_batching();
        }

        protected boolean startBatch(long j, float f, boolean z) {
            return com.android.server.location.gnss.hal.GnssNative.native_start_batch(j, f, z);
        }

        protected void flushBatch() {
            com.android.server.location.gnss.hal.GnssNative.native_flush_batch();
        }

        protected void stopBatch() {
            com.android.server.location.gnss.hal.GnssNative.native_stop_batch();
        }

        protected boolean isGeofencingSupported() {
            return com.android.server.location.gnss.hal.GnssNative.native_is_geofence_supported();
        }

        protected boolean addGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) {
            return com.android.server.location.gnss.hal.GnssNative.native_add_geofence(i, d, d2, d3, i2, i3, i4, i5);
        }

        protected boolean resumeGeofence(int i, int i2) {
            return com.android.server.location.gnss.hal.GnssNative.native_resume_geofence(i, i2);
        }

        protected boolean pauseGeofence(int i) {
            return com.android.server.location.gnss.hal.GnssNative.native_pause_geofence(i);
        }

        protected boolean removeGeofence(int i) {
            return com.android.server.location.gnss.hal.GnssNative.native_remove_geofence(i);
        }

        protected boolean isGnssVisibilityControlSupported() {
            return com.android.server.location.gnss.hal.GnssNative.native_is_gnss_visibility_control_supported();
        }

        protected void requestPowerStats() {
            com.android.server.location.gnss.hal.GnssNative.native_request_power_stats();
        }

        protected void setAgpsServer(int i, java.lang.String str, int i2) {
            com.android.server.location.gnss.hal.GnssNative.native_set_agps_server(i, str, i2);
        }

        protected void setAgpsSetId(int i, java.lang.String str) {
            com.android.server.location.gnss.hal.GnssNative.native_agps_set_id(i, str);
        }

        protected void setAgpsReferenceLocationCellId(int i, int i2, int i3, int i4, long j, int i5, int i6, int i7) {
            com.android.server.location.gnss.hal.GnssNative.native_agps_set_ref_location_cellid(i, i2, i3, i4, j, i5, i6, i7);
        }

        protected boolean isPsdsSupported() {
            return com.android.server.location.gnss.hal.GnssNative.native_supports_psds();
        }

        protected void injectPsdsData(byte[] bArr, int i, int i2) {
            com.android.server.location.gnss.hal.GnssNative.native_inject_psds_data(bArr, i, i2);
        }

        protected void injectNiSuplMessageData(byte[] bArr, int i, int i2) {
            com.android.server.location.gnss.hal.GnssNative.native_inject_ni_supl_message_data(bArr, i, i2);
        }
    }
}
