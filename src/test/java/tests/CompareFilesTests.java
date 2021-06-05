package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static utils.Files.readTextFromPath;

public class CompareFilesTests {
    String txtFilePath = "./src/test/resources/files/text.txt",
            pdfFilePath = "./src/test/resources/files/text.pdf",
            xlsFilePath = "./src/test/resources/files/text.xls",
            xlsxFilePath = "./src/test/resources/files/text.xlsx",
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
}
