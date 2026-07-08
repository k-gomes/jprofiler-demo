package formation.jprofiler.api;

import formation.jprofiler.controller.BookApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class BookApiService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int getPublicationyearFromTitle(String title) throws SQLException
    {
        int result = 0;
        String sql = "SELECT publicationyear FROM bibliotheque b WHERE b.title = ? ";

        try (
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getInt("publicationyear");
                System.out.println("Publication year founded for : " +title+ " is : "+result);
            }
        }

        return result;

    }


    public String getTitleFromIsbn(String isbn) throws SQLException {
        String result = "";
        String sql = "SELECT title FROM bibliotheque b WHERE b.isbn = ? ";

        try (
                Connection connection = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getString("title");
                System.out.println("Title founded for : " +isbn+ " is : "+result);
            }
        }

        return result;

    }
}
