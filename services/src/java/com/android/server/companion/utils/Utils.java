package com.android.server.companion.utils;

/* loaded from: classes.dex */
public final class Utils {
    private Utils() {
    }

    public static <T extends android.os.ResultReceiver> android.os.ResultReceiver prepareForIpc(T t) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        t.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) android.os.ResultReceiver.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return resultReceiver;
    }

    public static java.lang.String btDeviceToString(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(bluetoothDevice.getAddress());
        sb.append(" [name=");
        java.lang.String name = bluetoothDevice.getName();
        if (name != null) {
            sb.append('\'');
            sb.append(name);
            sb.append('\'');
        } else {
            sb.append("null");
        }
        java.lang.String alias = bluetoothDevice.getAlias();
        if (alias != null) {
            sb.append(", alias='");
            sb.append(alias);
            sb.append("'");
        }
        sb.append(']');
        return sb.toString();
    }
}
