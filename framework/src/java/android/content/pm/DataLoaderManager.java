package android.content.pm;

/* loaded from: classes.dex */
public class DataLoaderManager {
    private static final java.lang.String TAG = "DataLoaderManager";
    private final android.content.pm.IDataLoaderManager mService;

    public DataLoaderManager(android.content.pm.IDataLoaderManager iDataLoaderManager) {
        this.mService = iDataLoaderManager;
    }

    public boolean bindToDataLoader(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, long j, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) {
        try {
            return this.mService.bindToDataLoader(i, dataLoaderParamsParcel, j, iDataLoaderStatusListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.pm.IDataLoader getDataLoader(int i) {
        try {
            return this.mService.getDataLoader(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unbindFromDataLoader(int i) {
        try {
            this.mService.unbindFromDataLoader(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
