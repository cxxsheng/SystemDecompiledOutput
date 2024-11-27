package android.os.health;

/* loaded from: classes3.dex */
public class HealthStatsParceler implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.health.HealthStatsParceler> CREATOR = new android.os.Parcelable.Creator<android.os.health.HealthStatsParceler>() { // from class: android.os.health.HealthStatsParceler.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.health.HealthStatsParceler createFromParcel(android.os.Parcel parcel) {
            return new android.os.health.HealthStatsParceler(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.health.HealthStatsParceler[] newArray(int i) {
            return new android.os.health.HealthStatsParceler[i];
        }
    };
    private android.os.health.HealthStats mHealthStats;
    private android.os.health.HealthStatsWriter mWriter;

    public HealthStatsParceler(android.os.health.HealthStatsWriter healthStatsWriter) {
        this.mWriter = healthStatsWriter;
    }

    public HealthStatsParceler(android.os.Parcel parcel) {
        this.mHealthStats = new android.os.health.HealthStats(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mWriter != null) {
            this.mWriter.flattenToParcel(parcel);
            return;
        }
        throw new java.lang.RuntimeException("Can not re-parcel HealthStatsParceler that was constructed from a Parcel");
    }

    public android.os.health.HealthStats getHealthStats() {
        if (this.mWriter != null) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            this.mWriter.flattenToParcel(obtain);
            obtain.setDataPosition(0);
            this.mHealthStats = new android.os.health.HealthStats(obtain);
            obtain.recycle();
        }
        return this.mHealthStats;
    }
}
