package android.tracing.perfetto;

/* loaded from: classes3.dex */
public class CreateTlsStateArgs<DataSourceInstanceType extends android.tracing.perfetto.DataSourceInstance> {
    private final android.tracing.perfetto.DataSource<DataSourceInstanceType, java.lang.Object, java.lang.Object> mDataSource;
    private final int mInstanceIndex;

    CreateTlsStateArgs(android.tracing.perfetto.DataSource dataSource, int i) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = i;
    }

    public DataSourceInstanceType getDataSourceInstanceLocked() {
        return this.mDataSource.getDataSourceInstanceLocked(this.mInstanceIndex);
    }
}
