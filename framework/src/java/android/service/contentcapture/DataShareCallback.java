package android.service.contentcapture;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface DataShareCallback {
    void onAccept(java.util.concurrent.Executor executor, android.service.contentcapture.DataShareReadAdapter dataShareReadAdapter);

    void onReject();
}
