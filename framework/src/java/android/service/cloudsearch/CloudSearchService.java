package android.service.cloudsearch;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class CloudSearchService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.cloudsearch.CloudSearchService";

    public abstract void onSearch(android.app.cloudsearch.SearchRequest searchRequest);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    public final void returnResults(java.lang.String str, android.app.cloudsearch.SearchResponse searchResponse) {
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return null;
    }
}
