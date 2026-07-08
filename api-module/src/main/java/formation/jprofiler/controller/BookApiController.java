package formation.jprofiler.controller;

import formation.jprofiler.api.BookApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/book")
public class BookApiController {

    final BookApiService bookApiService;

    public BookApiController(BookApiService bookApiService)
    {
        this.bookApiService = bookApiService;
    }

    @GetMapping("/publicationyear")
    public int getYear(@RequestParam String title) throws SQLException {
        System.out.println("Getting publication year for : " +title);
        return bookApiService.getPublicationyearFromTitle(title);
    }

    @GetMapping("/isbn")
    public String getTitleFromIsbn(@RequestParam String isbn) throws SQLException {
        System.out.println("Getting title for : " +isbn);
        return bookApiService.getTitleFromIsbn(isbn);
    }

    @GetMapping("/test")
    public String getHealthCheck()  {
        return "Im alive";
    }

}
