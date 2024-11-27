package com.android.server.location.gnss;

/* loaded from: classes2.dex */
class GnssGeofenceProxy extends android.location.IGpsGeofenceHardware.Stub implements com.android.server.location.gnss.hal.GnssNative.BaseCallbacks {
    private final com.android.server.location.gnss.hal.GnssNative mGnssNative;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.location.gnss.GnssGeofenceProxy.GeofenceEntry> mGeofenceEntries = new android.util.SparseArray<>();

    private static class GeofenceEntry {
        public int geofenceId;
        public int lastTransition;
        public double latitude;
        public double longitude;
        public int monitorTransitions;
        public int notificationResponsiveness;
        public boolean paused;
        public double radius;
        public int unknownTimer;

        private GeofenceEntry() {
        }
    }

    GnssGeofenceProxy(com.android.server.location.gnss.hal.GnssNative gnssNative) {
        this.mGnssNative = gnssNative;
        this.mGnssNative.addBaseCallbacks(this);
    }

    public boolean isHardwareGeofenceSupported() {
        boolean isGeofencingSupported;
        synchronized (this.mLock) {
            isGeofencingSupported = this.mGnssNative.isGeofencingSupported();
        }
        return isGeofencingSupported;
    }

    public boolean addCircularHardwareGeofence(int i, double d, double d2, double d3, int i2, int i3, int i4, int i5) {
        boolean addGeofence;
        synchronized (this.mLock) {
            try {
                addGeofence = this.mGnssNative.addGeofence(i, d, d2, d3, i2, i3, i4, i5);
                if (addGeofence) {
                    com.android.server.location.gnss.GnssGeofenceProxy.GeofenceEntry geofenceEntry = new com.android.server.location.gnss.GnssGeofenceProxy.GeofenceEntry();
                    geofenceEntry.geofenceId = i;
                    geofenceEntry.latitude = d;
                    geofenceEntry.longitude = d2;
                    geofenceEntry.radius = d3;
                    geofenceEntry.lastTransition = i2;
                    geofenceEntry.monitorTransitions = i3;
                    geofenceEntry.notificationResponsiveness = i4;
                    geofenceEntry.unknownTimer = i5;
                    this.mGeofenceEntries.put(i, geofenceEntry);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return addGeofence;
    }

    public boolean removeHardwareGeofence(int i) {
        boolean removeGeofence;
        synchronized (this.mLock) {
            try {
                removeGeofence = this.mGnssNative.removeGeofence(i);
                if (removeGeofence) {
                    this.mGeofenceEntries.remove(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return removeGeofence;
    }

    public boolean pauseHardwareGeofence(int i) {
        boolean pauseGeofence;
        com.android.server.location.gnss.GnssGeofenceProxy.GeofenceEntry geofenceEntry;
        synchronized (this.mLock) {
            try {
                pauseGeofence = this.mGnssNative.pauseGeofence(i);
                if (pauseGeofence && (geofenceEntry = this.mGeofenceEntries.get(i)) != null) {
                    geofenceEntry.paused = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return pauseGeofence;
    }

    public boolean resumeHardwareGeofence(int i, int i2) {
        boolean resumeGeofence;
        com.android.server.location.gnss.GnssGeofenceProxy.GeofenceEntry geofenceEntry;
        synchronized (this.mLock) {
            try {
                resumeGeofence = this.mGnssNative.resumeGeofence(i, i2);
                if (resumeGeofence && (geofenceEntry = this.mGeofenceEntries.get(i)) != null) {
                    geofenceEntry.paused = false;
                    geofenceEntry.monitorTransitions = i2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return resumeGeofence;
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mGeofenceEntries.size(); i++) {
                try {
                    com.android.server.location.gnss.GnssGeofenceProxy.GeofenceEntry valueAt = this.mGeofenceEntries.valueAt(i);
                    if (this.mGnssNative.addGeofence(valueAt.geofenceId, valueAt.latitude, valueAt.longitude, valueAt.radius, valueAt.lastTransition, valueAt.monitorTransitions, valueAt.notificationResponsiveness, valueAt.unknownTimer) && valueAt.paused) {
                        this.mGnssNative.pauseGeofence(valueAt.geofenceId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
