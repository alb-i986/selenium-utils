package me.alb_i986.selenium.utils.table;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfNestedElementsLocatedBy;

public class WebTable {

    private final WebDriverWait wait;
    private final By tableLocator;
    private final List<Row> rows = new ArrayList<>();
    private final List<String> columnNames = new ArrayList<>();
    private WebElement table;

    public WebTable(By tableLocator, WebDriverWait wait) {
        this.tableLocator = tableLocator;
        this.wait = wait;
        reload();
    }

    public Row findFirstRow(Where where) {
        List<Row> rows = findRows(where);
        if (rows.isEmpty()) {
            throw new NoSuchElementException("No table row found " + where);
        }
        return rows.get(0);
    }

    /**
     * Find the rows that match the given SQL-like WHERE clause.
     * <p/>
     * Example:
     * <pre>
     * rowIndexes = table.findRows(Where.column("email").contains("email@example.com"));
     *
     * rowIndexes = table.findRows(Where.column("email").contains("email@example.com")
     *      .and(Where.column("name").contains("John"));
     * </pre>
     *
     * @param where to be instantiated by using {@link Where}, which provides a fluent interface
     *                for expressing the search criteria in SQL-like syntax.
     * @see Where
     */
    public List<Row> findRows(Where where) {
        return where.findMatchingRows(this);
    }

    public WebTable reload(){
        table = wait.until(visibilityOfElementLocated(tableLocator));
        wait.until(visibilityOfNestedElementsLocatedBy(tableLocator, thLocator()));
        readTableFull();
        return this;
    }

    private void readTableFull() {
        // read th
        for (WebElement header : table.findElements(thLocator())) {
            columnNames.add(header.getText().trim().toLowerCase());
        }
        // read tr
        for (WebElement row : table.findElements(trLocator())) {
            if (row.isDisplayed()) { // skip hidden rows
                rows.add(new Row(row));
            }
        }
    }

    public List<Row> getRows() {
        return rows;
    }

    public Row getRow(int i) {
        return rows.get(i);
    }

    public int getNumColumns() {
        return columnNames.size();
    }

    /**
     * Subclasses may override this in order to customise the locator for the header rows.
     */
    protected By thLocator() {
        return By.xpath("./thead/tr/th");
    }

    /**
     * Subclasses may override this in order to customise the locator for the content rows.
     */
    protected By trLocator() {
        return By.xpath("./tbody/tr");
    }

    protected By tdLocator() {
        return By.xpath("./td");
    }

    @Override
    public String toString() {
        return table.toString();
    }

    public class Row {
        private final List<WebElement> cells = new ArrayList<>();
        private final WebElement tr;

        public Row(WebElement tr) {
            this.tr = tr;
            for (WebElement column : tr.findElements(tdLocator())) {
                cells.add(column);
            }
        }

        public WebElement getCellByColumnIndex(int i) {
            return cells.get(i);
        }

        public WebElement getCellByColumnName(String columnName) {
            return cells.get(columnNames.indexOf(columnName.trim().toLowerCase()));
        }

        public WebElement getLastColumn() {
            return getCellByColumnIndex(getNumColumns() - 1);
        }
    }
}