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

import exercise.repository.PostRepository;
import exercise.model.Post;
import exercise.model.PostState;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DBRider
// Файл с данными для тестов (фикстуры)
@DataSet("articles.yml")

public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

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
    void testGetPosts() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/posts"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Death Be Not Proud");
        assertThat(response.getContentAsString()).contains("The Monkey`s Raincoat");
    }

    @Test
    void testGetPost() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/posts/4"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("The Monkey`s Raincoat");
        assertThat(response.getContentAsString()).contains("Do the dead frighten you?");
    }

    @Test
    void testCreatePost() throws Exception {
        String content = "{\"id\": 6, \"title\": \"Test post\", \"body\": \"Test body\"}";
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        // Проверяем, что новый пост создаётся в начальном состоянии CREATED
        MockHttpServletResponse response = mockMvc
            .perform(get("/posts/6"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Test post", "Test body");
        assertThat(response.getContentAsString()).contains("CREATED");

        // Проверяем, что пост есть в базе данных
        Post post = postRepository.findByTitle("Test post");
        assertThat(post).isNotNull();
        assertThat(post.getState()).isEqualTo(PostState.CREATED);

    }

    @Test
    void testPublishPost() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(patch("/posts/1/publish"))
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        long id = 1;
        Post post = postRepository.findById(id).get();
        assertThat(post.getState()).isEqualTo(PostState.PUBLISHED);
    }

    @Test
    void testArchivePost() throws Exception {
        MockHttpServletResponse responsePost1 = mockMvc
            .perform(patch("/posts/1/archive"))
            .andReturn()
            .getResponse();

        assertThat(responsePost1.getStatus()).isEqualTo(200);

        long id1 = 1;
        Post post1 = postRepository.findById(id1).get();
        assertThat(post1.getState()).isEqualTo(PostState.ARCHIVED);

        MockHttpServletResponse responsePost2 = mockMvc
            .perform(patch("/posts/2/archive"))
            .andReturn()
            .getResponse();

        assertThat(responsePost2.getStatus()).isEqualTo(200);

        long id2 = 2;
        Post post2 = postRepository.findById(id2).get();
        assertThat(post2.getState()).isEqualTo(PostState.ARCHIVED);
    }

    @Test
    void testArchiveArchivedPost() throws Exception {
        MockHttpServletResponse responsePost1 = mockMvc
            .perform(patch("/posts/3/archive"))
            .andReturn()
            .getResponse();

        assertThat(responsePost1.getStatus()).isEqualTo(422);
    }

    @Test
    void testPublishPublishedPost() throws Exception {
        MockHttpServletResponse responsePost1 = mockMvc
            .perform(patch("/posts/2/publish"))
            .andReturn()
            .getResponse();

        assertThat(responsePost1.getStatus()).isEqualTo(422);
    }

    @Test
    void testPublishArchivedPost() throws Exception {
        MockHttpServletResponse responsePost1 = mockMvc
            .perform(patch("/posts/5/publish"))
            .andReturn()
            .getResponse();

        assertThat(responsePost1.getStatus()).isEqualTo(422);
    }

}
