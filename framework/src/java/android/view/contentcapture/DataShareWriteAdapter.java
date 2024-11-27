package android.view.contentcapture;

/* loaded from: classes4.dex */
public interface DataShareWriteAdapter {
    void onRejected();

    void onWrite(android.os.ParcelFileDescriptor parcelFileDescriptor);

    default void onError(int i) {
    }
}
