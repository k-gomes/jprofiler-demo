package batch.bibliotheque.processor;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import batch.bibliotheque.dto.BookDto;
import org.springframework.web.client.RestTemplate;

@StepScope
@Service
public class BibliothequeProcessor implements ItemProcessor<BookDto, BookDto> {
  private JdbcTemplate jdbcTemplate;

  @Override
  public BookDto process(BookDto book) throws SQLException, IOException, InterruptedException {

    int stock = getStockFromBook(book);
    book.setStatut("MIS A JOUR AVEC SUCCES");
    System.out.println("Processing Book: " + book.getIsbn()+ " Publication year : " +this.getYearFromBook(book)+ " Title : " +this.getTitleFromBook(book));
    return book;
  }

  private int getStockFromBook(BookDto book) throws SQLException {

    int result = 0;
    String sql = "SELECT stock FROM bibliotheque b WHERE b.title = ? ";

    try (Connection connection = jdbcTemplate.getDataSource().getConnection();
         PreparedStatement ps = connection.prepareStatement(sql);)
    {
      ps.setString(1, book.getTitle());
      ResultSet rs = ps.executeQuery();
      while (rs.next())
      { result = rs.getInt("stock"); }
    }

    return result;
  }

  public String getYearFromBook(BookDto book) throws IOException, InterruptedException {
    String uri = "http://localhost:8080/api/book/publicationyear?title="+book.getTitle();

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .header( "accept", "application/json")
            .uri(URI.create(uri))
            .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();

  }

  public String getTitleFromBook(BookDto book) throws IOException, InterruptedException {
    String uri = "http://localhost:8080/api/book/isbn?isbn="+book.getIsbn();

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .header( "accept", "application/json")
            .uri(URI.create(uri))
            .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();

  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


}
