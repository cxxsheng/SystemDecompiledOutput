package android.app;

/* loaded from: classes.dex */
public class ProfilerInfo implements android.os.Parcelable {
    public static final int CLOCK_TYPE_DEFAULT = 0;
    public static final int CLOCK_TYPE_DUAL = 272;
    public static final int CLOCK_TYPE_THREAD_CPU = 256;
    public static final int CLOCK_TYPE_WALL = 16;
    public static final android.os.Parcelable.Creator<android.app.ProfilerInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ProfilerInfo>() { // from class: android.app.ProfilerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ProfilerInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.ProfilerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ProfilerInfo[] newArray(int i) {
            return new android.app.ProfilerInfo[i];
        }
    };
    private static final java.lang.String TAG = "ProfilerInfo";
    public final java.lang.String agent;
    public final boolean attachAgentDuringBind;
    public final boolean autoStopProfiler;
    public final int clockType;
    public android.os.ParcelFileDescriptor profileFd;
    public final java.lang.String profileFile;
    public final int samplingInterval;
    public final boolean streamingOutput;

    public ProfilerInfo(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, boolean z, boolean z2, java.lang.String str2, boolean z3, int i2) {
        this.profileFile = str;
        this.profileFd = parcelFileDescriptor;
        this.samplingInterval = i;
        this.autoStopProfiler = z;
        this.streamingOutput = z2;
        this.clockType = i2;
        this.agent = str2;
        this.attachAgentDuringBind = z3;
    }

    public ProfilerInfo(android.app.ProfilerInfo profilerInfo) {
        this.profileFile = profilerInfo.profileFile;
        this.profileFd = profilerInfo.profileFd;
        this.samplingInterval = profilerInfo.samplingInterval;
        this.autoStopProfiler = profilerInfo.autoStopProfiler;
        this.streamingOutput = profilerInfo.streamingOutput;
        this.agent = profilerInfo.agent;
        this.attachAgentDuringBind = profilerInfo.attachAgentDuringBind;
        this.clockType = profilerInfo.clockType;
    }

    public static int getClockTypeFromString(java.lang.String str) {
        if ("thread-cpu".equals(str)) {
            return 256;
        }
        if ("wall".equals(str)) {
            return 16;
        }
        if ("dual".equals(str)) {
            return 272;
        }
        return 0;
    }

    public android.app.ProfilerInfo setAgent(java.lang.String str, boolean z) {
        return new android.app.ProfilerInfo(this.profileFile, this.profileFd, this.samplingInterval, this.autoStopProfiler, this.streamingOutput, str, z, this.clockType);
    }

    public void closeFd() {
        if (this.profileFd != null) {
            try {
                this.profileFd.close();
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failure closing profile fd", e);
            }
            this.profileFd = null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (this.profileFd != null) {
            return this.profileFd.describeContents();
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.profileFile);
        if (this.profileFd != null) {
            parcel.writeInt(1);
            this.profileFd.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.samplingInterval);
        parcel.writeInt(this.autoStopProfiler ? 1 : 0);
        parcel.writeInt(this.streamingOutput ? 1 : 0);
        parcel.writeString(this.agent);
        parcel.writeBoolean(this.attachAgentDuringBind);
        parcel.writeInt(this.clockType);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.profileFile);
        if (this.profileFd != null) {
            protoOutputStream.write(1120986464258L, this.profileFd.getFd());
        }
        protoOutputStream.write(1120986464259L, this.samplingInterval);
        protoOutputStream.write(1133871366148L, this.autoStopProfiler);
        protoOutputStream.write(1133871366149L, this.streamingOutput);
        protoOutputStream.write(1138166333446L, this.agent);
        protoOutputStream.write(1120986464263L, this.clockType);
        protoOutputStream.end(start);
    }

    private ProfilerInfo(android.os.Parcel parcel) {
        this.profileFile = parcel.readString();
        this.profileFd = parcel.readInt() != 0 ? android.os.ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
        this.samplingInterval = parcel.readInt();
        this.autoStopProfiler = parcel.readInt() != 0;
        this.streamingOutput = parcel.readInt() != 0;
        this.agent = parcel.readString();
        this.attachAgentDuringBind = parcel.readBoolean();
        this.clockType = parcel.readInt();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.ProfilerInfo profilerInfo = (android.app.ProfilerInfo) obj;
        if (java.util.Objects.equals(this.profileFile, profilerInfo.profileFile) && this.autoStopProfiler == profilerInfo.autoStopProfiler && this.samplingInterval == profilerInfo.samplingInterval && this.streamingOutput == profilerInfo.streamingOutput && java.util.Objects.equals(this.agent, profilerInfo.agent) && this.clockType == profilerInfo.clockType) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((527 + java.util.Objects.hashCode(this.profileFile)) * 31) + this.samplingInterval) * 31) + (this.autoStopProfiler ? 1 : 0)) * 31) + (this.streamingOutput ? 1 : 0)) * 31) + java.util.Objects.hashCode(this.agent)) * 31) + this.clockType;
    }
}
