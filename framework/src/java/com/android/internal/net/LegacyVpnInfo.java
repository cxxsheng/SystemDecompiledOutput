package com.android.internal.net;

/* loaded from: classes4.dex */
public class LegacyVpnInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.net.LegacyVpnInfo> CREATOR = new android.os.Parcelable.Creator<com.android.internal.net.LegacyVpnInfo>() { // from class: com.android.internal.net.LegacyVpnInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.net.LegacyVpnInfo createFromParcel(android.os.Parcel parcel) {
            com.android.internal.net.LegacyVpnInfo legacyVpnInfo = new com.android.internal.net.LegacyVpnInfo();
            legacyVpnInfo.key = parcel.readString();
            legacyVpnInfo.state = parcel.readInt();
            legacyVpnInfo.intent = (android.app.PendingIntent) parcel.readParcelable(null, android.app.PendingIntent.class);
            return legacyVpnInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.net.LegacyVpnInfo[] newArray(int i) {
            return new com.android.internal.net.LegacyVpnInfo[i];
        }
    };
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_FAILED = 5;
    public static final int STATE_INITIALIZING = 1;
    public static final int STATE_TIMEOUT = 4;
    private static final java.lang.String TAG = "LegacyVpnInfo";
    public android.app.PendingIntent intent;
    public java.lang.String key;
    public int state = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeInt(this.state);
        parcel.writeParcelable(this.intent, i);
    }

    /* renamed from: com.android.internal.net.LegacyVpnInfo$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$DetailedState = new int[android.net.NetworkInfo.DetailedState.values().length];

        static {
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.CONNECTING.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.CONNECTED.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.DISCONNECTED.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.FAILED.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
        }
    }

    public static int stateFromNetworkInfo(android.net.NetworkInfo.DetailedState detailedState) {
        switch (com.android.internal.net.LegacyVpnInfo.AnonymousClass2.$SwitchMap$android$net$NetworkInfo$DetailedState[detailedState.ordinal()]) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                android.util.Log.w(TAG, "Unhandled state " + detailedState + " ; treating as disconnected");
                break;
        }
        return 0;
    }
}
