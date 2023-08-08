package com.kemz.programs.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.MediaSizeName;
import java.io.ByteArrayInputStream;
import java.io.File;
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
        docFlavor = DocFlavor.INPUT_STREAM.TEXT_HTML_HOST;
        Document document;
        try {
             document = Jsoup.parse(new File(PATH + fileName));
            Element name = document.getElementById("table");

            data.forEach((e, k)-> {
                name.appendElement(String.format("<rt> <td> %s </td> <td> %s </td> </tr>",e, k.toString()));

            });



            log.info(document.outerHtml());
        } catch (Exception e){
            log.info("Тут вместо печати инструментов " + e.getMessage());
        }

        log.info("lol");


    }

}
