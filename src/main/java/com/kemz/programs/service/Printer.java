package com.kemz.programs.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

@Slf4j
@Service
public abstract class Printer {

    private static PrintService printService;
    private static DocPrintJob printJob;
    private static DocFlavor docFlavor;
    private static DocAttributeSet docAttributeSet;
    private static PrintRequestAttributeSet printRequestAttribute;
    private static Doc doc;

    private static final String PATH = "src/main/resources/static/sample/";

    static {
        printService = PrintServiceLookup.lookupDefaultPrintService();
        printJob = printService.createPrintJob();

        docAttributeSet = new HashDocAttributeSet();

        printRequestAttribute = new HashPrintRequestAttributeSet();
        printRequestAttribute.add(MediaSizeName.ISO_A4);
    }

    public static void printImg(ByteArrayInputStream html) throws PrintException {
        docFlavor = DocFlavor.INPUT_STREAM.PNG;

        doc = new SimpleDoc(html, docFlavor, docAttributeSet);
        printJob.print(doc, printRequestAttribute);
    }

    public static void printTools(String fileName, Map<String, Object> data){
        Document document;
        File file = new File("html.pdf");
        try (FileOutputStream outputStream = new FileOutputStream(file);) {

            document = Jsoup.parse(new File(PATH + fileName));
            Element table = document.getElementById("table");

            if (table!=null)
            data.forEach((e, k)-> {
                table.appendElement("tr").append(String.format("<td> %s </td> <td> %s </td>",e, k.toString()));
            });
            log.info(document.outerHtml());

            ITextRenderer renderer = new ITextRenderer();
            SharedContext context = renderer.getSharedContext();
            context.setPrint(true);
            context.setInteractive(false);
            renderer.setDocumentFromString(document.outerHtml());
            renderer.layout();
            renderer.createPDF(outputStream);

            docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            log.info(String.format("Тип печати %s ", docFlavor.getMimeType()));
            doc = new SimpleDoc(new FileInputStream(file), docFlavor, docAttributeSet);

            printJob.print(doc, printRequestAttribute);

        } catch (Exception e){
            log.info("Тут вместо печати инструментов " + e.getMessage());
        }
        finally {
            log.info("Мы сделали все что могли... осталось ждать pdf");
        }




    }

}
