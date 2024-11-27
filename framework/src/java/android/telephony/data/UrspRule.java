package android.telephony.data;

/* loaded from: classes3.dex */
public final class UrspRule implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.UrspRule> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.UrspRule>() { // from class: android.telephony.data.UrspRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.UrspRule createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.UrspRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.UrspRule[] newArray(int i) {
            return new android.telephony.data.UrspRule[i];
        }
    };
    public static final int MAX_URSP_PRECEDENCE = 255;
    public static final int MIN_URSP_PRECEDENCE = 0;
    private final int mPrecedence;
    private final java.util.List<android.telephony.data.RouteSelectionDescriptor> mRouteSelectionDescriptor;
    private final java.util.List<android.telephony.data.TrafficDescriptor> mTrafficDescriptors;

    public UrspRule(int i, java.util.List<android.telephony.data.TrafficDescriptor> list, java.util.List<android.telephony.data.RouteSelectionDescriptor> list2) {
        this.mPrecedence = i;
        this.mTrafficDescriptors = new java.util.ArrayList();
        this.mTrafficDescriptors.addAll(list);
        this.mRouteSelectionDescriptor = new java.util.ArrayList();
        this.mRouteSelectionDescriptor.addAll(list2);
    }

    private UrspRule(android.os.Parcel parcel) {
        this.mPrecedence = parcel.readInt();
        this.mTrafficDescriptors = parcel.createTypedArrayList(android.telephony.data.TrafficDescriptor.CREATOR);
        this.mRouteSelectionDescriptor = parcel.createTypedArrayList(android.telephony.data.RouteSelectionDescriptor.CREATOR);
    }

    public int getPrecedence() {
        return this.mPrecedence;
    }

    public java.util.List<android.telephony.data.TrafficDescriptor> getTrafficDescriptors() {
        return this.mTrafficDescriptors;
    }

    public java.util.List<android.telephony.data.RouteSelectionDescriptor> getRouteSelectionDescriptor() {
        return this.mRouteSelectionDescriptor;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPrecedence);
        parcel.writeTypedList(this.mTrafficDescriptors, i);
        parcel.writeTypedList(this.mRouteSelectionDescriptor, i);
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
        android.telephony.data.UrspRule urspRule = (android.telephony.data.UrspRule) obj;
        if (this.mPrecedence == urspRule.mPrecedence && this.mTrafficDescriptors.size() == urspRule.mTrafficDescriptors.size() && this.mTrafficDescriptors.containsAll(urspRule.mTrafficDescriptors) && this.mRouteSelectionDescriptor.size() == urspRule.mRouteSelectionDescriptor.size() && this.mRouteSelectionDescriptor.containsAll(urspRule.mRouteSelectionDescriptor)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPrecedence), this.mTrafficDescriptors, this.mRouteSelectionDescriptor);
    }

    public java.lang.String toString() {
        return "{.precedence = " + this.mPrecedence + ", .trafficDescriptors = " + this.mTrafficDescriptors + ", .routeSelectionDescriptor = " + this.mRouteSelectionDescriptor + "}";
    }
}
