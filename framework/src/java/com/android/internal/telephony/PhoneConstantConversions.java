package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class PhoneConstantConversions {
    public static int convertCallState(com.android.internal.telephony.PhoneConstants.State state) {
        switch (state) {
            case RINGING:
                return 1;
            case OFFHOOK:
                return 2;
            default:
                return 0;
        }
    }

    public static com.android.internal.telephony.PhoneConstants.State convertCallState(int i) {
        switch (i) {
            case 1:
                return com.android.internal.telephony.PhoneConstants.State.RINGING;
            case 2:
                return com.android.internal.telephony.PhoneConstants.State.OFFHOOK;
            default:
                return com.android.internal.telephony.PhoneConstants.State.IDLE;
        }
    }

    public static int convertDataState(com.android.internal.telephony.PhoneConstants.DataState dataState) {
        switch (dataState) {
            case CONNECTING:
                return 1;
            case CONNECTED:
                return 2;
            case SUSPENDED:
                return 3;
            case DISCONNECTING:
                return 4;
            default:
                return 0;
        }
    }

    public static com.android.internal.telephony.PhoneConstants.DataState convertDataState(int i) {
        switch (i) {
            case 1:
                return com.android.internal.telephony.PhoneConstants.DataState.CONNECTING;
            case 2:
                return com.android.internal.telephony.PhoneConstants.DataState.CONNECTED;
            case 3:
                return com.android.internal.telephony.PhoneConstants.DataState.SUSPENDED;
            case 4:
                return com.android.internal.telephony.PhoneConstants.DataState.DISCONNECTING;
            default:
                return com.android.internal.telephony.PhoneConstants.DataState.DISCONNECTED;
        }
    }
}
