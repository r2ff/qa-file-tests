package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.xlstest.XLS;
import io.qameta.allure.selenide.AllureSelenide;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static utils.Files.*;

//отличный код!

public class CompareFilesTests {
    String txtFilePath = "./src/test/resources/files/text.txt",
            pdfFilePath = "./src/test/resources/files/text.pdf",
            xlsFilePath = "./src/test/resources/files/text.xls",
            xlsxFilePath = "./src/test/resources/files/text.xlsx",
            docFilePath = "./src/test/resources/files/text.doc",
            docxFilePath = "./src/test/resources/files/text.docx",
            zipFilePath = "./src/test/resources/files/text.zip",
            unzipFolderPath = "./src/test/resources/files/unzip/",
            unzipTxtFilePath = "./src/test/resources/files/unzip/text.txt",
            zipPassword = "123",
            expectedData = "a large language ocean";

    @Test
    void txtFileCompareTest() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        String actualData = readTextFromPath(txtFilePath);
        assertThat(actualData, containsString(expectedData));
    }

    @Test
    void pdfFileCompareTest() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        PDF pdf = new PDF(new File(pdfFilePath));
        assertThat(pdf, PDF.containsText(expectedData));
    }

    @Test
    void xlsFileCompareTest() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        XLS spreadsheet = new XLS(new File(xlsFilePath));
        assertThat(spreadsheet, XLS.containsText(expectedData));
    }

    @Test
    void xlsxFileCompareTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        String actualDate = readXlsxFromPath(xlsxFilePath);
        assertThat(actualDate, containsString(expectedData));
    }


    @Test
    void zipFileCompareTest() throws IOException, ZipException {
        SelenideLogger.addListener("allure", new AllureSelenide());

        ZipFile zipFile = new ZipFile(zipFilePath);
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(zipPassword);
        }
        zipFile.extractAll(unzipFolderPath);

        String actualDate = readTextFromPath(unzipTxtFilePath);
        assertThat(actualDate, containsString(expectedData));

    }


    //https://gist.github.com/madan712/10641676
    @Test
    void docFileCompareTest() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());

        FileInputStream fis = new FileInputStream(docFilePath);
        HWPFDocument doc = new HWPFDocument(fis);
        WordExtractor we = new WordExtractor(doc);

        assertThat(we.getText(), containsString(expectedData));
        fis.close();

    }

    @Test
    void docxFileCompareTest() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        FileInputStream stream = new FileInputStream(docxFilePath);
        XWPFDocument document = new XWPFDocument(stream);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        assertThat(extractor.getText(), containsString(expectedData));
    }
}
