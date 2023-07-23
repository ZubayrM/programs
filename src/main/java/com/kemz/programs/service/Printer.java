package com.kemz.programs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.ByteArrayInputStream;

@Slf4j
@Service
public class Printer {

    private static PrintService printService;
    private static DocPrintJob printJob;
    private static DocFlavor docFlavor;
    private static DocAttributeSet docAttributeSet;
    private static PrintRequestAttributeSet printRequestAttribute;
    private static Doc doc;

    static {
        printService = PrintServiceLookup.lookupDefaultPrintService();
        printJob = printService.createPrintJob();
        docFlavor = new DocFlavor("text/html;charset=us-ascii", "java.io.InputStream");
        docAttributeSet = new HashDocAttributeSet();
        printRequestAttribute = new HashPrintRequestAttributeSet();
    }

    public static void pull(ByteArrayInputStream html) throws PrintException {
        doc = new SimpleDoc(html, docFlavor, docAttributeSet);
        printJob.print(doc, printRequestAttribute);
    }

}
