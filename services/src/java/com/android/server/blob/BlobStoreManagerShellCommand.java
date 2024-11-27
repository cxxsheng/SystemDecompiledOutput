package com.android.server.blob;

/* loaded from: classes.dex */
class BlobStoreManagerShellCommand extends android.os.ShellCommand {
    private final com.android.server.blob.BlobStoreManagerService mService;

    BlobStoreManagerShellCommand(com.android.server.blob.BlobStoreManagerService blobStoreManagerService) {
        this.mService = blobStoreManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands((java.lang.String) null);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        switch (str.hashCode()) {
            case -1168531841:
                if (str.equals("delete-blob")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -971115831:
                if (str.equals("clear-all-sessions")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -258166326:
                if (str.equals("clear-all-blobs")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 712607671:
                if (str.equals("query-blob-existence")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1861559962:
                if (str.equals("idle-maintenance")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return runClearAllSessions(outPrintWriter);
            case 1:
                return runClearAllBlobs(outPrintWriter);
            case 2:
                return runDeleteBlob(outPrintWriter);
            case 3:
                return runIdleMaintenance(outPrintWriter);
            case 4:
                return runQueryBlobExistence(outPrintWriter);
            default:
                return handleDefaultCommands(str);
        }
    }

    private int runClearAllSessions(java.io.PrintWriter printWriter) {
        com.android.server.blob.BlobStoreManagerShellCommand.ParsedArgs parsedArgs = new com.android.server.blob.BlobStoreManagerShellCommand.ParsedArgs();
        parsedArgs.userId = -1;
        if (parseOptions(printWriter, parsedArgs) < 0) {
            return -1;
        }
        this.mService.runClearAllSessions(parsedArgs.userId);
        return 0;
    }

    private int runClearAllBlobs(java.io.PrintWriter printWriter) {
        com.android.server.blob.BlobStoreManagerShellCommand.ParsedArgs parsedArgs = new com.android.server.blob.BlobStoreManagerShellCommand.ParsedArgs();
        parsedArgs.userId = -1;
        if (parseOptions(printWriter, parsedArgs) < 0) {
            return -1;
        }
        this.mService.runClearAllBlobs(parsedArgs.userId);
        return 0;
    }

    private int runDeleteBlob(java.io.PrintWriter printWriter) {
        com.android.server.blob.BlobStoreManagerShellCommand.ParsedArgs parsedArgs = new com.android.server.blob.BlobStoreManagerShellCommand.ParsedArgs();
        if (parseOptions(printWriter, parsedArgs) < 0) {
            return -1;
        }
        this.mService.deleteBlob(parsedArgs.getBlobHandle(), parsedArgs.userId);
        return 0;
    }

    private int runIdleMaintenance(java.io.PrintWriter printWriter) {
        this.mService.runIdleMaintenance();
        return 0;
    }

    private int runQueryBlobExistence(java.io.PrintWriter printWriter) {
        com.android.server.blob.BlobStoreManagerShellCommand.ParsedArgs parsedArgs = new com.android.server.blob.BlobStoreManagerShellCommand.ParsedArgs();
        if (parseOptions(printWriter, parsedArgs) < 0) {
            return -1;
        }
        printWriter.println(this.mService.isBlobAvailable(parsedArgs.blobId, parsedArgs.userId) ? 1 : 0);
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("BlobStore service (blob_store) commands:");
        outPrintWriter.println("help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("clear-all-sessions [-u | --user USER_ID]");
        outPrintWriter.println("    Remove all sessions.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's sessions to be removed.");
        outPrintWriter.println("                    If not specified, sessions in all users are removed.");
        outPrintWriter.println();
        outPrintWriter.println("clear-all-blobs [-u | --user USER_ID]");
        outPrintWriter.println("    Remove all blobs.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's blobs to be removed.");
        outPrintWriter.println("                    If not specified, blobs in all users are removed.");
        outPrintWriter.println("delete-blob [-u | --user USER_ID] [--digest DIGEST] [--expiry EXPIRY_TIME] [--label LABEL] [--tag TAG]");
        outPrintWriter.println("    Delete a blob.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's blobs to be removed;");
        outPrintWriter.println("                    If not specified, blobs in all users are removed.");
        outPrintWriter.println("      --digest: Base64 encoded digest of the blob to delete.");
        outPrintWriter.println("      --expiry: Expiry time of the blob to delete, in milliseconds.");
        outPrintWriter.println("      --label: Label of the blob to delete.");
        outPrintWriter.println("      --tag: Tag of the blob to delete.");
        outPrintWriter.println("idle-maintenance");
        outPrintWriter.println("    Run idle maintenance which takes care of removing stale data.");
        outPrintWriter.println("query-blob-existence [-b BLOB_ID]");
        outPrintWriter.println("    Prints 1 if blob exists, otherwise 0.");
        outPrintWriter.println();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0049, code lost:
    
        if (r0.equals("-u") != false) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int parseOptions(java.io.PrintWriter printWriter, com.android.server.blob.BlobStoreManagerShellCommand.ParsedArgs parsedArgs) {
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 0;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -1620968108:
                        if (nextOption.equals("--label")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1493:
                        if (nextOption.equals("-b")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1512:
                        break;
                    case 43013626:
                        if (nextOption.equals("--tag")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1068100452:
                        if (nextOption.equals("--digest")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1110854355:
                        if (nextOption.equals("--expiry")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1332867059:
                        if (nextOption.equals("--algo")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                        parsedArgs.userId = java.lang.Integer.parseInt(getNextArgRequired());
                        break;
                    case 2:
                        parsedArgs.algorithm = getNextArgRequired();
                        break;
                    case 3:
                        parsedArgs.digest = java.util.Base64.getDecoder().decode(getNextArgRequired());
                        break;
                    case 4:
                        parsedArgs.label = getNextArgRequired();
                        break;
                    case 5:
                        parsedArgs.expiryTimeMillis = java.lang.Long.parseLong(getNextArgRequired());
                        break;
                    case 6:
                        parsedArgs.tag = getNextArgRequired();
                        break;
                    case 7:
                        parsedArgs.blobId = java.lang.Long.parseLong(getNextArgRequired());
                        break;
                    default:
                        printWriter.println("Error: unknown option '" + nextOption + "'");
                        return -1;
                }
            } else {
                if (parsedArgs.userId == -2) {
                    parsedArgs.userId = android.app.ActivityManager.getCurrentUser();
                }
                return 0;
            }
        }
    }

    private static class ParsedArgs {
        public java.lang.String algorithm;
        public long blobId;
        public byte[] digest;
        public long expiryTimeMillis;
        public java.lang.CharSequence label;
        public java.lang.String tag;
        public int userId;

        private ParsedArgs() {
            this.userId = -2;
            this.algorithm = "SHA-256";
        }

        public android.app.blob.BlobHandle getBlobHandle() {
            return android.app.blob.BlobHandle.create(this.algorithm, this.digest, this.label, this.expiryTimeMillis, this.tag);
        }
    }
}
