package com.android.server.location.countrydetector;

/* loaded from: classes2.dex */
public class LocationBasedCountryDetector extends com.android.server.location.countrydetector.CountryDetectorBase {
    private static final long QUERY_LOCATION_TIMEOUT = 300000;
    private static final java.lang.String TAG = "LocationBasedCountryDetector";
    private java.util.List<java.lang.String> mEnabledProviders;
    protected java.util.List<android.location.LocationListener> mLocationListeners;
    private android.location.LocationManager mLocationManager;
    protected java.lang.Thread mQueryThread;
    protected java.util.Timer mTimer;

    public LocationBasedCountryDetector(android.content.Context context) {
        super(context);
        this.mLocationManager = (android.location.LocationManager) context.getSystemService("location");
    }

    protected java.lang.String getCountryFromLocation(android.location.Location location) {
        try {
            java.util.List<android.location.Address> fromLocation = new android.location.Geocoder(this.mContext).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (fromLocation == null || fromLocation.size() <= 0) {
                return null;
            }
            return fromLocation.get(0).getCountryCode();
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Exception occurs when getting country from location");
            return null;
        }
    }

    protected boolean isAcceptableProvider(java.lang.String str) {
        return "passive".equals(str);
    }

    protected void registerListener(java.lang.String str, android.location.LocationListener locationListener) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mLocationManager.requestLocationUpdates(str, 0L, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, locationListener);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    protected void unregisterListener(android.location.LocationListener locationListener) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mLocationManager.removeUpdates(locationListener);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    protected android.location.Location getLastKnownLocation() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.Iterator<java.lang.String> it = this.mLocationManager.getAllProviders().iterator();
            android.location.Location location = null;
            while (it.hasNext()) {
                android.location.Location lastKnownLocation = this.mLocationManager.getLastKnownLocation(it.next());
                if (lastKnownLocation != null && (location == null || location.getElapsedRealtimeNanos() < lastKnownLocation.getElapsedRealtimeNanos())) {
                    location = lastKnownLocation;
                }
            }
            return location;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    protected long getQueryLocationTimeout() {
        return 300000L;
    }

    protected java.util.List<java.lang.String> getEnabledProviders() {
        if (this.mEnabledProviders == null) {
            this.mEnabledProviders = this.mLocationManager.getProviders(true);
        }
        return this.mEnabledProviders;
    }

    @Override // com.android.server.location.countrydetector.CountryDetectorBase
    public synchronized android.location.Country detectCountry() {
        try {
            if (this.mLocationListeners != null) {
                throw new java.lang.IllegalStateException();
            }
            java.util.List<java.lang.String> enabledProviders = getEnabledProviders();
            int size = enabledProviders.size();
            if (size > 0) {
                this.mLocationListeners = new java.util.ArrayList(size);
                for (int i = 0; i < size; i++) {
                    java.lang.String str = enabledProviders.get(i);
                    if (isAcceptableProvider(str)) {
                        android.location.LocationListener locationListener = new android.location.LocationListener() { // from class: com.android.server.location.countrydetector.LocationBasedCountryDetector.1
                            @Override // android.location.LocationListener
                            public void onLocationChanged(android.location.Location location) {
                                if (location != null) {
                                    com.android.server.location.countrydetector.LocationBasedCountryDetector.this.stop();
                                    com.android.server.location.countrydetector.LocationBasedCountryDetector.this.queryCountryCode(location);
                                }
                            }

                            @Override // android.location.LocationListener
                            public void onProviderDisabled(java.lang.String str2) {
                            }

                            @Override // android.location.LocationListener
                            public void onProviderEnabled(java.lang.String str2) {
                            }

                            @Override // android.location.LocationListener
                            public void onStatusChanged(java.lang.String str2, int i2, android.os.Bundle bundle) {
                            }
                        };
                        this.mLocationListeners.add(locationListener);
                        registerListener(str, locationListener);
                    }
                }
                this.mTimer = new java.util.Timer();
                this.mTimer.schedule(new java.util.TimerTask() { // from class: com.android.server.location.countrydetector.LocationBasedCountryDetector.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        com.android.server.location.countrydetector.LocationBasedCountryDetector.this.mTimer = null;
                        com.android.server.location.countrydetector.LocationBasedCountryDetector.this.stop();
                        com.android.server.location.countrydetector.LocationBasedCountryDetector.this.queryCountryCode(com.android.server.location.countrydetector.LocationBasedCountryDetector.this.getLastKnownLocation());
                    }
                }, getQueryLocationTimeout());
            } else {
                queryCountryCode(getLastKnownLocation());
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return this.mDetectedCountry;
    }

    @Override // com.android.server.location.countrydetector.CountryDetectorBase
    public synchronized void stop() {
        try {
            if (this.mLocationListeners != null) {
                java.util.Iterator<android.location.LocationListener> it = this.mLocationListeners.iterator();
                while (it.hasNext()) {
                    unregisterListener(it.next());
                }
                this.mLocationListeners = null;
            }
            if (this.mTimer != null) {
                this.mTimer.cancel();
                this.mTimer = null;
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void queryCountryCode(final android.location.Location location) {
        if (this.mQueryThread != null) {
            return;
        }
        this.mQueryThread = new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.location.countrydetector.LocationBasedCountryDetector.3
            @Override // java.lang.Runnable
            public void run() {
                if (location == null) {
                    com.android.server.location.countrydetector.LocationBasedCountryDetector.this.notifyListener(null);
                    return;
                }
                java.lang.String countryFromLocation = com.android.server.location.countrydetector.LocationBasedCountryDetector.this.getCountryFromLocation(location);
                if (countryFromLocation != null) {
                    com.android.server.location.countrydetector.LocationBasedCountryDetector.this.mDetectedCountry = new android.location.Country(countryFromLocation, 1);
                } else {
                    com.android.server.location.countrydetector.LocationBasedCountryDetector.this.mDetectedCountry = null;
                }
                com.android.server.location.countrydetector.LocationBasedCountryDetector.this.notifyListener(com.android.server.location.countrydetector.LocationBasedCountryDetector.this.mDetectedCountry);
                com.android.server.location.countrydetector.LocationBasedCountryDetector.this.mQueryThread = null;
            }
        });
        this.mQueryThread.start();
    }
}
