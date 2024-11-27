package com.android.server.pm;

/* loaded from: classes2.dex */
public final class BackgroundDexOptJobService extends android.app.job.JobService {
    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        return com.android.server.pm.BackgroundDexOptService.getService().onStartJob(this, jobParameters);
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        return com.android.server.pm.BackgroundDexOptService.getService().onStopJob(this, jobParameters);
    }
}
