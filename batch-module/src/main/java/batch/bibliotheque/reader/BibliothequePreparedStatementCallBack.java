package batch.bibliotheque.reader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.jdbc.core.PreparedStatementCallback;

import batch.bibliotheque.dto.BookDto;

public class BibliothequePreparedStatementCallBack implements PreparedStatementCallback<List<BookDto>> {
  private StepExecution executionContext;

  private static final Logger LOG = LoggerFactory.getLogger(BibliothequePreparedStatementCallBack.class);

  public List<BookDto> doInPreparedStatement(PreparedStatement ps) throws SQLException
  {
    /** liste BookDto */
    List<BookDto> lstReturn = new ArrayList<>();
    try (ResultSet rs = ps.executeQuery()) {
      BookDto book;
      while (rs.next())
      {
        if ((rs.getString("isbn")) != null) {
          book = mappingBookFromDatabase(rs);
          lstReturn.add(book);
        }
      }
      return lstReturn;
    }
  }

    private BookDto mappingBookFromDatabase (ResultSet rs) throws SQLException
    {
      //isbn, title, author, publicationyear, stock, estimationtotal
      BookDto book = new BookDto();
      book.setIsbn(rs.getString("isbn"));
      book.setTitle(rs.getString("title"));
      book.setAuthor(rs.getString("author"));
      book.setPublicationYear(rs.getInt("publicationyear"));
      book.setStock(rs.getInt("stock"));
      book.setEuroPrices(rs.getDouble("estimationtotal"));
      return book;
    }

  public void setExecutionContext(StepExecution executionContext) {
    this.executionContext = executionContext;
  }

}
