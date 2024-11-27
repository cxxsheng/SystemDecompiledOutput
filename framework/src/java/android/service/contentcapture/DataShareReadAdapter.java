package android.service.contentcapture;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface DataShareReadAdapter {
    void onError(int i);

    void onStart(android.os.ParcelFileDescriptor parcelFileDescriptor);
}
