package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.CsvFileWriterService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.ProductDao;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ProductDaoImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class Main {
    private static final String VALID_SOURCE_PATH = "src//main//resources//Correct.csv";
    private static final String INVALID_SOURCE_PATH = "src//main//resources//InCorrect.csv";
    private static final String NEGATIVE_OPERATION_SOURCE_PATH =
            "src//main//resources//NullParameters.csv";
    private static final String WRITE_TO_FILE_PATH = "src//main//resources//Report.csv";

    public static void main(String[] args) {
        CsvFileReaderService fileReaderService = new CsvFileReaderServiceImpl();
        List<String> linesFromFile = fileReaderService.readLines(VALID_SOURCE_PATH);

        ParserService parserService = new ParserServiceImpl();
        List<FruitTransaction> fruitTransactions = parserService.parseTransaction(linesFromFile);

        ProductDao productDao = new ProductDaoImpl();
        OperationStrategy operationStrategy =
                new OperationStrategy(productDao);

        FruitService fruit =
                new FruitServiceImpl(operationStrategy);
        fruit.process(fruitTransactions);

        ReportService reportService = new ReportServiceImpl();
        CsvFileWriterService csvFileWriterService = new CsvFileWriterServiceImpl();
        csvFileWriterService.writeToFile(WRITE_TO_FILE_PATH,
                reportService.createReport(productDao.getAll()));
    }
}
