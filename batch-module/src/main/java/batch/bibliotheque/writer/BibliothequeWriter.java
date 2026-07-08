package batch.bibliotheque.writer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import batch.bibliotheque.dto.BookDto;

@StepScope
@Service
public class BibliothequeWriter implements ItemWriter<BookDto> {
  private JdbcTemplate jdbcTemplate;

  private StepExecution executionContext;

  String sql = "UPDATE bibliotheque " +
      "SET statut = ?, stock = ? " +
      "WHERE title = ?";

  private static final Logger LOG = LoggerFactory.getLogger(BibliothequeWriter.class);

  public void setExecutionContext(StepExecution executionContext) {
    this.executionContext = executionContext;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


  @Override
  public void write(List<? extends BookDto> books) throws Exception
  {

    System.out.println("Writing Books for partition" + executionContext.getExecutionContext().get("KEY"));

    try(Connection connection = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);)
    {
      for (BookDto book : books)
      {
        if(book != null) {

          updateBook(book, ps);
          ps.addBatch();
        }
      }

      ps.executeBatch();
    }
    catch (SQLException e)
    {
      throw e;
    }
  }

  private void updateBook(BookDto book, PreparedStatement ps) throws SQLException {
    try
    {
      int index =1;

      ps.setString(index++, book.getStatut());
      ps.setInt(index++,book.getStock());

      //WHERE
      ps.setString(index, book.getTitle());

    }
    catch (SQLException e)
    {
      ps.close();
      throw  new SQLException();
    }
  }

}
