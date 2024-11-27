package android.app.cloudsearch;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class CloudSearchManager {

    public interface CallBack {
        void onSearchFailed(android.app.cloudsearch.SearchRequest searchRequest, android.app.cloudsearch.SearchResponse searchResponse);

        void onSearchSucceeded(android.app.cloudsearch.SearchRequest searchRequest, android.app.cloudsearch.SearchResponse searchResponse);
    }

    @android.annotation.SystemApi
    public void search(final android.app.cloudsearch.SearchRequest searchRequest, java.util.concurrent.Executor executor, final android.app.cloudsearch.CloudSearchManager.CallBack callBack) {
        executor.execute(new java.lang.Runnable() { // from class: android.app.cloudsearch.CloudSearchManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.app.cloudsearch.CloudSearchManager.CallBack.this.onSearchFailed(searchRequest, new android.app.cloudsearch.SearchResponse.Builder(-1).build());
            }
        });
    }
}
