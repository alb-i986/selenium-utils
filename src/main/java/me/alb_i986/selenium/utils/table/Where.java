package me.alb_i986.selenium.utils.table;

import java.util.List;

/**
 * To specify a clause to find a table row.
 * <p>
 * Inspired by Selenium's {@link org.openqa.selenium.By} pattern, which finds elements in a
 * SearchContext aka WebDriver aka web page, whereas this finds rows within a table.
 *
 * @author ascotto
 */
public interface Where {

    List<WebTable.Row> findMatchingRows(WebTable table);

    static WhereColumn column(int columnNumber) {
        return new WhereColumn(columnNumber);
    }

    static WhereColumn column(String columnName) {
        return new WhereColumn(columnName);
    }

}
