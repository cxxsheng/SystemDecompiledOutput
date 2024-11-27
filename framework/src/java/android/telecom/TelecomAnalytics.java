package android.telecom;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class TelecomAnalytics implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.TelecomAnalytics> CREATOR = new android.os.Parcelable.Creator<android.telecom.TelecomAnalytics>() { // from class: android.telecom.TelecomAnalytics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.TelecomAnalytics createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.TelecomAnalytics(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.TelecomAnalytics[] newArray(int i) {
            return new android.telecom.TelecomAnalytics[i];
        }
    };
    private java.util.List<android.telecom.ParcelableCallAnalytics> mCallAnalytics;
    private java.util.List<android.telecom.TelecomAnalytics.SessionTiming> mSessionTimings;

    public static final class SessionTiming extends android.telecom.TimedEvent<java.lang.Integer> implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.telecom.TelecomAnalytics.SessionTiming> CREATOR = new android.os.Parcelable.Creator<android.telecom.TelecomAnalytics.SessionTiming>() { // from class: android.telecom.TelecomAnalytics.SessionTiming.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telecom.TelecomAnalytics.SessionTiming createFromParcel(android.os.Parcel parcel) {
                return new android.telecom.TelecomAnalytics.SessionTiming(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telecom.TelecomAnalytics.SessionTiming[] newArray(int i) {
                return new android.telecom.TelecomAnalytics.SessionTiming[i];
            }
        };
        public static final int CSW_ADD_CONFERENCE_CALL = 108;
        public static final int CSW_HANDLE_CREATE_CONNECTION_COMPLETE = 100;
        public static final int CSW_REMOVE_CALL = 106;
        public static final int CSW_SET_ACTIVE = 101;
        public static final int CSW_SET_DIALING = 103;
        public static final int CSW_SET_DISCONNECTED = 104;
        public static final int CSW_SET_IS_CONFERENCED = 107;
        public static final int CSW_SET_ON_HOLD = 105;
        public static final int CSW_SET_RINGING = 102;
        public static final int ICA_ANSWER_CALL = 1;
        public static final int ICA_CONFERENCE = 8;
        public static final int ICA_DISCONNECT_CALL = 3;
        public static final int ICA_HOLD_CALL = 4;
        public static final int ICA_MUTE = 6;
        public static final int ICA_REJECT_CALL = 2;
        public static final int ICA_SET_AUDIO_ROUTE = 7;
        public static final int ICA_UNHOLD_CALL = 5;
        private int mId;
        private long mTime;

        public SessionTiming(int i, long j) {
            this.mId = i;
            this.mTime = j;
        }

        private SessionTiming(android.os.Parcel parcel) {
            this.mId = parcel.readInt();
            this.mTime = parcel.readLong();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.telecom.TimedEvent
        public java.lang.Integer getKey() {
            return java.lang.Integer.valueOf(this.mId);
        }

        @Override // android.telecom.TimedEvent
        public long getTime() {
            return this.mTime;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mId);
            parcel.writeLong(this.mTime);
        }
    }

    public TelecomAnalytics(java.util.List<android.telecom.TelecomAnalytics.SessionTiming> list, java.util.List<android.telecom.ParcelableCallAnalytics> list2) {
        this.mSessionTimings = list;
        this.mCallAnalytics = list2;
    }

    private TelecomAnalytics(android.os.Parcel parcel) {
        this.mSessionTimings = new java.util.ArrayList();
        parcel.readTypedList(this.mSessionTimings, android.telecom.TelecomAnalytics.SessionTiming.CREATOR);
        this.mCallAnalytics = new java.util.ArrayList();
        parcel.readTypedList(this.mCallAnalytics, android.telecom.ParcelableCallAnalytics.CREATOR);
    }

    public java.util.List<android.telecom.TelecomAnalytics.SessionTiming> getSessionTimings() {
        return this.mSessionTimings;
    }

    public java.util.List<android.telecom.ParcelableCallAnalytics> getCallAnalytics() {
        return this.mCallAnalytics;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mSessionTimings);
        parcel.writeTypedList(this.mCallAnalytics);
    }
}
