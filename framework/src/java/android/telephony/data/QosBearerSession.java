package android.telephony.data;

/* loaded from: classes3.dex */
public final class QosBearerSession implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.QosBearerSession> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.QosBearerSession>() { // from class: android.telephony.data.QosBearerSession.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.QosBearerSession createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.QosBearerSession(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.QosBearerSession[] newArray(int i) {
            return new android.telephony.data.QosBearerSession[i];
        }
    };
    final android.telephony.data.Qos qos;
    final java.util.List<android.telephony.data.QosBearerFilter> qosBearerFilterList;
    final int qosBearerSessionId;

    public QosBearerSession(int i, android.telephony.data.Qos qos, java.util.List<android.telephony.data.QosBearerFilter> list) {
        this.qosBearerSessionId = i;
        this.qos = qos;
        this.qosBearerFilterList = new java.util.ArrayList();
        this.qosBearerFilterList.addAll(list);
    }

    private QosBearerSession(android.os.Parcel parcel) {
        this.qosBearerSessionId = parcel.readInt();
        this.qos = (android.telephony.data.Qos) parcel.readParcelable(android.telephony.data.Qos.class.getClassLoader(), android.telephony.data.Qos.class);
        this.qosBearerFilterList = new java.util.ArrayList();
        parcel.readList(this.qosBearerFilterList, android.telephony.data.QosBearerFilter.class.getClassLoader(), android.telephony.data.QosBearerFilter.class);
    }

    public int getQosBearerSessionId() {
        return this.qosBearerSessionId;
    }

    public android.telephony.data.Qos getQos() {
        return this.qos;
    }

    public java.util.List<android.telephony.data.QosBearerFilter> getQosBearerFilterList() {
        return this.qosBearerFilterList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.qosBearerSessionId);
        if (this.qos.getType() == 1) {
            parcel.writeParcelable((android.telephony.data.EpsQos) this.qos, i);
        } else {
            parcel.writeParcelable((android.telephony.data.NrQos) this.qos, i);
        }
        parcel.writeList(this.qosBearerFilterList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "QosBearerSession { qosBearerSessionId=" + this.qosBearerSessionId + " qos=" + this.qos + " qosBearerFilterList=" + this.qosBearerFilterList + "}";
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.qosBearerSessionId), this.qos, this.qosBearerFilterList);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.telephony.data.QosBearerSession)) {
            return false;
        }
        android.telephony.data.QosBearerSession qosBearerSession = (android.telephony.data.QosBearerSession) obj;
        if (this.qosBearerSessionId == qosBearerSession.qosBearerSessionId && java.util.Objects.equals(this.qos, qosBearerSession.qos) && this.qosBearerFilterList.size() == qosBearerSession.qosBearerFilterList.size() && this.qosBearerFilterList.containsAll(qosBearerSession.qosBearerFilterList)) {
            return true;
        }
        return false;
    }
}
