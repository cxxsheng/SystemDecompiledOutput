package android.telephony.data;

/* loaded from: classes3.dex */
public final class RouteSelectionDescriptor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.RouteSelectionDescriptor> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.RouteSelectionDescriptor>() { // from class: android.telephony.data.RouteSelectionDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.RouteSelectionDescriptor createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.RouteSelectionDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.RouteSelectionDescriptor[] newArray(int i) {
            return new android.telephony.data.RouteSelectionDescriptor[i];
        }
    };
    public static final int MAX_ROUTE_PRECEDENCE = 255;
    public static final int MAX_ROUTE_SSC_MODE = 3;
    public static final int MIN_ROUTE_PRECEDENCE = 0;
    public static final int MIN_ROUTE_SSC_MODE = 1;
    public static final int ROUTE_SSC_MODE_1 = 1;
    public static final int ROUTE_SSC_MODE_2 = 2;
    public static final int ROUTE_SSC_MODE_3 = 3;
    public static final int SESSION_TYPE_IPV4 = 0;
    public static final int SESSION_TYPE_IPV4V6 = 2;
    public static final int SESSION_TYPE_IPV6 = 1;
    private final java.util.List<java.lang.String> mDnn;
    private final int mPrecedence;
    private final int mSessionType;
    private final java.util.List<android.telephony.data.NetworkSliceInfo> mSliceInfo;
    private final int mSscMode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RouteSessionType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RouteSscMode {
    }

    public RouteSelectionDescriptor(int i, int i2, int i3, java.util.List<android.telephony.data.NetworkSliceInfo> list, java.util.List<java.lang.String> list2) {
        this.mPrecedence = i;
        this.mSessionType = i2;
        this.mSscMode = i3;
        this.mSliceInfo = new java.util.ArrayList();
        this.mSliceInfo.addAll(list);
        this.mDnn = new java.util.ArrayList();
        this.mDnn.addAll(list2);
    }

    private RouteSelectionDescriptor(android.os.Parcel parcel) {
        this.mPrecedence = parcel.readInt();
        this.mSessionType = parcel.readInt();
        this.mSscMode = parcel.readInt();
        this.mSliceInfo = parcel.createTypedArrayList(android.telephony.data.NetworkSliceInfo.CREATOR);
        this.mDnn = new java.util.ArrayList();
        parcel.readStringList(this.mDnn);
    }

    public int getPrecedence() {
        return this.mPrecedence;
    }

    public int getSessionType() {
        return this.mSessionType;
    }

    public int getSscMode() {
        return this.mSscMode;
    }

    public java.util.List<android.telephony.data.NetworkSliceInfo> getSliceInfo() {
        return this.mSliceInfo;
    }

    public java.util.List<java.lang.String> getDataNetworkName() {
        return this.mDnn;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPrecedence);
        parcel.writeInt(this.mSessionType);
        parcel.writeInt(this.mSscMode);
        parcel.writeTypedList(this.mSliceInfo, i);
        parcel.writeStringList(this.mDnn);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.data.RouteSelectionDescriptor routeSelectionDescriptor = (android.telephony.data.RouteSelectionDescriptor) obj;
        if (this.mPrecedence == routeSelectionDescriptor.mPrecedence && this.mSessionType == routeSelectionDescriptor.mSessionType && this.mSscMode == routeSelectionDescriptor.mSscMode && this.mSliceInfo.size() == routeSelectionDescriptor.mSliceInfo.size() && this.mSliceInfo.containsAll(routeSelectionDescriptor.mSliceInfo) && this.mDnn.size() == routeSelectionDescriptor.mDnn.size() && this.mDnn.containsAll(routeSelectionDescriptor.mDnn)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPrecedence), java.lang.Integer.valueOf(this.mSessionType), java.lang.Integer.valueOf(this.mSscMode), this.mSliceInfo, this.mDnn);
    }

    public java.lang.String toString() {
        return "{.precedence = " + this.mPrecedence + ", .sessionType = " + this.mSessionType + ", .sscMode = " + this.mSscMode + ", .sliceInfo = " + this.mSliceInfo + ", .dnn = " + this.mDnn + "}";
    }
}
