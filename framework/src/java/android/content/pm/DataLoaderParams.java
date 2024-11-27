package android.content.pm;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class DataLoaderParams {
    private final android.content.pm.DataLoaderParamsParcel mData;

    public static final android.content.pm.DataLoaderParams forStreaming(android.content.ComponentName componentName, java.lang.String str) {
        return new android.content.pm.DataLoaderParams(1, componentName, str);
    }

    @android.annotation.SystemApi
    public static final android.content.pm.DataLoaderParams forIncremental(android.content.ComponentName componentName, java.lang.String str) {
        return new android.content.pm.DataLoaderParams(2, componentName, str);
    }

    public DataLoaderParams(int i, android.content.ComponentName componentName, java.lang.String str) {
        android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel = new android.content.pm.DataLoaderParamsParcel();
        dataLoaderParamsParcel.type = i;
        dataLoaderParamsParcel.packageName = componentName.getPackageName();
        dataLoaderParamsParcel.className = componentName.getClassName();
        dataLoaderParamsParcel.arguments = str;
        this.mData = dataLoaderParamsParcel;
    }

    DataLoaderParams(android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel) {
        this.mData = dataLoaderParamsParcel;
    }

    public final android.content.pm.DataLoaderParamsParcel getData() {
        return this.mData;
    }

    public final int getType() {
        return this.mData.type;
    }

    public final android.content.ComponentName getComponentName() {
        return new android.content.ComponentName(this.mData.packageName, this.mData.className);
    }

    public final java.lang.String getArguments() {
        return this.mData.arguments;
    }
}
