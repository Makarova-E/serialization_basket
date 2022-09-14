import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String[]> clientLog;
    private String[] firstString = {"productNum", "amount"};

    public List<String[]> getClientLog() {
        return clientLog;
    }

    public String[] getFirstString() {
        return firstString;
    }

    public void log(int productNum, int amount) {
        clientLog.add(new String[]
                {String.valueOf(productNum), String.valueOf(amount)});
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile), ',',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            csvWriter.writeNext(firstString);
            csvWriter.writeAll(clientLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
