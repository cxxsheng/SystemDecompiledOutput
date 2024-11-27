package android.location;

/* loaded from: classes2.dex */
public class Location implements android.os.Parcelable {

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_NO_GPS_LOCATION = "noGPSLocation";
    public static final int FORMAT_DEGREES = 0;
    public static final int FORMAT_MINUTES = 1;
    public static final int FORMAT_SECONDS = 2;
    private static final int HAS_ALTITUDE_ACCURACY_MASK = 32;
    private static final int HAS_ALTITUDE_MASK = 1;
    private static final int HAS_BEARING_ACCURACY_MASK = 128;
    private static final int HAS_BEARING_MASK = 4;
    private static final int HAS_ELAPSED_REALTIME_UNCERTAINTY_MASK = 256;
    private static final int HAS_HORIZONTAL_ACCURACY_MASK = 8;
    private static final int HAS_MOCK_PROVIDER_MASK = 16;
    private static final int HAS_MSL_ALTITUDE_ACCURACY_MASK = 1024;
    private static final int HAS_MSL_ALTITUDE_MASK = 512;
    private static final int HAS_SPEED_ACCURACY_MASK = 64;
    private static final int HAS_SPEED_MASK = 2;
    private float mAltitudeAccuracyMeters;
    private double mAltitudeMeters;
    private float mBearingAccuracyDegrees;
    private float mBearingDegrees;
    private long mElapsedRealtimeNs;
    private double mElapsedRealtimeUncertaintyNs;
    private float mHorizontalAccuracyMeters;
    private double mLatitudeDegrees;
    private double mLongitudeDegrees;
    private float mMslAltitudeAccuracyMeters;
    private double mMslAltitudeMeters;
    private java.lang.String mProvider;
    private float mSpeedAccuracyMetersPerSecond;
    private float mSpeedMetersPerSecond;
    private long mTimeMs;
    private static final java.lang.ThreadLocal<android.location.Location.BearingDistanceCache> sBearingDistanceCache = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: android.location.Location$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return android.location.Location.m1908$r8$lambda$LCoyno7iOKo6n1w2mcfXqv702o();
        }
    });
    public static final android.os.Parcelable.Creator<android.location.Location> CREATOR = new android.os.Parcelable.Creator<android.location.Location>() { // from class: android.location.Location.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.location.Location createFromParcel(android.os.Parcel parcel) {
            android.location.Location location = new android.location.Location(parcel.readString8());
            location.mFieldsMask = parcel.readInt();
            location.mTimeMs = parcel.readLong();
            location.mElapsedRealtimeNs = parcel.readLong();
            if (location.hasElapsedRealtimeUncertaintyNanos()) {
                location.mElapsedRealtimeUncertaintyNs = parcel.readDouble();
            }
            location.mLatitudeDegrees = parcel.readDouble();
            location.mLongitudeDegrees = parcel.readDouble();
            if (location.hasAltitude()) {
                location.mAltitudeMeters = parcel.readDouble();
            }
            if (location.hasSpeed()) {
                location.mSpeedMetersPerSecond = parcel.readFloat();
            }
            if (location.hasBearing()) {
                location.mBearingDegrees = parcel.readFloat();
            }
            if (location.hasAccuracy()) {
                location.mHorizontalAccuracyMeters = parcel.readFloat();
            }
            if (location.hasVerticalAccuracy()) {
                location.mAltitudeAccuracyMeters = parcel.readFloat();
            }
            if (location.hasSpeedAccuracy()) {
                location.mSpeedAccuracyMetersPerSecond = parcel.readFloat();
            }
            if (location.hasBearingAccuracy()) {
                location.mBearingAccuracyDegrees = parcel.readFloat();
            }
            if (location.hasMslAltitude()) {
                location.mMslAltitudeMeters = parcel.readDouble();
            }
            if (location.hasMslAltitudeAccuracy()) {
                location.mMslAltitudeAccuracyMeters = parcel.readFloat();
            }
            location.mExtras = android.os.Bundle.setDefusable(parcel.readBundle(), true);
            return location;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.location.Location[] newArray(int i) {
            return new android.location.Location[i];
        }
    };
    private int mFieldsMask = 0;
    private android.os.Bundle mExtras = null;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Format {
    }

    /* renamed from: $r8$lambda$LCoyno7iOKo6n1w2mcfX-qv702o, reason: not valid java name */
    public static /* synthetic */ android.location.Location.BearingDistanceCache m1908$r8$lambda$LCoyno7iOKo6n1w2mcfXqv702o() {
        return new android.location.Location.BearingDistanceCache();
    }

    public Location(java.lang.String str) {
        this.mProvider = str;
    }

    public Location(android.location.Location location) {
        set(location);
    }

    public void set(android.location.Location location) {
        this.mFieldsMask = location.mFieldsMask;
        this.mProvider = location.mProvider;
        this.mTimeMs = location.mTimeMs;
        this.mElapsedRealtimeNs = location.mElapsedRealtimeNs;
        this.mElapsedRealtimeUncertaintyNs = location.mElapsedRealtimeUncertaintyNs;
        this.mLatitudeDegrees = location.mLatitudeDegrees;
        this.mLongitudeDegrees = location.mLongitudeDegrees;
        this.mHorizontalAccuracyMeters = location.mHorizontalAccuracyMeters;
        this.mAltitudeMeters = location.mAltitudeMeters;
        this.mAltitudeAccuracyMeters = location.mAltitudeAccuracyMeters;
        this.mSpeedMetersPerSecond = location.mSpeedMetersPerSecond;
        this.mSpeedAccuracyMetersPerSecond = location.mSpeedAccuracyMetersPerSecond;
        this.mBearingDegrees = location.mBearingDegrees;
        this.mBearingAccuracyDegrees = location.mBearingAccuracyDegrees;
        this.mMslAltitudeMeters = location.mMslAltitudeMeters;
        this.mMslAltitudeAccuracyMeters = location.mMslAltitudeAccuracyMeters;
        this.mExtras = location.mExtras == null ? null : new android.os.Bundle(location.mExtras);
    }

    public void reset() {
        this.mProvider = null;
        this.mTimeMs = 0L;
        this.mElapsedRealtimeNs = 0L;
        this.mElapsedRealtimeUncertaintyNs = 0.0d;
        this.mFieldsMask = 0;
        this.mLatitudeDegrees = 0.0d;
        this.mLongitudeDegrees = 0.0d;
        this.mAltitudeMeters = 0.0d;
        this.mSpeedMetersPerSecond = 0.0f;
        this.mBearingDegrees = 0.0f;
        this.mHorizontalAccuracyMeters = 0.0f;
        this.mAltitudeAccuracyMeters = 0.0f;
        this.mSpeedAccuracyMetersPerSecond = 0.0f;
        this.mBearingAccuracyDegrees = 0.0f;
        this.mMslAltitudeMeters = 0.0d;
        this.mMslAltitudeAccuracyMeters = 0.0f;
        this.mExtras = null;
    }

    public float distanceTo(android.location.Location location) {
        android.location.Location.BearingDistanceCache bearingDistanceCache = sBearingDistanceCache.get();
        if (this.mLatitudeDegrees != bearingDistanceCache.mLat1 || this.mLongitudeDegrees != bearingDistanceCache.mLon1 || location.mLatitudeDegrees != bearingDistanceCache.mLat2 || location.mLongitudeDegrees != bearingDistanceCache.mLon2) {
            computeDistanceAndBearing(this.mLatitudeDegrees, this.mLongitudeDegrees, location.mLatitudeDegrees, location.mLongitudeDegrees, bearingDistanceCache);
        }
        return bearingDistanceCache.mDistance;
    }

    public float bearingTo(android.location.Location location) {
        android.location.Location.BearingDistanceCache bearingDistanceCache = sBearingDistanceCache.get();
        if (this.mLatitudeDegrees != bearingDistanceCache.mLat1 || this.mLongitudeDegrees != bearingDistanceCache.mLon1 || location.mLatitudeDegrees != bearingDistanceCache.mLat2 || location.mLongitudeDegrees != bearingDistanceCache.mLon2) {
            computeDistanceAndBearing(this.mLatitudeDegrees, this.mLongitudeDegrees, location.mLatitudeDegrees, location.mLongitudeDegrees, bearingDistanceCache);
        }
        return bearingDistanceCache.mInitialBearing;
    }

    public java.lang.String getProvider() {
        return this.mProvider;
    }

    public void setProvider(java.lang.String str) {
        this.mProvider = str;
    }

    public long getTime() {
        return this.mTimeMs;
    }

    public void setTime(long j) {
        this.mTimeMs = j;
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNs;
    }

    public long getElapsedRealtimeMillis() {
        return java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(this.mElapsedRealtimeNs);
    }

    public long getElapsedRealtimeAgeMillis() {
        return getElapsedRealtimeAgeMillis(android.os.SystemClock.elapsedRealtime());
    }

    public long getElapsedRealtimeAgeMillis(long j) {
        return j - getElapsedRealtimeMillis();
    }

    public void setElapsedRealtimeNanos(long j) {
        this.mElapsedRealtimeNs = j;
    }

    public double getElapsedRealtimeUncertaintyNanos() {
        return this.mElapsedRealtimeUncertaintyNs;
    }

    public void setElapsedRealtimeUncertaintyNanos(double d) {
        this.mElapsedRealtimeUncertaintyNs = d;
        this.mFieldsMask |= 256;
    }

    public boolean hasElapsedRealtimeUncertaintyNanos() {
        return (this.mFieldsMask & 256) != 0;
    }

    public void removeElapsedRealtimeUncertaintyNanos() {
        this.mFieldsMask &= -257;
    }

    public double getLatitude() {
        return this.mLatitudeDegrees;
    }

    public void setLatitude(double d) {
        this.mLatitudeDegrees = d;
    }

    public double getLongitude() {
        return this.mLongitudeDegrees;
    }

    public void setLongitude(double d) {
        this.mLongitudeDegrees = d;
    }

    public float getAccuracy() {
        return this.mHorizontalAccuracyMeters;
    }

    public void setAccuracy(float f) {
        this.mHorizontalAccuracyMeters = f;
        this.mFieldsMask |= 8;
    }

    public boolean hasAccuracy() {
        return (this.mFieldsMask & 8) != 0;
    }

    public void removeAccuracy() {
        this.mFieldsMask &= -9;
    }

    public double getAltitude() {
        return this.mAltitudeMeters;
    }

    public void setAltitude(double d) {
        this.mAltitudeMeters = d;
        this.mFieldsMask |= 1;
    }

    public boolean hasAltitude() {
        return (this.mFieldsMask & 1) != 0;
    }

    public void removeAltitude() {
        this.mFieldsMask &= -2;
    }

    public float getVerticalAccuracyMeters() {
        return this.mAltitudeAccuracyMeters;
    }

    public void setVerticalAccuracyMeters(float f) {
        this.mAltitudeAccuracyMeters = f;
        this.mFieldsMask |= 32;
    }

    public boolean hasVerticalAccuracy() {
        return (this.mFieldsMask & 32) != 0;
    }

    public void removeVerticalAccuracy() {
        this.mFieldsMask &= -33;
    }

    public float getSpeed() {
        return this.mSpeedMetersPerSecond;
    }

    public void setSpeed(float f) {
        this.mSpeedMetersPerSecond = f;
        this.mFieldsMask |= 2;
    }

    public boolean hasSpeed() {
        return (this.mFieldsMask & 2) != 0;
    }

    public void removeSpeed() {
        this.mFieldsMask &= -3;
    }

    public float getSpeedAccuracyMetersPerSecond() {
        return this.mSpeedAccuracyMetersPerSecond;
    }

    public void setSpeedAccuracyMetersPerSecond(float f) {
        this.mSpeedAccuracyMetersPerSecond = f;
        this.mFieldsMask |= 64;
    }

    public boolean hasSpeedAccuracy() {
        return (this.mFieldsMask & 64) != 0;
    }

    public void removeSpeedAccuracy() {
        this.mFieldsMask &= -65;
    }

    public float getBearing() {
        return this.mBearingDegrees;
    }

    public void setBearing(float f) {
        com.android.internal.util.Preconditions.checkArgument(java.lang.Float.isFinite(f));
        float f2 = (f % 360.0f) + 0.0f;
        if (f2 < 0.0f) {
            f2 += 360.0f;
        }
        this.mBearingDegrees = f2;
        this.mFieldsMask |= 4;
    }

    public boolean hasBearing() {
        return (this.mFieldsMask & 4) != 0;
    }

    public void removeBearing() {
        this.mFieldsMask &= -5;
    }

    public float getBearingAccuracyDegrees() {
        return this.mBearingAccuracyDegrees;
    }

    public void setBearingAccuracyDegrees(float f) {
        this.mBearingAccuracyDegrees = f;
        this.mFieldsMask |= 128;
    }

    public boolean hasBearingAccuracy() {
        return (this.mFieldsMask & 128) != 0;
    }

    public void removeBearingAccuracy() {
        this.mFieldsMask &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
    }

    public double getMslAltitudeMeters() {
        return this.mMslAltitudeMeters;
    }

    public void setMslAltitudeMeters(double d) {
        this.mMslAltitudeMeters = d;
        this.mFieldsMask |= 512;
    }

    public boolean hasMslAltitude() {
        return (this.mFieldsMask & 512) != 0;
    }

    public void removeMslAltitude() {
        this.mFieldsMask &= -513;
    }

    public float getMslAltitudeAccuracyMeters() {
        return this.mMslAltitudeAccuracyMeters;
    }

    public void setMslAltitudeAccuracyMeters(float f) {
        this.mMslAltitudeAccuracyMeters = f;
        this.mFieldsMask |= 1024;
    }

    public boolean hasMslAltitudeAccuracy() {
        return (this.mFieldsMask & 1024) != 0;
    }

    public void removeMslAltitudeAccuracy() {
        this.mFieldsMask &= -1025;
    }

    @java.lang.Deprecated
    public boolean isFromMockProvider() {
        return isMock();
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setIsFromMockProvider(boolean z) {
        setMock(z);
    }

    public boolean isMock() {
        return (this.mFieldsMask & 16) != 0;
    }

    public void setMock(boolean z) {
        if (z) {
            this.mFieldsMask |= 16;
        } else {
            this.mFieldsMask &= -17;
        }
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public void setExtras(android.os.Bundle bundle) {
        this.mExtras = bundle == null ? null : new android.os.Bundle(bundle);
    }

    public boolean isComplete() {
        return (this.mProvider == null || !hasAccuracy() || this.mTimeMs == 0 || this.mElapsedRealtimeNs == 0) ? false : true;
    }

    @android.annotation.SystemApi
    public void makeComplete() {
        if (this.mProvider == null) {
            this.mProvider = "";
        }
        if (!hasAccuracy()) {
            this.mFieldsMask |= 8;
            this.mHorizontalAccuracyMeters = 100.0f;
        }
        if (this.mTimeMs == 0) {
            this.mTimeMs = java.lang.System.currentTimeMillis();
        }
        if (this.mElapsedRealtimeNs == 0) {
            this.mElapsedRealtimeNs = android.os.SystemClock.elapsedRealtimeNanos();
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.location.Location)) {
            return false;
        }
        android.location.Location location = (android.location.Location) obj;
        return this.mTimeMs == location.mTimeMs && this.mElapsedRealtimeNs == location.mElapsedRealtimeNs && hasElapsedRealtimeUncertaintyNanos() == location.hasElapsedRealtimeUncertaintyNanos() && (!hasElapsedRealtimeUncertaintyNanos() || java.lang.Double.compare(location.mElapsedRealtimeUncertaintyNs, this.mElapsedRealtimeUncertaintyNs) == 0) && java.lang.Double.compare(location.mLatitudeDegrees, this.mLatitudeDegrees) == 0 && java.lang.Double.compare(location.mLongitudeDegrees, this.mLongitudeDegrees) == 0 && hasAltitude() == location.hasAltitude() && ((!hasAltitude() || java.lang.Double.compare(location.mAltitudeMeters, this.mAltitudeMeters) == 0) && hasSpeed() == location.hasSpeed() && ((!hasSpeed() || java.lang.Float.compare(location.mSpeedMetersPerSecond, this.mSpeedMetersPerSecond) == 0) && hasBearing() == location.hasBearing() && ((!hasBearing() || java.lang.Float.compare(location.mBearingDegrees, this.mBearingDegrees) == 0) && hasAccuracy() == location.hasAccuracy() && ((!hasAccuracy() || java.lang.Float.compare(location.mHorizontalAccuracyMeters, this.mHorizontalAccuracyMeters) == 0) && hasVerticalAccuracy() == location.hasVerticalAccuracy() && ((!hasVerticalAccuracy() || java.lang.Float.compare(location.mAltitudeAccuracyMeters, this.mAltitudeAccuracyMeters) == 0) && hasSpeedAccuracy() == location.hasSpeedAccuracy() && ((!hasSpeedAccuracy() || java.lang.Float.compare(location.mSpeedAccuracyMetersPerSecond, this.mSpeedAccuracyMetersPerSecond) == 0) && hasBearingAccuracy() == location.hasBearingAccuracy() && ((!hasBearingAccuracy() || java.lang.Float.compare(location.mBearingAccuracyDegrees, this.mBearingAccuracyDegrees) == 0) && hasMslAltitude() == location.hasMslAltitude() && ((!hasMslAltitude() || java.lang.Double.compare(location.mMslAltitudeMeters, this.mMslAltitudeMeters) == 0) && hasMslAltitudeAccuracy() == location.hasMslAltitudeAccuracy() && ((!hasMslAltitudeAccuracy() || java.lang.Float.compare(location.mMslAltitudeAccuracyMeters, this.mMslAltitudeAccuracyMeters) == 0) && java.util.Objects.equals(this.mProvider, location.mProvider) && areExtrasEqual(this.mExtras, location.mExtras))))))))));
    }

    private static boolean areExtrasEqual(android.os.Bundle bundle, android.os.Bundle bundle2) {
        if ((bundle == null || bundle.isEmpty()) && (bundle2 == null || bundle2.isEmpty())) {
            return true;
        }
        if (bundle == null || bundle2 == null) {
            return false;
        }
        return bundle.kindofEquals(bundle2);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mProvider, java.lang.Long.valueOf(this.mElapsedRealtimeNs), java.lang.Double.valueOf(this.mLatitudeDegrees), java.lang.Double.valueOf(this.mLongitudeDegrees));
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Location[");
        sb.append(this.mProvider);
        sb.append(" ").append(java.lang.String.format(java.util.Locale.ROOT, "%.6f,%.6f", java.lang.Double.valueOf(this.mLatitudeDegrees), java.lang.Double.valueOf(this.mLongitudeDegrees)));
        if (hasAccuracy()) {
            sb.append(" hAcc=").append(this.mHorizontalAccuracyMeters);
        }
        sb.append(" et=");
        android.util.TimeUtils.formatDuration(getElapsedRealtimeMillis(), sb);
        if (hasAltitude()) {
            sb.append(" alt=").append(this.mAltitudeMeters);
            if (hasVerticalAccuracy()) {
                sb.append(" vAcc=").append(this.mAltitudeAccuracyMeters);
            }
        }
        if (hasMslAltitude()) {
            sb.append(" mslAlt=").append(this.mMslAltitudeMeters);
            if (hasMslAltitudeAccuracy()) {
                sb.append(" mslAltAcc=").append(this.mMslAltitudeAccuracyMeters);
            }
        }
        if (hasSpeed()) {
            sb.append(" vel=").append(this.mSpeedMetersPerSecond);
            if (hasSpeedAccuracy()) {
                sb.append(" sAcc=").append(this.mSpeedAccuracyMetersPerSecond);
            }
        }
        if (hasBearing()) {
            sb.append(" bear=").append(this.mBearingDegrees);
            if (hasBearingAccuracy()) {
                sb.append(" bAcc=").append(this.mBearingAccuracyDegrees);
            }
        }
        if (isMock()) {
            sb.append(" mock");
        }
        if (this.mExtras != null && !this.mExtras.isEmpty()) {
            sb.append(" {").append(this.mExtras).append('}');
        }
        sb.append(']');
        return sb.toString();
    }

    @java.lang.Deprecated
    public void dump(android.util.Printer printer, java.lang.String str) {
        printer.println(str + this);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mProvider);
        parcel.writeInt(this.mFieldsMask);
        parcel.writeLong(this.mTimeMs);
        parcel.writeLong(this.mElapsedRealtimeNs);
        if (hasElapsedRealtimeUncertaintyNanos()) {
            parcel.writeDouble(this.mElapsedRealtimeUncertaintyNs);
        }
        parcel.writeDouble(this.mLatitudeDegrees);
        parcel.writeDouble(this.mLongitudeDegrees);
        if (hasAltitude()) {
            parcel.writeDouble(this.mAltitudeMeters);
        }
        if (hasSpeed()) {
            parcel.writeFloat(this.mSpeedMetersPerSecond);
        }
        if (hasBearing()) {
            parcel.writeFloat(this.mBearingDegrees);
        }
        if (hasAccuracy()) {
            parcel.writeFloat(this.mHorizontalAccuracyMeters);
        }
        if (hasVerticalAccuracy()) {
            parcel.writeFloat(this.mAltitudeAccuracyMeters);
        }
        if (hasSpeedAccuracy()) {
            parcel.writeFloat(this.mSpeedAccuracyMetersPerSecond);
        }
        if (hasBearingAccuracy()) {
            parcel.writeFloat(this.mBearingAccuracyDegrees);
        }
        if (hasMslAltitude()) {
            parcel.writeDouble(this.mMslAltitudeMeters);
        }
        if (hasMslAltitudeAccuracy()) {
            parcel.writeFloat(this.mMslAltitudeAccuracyMeters);
        }
        parcel.writeBundle(this.mExtras);
    }

    public static java.lang.String convert(double d, int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(d, -180.0d, 180.0d, "coordinate");
        com.android.internal.util.Preconditions.checkArgument(i == 0 || i == 1 || i == 2, "%d is an unrecognized format", java.lang.Integer.valueOf(i));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (d < 0.0d) {
            sb.append('-');
            d = -d;
        }
        java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("###.#####");
        if (i == 1 || i == 2) {
            int floor = (int) java.lang.Math.floor(d);
            sb.append(floor);
            sb.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
            d = (d - floor) * 60.0d;
            if (i == 2) {
                int floor2 = (int) java.lang.Math.floor(d);
                sb.append(floor2);
                sb.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
                d = (d - floor2) * 60.0d;
            }
        }
        sb.append(decimalFormat.format(d));
        return sb.toString();
    }

    public static double convert(java.lang.String str) {
        java.lang.String str2;
        boolean z;
        double parseDouble;
        boolean z2;
        double d;
        java.util.Objects.requireNonNull(str);
        boolean z3 = false;
        if (str.charAt(0) != '-') {
            str2 = str;
            z = false;
        } else {
            str2 = str.substring(1);
            z = true;
        }
        java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str2, ":");
        int countTokens = stringTokenizer.countTokens();
        if (countTokens < 1) {
            throw new java.lang.IllegalArgumentException("coordinate=" + str2);
        }
        try {
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (countTokens == 1) {
                double parseDouble2 = java.lang.Double.parseDouble(nextToken);
                return z ? -parseDouble2 : parseDouble2;
            }
            java.lang.String nextToken2 = stringTokenizer.nextToken();
            int parseInt = java.lang.Integer.parseInt(nextToken);
            if (stringTokenizer.hasMoreTokens()) {
                parseDouble = java.lang.Integer.parseInt(nextToken2);
                d = java.lang.Double.parseDouble(stringTokenizer.nextToken());
                z2 = true;
            } else {
                parseDouble = java.lang.Double.parseDouble(nextToken2);
                z2 = false;
                d = 0.0d;
            }
            if (z && parseInt == 180 && parseDouble == 0.0d && d == 0.0d) {
                z3 = true;
            }
            double d2 = parseInt;
            if (d2 < 0.0d || (parseInt > 179 && !z3)) {
                throw new java.lang.IllegalArgumentException("coordinate=" + str2);
            }
            if (parseDouble < 0.0d || parseDouble >= 60.0d || (z2 && parseDouble > 59.0d)) {
                throw new java.lang.IllegalArgumentException("coordinate=" + str2);
            }
            if (d < 0.0d || d >= 60.0d) {
                throw new java.lang.IllegalArgumentException("coordinate=" + str2);
            }
            double d3 = (((d2 * 3600.0d) + (parseDouble * 60.0d)) + d) / 3600.0d;
            return z ? -d3 : d3;
        } catch (java.lang.NumberFormatException e) {
            throw new java.lang.IllegalArgumentException("coordinate=" + str2, e);
        }
    }

    private static void computeDistanceAndBearing(double d, double d2, double d3, double d4, android.location.Location.BearingDistanceCache bearingDistanceCache) {
        double d5;
        double d6;
        double d7;
        double d8 = d * 0.017453292519943295d;
        double d9 = d3 * 0.017453292519943295d;
        double d10 = d2 * 0.017453292519943295d;
        double d11 = 0.017453292519943295d * d4;
        double d12 = d11 - d10;
        double atan = java.lang.Math.atan(java.lang.Math.tan(d8) * 0.996647189328169d);
        double atan2 = java.lang.Math.atan(0.996647189328169d * java.lang.Math.tan(d9));
        double cos = java.lang.Math.cos(atan);
        double cos2 = java.lang.Math.cos(atan2);
        double sin = java.lang.Math.sin(atan);
        double sin2 = java.lang.Math.sin(atan2);
        double d13 = cos * cos2;
        double d14 = sin * sin2;
        double d15 = 0.0d;
        double d16 = 0.0d;
        double d17 = 0.0d;
        double d18 = 0.0d;
        double d19 = 0.0d;
        int i = 0;
        double d20 = d12;
        while (true) {
            if (i >= 20) {
                d5 = d9;
                d6 = d10;
                break;
            }
            d17 = java.lang.Math.cos(d20);
            d19 = java.lang.Math.sin(d20);
            double d21 = cos2 * d19;
            double d22 = (cos * sin2) - ((sin * cos2) * d17);
            d6 = d10;
            double sqrt = java.lang.Math.sqrt((d21 * d21) + (d22 * d22));
            d5 = d9;
            double d23 = d14 + (d13 * d17);
            d15 = java.lang.Math.atan2(sqrt, d23);
            if (sqrt == 0.0d) {
                d7 = 0.0d;
            } else {
                d7 = (d13 * d19) / sqrt;
            }
            double d24 = 1.0d - (d7 * d7);
            double d25 = d24 == 0.0d ? 0.0d : d23 - ((d14 * 2.0d) / d24);
            double d26 = 0.006739496756586903d * d24;
            double d27 = ((d26 / 16384.0d) * (((((320.0d - (175.0d * d26)) * d26) - 768.0d) * d26) + 4096.0d)) + 1.0d;
            double d28 = (d26 / 1024.0d) * ((d26 * (((74.0d - (47.0d * d26)) * d26) - 128.0d)) + 256.0d);
            double d29 = 2.0955066698943685E-4d * d24 * (((4.0d - (d24 * 3.0d)) * 0.0033528106718309896d) + 4.0d);
            double d30 = d25 * d25;
            double d31 = d28 * sqrt * (d25 + ((d28 / 4.0d) * ((((d30 * 2.0d) - 1.0d) * d23) - ((((d28 / 6.0d) * d25) * (((sqrt * 4.0d) * sqrt) - 3.0d)) * ((d30 * 4.0d) - 3.0d)))));
            double d32 = d12 + ((1.0d - d29) * 0.0033528106718309896d * d7 * (d15 + (sqrt * d29 * (d25 + (d29 * d23 * (((2.0d * d25) * d25) - 1.0d))))));
            if (java.lang.Math.abs((d32 - d20) / d32) >= 1.0E-12d) {
                i++;
                d10 = d6;
                d9 = d5;
                d16 = d31;
                d20 = d32;
                d18 = d27;
            } else {
                d16 = d31;
                d18 = d27;
                break;
            }
        }
        bearingDistanceCache.mDistance = (float) (6356752.3142d * d18 * (d15 - d16));
        double d33 = sin2 * cos;
        bearingDistanceCache.mInitialBearing = (float) (((float) java.lang.Math.atan2(cos2 * d19, d33 - ((sin * cos2) * d17))) * 57.29577951308232d);
        bearingDistanceCache.mFinalBearing = (float) (((float) java.lang.Math.atan2(cos * d19, ((-sin) * cos2) + (d33 * d17))) * 57.29577951308232d);
        bearingDistanceCache.mLat1 = d8;
        bearingDistanceCache.mLat2 = d5;
        bearingDistanceCache.mLon1 = d6;
        bearingDistanceCache.mLon2 = d11;
    }

    public static void distanceBetween(double d, double d2, double d3, double d4, float[] fArr) {
        if (fArr == null || fArr.length < 1) {
            throw new java.lang.IllegalArgumentException("results is null or has length < 1");
        }
        android.location.Location.BearingDistanceCache bearingDistanceCache = sBearingDistanceCache.get();
        computeDistanceAndBearing(d, d2, d3, d4, bearingDistanceCache);
        fArr[0] = bearingDistanceCache.mDistance;
        if (fArr.length > 1) {
            fArr[1] = bearingDistanceCache.mInitialBearing;
            if (fArr.length > 2) {
                fArr[2] = bearingDistanceCache.mFinalBearing;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BearingDistanceCache {
        float mDistance;
        float mFinalBearing;
        float mInitialBearing;
        double mLat1;
        double mLat2;
        double mLon1;
        double mLon2;

        private BearingDistanceCache() {
            this.mLat1 = 0.0d;
            this.mLon1 = 0.0d;
            this.mLat2 = 0.0d;
            this.mLon2 = 0.0d;
            this.mDistance = 0.0f;
            this.mInitialBearing = 0.0f;
            this.mFinalBearing = 0.0f;
        }
    }
}
