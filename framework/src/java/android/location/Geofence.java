package android.location;

/* loaded from: classes2.dex */
public final class Geofence implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.location.Geofence> CREATOR = new android.os.Parcelable.Creator<android.location.Geofence>() { // from class: android.location.Geofence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.location.Geofence createFromParcel(android.os.Parcel parcel) {
            return new android.location.Geofence(parcel.readDouble(), parcel.readDouble(), parcel.readFloat(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.location.Geofence[] newArray(int i) {
            return new android.location.Geofence[i];
        }
    };
    private long mExpirationRealtimeMs;
    private final double mLatitude;
    private final double mLongitude;
    private final float mRadius;

    public static android.location.Geofence createCircle(double d, double d2, float f, long j) {
        return new android.location.Geofence(d, d2, f, j);
    }

    Geofence(double d, double d2, float f, long j) {
        com.android.internal.util.Preconditions.checkArgumentInRange(d, -90.0d, 90.0d, android.provider.CallLog.Locations.LATITUDE);
        com.android.internal.util.Preconditions.checkArgumentInRange(d2, -180.0d, 180.0d, android.provider.CallLog.Locations.LATITUDE);
        com.android.internal.util.Preconditions.checkArgument(f > 0.0f, "invalid radius: %f", java.lang.Float.valueOf(f));
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mRadius = f;
        this.mExpirationRealtimeMs = j;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public boolean isExpired() {
        return isExpired(android.os.SystemClock.elapsedRealtime());
    }

    public boolean isExpired(long j) {
        return j >= this.mExpirationRealtimeMs;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitude);
        parcel.writeFloat(this.mRadius);
        parcel.writeLong(this.mExpirationRealtimeMs);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.location.Geofence)) {
            return false;
        }
        android.location.Geofence geofence = (android.location.Geofence) obj;
        return java.lang.Double.compare(geofence.mLatitude, this.mLatitude) == 0 && java.lang.Double.compare(geofence.mLongitude, this.mLongitude) == 0 && java.lang.Float.compare(geofence.mRadius, this.mRadius) == 0 && this.mExpirationRealtimeMs == geofence.mExpirationRealtimeMs;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Double.valueOf(this.mLatitude), java.lang.Double.valueOf(this.mLongitude), java.lang.Float.valueOf(this.mRadius));
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Geofence[(").append(this.mLatitude).append(", ").append(this.mLongitude).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        sb.append(" ").append(this.mRadius).append("m");
        if (this.mExpirationRealtimeMs < Long.MAX_VALUE) {
            if (isExpired()) {
                sb.append(" expired");
            } else {
                sb.append(" expires=");
                android.util.TimeUtils.formatDuration(this.mExpirationRealtimeMs, sb);
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
