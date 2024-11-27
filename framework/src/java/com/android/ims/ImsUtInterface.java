package com.android.ims;

/* loaded from: classes4.dex */
public interface ImsUtInterface {
    public static final int ACTION_ACTIVATION = 1;
    public static final int ACTION_DEACTIVATION = 0;
    public static final int ACTION_ERASURE = 4;
    public static final int ACTION_INTERROGATION = 5;
    public static final int ACTION_REGISTRATION = 3;
    public static final int CB_BAIC = 1;
    public static final int CB_BAOC = 2;
    public static final int CB_BA_ALL = 7;
    public static final int CB_BA_MO = 8;
    public static final int CB_BA_MT = 9;
    public static final int CB_BIC_ACR = 6;
    public static final int CB_BIC_WR = 5;
    public static final int CB_BOIC = 3;
    public static final int CB_BOIC_EXHC = 4;
    public static final int CB_BS_MT = 10;
    public static final int CDIV_CF_ALL = 4;
    public static final int CDIV_CF_ALL_CONDITIONAL = 5;
    public static final int CDIV_CF_BUSY = 1;
    public static final int CDIV_CF_NOT_LOGGED_IN = 6;
    public static final int CDIV_CF_NOT_REACHABLE = 3;
    public static final int CDIV_CF_NO_REPLY = 2;
    public static final int CDIV_CF_UNCONDITIONAL = 0;
    public static final int INVALID = -1;
    public static final int OIR_DEFAULT = 0;
    public static final int OIR_PRESENTATION_NOT_RESTRICTED = 2;
    public static final int OIR_PRESENTATION_RESTRICTED = 1;

    void queryCLIP(android.os.Message message);

    void queryCLIR(android.os.Message message);

    void queryCOLP(android.os.Message message);

    void queryCOLR(android.os.Message message);

    void queryCallBarring(int i, android.os.Message message);

    void queryCallBarring(int i, android.os.Message message, int i2);

    void queryCallForward(int i, java.lang.String str, android.os.Message message);

    void queryCallWaiting(android.os.Message message);

    void registerForSuppServiceIndication(android.os.Handler handler, int i, java.lang.Object obj);

    void unregisterForSuppServiceIndication(android.os.Handler handler);

    void updateCLIP(boolean z, android.os.Message message);

    void updateCLIR(int i, android.os.Message message);

    void updateCOLP(boolean z, android.os.Message message);

    void updateCOLR(int i, android.os.Message message);

    void updateCallBarring(int i, int i2, android.os.Message message, java.lang.String[] strArr);

    void updateCallBarring(int i, int i2, android.os.Message message, java.lang.String[] strArr, int i3);

    void updateCallBarring(int i, int i2, android.os.Message message, java.lang.String[] strArr, int i3, java.lang.String str);

    void updateCallForward(int i, int i2, java.lang.String str, int i3, int i4, android.os.Message message);

    void updateCallWaiting(boolean z, int i, android.os.Message message);
}
