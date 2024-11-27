package android.hardware.location;

/* loaded from: classes2.dex */
public final class GeofenceHardwareImpl {
    private static final int ADD_GEOFENCE_CALLBACK = 2;
    private static final int CALLBACK_ADD = 2;
    private static final int CALLBACK_REMOVE = 3;
    private static final int CAPABILITY_GNSS = 1;
    private static final int FIRST_VERSION_WITH_CAPABILITIES = 2;
    private static final int GEOFENCE_CALLBACK_BINDER_DIED = 6;
    private static final int GEOFENCE_STATUS = 1;
    private static final int GEOFENCE_TRANSITION_CALLBACK = 1;
    private static final int LOCATION_HAS_ACCURACY = 16;
    private static final int LOCATION_HAS_ALTITUDE = 2;
    private static final int LOCATION_HAS_BEARING = 8;
    private static final int LOCATION_HAS_LAT_LONG = 1;
    private static final int LOCATION_HAS_SPEED = 4;
    private static final int LOCATION_INVALID = 0;
    private static final int MONITOR_CALLBACK_BINDER_DIED = 4;
    private static final int PAUSE_GEOFENCE_CALLBACK = 4;
    private static final int REAPER_GEOFENCE_ADDED = 1;
    private static final int REAPER_MONITOR_CALLBACK_ADDED = 2;
    private static final int REAPER_REMOVED = 3;
    private static final int REMOVE_GEOFENCE_CALLBACK = 3;
    private static final int RESOLUTION_LEVEL_COARSE = 2;
    private static final int RESOLUTION_LEVEL_FINE = 3;
    private static final int RESOLUTION_LEVEL_NONE = 1;
    private static final int RESUME_GEOFENCE_CALLBACK = 5;
    private static android.hardware.location.GeofenceHardwareImpl sInstance;
    private int mCapabilities;
    private final android.content.Context mContext;
    private android.location.IFusedGeofenceHardware mFusedService;
    private android.location.IGpsGeofenceHardware mGpsService;
    private android.os.PowerManager.WakeLock mWakeLock;
    private static final java.lang.String TAG = "GeofenceHardwareImpl";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final android.util.SparseArray<android.hardware.location.IGeofenceHardwareCallback> mGeofences = new android.util.SparseArray<>();
    private final java.util.ArrayList<android.hardware.location.IGeofenceHardwareMonitorCallback>[] mCallbacks = new java.util.ArrayList[2];
    private final java.util.ArrayList<android.hardware.location.GeofenceHardwareImpl.Reaper> mReapers = new java.util.ArrayList<>();
    private int mVersion = 1;
    private int[] mSupportedMonitorTypes = new int[2];
    private android.os.Handler mGeofenceHandler = new android.os.Handler() { // from class: android.hardware.location.GeofenceHardwareImpl.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback;
            android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback2;
            android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback3;
            android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback4;
            android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback5;
            int i = 0;
            switch (message.what) {
                case 1:
                    android.hardware.location.GeofenceHardwareImpl.GeofenceTransition geofenceTransition = (android.hardware.location.GeofenceHardwareImpl.GeofenceTransition) message.obj;
                    synchronized (android.hardware.location.GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback = (android.hardware.location.IGeofenceHardwareCallback) android.hardware.location.GeofenceHardwareImpl.this.mGeofences.get(geofenceTransition.mGeofenceId);
                        if (android.hardware.location.GeofenceHardwareImpl.DEBUG) {
                            android.util.Log.d(android.hardware.location.GeofenceHardwareImpl.TAG, "GeofenceTransistionCallback: GPS : GeofenceId: " + geofenceTransition.mGeofenceId + " Transition: " + geofenceTransition.mTransition + " Location: " + geofenceTransition.mLocation + ":" + android.hardware.location.GeofenceHardwareImpl.this.mGeofences);
                        }
                    }
                    if (iGeofenceHardwareCallback != null) {
                        try {
                            iGeofenceHardwareCallback.onGeofenceTransition(geofenceTransition.mGeofenceId, geofenceTransition.mTransition, geofenceTransition.mLocation, geofenceTransition.mTimestamp, geofenceTransition.mMonitoringType);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                    android.hardware.location.GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 2:
                    int i2 = message.arg1;
                    synchronized (android.hardware.location.GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback2 = (android.hardware.location.IGeofenceHardwareCallback) android.hardware.location.GeofenceHardwareImpl.this.mGeofences.get(i2);
                    }
                    if (iGeofenceHardwareCallback2 != null) {
                        try {
                            iGeofenceHardwareCallback2.onGeofenceAdd(i2, message.arg2);
                        } catch (android.os.RemoteException e2) {
                            android.util.Log.i(android.hardware.location.GeofenceHardwareImpl.TAG, "Remote Exception:" + e2);
                        }
                    }
                    android.hardware.location.GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 3:
                    int i3 = message.arg1;
                    synchronized (android.hardware.location.GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback3 = (android.hardware.location.IGeofenceHardwareCallback) android.hardware.location.GeofenceHardwareImpl.this.mGeofences.get(i3);
                    }
                    if (iGeofenceHardwareCallback3 != null) {
                        try {
                            iGeofenceHardwareCallback3.onGeofenceRemove(i3, message.arg2);
                        } catch (android.os.RemoteException e3) {
                        }
                        android.os.IBinder asBinder = iGeofenceHardwareCallback3.asBinder();
                        synchronized (android.hardware.location.GeofenceHardwareImpl.this.mGeofences) {
                            android.hardware.location.GeofenceHardwareImpl.this.mGeofences.remove(i3);
                            int i4 = 0;
                            while (true) {
                                if (i4 < android.hardware.location.GeofenceHardwareImpl.this.mGeofences.size()) {
                                    if (((android.hardware.location.IGeofenceHardwareCallback) android.hardware.location.GeofenceHardwareImpl.this.mGeofences.valueAt(i4)).asBinder() != asBinder) {
                                        i4++;
                                    } else {
                                        i = 1;
                                    }
                                }
                            }
                        }
                        if (i == 0) {
                            java.util.Iterator it = android.hardware.location.GeofenceHardwareImpl.this.mReapers.iterator();
                            while (it.hasNext()) {
                                android.hardware.location.GeofenceHardwareImpl.Reaper reaper = (android.hardware.location.GeofenceHardwareImpl.Reaper) it.next();
                                if (reaper.mCallback != null && reaper.mCallback.asBinder() == asBinder) {
                                    it.remove();
                                    reaper.unlinkToDeath();
                                    if (android.hardware.location.GeofenceHardwareImpl.DEBUG) {
                                        android.util.Log.d(android.hardware.location.GeofenceHardwareImpl.TAG, java.lang.String.format("Removed reaper %s because binder %s is no longer needed.", reaper, asBinder));
                                    }
                                }
                            }
                        }
                    }
                    android.hardware.location.GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 4:
                    int i5 = message.arg1;
                    synchronized (android.hardware.location.GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback4 = (android.hardware.location.IGeofenceHardwareCallback) android.hardware.location.GeofenceHardwareImpl.this.mGeofences.get(i5);
                    }
                    if (iGeofenceHardwareCallback4 != null) {
                        try {
                            iGeofenceHardwareCallback4.onGeofencePause(i5, message.arg2);
                        } catch (android.os.RemoteException e4) {
                        }
                    }
                    android.hardware.location.GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 5:
                    int i6 = message.arg1;
                    synchronized (android.hardware.location.GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback5 = (android.hardware.location.IGeofenceHardwareCallback) android.hardware.location.GeofenceHardwareImpl.this.mGeofences.get(i6);
                    }
                    if (iGeofenceHardwareCallback5 != null) {
                        try {
                            iGeofenceHardwareCallback5.onGeofenceResume(i6, message.arg2);
                        } catch (android.os.RemoteException e5) {
                        }
                    }
                    android.hardware.location.GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 6:
                    android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback6 = (android.hardware.location.IGeofenceHardwareCallback) message.obj;
                    if (android.hardware.location.GeofenceHardwareImpl.DEBUG) {
                        android.util.Log.d(android.hardware.location.GeofenceHardwareImpl.TAG, "Geofence callback reaped:" + iGeofenceHardwareCallback6);
                    }
                    int i7 = message.arg1;
                    synchronized (android.hardware.location.GeofenceHardwareImpl.this.mGeofences) {
                        while (i < android.hardware.location.GeofenceHardwareImpl.this.mGeofences.size()) {
                            if (((android.hardware.location.IGeofenceHardwareCallback) android.hardware.location.GeofenceHardwareImpl.this.mGeofences.valueAt(i)).equals(iGeofenceHardwareCallback6)) {
                                int keyAt = android.hardware.location.GeofenceHardwareImpl.this.mGeofences.keyAt(i);
                                android.hardware.location.GeofenceHardwareImpl.this.removeGeofence(android.hardware.location.GeofenceHardwareImpl.this.mGeofences.keyAt(i), i7);
                                android.hardware.location.GeofenceHardwareImpl.this.mGeofences.remove(keyAt);
                            }
                            i++;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private android.os.Handler mCallbacksHandler = new android.os.Handler() { // from class: android.hardware.location.GeofenceHardwareImpl.2
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.hardware.location.GeofenceHardwareMonitorEvent geofenceHardwareMonitorEvent = (android.hardware.location.GeofenceHardwareMonitorEvent) message.obj;
                    java.util.ArrayList arrayList = android.hardware.location.GeofenceHardwareImpl.this.mCallbacks[geofenceHardwareMonitorEvent.getMonitoringType()];
                    if (arrayList != null) {
                        if (android.hardware.location.GeofenceHardwareImpl.DEBUG) {
                            android.util.Log.d(android.hardware.location.GeofenceHardwareImpl.TAG, "MonitoringSystemChangeCallback: " + geofenceHardwareMonitorEvent);
                        }
                        java.util.Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            try {
                                ((android.hardware.location.IGeofenceHardwareMonitorCallback) it.next()).onMonitoringSystemChange(geofenceHardwareMonitorEvent);
                            } catch (android.os.RemoteException e) {
                                android.util.Log.d(android.hardware.location.GeofenceHardwareImpl.TAG, "Error reporting onMonitoringSystemChange.", e);
                            }
                        }
                    }
                    android.hardware.location.GeofenceHardwareImpl.this.releaseWakeLock();
                    break;
                case 2:
                    int i = message.arg1;
                    android.hardware.location.IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback = (android.hardware.location.IGeofenceHardwareMonitorCallback) message.obj;
                    java.util.ArrayList arrayList2 = android.hardware.location.GeofenceHardwareImpl.this.mCallbacks[i];
                    if (arrayList2 == null) {
                        arrayList2 = new java.util.ArrayList();
                        android.hardware.location.GeofenceHardwareImpl.this.mCallbacks[i] = arrayList2;
                    }
                    if (!arrayList2.contains(iGeofenceHardwareMonitorCallback)) {
                        arrayList2.add(iGeofenceHardwareMonitorCallback);
                        break;
                    }
                    break;
                case 3:
                    int i2 = message.arg1;
                    android.hardware.location.IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback2 = (android.hardware.location.IGeofenceHardwareMonitorCallback) message.obj;
                    java.util.ArrayList arrayList3 = android.hardware.location.GeofenceHardwareImpl.this.mCallbacks[i2];
                    if (arrayList3 != null) {
                        arrayList3.remove(iGeofenceHardwareMonitorCallback2);
                        break;
                    }
                    break;
                case 4:
                    android.hardware.location.IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback3 = (android.hardware.location.IGeofenceHardwareMonitorCallback) message.obj;
                    if (android.hardware.location.GeofenceHardwareImpl.DEBUG) {
                        android.util.Log.d(android.hardware.location.GeofenceHardwareImpl.TAG, "Monitor callback reaped:" + iGeofenceHardwareMonitorCallback3);
                    }
                    java.util.ArrayList arrayList4 = android.hardware.location.GeofenceHardwareImpl.this.mCallbacks[message.arg1];
                    if (arrayList4 != null && arrayList4.contains(iGeofenceHardwareMonitorCallback3)) {
                        arrayList4.remove(iGeofenceHardwareMonitorCallback3);
                        break;
                    }
                    break;
            }
        }
    };
    private android.os.Handler mReaperHandler = new android.os.Handler() { // from class: android.hardware.location.GeofenceHardwareImpl.3
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback = (android.hardware.location.IGeofenceHardwareCallback) message.obj;
                    android.hardware.location.GeofenceHardwareImpl.Reaper reaper = android.hardware.location.GeofenceHardwareImpl.this.new Reaper(iGeofenceHardwareCallback, message.arg1);
                    if (!android.hardware.location.GeofenceHardwareImpl.this.mReapers.contains(reaper)) {
                        android.hardware.location.GeofenceHardwareImpl.this.mReapers.add(reaper);
                        try {
                            iGeofenceHardwareCallback.asBinder().linkToDeath(reaper, 0);
                            break;
                        } catch (android.os.RemoteException e) {
                            return;
                        }
                    }
                    break;
                case 2:
                    android.hardware.location.IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback = (android.hardware.location.IGeofenceHardwareMonitorCallback) message.obj;
                    android.hardware.location.GeofenceHardwareImpl.Reaper reaper2 = android.hardware.location.GeofenceHardwareImpl.this.new Reaper(iGeofenceHardwareMonitorCallback, message.arg1);
                    if (!android.hardware.location.GeofenceHardwareImpl.this.mReapers.contains(reaper2)) {
                        android.hardware.location.GeofenceHardwareImpl.this.mReapers.add(reaper2);
                        try {
                            iGeofenceHardwareMonitorCallback.asBinder().linkToDeath(reaper2, 0);
                            break;
                        } catch (android.os.RemoteException e2) {
                            return;
                        }
                    }
                    break;
                case 3:
                    android.hardware.location.GeofenceHardwareImpl.this.mReapers.remove((android.hardware.location.GeofenceHardwareImpl.Reaper) message.obj);
                    break;
            }
        }
    };

    public static synchronized android.hardware.location.GeofenceHardwareImpl getInstance(android.content.Context context) {
        android.hardware.location.GeofenceHardwareImpl geofenceHardwareImpl;
        synchronized (android.hardware.location.GeofenceHardwareImpl.class) {
            if (sInstance == null) {
                sInstance = new android.hardware.location.GeofenceHardwareImpl(context);
            }
            geofenceHardwareImpl = sInstance;
        }
        return geofenceHardwareImpl;
    }

    private GeofenceHardwareImpl(android.content.Context context) {
        this.mContext = context;
        setMonitorAvailability(0, 2);
        setMonitorAvailability(1, 2);
    }

    private void acquireWakeLock() {
        if (this.mWakeLock == null) {
            this.mWakeLock = ((android.os.PowerManager) this.mContext.getSystemService(android.content.Context.POWER_SERVICE)).newWakeLock(1, TAG);
        }
        this.mWakeLock.acquire();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseWakeLock() {
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    private void updateGpsHardwareAvailability() {
        boolean z;
        try {
            z = this.mGpsService.isHardwareGeofenceSupported();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Remote Exception calling LocationManagerService");
            z = false;
        }
        if (z) {
            setMonitorAvailability(0, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0014 A[Catch: RemoteException -> 0x0024, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x0024, blocks: (B:3:0x0002, B:5:0x0007, B:9:0x0010, B:11:0x0014), top: B:2:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateFusedHardwareAvailability() {
        boolean z;
        boolean z2;
        try {
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException calling LocationManagerService");
            z = false;
        }
        if (this.mVersion >= 2 && (this.mCapabilities & 1) == 0) {
            z2 = false;
            if (this.mFusedService == null) {
                z = this.mFusedService.isSupported() && z2;
            } else {
                z = false;
            }
            if (!z) {
                setMonitorAvailability(1, 0);
                return;
            }
            return;
        }
        z2 = true;
        if (this.mFusedService == null) {
        }
        if (!z) {
        }
    }

    public void setGpsHardwareGeofence(android.location.IGpsGeofenceHardware iGpsGeofenceHardware) {
        if (this.mGpsService == null) {
            this.mGpsService = iGpsGeofenceHardware;
            updateGpsHardwareAvailability();
        } else if (iGpsGeofenceHardware != null) {
            android.util.Log.e(TAG, "Error: GpsService being set again.");
        } else {
            this.mGpsService = null;
            android.util.Log.w(TAG, "GPS Geofence Hardware service seems to have crashed");
        }
    }

    public void onCapabilities(int i) {
        this.mCapabilities = i;
        updateFusedHardwareAvailability();
    }

    public void setVersion(int i) {
        this.mVersion = i;
        updateFusedHardwareAvailability();
    }

    public void setFusedGeofenceHardware(android.location.IFusedGeofenceHardware iFusedGeofenceHardware) {
        if (this.mFusedService == null) {
            this.mFusedService = iFusedGeofenceHardware;
            updateFusedHardwareAvailability();
        } else if (iFusedGeofenceHardware != null) {
            android.util.Log.e(TAG, "Error: FusedService being set again");
        } else {
            this.mFusedService = null;
            android.util.Log.w(TAG, "Fused Geofence Hardware service seems to have crashed");
        }
    }

    public int[] getMonitoringTypes() {
        boolean z;
        boolean z2;
        synchronized (this.mSupportedMonitorTypes) {
            z = this.mSupportedMonitorTypes[0] != 2;
            z2 = this.mSupportedMonitorTypes[1] != 2;
        }
        if (z) {
            if (z2) {
                return new int[]{0, 1};
            }
            return new int[]{0};
        }
        if (z2) {
            return new int[]{1};
        }
        return new int[0];
    }

    public int getStatusOfMonitoringType(int i) {
        int i2;
        synchronized (this.mSupportedMonitorTypes) {
            if (i >= this.mSupportedMonitorTypes.length || i < 0) {
                throw new java.lang.IllegalArgumentException("Unknown monitoring type");
            }
            i2 = this.mSupportedMonitorTypes[i];
        }
        return i2;
    }

    public int getCapabilitiesForMonitoringType(int i) {
        switch (this.mSupportedMonitorTypes[i]) {
            case 0:
                switch (i) {
                    case 0:
                        return 1;
                    case 1:
                        if (this.mVersion < 2) {
                            return 1;
                        }
                        return this.mCapabilities;
                    default:
                        return 0;
                }
            default:
                return 0;
        }
    }

    public boolean addCircularFence(int i, android.hardware.location.GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable, android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback) {
        int id = geofenceHardwareRequestParcelable.getId();
        if (DEBUG) {
            android.util.Log.d(TAG, java.lang.String.format("addCircularFence: monitoringType=%d, %s", java.lang.Integer.valueOf(i), geofenceHardwareRequestParcelable));
        }
        synchronized (this.mGeofences) {
            this.mGeofences.put(id, iGeofenceHardwareCallback);
        }
        boolean z = false;
        switch (i) {
            case 0:
                if (this.mGpsService != null) {
                    try {
                        z = this.mGpsService.addCircularHardwareGeofence(geofenceHardwareRequestParcelable.getId(), geofenceHardwareRequestParcelable.getLatitude(), geofenceHardwareRequestParcelable.getLongitude(), geofenceHardwareRequestParcelable.getRadius(), geofenceHardwareRequestParcelable.getLastTransition(), geofenceHardwareRequestParcelable.getMonitorTransitions(), geofenceHardwareRequestParcelable.getNotificationResponsiveness(), geofenceHardwareRequestParcelable.getUnknownTimer());
                        break;
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(TAG, "AddGeofence: Remote Exception calling LocationManagerService");
                        break;
                    }
                } else {
                    return false;
                }
            case 1:
                if (this.mFusedService != null) {
                    try {
                        this.mFusedService.addGeofences(new android.hardware.location.GeofenceHardwareRequestParcelable[]{geofenceHardwareRequestParcelable});
                        z = true;
                        break;
                    } catch (android.os.RemoteException e2) {
                        android.util.Log.e(TAG, "AddGeofence: RemoteException calling LocationManagerService");
                        break;
                    }
                } else {
                    return false;
                }
        }
        if (z) {
            android.os.Message obtainMessage = this.mReaperHandler.obtainMessage(1, iGeofenceHardwareCallback);
            obtainMessage.arg1 = i;
            this.mReaperHandler.sendMessage(obtainMessage);
        } else {
            synchronized (this.mGeofences) {
                this.mGeofences.remove(id);
            }
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "addCircularFence: Result is: " + z);
        }
        return z;
    }

    public boolean removeGeofence(int i, int i2) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Remove Geofence: GeofenceId: " + i);
        }
        synchronized (this.mGeofences) {
            if (this.mGeofences.get(i) == null) {
                throw new java.lang.IllegalArgumentException("Geofence " + i + " not registered.");
            }
        }
        boolean z = false;
        switch (i2) {
            case 0:
                if (this.mGpsService == null) {
                    return false;
                }
                try {
                    z = this.mGpsService.removeHardwareGeofence(i);
                    break;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "RemoveGeofence: Remote Exception calling LocationManagerService");
                    break;
                }
            case 1:
                if (this.mFusedService == null) {
                    return false;
                }
                try {
                    this.mFusedService.removeGeofences(new int[]{i});
                    z = true;
                    break;
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(TAG, "RemoveGeofence: RemoteException calling LocationManagerService");
                    break;
                }
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "removeGeofence: Result is: " + z);
        }
        return z;
    }

    public boolean pauseGeofence(int i, int i2) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Pause Geofence: GeofenceId: " + i);
        }
        synchronized (this.mGeofences) {
            if (this.mGeofences.get(i) == null) {
                throw new java.lang.IllegalArgumentException("Geofence " + i + " not registered.");
            }
        }
        boolean z = false;
        switch (i2) {
            case 0:
                if (this.mGpsService == null) {
                    return false;
                }
                try {
                    z = this.mGpsService.pauseHardwareGeofence(i);
                    break;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "PauseGeofence: Remote Exception calling LocationManagerService");
                    break;
                }
            case 1:
                if (this.mFusedService == null) {
                    return false;
                }
                try {
                    this.mFusedService.pauseMonitoringGeofence(i);
                    z = true;
                    break;
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(TAG, "PauseGeofence: RemoteException calling LocationManagerService");
                    break;
                }
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "pauseGeofence: Result is: " + z);
        }
        return z;
    }

    public boolean resumeGeofence(int i, int i2, int i3) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Resume Geofence: GeofenceId: " + i);
        }
        synchronized (this.mGeofences) {
            if (this.mGeofences.get(i) == null) {
                throw new java.lang.IllegalArgumentException("Geofence " + i + " not registered.");
            }
        }
        boolean z = false;
        switch (i2) {
            case 0:
                if (this.mGpsService == null) {
                    return false;
                }
                try {
                    z = this.mGpsService.resumeHardwareGeofence(i, i3);
                    break;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "ResumeGeofence: Remote Exception calling LocationManagerService");
                    break;
                }
            case 1:
                if (this.mFusedService == null) {
                    return false;
                }
                try {
                    this.mFusedService.resumeMonitoringGeofence(i, i3);
                    z = true;
                    break;
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(TAG, "ResumeGeofence: RemoteException calling LocationManagerService");
                    break;
                }
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "resumeGeofence: Result is: " + z);
        }
        return z;
    }

    public boolean registerForMonitorStateChangeCallback(int i, android.hardware.location.IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) {
        android.os.Message obtainMessage = this.mReaperHandler.obtainMessage(2, iGeofenceHardwareMonitorCallback);
        obtainMessage.arg1 = i;
        this.mReaperHandler.sendMessage(obtainMessage);
        android.os.Message obtainMessage2 = this.mCallbacksHandler.obtainMessage(2, iGeofenceHardwareMonitorCallback);
        obtainMessage2.arg1 = i;
        this.mCallbacksHandler.sendMessage(obtainMessage2);
        return true;
    }

    public boolean unregisterForMonitorStateChangeCallback(int i, android.hardware.location.IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) {
        android.os.Message obtainMessage = this.mCallbacksHandler.obtainMessage(3, iGeofenceHardwareMonitorCallback);
        obtainMessage.arg1 = i;
        this.mCallbacksHandler.sendMessage(obtainMessage);
        return true;
    }

    public void reportGeofenceTransition(int i, android.location.Location location, int i2, long j, int i3, int i4) {
        if (location == null) {
            android.util.Log.e(TAG, java.lang.String.format("Invalid Geofence Transition: location=null", new java.lang.Object[0]));
            return;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "GeofenceTransition| " + location + ", transition:" + i2 + ", transitionTimestamp:" + j + ", monitoringType:" + i3 + ", sourcesUsed:" + i4);
        }
        android.hardware.location.GeofenceHardwareImpl.GeofenceTransition geofenceTransition = new android.hardware.location.GeofenceHardwareImpl.GeofenceTransition(i, i2, j, location, i3, i4);
        acquireWakeLock();
        this.mGeofenceHandler.obtainMessage(1, geofenceTransition).sendToTarget();
    }

    public void reportGeofenceMonitorStatus(int i, int i2, android.location.Location location, int i3) {
        setMonitorAvailability(i, i2);
        acquireWakeLock();
        this.mCallbacksHandler.obtainMessage(1, new android.hardware.location.GeofenceHardwareMonitorEvent(i, i2, i3, location)).sendToTarget();
    }

    private void reportGeofenceOperationStatus(int i, int i2, int i3) {
        acquireWakeLock();
        android.os.Message obtainMessage = this.mGeofenceHandler.obtainMessage(i);
        obtainMessage.arg1 = i2;
        obtainMessage.arg2 = i3;
        obtainMessage.sendToTarget();
    }

    public void reportGeofenceAddStatus(int i, int i2) {
        if (DEBUG) {
            android.util.Log.d(TAG, "AddCallback| id:" + i + ", status:" + i2);
        }
        reportGeofenceOperationStatus(2, i, i2);
    }

    public void reportGeofenceRemoveStatus(int i, int i2) {
        if (DEBUG) {
            android.util.Log.d(TAG, "RemoveCallback| id:" + i + ", status:" + i2);
        }
        reportGeofenceOperationStatus(3, i, i2);
    }

    public void reportGeofencePauseStatus(int i, int i2) {
        if (DEBUG) {
            android.util.Log.d(TAG, "PauseCallbac| id:" + i + ", status" + i2);
        }
        reportGeofenceOperationStatus(4, i, i2);
    }

    public void reportGeofenceResumeStatus(int i, int i2) {
        if (DEBUG) {
            android.util.Log.d(TAG, "ResumeCallback| id:" + i + ", status:" + i2);
        }
        reportGeofenceOperationStatus(5, i, i2);
    }

    private class GeofenceTransition {
        private int mGeofenceId;
        private android.location.Location mLocation;
        private int mMonitoringType;
        private int mSourcesUsed;
        private long mTimestamp;
        private int mTransition;

        GeofenceTransition(int i, int i2, long j, android.location.Location location, int i3, int i4) {
            this.mGeofenceId = i;
            this.mTransition = i2;
            this.mTimestamp = j;
            this.mLocation = location;
            this.mMonitoringType = i3;
            this.mSourcesUsed = i4;
        }
    }

    private void setMonitorAvailability(int i, int i2) {
        synchronized (this.mSupportedMonitorTypes) {
            this.mSupportedMonitorTypes[i] = i2;
        }
    }

    int getMonitoringResolutionLevel(int i) {
        switch (i) {
        }
        return 3;
    }

    class Reaper implements android.os.IBinder.DeathRecipient {
        private android.hardware.location.IGeofenceHardwareCallback mCallback;
        private android.hardware.location.IGeofenceHardwareMonitorCallback mMonitorCallback;
        private int mMonitoringType;

        Reaper(android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback, int i) {
            this.mCallback = iGeofenceHardwareCallback;
            this.mMonitoringType = i;
        }

        Reaper(android.hardware.location.IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback, int i) {
            this.mMonitorCallback = iGeofenceHardwareMonitorCallback;
            this.mMonitoringType = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (this.mCallback != null) {
                android.os.Message obtainMessage = android.hardware.location.GeofenceHardwareImpl.this.mGeofenceHandler.obtainMessage(6, this.mCallback);
                obtainMessage.arg1 = this.mMonitoringType;
                android.hardware.location.GeofenceHardwareImpl.this.mGeofenceHandler.sendMessage(obtainMessage);
            } else if (this.mMonitorCallback != null) {
                android.os.Message obtainMessage2 = android.hardware.location.GeofenceHardwareImpl.this.mCallbacksHandler.obtainMessage(4, this.mMonitorCallback);
                obtainMessage2.arg1 = this.mMonitoringType;
                android.hardware.location.GeofenceHardwareImpl.this.mCallbacksHandler.sendMessage(obtainMessage2);
            }
            android.hardware.location.GeofenceHardwareImpl.this.mReaperHandler.sendMessage(android.hardware.location.GeofenceHardwareImpl.this.mReaperHandler.obtainMessage(3, this));
        }

        public int hashCode() {
            return ((((527 + (this.mCallback != null ? this.mCallback.asBinder().hashCode() : 0)) * 31) + (this.mMonitorCallback != null ? this.mMonitorCallback.asBinder().hashCode() : 0)) * 31) + this.mMonitoringType;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            android.hardware.location.GeofenceHardwareImpl.Reaper reaper = (android.hardware.location.GeofenceHardwareImpl.Reaper) obj;
            if (!binderEquals(reaper.mCallback, this.mCallback) || !binderEquals(reaper.mMonitorCallback, this.mMonitorCallback) || reaper.mMonitoringType != this.mMonitoringType) {
                return false;
            }
            return true;
        }

        private boolean binderEquals(android.os.IInterface iInterface, android.os.IInterface iInterface2) {
            return iInterface == null ? iInterface2 == null : iInterface2 != null && iInterface.asBinder() == iInterface2.asBinder();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean unlinkToDeath() {
            if (this.mMonitorCallback != null) {
                return this.mMonitorCallback.asBinder().unlinkToDeath(this, 0);
            }
            if (this.mCallback != null) {
                return this.mCallback.asBinder().unlinkToDeath(this, 0);
            }
            return true;
        }

        private boolean callbackEquals(android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback) {
            return this.mCallback != null && this.mCallback.asBinder() == iGeofenceHardwareCallback.asBinder();
        }
    }

    int getAllowedResolutionLevel(int i, int i2) {
        if (this.mContext.checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, i, i2) == 0) {
            return 3;
        }
        if (this.mContext.checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, i, i2) == 0) {
            return 2;
        }
        return 1;
    }
}
