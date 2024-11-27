package com.android.server.broadcastradio.hal2;

/* loaded from: classes.dex */
class Utils {
    private static final java.lang.String TAG = "BcRadio2Srv.utils";

    interface FuncThrowingRemoteException<T> {
        T exec() throws android.os.RemoteException;
    }

    interface VoidFuncThrowingRemoteException {
        void exec() throws android.os.RemoteException;
    }

    private Utils() {
        throw new java.lang.UnsupportedOperationException("Utils class is noninstantiable");
    }

    static com.android.server.broadcastradio.hal2.FrequencyBand getBand(int i) {
        return i < 30 ? com.android.server.broadcastradio.hal2.FrequencyBand.UNKNOWN : i < 500 ? com.android.server.broadcastradio.hal2.FrequencyBand.AM_LW : i < 1705 ? com.android.server.broadcastradio.hal2.FrequencyBand.AM_MW : i < 30000 ? com.android.server.broadcastradio.hal2.FrequencyBand.AM_SW : i < 60000 ? com.android.server.broadcastradio.hal2.FrequencyBand.UNKNOWN : i < 110000 ? com.android.server.broadcastradio.hal2.FrequencyBand.FM : com.android.server.broadcastradio.hal2.FrequencyBand.UNKNOWN;
    }

    static <T> T maybeRethrow(@android.annotation.NonNull com.android.server.broadcastradio.hal2.Utils.FuncThrowingRemoteException<T> funcThrowingRemoteException) {
        try {
            return funcThrowingRemoteException.exec();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    static void maybeRethrow(@android.annotation.NonNull com.android.server.broadcastradio.hal2.Utils.VoidFuncThrowingRemoteException voidFuncThrowingRemoteException) {
        try {
            voidFuncThrowingRemoteException.exec();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
