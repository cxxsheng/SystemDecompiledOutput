package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
public final class IkeTrafficSelectorUtils {
    private static final java.lang.String END_ADDRESS_KEY = "END_ADDRESS_KEY";
    private static final java.lang.String END_PORT_KEY = "END_PORT_KEY";
    private static final java.lang.String START_ADDRESS_KEY = "START_ADDRESS_KEY";
    private static final java.lang.String START_PORT_KEY = "START_PORT_KEY";

    public static android.net.ipsec.ike.IkeTrafficSelector fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        int i = persistableBundle.getInt(START_PORT_KEY);
        int i2 = persistableBundle.getInt(END_PORT_KEY);
        java.lang.String string = persistableBundle.getString(START_ADDRESS_KEY);
        java.lang.String string2 = persistableBundle.getString(END_ADDRESS_KEY);
        java.util.Objects.requireNonNull(string, "startAddress was null");
        java.util.Objects.requireNonNull(string, "endAddress was null");
        return new android.net.ipsec.ike.IkeTrafficSelector(i, i2, android.net.InetAddresses.parseNumericAddress(string), android.net.InetAddresses.parseNumericAddress(string2));
    }

    public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeTrafficSelector ikeTrafficSelector) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putInt(START_PORT_KEY, ikeTrafficSelector.startPort);
        persistableBundle.putInt(END_PORT_KEY, ikeTrafficSelector.endPort);
        persistableBundle.putString(START_ADDRESS_KEY, ikeTrafficSelector.startingAddress.getHostAddress());
        persistableBundle.putString(END_ADDRESS_KEY, ikeTrafficSelector.endingAddress.getHostAddress());
        return persistableBundle;
    }
}
