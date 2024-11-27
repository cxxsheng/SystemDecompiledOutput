package com.android.server.notification;

/* loaded from: classes2.dex */
public class SysUiStatsEvent {

    static class Builder {
        private final android.util.StatsEvent.Builder mBuilder;

        protected Builder(android.util.StatsEvent.Builder builder) {
            this.mBuilder = builder;
        }

        public android.util.StatsEvent build() {
            return this.mBuilder.build();
        }

        public com.android.server.notification.SysUiStatsEvent.Builder setAtomId(int i) {
            this.mBuilder.setAtomId(i);
            return this;
        }

        public com.android.server.notification.SysUiStatsEvent.Builder writeInt(int i) {
            this.mBuilder.writeInt(i);
            return this;
        }

        public com.android.server.notification.SysUiStatsEvent.Builder addBooleanAnnotation(byte b, boolean z) {
            this.mBuilder.addBooleanAnnotation(b, z);
            return this;
        }

        public com.android.server.notification.SysUiStatsEvent.Builder writeString(java.lang.String str) {
            this.mBuilder.writeString(str);
            return this;
        }

        public com.android.server.notification.SysUiStatsEvent.Builder writeBoolean(boolean z) {
            this.mBuilder.writeBoolean(z);
            return this;
        }

        public com.android.server.notification.SysUiStatsEvent.Builder writeByteArray(byte[] bArr) {
            this.mBuilder.writeByteArray(bArr);
            return this;
        }
    }

    static class BuilderFactory {
        BuilderFactory() {
        }

        com.android.server.notification.SysUiStatsEvent.Builder newBuilder() {
            return new com.android.server.notification.SysUiStatsEvent.Builder(android.util.StatsEvent.newBuilder());
        }
    }
}
