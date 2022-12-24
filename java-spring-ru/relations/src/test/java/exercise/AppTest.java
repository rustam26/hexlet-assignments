package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.junit5.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import exercise.repository.ArticleRepository;

import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@DBRider
// Файл с данными для тестов (фикстуры)
@DataSet("articles.yml")

public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void testRootPage() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("Welcome to Spring");
    }

    @Test
    void testGetArticles() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/articles"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("The Moving Toyshop");
        assertThat(response.getContentAsString()).contains("Death Be Not Proud");
    }

    @Test
    void testGetArticle() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/articles/3"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Dying of the Light");
        assertThat(response.getContentAsString()).contains("Give me honorable enemies rather than ambitious ones");
        assertThat(response.getContentAsString()).contains("Cinema");
    }

    @Test
    void testCreateArticle() throws Exception {
        String content = "{\"name\": \"Test article\", \"body\": \"Test body\", \"category\": {\"id\": \"5\"}}";
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/articles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/articles"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Test article", "Test body");
        assertThat(response.getContentAsString()).contains("Other");
    }

    @Test
    void testUpdateArticle() throws Exception {
        String content = "{\"name\": \"Updated article\", \"body\": \"Updated body\", \"category\": {\"id\": \"5\"}}";
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                patch("/articles/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/articles"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Updated article", "Updated body");
        assertThat(response.getContentAsString())
            .doesNotContain("The Moving Toyshop", "Why is it that when one man builds a wall");

    }

    @Test
    void testDeleteArticle() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(delete("/articles/1"))
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/articles"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString())
            .doesNotContain("The Moving Toyshop", "Why is it that when one man builds a wall");
    }
}
