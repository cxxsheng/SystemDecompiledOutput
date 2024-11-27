package android.telephony;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class CellLocation {
    public abstract void fillInNotifierBundle(android.os.Bundle bundle);

    public abstract boolean isEmpty();

    public abstract void setStateInvalid();

    @java.lang.Deprecated
    public static void requestLocationUpdate() {
        android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        if (currentApplication == null) {
            return;
        }
        try {
            com.android.internal.telephony.ITelephony asInterface = com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
            if (asInterface != null) {
                asInterface.updateServiceLocationWithPackageName(currentApplication.getOpPackageName());
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public static android.telephony.CellLocation newFromBundle(android.os.Bundle bundle) {
        switch (android.telephony.TelephonyManager.getDefault().getCurrentPhoneType()) {
            case 1:
                return new android.telephony.gsm.GsmCellLocation(bundle);
            case 2:
                return new android.telephony.cdma.CdmaCellLocation(bundle);
            default:
                return null;
        }
    }

    public static android.telephony.CellLocation getEmpty() {
        switch (android.telephony.TelephonyManager.getDefault().getCurrentPhoneType()) {
            case 1:
                return new android.telephony.gsm.GsmCellLocation();
            case 2:
                return new android.telephony.cdma.CdmaCellLocation();
            default:
                return null;
        }
    }
}
