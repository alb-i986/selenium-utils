package me.alb_i986.selenium.utils.table;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Where} clause for searching rows whose column,
 * either specified by name or by index, matches the given text.
 * <p/>
 * Please use the static factory methods in {@link Where} to instantiate.
 * <p/>
 * Examples:
 * <pre>
 * Where.column("email").is("john.doe@example.com");
 * Where.column("email").containsIgnoreCase("example.COM");
 * </pre>
 *
 * @author ascotto
 */
public class WhereColumn implements Where {

    private final Integer columnIndex;
    private final String columnName;

    private Matcher<String> tableCellTextMatcher;

    /**
     * Specify the column to match against by index.
     *
     * @see Where#column(int)
     */
    protected WhereColumn(int columnIndex) {
        this.columnIndex = columnIndex;
        this.columnName = null;
    }

    /**
     * Specify the column to match against by name.
     *
     * @see Where#column(String)
     */
    protected WhereColumn(String columnName) {
        this.columnName = columnName;
        this.columnIndex = null;
    }

    @Override
    public List<WebTable.Row> findMatchingRows(WebTable table) {
        if (tableCellTextMatcher == null) {
            throw new IllegalStateException(
                    "Please first configure this object by calling #matches or similar");
        }

        List<WebTable.Row> rows = new ArrayList<>();
        for (WebTable.Row row : table.getRows()) {
            WebElement cell;
            if (columnName != null) {
                cell = row.getCellByColumnName(columnName);
            } else {
                cell = row.getCellByColumnIndex(columnIndex);
            }
            if (tableCellTextMatcher.matches(cell.getText().trim())) {
                rows.add(row);
            }
        }
        return rows;
    }

    /**
     * Set the partial text to search for.
     */
    public WhereColumn contains(String partialText) {
        matches(Matchers.containsString(partialText));
        return this;
    }

    /**
     * Set the partial text to search for, ignoring case.
     */
    public WhereColumn containsIgnoringCase(String partialText) {
        matches(Matchers.containsStringIgnoringCase(partialText));
        return this;
    }

    /**
     * Set the exact text to search for.
     */
    public WhereColumn is(String exactText) {
        matches(Matchers.equalTo(exactText));
        return this;
    }

    /**
     * Set the exact text to search for, ignoring case.
     */
    public WhereColumn isIgnoringCase(String exactText) {
        matches(Matchers.equalToIgnoringCase(exactText));
        return this;
    }

    /**
     * Set the Matcher for matching the text found in the table cell.
     */
    public WhereColumn matches(Matcher<String> matcher) {
        this.tableCellTextMatcher = matcher;
        return this;
    }

    @Override
    public String toString() {
        return "WHERE column " + columnToString() + tableCellTextMatcher;
    }

    private String columnToString() {
        return columnName != null ? columnName : "#" + columnIndex;
    }
}
