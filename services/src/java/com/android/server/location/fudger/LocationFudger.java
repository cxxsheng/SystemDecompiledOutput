package com.android.server.location.fudger;

/* loaded from: classes2.dex */
public class LocationFudger {
    private static final int APPROXIMATE_METERS_PER_DEGREE_AT_EQUATOR = 111000;
    private static final double CHANGE_PER_INTERVAL = 0.03d;
    private static final double MAX_LATITUDE = 89.999990990991d;
    private static final float MIN_ACCURACY_M = 200.0f;
    private static final double NEW_WEIGHT = 0.03d;

    @com.android.internal.annotations.VisibleForTesting
    static final long OFFSET_UPDATE_INTERVAL_MS = 3600000;
    private static final double OLD_WEIGHT = java.lang.Math.sqrt(0.9991d);
    private final float mAccuracyM;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.location.Location mCachedCoarseLocation;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.location.LocationResult mCachedCoarseLocationResult;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.location.Location mCachedFineLocation;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.location.LocationResult mCachedFineLocationResult;
    private final java.time.Clock mClock;

    @com.android.internal.annotations.GuardedBy({"this"})
    private double mLatitudeOffsetM;

    @com.android.internal.annotations.GuardedBy({"this"})
    private double mLongitudeOffsetM;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNextUpdateRealtimeMs;
    private final java.util.Random mRandom;

    public LocationFudger(float f) {
        this(f, android.os.SystemClock.elapsedRealtimeClock(), new java.security.SecureRandom());
    }

    @com.android.internal.annotations.VisibleForTesting
    LocationFudger(float f, java.time.Clock clock, java.util.Random random) {
        this.mClock = clock;
        this.mRandom = random;
        this.mAccuracyM = java.lang.Math.max(f, MIN_ACCURACY_M);
        resetOffsets();
    }

    public void resetOffsets() {
        this.mLatitudeOffsetM = nextRandomOffset();
        this.mLongitudeOffsetM = nextRandomOffset();
        this.mNextUpdateRealtimeMs = this.mClock.millis() + 3600000;
    }

    public android.location.LocationResult createCoarse(android.location.LocationResult locationResult) {
        synchronized (this) {
            if (locationResult == this.mCachedFineLocationResult || locationResult == this.mCachedCoarseLocationResult) {
                return this.mCachedCoarseLocationResult;
            }
            android.location.LocationResult map = locationResult.map(new java.util.function.Function() { // from class: com.android.server.location.fudger.LocationFudger$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return com.android.server.location.fudger.LocationFudger.this.createCoarse((android.location.Location) obj);
                }
            });
            synchronized (this) {
                this.mCachedFineLocationResult = locationResult;
                this.mCachedCoarseLocationResult = map;
            }
            return map;
        }
    }

    public android.location.Location createCoarse(android.location.Location location) {
        synchronized (this) {
            if (location == this.mCachedFineLocation || location == this.mCachedCoarseLocation) {
                return this.mCachedCoarseLocation;
            }
            updateOffsets();
            android.location.Location location2 = new android.location.Location(location);
            location2.removeBearing();
            location2.removeSpeed();
            location2.removeAltitude();
            location2.setExtras(null);
            double wrapLatitude = wrapLatitude(location2.getLatitude());
            double wrapLongitude = wrapLongitude(location2.getLongitude()) + wrapLongitude(metersToDegreesLongitude(this.mLongitudeOffsetM, wrapLatitude));
            double wrapLatitude2 = wrapLatitude + wrapLatitude(metersToDegreesLatitude(this.mLatitudeOffsetM));
            double wrapLatitude3 = wrapLatitude(java.lang.Math.round(wrapLatitude2 / r5) * metersToDegreesLatitude(this.mAccuracyM));
            double wrapLongitude2 = wrapLongitude(java.lang.Math.round(wrapLongitude / r5) * metersToDegreesLongitude(this.mAccuracyM, wrapLatitude3));
            location2.setLatitude(wrapLatitude3);
            location2.setLongitude(wrapLongitude2);
            location2.setAccuracy(java.lang.Math.max(this.mAccuracyM, location2.getAccuracy()));
            synchronized (this) {
                this.mCachedFineLocation = location;
                this.mCachedCoarseLocation = location2;
            }
            return location2;
        }
    }

    private synchronized void updateOffsets() {
        long millis = this.mClock.millis();
        if (millis < this.mNextUpdateRealtimeMs) {
            return;
        }
        this.mLatitudeOffsetM = (OLD_WEIGHT * this.mLatitudeOffsetM) + (nextRandomOffset() * 0.03d);
        this.mLongitudeOffsetM = (OLD_WEIGHT * this.mLongitudeOffsetM) + (nextRandomOffset() * 0.03d);
        this.mNextUpdateRealtimeMs = millis + 3600000;
    }

    private double nextRandomOffset() {
        return this.mRandom.nextGaussian() * (this.mAccuracyM / 4.0d);
    }

    private static double wrapLatitude(double d) {
        if (d > MAX_LATITUDE) {
            d = 89.999990990991d;
        }
        if (d < -89.999990990991d) {
            return -89.999990990991d;
        }
        return d;
    }

    private static double wrapLongitude(double d) {
        double d2 = d % 360.0d;
        if (d2 >= 180.0d) {
            d2 -= 360.0d;
        }
        if (d2 < -180.0d) {
            return d2 + 360.0d;
        }
        return d2;
    }

    private static double metersToDegreesLatitude(double d) {
        return d / 111000.0d;
    }

    private static double metersToDegreesLongitude(double d, double d2) {
        return (d / 111000.0d) / java.lang.Math.cos(java.lang.Math.toRadians(d2));
    }
}
