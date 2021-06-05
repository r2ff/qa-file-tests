package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static utils.Files.readTextFromPath;
import static utils.Files.readXlsxFromPath;

public class CompareFilesTests {
    String txtFilePath = "./src/test/resources/files/text.txt",
            pdfFilePath = "./src/test/resources/files/text.pdf",
            xlsFilePath = "./src/test/resources/files/text.xls",
            xlsxFilePath = "./src/test/resources/files/text.xlsx",
            zipFilePath = "./src/test/resources/files/text.zip",
            unzipFolderPath = "./src/test/resources/files/unzip/",
            unzipTxtFilePath = "./src/test/resources/files/unzip/text.txt",
            zipPassword = "123",
            expectedData = "a large language ocean";

    @Test
    void txtFileCompareTest() throws IOException {
        String actualData = readTextFromPath(txtFilePath);

        assertThat(actualData, containsString(expectedData));
    }

    @Test
    void pdfFileCompareTest() throws IOException {
        PDF pdf = new PDF(new File(pdfFilePath));
        assertThat(pdf, PDF.containsText(expectedData));
    }

    @Test
    void xlsFileCompareTest() throws IOException {
        XLS spreadsheet = new XLS(new File(xlsFilePath));
        assertThat(spreadsheet, XLS.containsText(expectedData));
    }

    @Test
    void xlsxFileCompareTest() {
        String actualDate = readXlsxFromPath(xlsxFilePath);
        assertThat(actualDate, containsString(expectedData));
    }


    @Test
    void zipFileCompareTest() throws IOException, ZipException {

        ZipFile zipFile = new ZipFile(zipFilePath);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(zipPassword);
            }
            zipFile.extractAll(unzipFolderPath);

        String actualDate = readTextFromPath(unzipTxtFilePath);
        assertThat(actualDate, containsString(expectedData));

    }
}
