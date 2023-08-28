package com.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PDFReaderTest {

    public static void main(String[] args) {

        final String URL = "https://www.africau.edu/images/default/sample.pdf";
        final int PAGE_NUMBER = 2;

        try {
            byte[] bytes  = readPDFFromURL(URL);
            PdfReader reader = new PdfReader(bytes);
            TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            String currentText = PdfTextExtractor.getTextFromPage(reader, PAGE_NUMBER, strategy);
            System.out.print(currentText);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static byte[] readPDFFromURL(String url) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri( new URI(url))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        return response.body();
    }
}
