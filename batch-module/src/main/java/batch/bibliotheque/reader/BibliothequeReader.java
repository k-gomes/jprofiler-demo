package batch.bibliotheque.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

import batch.bibliotheque.dto.BookDto;
import batch.bibliotheque.partitionner.BibliothequePartitionner;

@StepScope
@Service
public class BibliothequeReader implements ItemReader<BookDto> {

  private static final Logger LOG = LoggerFactory.getLogger(BibliothequeReader.class);

  private List<BookDto> books = null;

  private JdbcTemplate jdbcTemplate;

  private PreparedStatementCallback<List<BookDto>> pstc;

  private StepExecution executionContext;

  private String sql = "SELECT isbn, title, author, publicationyear, stock, estimationtotal FROM bibliotheque b "
      + "WHERE SUBSTR(b.title,1,1) = '#KEY' ";

  @Override
  public BookDto read()
  {
    String key =  (String) executionContext.getExecutionContext().get("KEY");
    BookDto book = null;
    sql = sql.replace("#KEY", Objects.requireNonNull(key));

    if (books == null) { books = jdbcTemplate.execute(sql, pstc); }

    if (!Objects.requireNonNull(books).isEmpty() && books.get(0) != null) {
      book = books.get(0);
      books.remove(0);
    }

    return book;
  }

  public void setPstc(PreparedStatementCallback<List<BookDto>> pstc) { this.pstc = pstc; }

  public void setExecutionContext(StepExecution executionContext) {
    this.executionContext = executionContext;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
}
