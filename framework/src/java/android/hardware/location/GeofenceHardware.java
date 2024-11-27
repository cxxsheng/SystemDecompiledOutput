package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class GeofenceHardware {
    public static final int GEOFENCE_ENTERED = 1;
    public static final int GEOFENCE_ERROR_ID_EXISTS = 2;
    public static final int GEOFENCE_ERROR_ID_UNKNOWN = 3;
    public static final int GEOFENCE_ERROR_INSUFFICIENT_MEMORY = 6;
    public static final int GEOFENCE_ERROR_INVALID_TRANSITION = 4;
    public static final int GEOFENCE_ERROR_TOO_MANY_GEOFENCES = 1;
    public static final int GEOFENCE_EXITED = 2;
    public static final int GEOFENCE_FAILURE = 5;
    public static final int GEOFENCE_SUCCESS = 0;
    public static final int GEOFENCE_UNCERTAIN = 4;
    public static final int MONITORING_TYPE_FUSED_HARDWARE = 1;
    public static final int MONITORING_TYPE_GPS_HARDWARE = 0;
    public static final int MONITOR_CURRENTLY_AVAILABLE = 0;
    public static final int MONITOR_CURRENTLY_UNAVAILABLE = 1;
    public static final int MONITOR_UNSUPPORTED = 2;
    static final int NUM_MONITORS = 2;
    public static final int SOURCE_TECHNOLOGY_BLUETOOTH = 16;
    public static final int SOURCE_TECHNOLOGY_CELL = 8;
    public static final int SOURCE_TECHNOLOGY_GNSS = 1;
    public static final int SOURCE_TECHNOLOGY_SENSORS = 4;
    public static final int SOURCE_TECHNOLOGY_WIFI = 2;
    private java.util.HashMap<android.hardware.location.GeofenceHardwareCallback, android.hardware.location.GeofenceHardware.GeofenceHardwareCallbackWrapper> mCallbacks = new java.util.HashMap<>();
    private java.util.HashMap<android.hardware.location.GeofenceHardwareMonitorCallback, android.hardware.location.GeofenceHardware.GeofenceHardwareMonitorCallbackWrapper> mMonitorCallbacks = new java.util.HashMap<>();
    private android.hardware.location.IGeofenceHardware mService;

    public GeofenceHardware(android.hardware.location.IGeofenceHardware iGeofenceHardware) {
        this.mService = iGeofenceHardware;
    }

    public int[] getMonitoringTypes() {
        try {
            return this.mService.getMonitoringTypes();
        } catch (android.os.RemoteException e) {
            return new int[0];
        }
    }

    public int getStatusOfMonitoringType(int i) {
        try {
            return this.mService.getStatusOfMonitoringType(i);
        } catch (android.os.RemoteException e) {
            return 2;
        }
    }

    public boolean addGeofence(int i, int i2, android.hardware.location.GeofenceHardwareRequest geofenceHardwareRequest, android.hardware.location.GeofenceHardwareCallback geofenceHardwareCallback) {
        try {
            if (geofenceHardwareRequest.getType() == 0) {
                return this.mService.addCircularFence(i2, new android.hardware.location.GeofenceHardwareRequestParcelable(i, geofenceHardwareRequest), getCallbackWrapper(geofenceHardwareCallback));
            }
            throw new java.lang.IllegalArgumentException("Geofence Request type not supported");
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean removeGeofence(int i, int i2) {
        try {
            return this.mService.removeGeofence(i, i2);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean pauseGeofence(int i, int i2) {
        try {
            return this.mService.pauseGeofence(i, i2);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean resumeGeofence(int i, int i2, int i3) {
        try {
            return this.mService.resumeGeofence(i, i2, i3);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean registerForMonitorStateChangeCallback(int i, android.hardware.location.GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
        try {
            return this.mService.registerForMonitorStateChangeCallback(i, getMonitorCallbackWrapper(geofenceHardwareMonitorCallback));
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean unregisterForMonitorStateChangeCallback(int i, android.hardware.location.GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
        boolean z = false;
        try {
            z = this.mService.unregisterForMonitorStateChangeCallback(i, getMonitorCallbackWrapper(geofenceHardwareMonitorCallback));
            if (z) {
                removeMonitorCallback(geofenceHardwareMonitorCallback);
            }
        } catch (android.os.RemoteException e) {
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCallback(android.hardware.location.GeofenceHardwareCallback geofenceHardwareCallback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(geofenceHardwareCallback);
        }
    }

    private android.hardware.location.GeofenceHardware.GeofenceHardwareCallbackWrapper getCallbackWrapper(android.hardware.location.GeofenceHardwareCallback geofenceHardwareCallback) {
        android.hardware.location.GeofenceHardware.GeofenceHardwareCallbackWrapper geofenceHardwareCallbackWrapper;
        synchronized (this.mCallbacks) {
            geofenceHardwareCallbackWrapper = this.mCallbacks.get(geofenceHardwareCallback);
            if (geofenceHardwareCallbackWrapper == null) {
                geofenceHardwareCallbackWrapper = new android.hardware.location.GeofenceHardware.GeofenceHardwareCallbackWrapper(geofenceHardwareCallback);
                this.mCallbacks.put(geofenceHardwareCallback, geofenceHardwareCallbackWrapper);
            }
        }
        return geofenceHardwareCallbackWrapper;
    }

    private void removeMonitorCallback(android.hardware.location.GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
        synchronized (this.mMonitorCallbacks) {
            this.mMonitorCallbacks.remove(geofenceHardwareMonitorCallback);
        }
    }

    private android.hardware.location.GeofenceHardware.GeofenceHardwareMonitorCallbackWrapper getMonitorCallbackWrapper(android.hardware.location.GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
        android.hardware.location.GeofenceHardware.GeofenceHardwareMonitorCallbackWrapper geofenceHardwareMonitorCallbackWrapper;
        synchronized (this.mMonitorCallbacks) {
            geofenceHardwareMonitorCallbackWrapper = this.mMonitorCallbacks.get(geofenceHardwareMonitorCallback);
            if (geofenceHardwareMonitorCallbackWrapper == null) {
                geofenceHardwareMonitorCallbackWrapper = new android.hardware.location.GeofenceHardware.GeofenceHardwareMonitorCallbackWrapper(geofenceHardwareMonitorCallback);
                this.mMonitorCallbacks.put(geofenceHardwareMonitorCallback, geofenceHardwareMonitorCallbackWrapper);
            }
        }
        return geofenceHardwareMonitorCallbackWrapper;
    }

    class GeofenceHardwareMonitorCallbackWrapper extends android.hardware.location.IGeofenceHardwareMonitorCallback.Stub {
        private java.lang.ref.WeakReference<android.hardware.location.GeofenceHardwareMonitorCallback> mCallback;

        GeofenceHardwareMonitorCallbackWrapper(android.hardware.location.GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback) {
            this.mCallback = new java.lang.ref.WeakReference<>(geofenceHardwareMonitorCallback);
        }

        @Override // android.hardware.location.IGeofenceHardwareMonitorCallback
        public void onMonitoringSystemChange(android.hardware.location.GeofenceHardwareMonitorEvent geofenceHardwareMonitorEvent) {
            android.hardware.location.GeofenceHardwareMonitorCallback geofenceHardwareMonitorCallback = this.mCallback.get();
            if (geofenceHardwareMonitorCallback == null) {
                return;
            }
            geofenceHardwareMonitorCallback.onMonitoringSystemChange(geofenceHardwareMonitorEvent.getMonitoringType(), geofenceHardwareMonitorEvent.getMonitoringStatus() == 0, geofenceHardwareMonitorEvent.getLocation());
            geofenceHardwareMonitorCallback.onMonitoringSystemChange(geofenceHardwareMonitorEvent);
        }
    }

    class GeofenceHardwareCallbackWrapper extends android.hardware.location.IGeofenceHardwareCallback.Stub {
        private java.lang.ref.WeakReference<android.hardware.location.GeofenceHardwareCallback> mCallback;

        GeofenceHardwareCallbackWrapper(android.hardware.location.GeofenceHardwareCallback geofenceHardwareCallback) {
            this.mCallback = new java.lang.ref.WeakReference<>(geofenceHardwareCallback);
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceTransition(int i, int i2, android.location.Location location, long j, int i3) {
            android.hardware.location.GeofenceHardwareCallback geofenceHardwareCallback = this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofenceTransition(i, i2, location, j, i3);
            }
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceAdd(int i, int i2) {
            android.hardware.location.GeofenceHardwareCallback geofenceHardwareCallback = this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofenceAdd(i, i2);
            }
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceRemove(int i, int i2) {
            android.hardware.location.GeofenceHardwareCallback geofenceHardwareCallback = this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofenceRemove(i, i2);
                android.hardware.location.GeofenceHardware.this.removeCallback(geofenceHardwareCallback);
            }
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofencePause(int i, int i2) {
            android.hardware.location.GeofenceHardwareCallback geofenceHardwareCallback = this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofencePause(i, i2);
            }
        }

        @Override // android.hardware.location.IGeofenceHardwareCallback
        public void onGeofenceResume(int i, int i2) {
            android.hardware.location.GeofenceHardwareCallback geofenceHardwareCallback = this.mCallback.get();
            if (geofenceHardwareCallback != null) {
                geofenceHardwareCallback.onGeofenceResume(i, i2);
            }
        }
    }
}
