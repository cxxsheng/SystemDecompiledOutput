package android.hardware.location;

/* loaded from: classes2.dex */
public class GeofenceHardwareService extends android.app.Service {
    private android.os.IBinder mBinder = new android.hardware.location.IGeofenceHardware.Stub() { // from class: android.hardware.location.GeofenceHardwareService.1
        @Override // android.hardware.location.IGeofenceHardware
        public void setGpsGeofenceHardware(android.location.IGpsGeofenceHardware iGpsGeofenceHardware) {
            android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.setGpsHardwareGeofence(iGpsGeofenceHardware);
        }

        @Override // android.hardware.location.IGeofenceHardware
        public void setFusedGeofenceHardware(android.location.IFusedGeofenceHardware iFusedGeofenceHardware) {
            android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.setFusedGeofenceHardware(iFusedGeofenceHardware);
        }

        @Override // android.hardware.location.IGeofenceHardware
        public int[] getMonitoringTypes() {
            super.getMonitoringTypes_enforcePermission();
            return android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.getMonitoringTypes();
        }

        @Override // android.hardware.location.IGeofenceHardware
        public int getStatusOfMonitoringType(int i) {
            super.getStatusOfMonitoringType_enforcePermission();
            return android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.getStatusOfMonitoringType(i);
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean addCircularFence(int i, android.hardware.location.GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable, android.hardware.location.IGeofenceHardwareCallback iGeofenceHardwareCallback) {
            super.addCircularFence_enforcePermission();
            android.hardware.location.GeofenceHardwareService.this.checkPermission(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i);
            return android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.addCircularFence(i, geofenceHardwareRequestParcelable, iGeofenceHardwareCallback);
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean removeGeofence(int i, int i2) {
            super.removeGeofence_enforcePermission();
            android.hardware.location.GeofenceHardwareService.this.checkPermission(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2);
            return android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.removeGeofence(i, i2);
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean pauseGeofence(int i, int i2) {
            super.pauseGeofence_enforcePermission();
            android.hardware.location.GeofenceHardwareService.this.checkPermission(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2);
            return android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.pauseGeofence(i, i2);
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean resumeGeofence(int i, int i2, int i3) {
            super.resumeGeofence_enforcePermission();
            android.hardware.location.GeofenceHardwareService.this.checkPermission(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2);
            return android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.resumeGeofence(i, i2, i3);
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean registerForMonitorStateChangeCallback(int i, android.hardware.location.IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) {
            super.registerForMonitorStateChangeCallback_enforcePermission();
            android.hardware.location.GeofenceHardwareService.this.checkPermission(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i);
            return android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.registerForMonitorStateChangeCallback(i, iGeofenceHardwareMonitorCallback);
        }

        @Override // android.hardware.location.IGeofenceHardware
        public boolean unregisterForMonitorStateChangeCallback(int i, android.hardware.location.IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) {
            super.unregisterForMonitorStateChangeCallback_enforcePermission();
            android.hardware.location.GeofenceHardwareService.this.checkPermission(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i);
            return android.hardware.location.GeofenceHardwareService.this.mGeofenceHardwareImpl.unregisterForMonitorStateChangeCallback(i, iGeofenceHardwareMonitorCallback);
        }
    };
    private android.content.Context mContext;
    private android.hardware.location.GeofenceHardwareImpl mGeofenceHardwareImpl;

    @Override // android.app.Service
    public void onCreate() {
        this.mContext = this;
        this.mGeofenceHardwareImpl = android.hardware.location.GeofenceHardwareImpl.getInstance(this.mContext);
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mGeofenceHardwareImpl = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPermission(int i, int i2, int i3) {
        if (this.mGeofenceHardwareImpl.getAllowedResolutionLevel(i, i2) < this.mGeofenceHardwareImpl.getMonitoringResolutionLevel(i3)) {
            throw new java.lang.SecurityException("Insufficient permissions to access hardware geofence for type: " + i3);
        }
    }
}
