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

import exercise.repository.PostRepository;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DBRider
@DataSet("posts.yml")

public class PostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Test
    void testGetPosts() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/posts"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString())
            .contains("The Moving Toyshop", "Why is it that when one man builds a wall");
        assertThat(response.getContentAsString())
            .contains("The Monkey`s Raincoat", "Do the dead frighten you?");
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
        assertThat(response.getContentAsString()).contains("Do the dead frighten you");
    }

    @Test
    void testCreatePost() throws Exception {
        String content = "{\"title\": \"Test post\", \"body\": \"Test body\"}";
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/posts"))
            .andReturn()
            .getResponse();

        assertThat(response.getContentAsString()).contains("Test post", "Test body");
        assertThat(postRepository.existsByTitle("Test post")).isTrue();
    }

    @Test
    void testUpdatePost() throws Exception {
        String content = "{\"title\": \"Updated post\", \"body\": \"Updated body\"}";
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                patch("/posts/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/posts"))
            .andReturn()
            .getResponse();

        assertThat(response.getContentAsString()).contains("Updated post", "Updated body");
        assertThat(response.getContentAsString())
            .doesNotContain("The Moving Toyshop", "Why is it that when one man builds a wall");

    }

    @Test
    void testDeletePost() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(delete("/posts/1"))
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/posts"))
            .andReturn()
            .getResponse();

        assertThat(response.getContentAsString())
            .doesNotContain("The Moving Toyshop", "Why is it that when one man builds a wall");
        assertThat(postRepository.existsById(1L)).isFalse();
    }
}
