package android.nfc;

/* loaded from: classes2.dex */
public interface FeatureFlags {
    boolean enableNfcCharging();

    boolean enableNfcMainline();

    boolean enableNfcReaderOption();

    boolean enableNfcSetDiscoveryTech();

    boolean enableNfcUserRestriction();

    boolean enableTagDetectionBroadcasts();

    boolean nfcObserveMode();

    boolean nfcObserveModeStShim();

    boolean nfcReadPollingLoop();

    boolean nfcReadPollingLoopStShim();

    boolean nfcVendorCmd();
}
