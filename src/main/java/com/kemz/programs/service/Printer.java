package com.kemz.programs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.MediaSizeName;
import java.io.ByteArrayInputStream;

@Slf4j
@Service
public abstract class Printer {

    private static PrintService printService;
    private static DocPrintJob printJob;
    private static DocFlavor docFlavor;
    private static DocAttributeSet docAttributeSet;
    private static PrintRequestAttributeSet printRequestAttribute;
    private static Doc doc;

    static {
        printService = PrintServiceLookup.lookupDefaultPrintService();
        printJob = printService.createPrintJob();
        docFlavor = DocFlavor.INPUT_STREAM.PNG;
        docAttributeSet = new HashDocAttributeSet();

        printRequestAttribute = new HashPrintRequestAttributeSet();
        printRequestAttribute.add(MediaSizeName.ISO_A4);

    }

    public static void pull(ByteArrayInputStream html) throws PrintException {
        doc = new SimpleDoc(html, docFlavor, docAttributeSet);
        printJob.print(doc, printRequestAttribute);
    }

}
