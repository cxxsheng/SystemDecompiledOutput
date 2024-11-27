package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public interface ScanCallback {
    void onAnalogSifStandardReported(int i);

    void onAtsc3PlpInfosReported(android.media.tv.tuner.frontend.Atsc3PlpInfo[] atsc3PlpInfoArr);

    void onDvbsStandardReported(int i);

    void onDvbtStandardReported(int i);

    @java.lang.Deprecated
    void onFrequenciesReported(int[] iArr);

    void onGroupIdsReported(int[] iArr);

    void onHierarchyReported(int i);

    void onInputStreamIdsReported(int[] iArr);

    void onLocked();

    void onPlpIdsReported(int[] iArr);

    void onProgress(int i);

    void onScanStopped();

    void onSignalTypeReported(int i);

    void onSymbolRatesReported(int[] iArr);

    default void onUnlocked() {
    }

    default void onFrequenciesLongReported(long[] jArr) {
        int[] iArr = new int[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            iArr[i] = (int) jArr[i];
        }
        onFrequenciesReported(iArr);
    }

    default void onModulationReported(int i) {
    }

    default void onPriorityReported(boolean z) {
    }

    default void onDvbcAnnexReported(int i) {
    }

    default void onDvbtCellIdsReported(int[] iArr) {
    }
}
