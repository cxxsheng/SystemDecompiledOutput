package com.android.internal.print;

/* loaded from: classes5.dex */
public class DumpUtils {
    public static void writePrinterId(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PrinterId printerId) {
        long start = dualDumpOutputStream.start(str, j);
        com.android.internal.util.dump.DumpUtils.writeComponentName(dualDumpOutputStream, "service_name", 1146756268033L, printerId.getServiceName());
        dualDumpOutputStream.write("local_id", 1138166333442L, printerId.getLocalId());
        dualDumpOutputStream.end(start);
    }

    public static void writePrinterCapabilities(android.content.Context context, com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PrinterCapabilitiesInfo printerCapabilitiesInfo) {
        long start = dualDumpOutputStream.start(str, j);
        writeMargins(dualDumpOutputStream, "min_margins", 1146756268033L, printerCapabilitiesInfo.getMinMargins());
        int size = printerCapabilitiesInfo.getMediaSizes().size();
        for (int i = 0; i < size; i++) {
            writeMediaSize(context, dualDumpOutputStream, "media_sizes", 2246267895810L, printerCapabilitiesInfo.getMediaSizes().get(i));
        }
        int size2 = printerCapabilitiesInfo.getResolutions().size();
        for (int i2 = 0; i2 < size2; i2++) {
            writeResolution(dualDumpOutputStream, "resolutions", 2246267895811L, printerCapabilitiesInfo.getResolutions().get(i2));
        }
        if ((printerCapabilitiesInfo.getColorModes() & 1) != 0) {
            dualDumpOutputStream.write("color_modes", 2259152797700L, 1);
        }
        if ((printerCapabilitiesInfo.getColorModes() & 2) != 0) {
            dualDumpOutputStream.write("color_modes", 2259152797700L, 2);
        }
        if ((printerCapabilitiesInfo.getDuplexModes() & 1) != 0) {
            dualDumpOutputStream.write("duplex_modes", android.service.print.PrinterCapabilitiesProto.DUPLEX_MODES, 1);
        }
        if ((printerCapabilitiesInfo.getDuplexModes() & 2) != 0) {
            dualDumpOutputStream.write("duplex_modes", android.service.print.PrinterCapabilitiesProto.DUPLEX_MODES, 2);
        }
        if ((printerCapabilitiesInfo.getDuplexModes() & 4) != 0) {
            dualDumpOutputStream.write("duplex_modes", android.service.print.PrinterCapabilitiesProto.DUPLEX_MODES, 4);
        }
        dualDumpOutputStream.end(start);
    }

    public static void writePrinterInfo(android.content.Context context, com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PrinterInfo printerInfo) {
        long start = dualDumpOutputStream.start(str, j);
        writePrinterId(dualDumpOutputStream, "id", 1146756268033L, printerInfo.getId());
        dualDumpOutputStream.write("name", 1138166333442L, printerInfo.getName());
        dualDumpOutputStream.write("status", 1159641169923L, printerInfo.getStatus());
        dualDumpOutputStream.write("description", 1138166333444L, printerInfo.getDescription());
        android.print.PrinterCapabilitiesInfo capabilities = printerInfo.getCapabilities();
        if (capabilities != null) {
            writePrinterCapabilities(context, dualDumpOutputStream, "capabilities", 1146756268037L, capabilities);
        }
        dualDumpOutputStream.end(start);
    }

    public static void writeMediaSize(android.content.Context context, com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PrintAttributes.MediaSize mediaSize) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("id", 1138166333441L, mediaSize.getId());
        dualDumpOutputStream.write("label", 1138166333442L, mediaSize.getLabel(context.getPackageManager()));
        dualDumpOutputStream.write("height_mils", 1120986464259L, mediaSize.getHeightMils());
        dualDumpOutputStream.write("width_mils", 1120986464260L, mediaSize.getWidthMils());
        dualDumpOutputStream.end(start);
    }

    public static void writeResolution(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PrintAttributes.Resolution resolution) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("id", 1138166333441L, resolution.getId());
        dualDumpOutputStream.write("label", 1138166333442L, resolution.getLabel());
        dualDumpOutputStream.write("horizontal_DPI", 1120986464259L, resolution.getHorizontalDpi());
        dualDumpOutputStream.write("veritical_DPI", 1120986464260L, resolution.getVerticalDpi());
        dualDumpOutputStream.end(start);
    }

    public static void writeMargins(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PrintAttributes.Margins margins) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("top_mils", 1120986464257L, margins.getTopMils());
        dualDumpOutputStream.write("left_mils", 1120986464258L, margins.getLeftMils());
        dualDumpOutputStream.write("right_mils", 1120986464259L, margins.getRightMils());
        dualDumpOutputStream.write("bottom_mils", 1120986464260L, margins.getBottomMils());
        dualDumpOutputStream.end(start);
    }

    public static void writePrintAttributes(android.content.Context context, com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PrintAttributes printAttributes) {
        long start = dualDumpOutputStream.start(str, j);
        android.print.PrintAttributes.MediaSize mediaSize = printAttributes.getMediaSize();
        if (mediaSize != null) {
            writeMediaSize(context, dualDumpOutputStream, "media_size", 1146756268033L, mediaSize);
            dualDumpOutputStream.write("is_portrait", 1133871366146L, printAttributes.isPortrait());
        }
        android.print.PrintAttributes.Resolution resolution = printAttributes.getResolution();
        if (resolution != null) {
            writeResolution(dualDumpOutputStream, "resolution", 1146756268035L, resolution);
        }
        android.print.PrintAttributes.Margins minMargins = printAttributes.getMinMargins();
        if (minMargins != null) {
            writeMargins(dualDumpOutputStream, "min_margings", 1146756268036L, minMargins);
        }
        dualDumpOutputStream.write("color_mode", 1159641169925L, printAttributes.getColorMode());
        dualDumpOutputStream.write(android.provider.Telephony.ServiceStateTable.DUPLEX_MODE, 1159641169926L, printAttributes.getDuplexMode());
        dualDumpOutputStream.end(start);
    }

    public static void writePrintDocumentInfo(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PrintDocumentInfo printDocumentInfo) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("name", 1138166333441L, printDocumentInfo.getName());
        int pageCount = printDocumentInfo.getPageCount();
        if (pageCount != -1) {
            dualDumpOutputStream.write("page_count", 1120986464258L, pageCount);
        }
        dualDumpOutputStream.write("content_type", 1120986464259L, printDocumentInfo.getContentType());
        dualDumpOutputStream.write("data_size", 1112396529668L, printDocumentInfo.getDataSize());
        dualDumpOutputStream.end(start);
    }

    public static void writePageRange(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PageRange pageRange) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("start", 1120986464257L, pageRange.getStart());
        dualDumpOutputStream.write("end", 1120986464258L, pageRange.getEnd());
        dualDumpOutputStream.end(start);
    }

    public static void writePrintJobInfo(android.content.Context context, com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, android.print.PrintJobInfo printJobInfo) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("label", 1138166333441L, printJobInfo.getLabel());
        android.print.PrintJobId id = printJobInfo.getId();
        if (id != null) {
            dualDumpOutputStream.write("print_job_id", 1138166333442L, id.flattenToString());
        }
        int state = printJobInfo.getState();
        if (state >= 1 && state <= 7) {
            dualDumpOutputStream.write("state", 1159641169923L, state);
        } else {
            dualDumpOutputStream.write("state", 1159641169923L, 0);
        }
        android.print.PrinterId printerId = printJobInfo.getPrinterId();
        if (printerId != null) {
            writePrinterId(dualDumpOutputStream, "printer", 1146756268036L, printerId);
        }
        java.lang.String tag = printJobInfo.getTag();
        if (tag != null) {
            dualDumpOutputStream.write("tag", 1138166333445L, tag);
        }
        dualDumpOutputStream.write("creation_time", 1112396529670L, printJobInfo.getCreationTime());
        android.print.PrintAttributes attributes = printJobInfo.getAttributes();
        if (attributes != null) {
            writePrintAttributes(context, dualDumpOutputStream, "attributes", 1146756268039L, attributes);
        }
        android.print.PrintDocumentInfo documentInfo = printJobInfo.getDocumentInfo();
        if (documentInfo != null) {
            writePrintDocumentInfo(dualDumpOutputStream, "document_info", 1146756268040L, documentInfo);
        }
        dualDumpOutputStream.write("is_canceling", 1133871366153L, printJobInfo.isCancelling());
        android.print.PageRange[] pages = printJobInfo.getPages();
        if (pages != null) {
            for (android.print.PageRange pageRange : pages) {
                writePageRange(dualDumpOutputStream, "pages", 2246267895818L, pageRange);
            }
        }
        dualDumpOutputStream.write("has_advanced_options", 1133871366155L, printJobInfo.getAdvancedOptions() != null);
        dualDumpOutputStream.write(android.app.Notification.CATEGORY_PROGRESS, 1108101562380L, printJobInfo.getProgress());
        java.lang.CharSequence status = printJobInfo.getStatus(context.getPackageManager());
        if (status != null) {
            dualDumpOutputStream.write("status", 1138166333453L, status.toString());
        }
        dualDumpOutputStream.end(start);
    }
}
