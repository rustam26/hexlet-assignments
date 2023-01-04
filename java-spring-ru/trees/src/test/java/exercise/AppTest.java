package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;
import com.github.database.rider.junit5.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DBRider
@DataSet("courses.yml")
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

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
    void testGetCourse() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/courses/7"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Advanced Testing");
        assertThat(response.getContentAsString()).contains("Learn how to test real applications");
    }

    @Test
    void testGetPreviousWithRoot() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/courses/1/previous"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetPrevious1() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/courses/2/previous"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Introduction to programming");
        assertThat(response.getContentAsString()).contains("Learn about programming basics");
    }

    @Test
    void testGetPrevious2() throws Exception {
        MockHttpServletResponse response = mockMvc
            .perform(get("/courses/5/previous"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains(
            "Introduction to programming", "Learn about programming basics"
        );
        assertThat(response.getContentAsString()).contains(
            "Arrays", "Learn about arrays"
        );
        assertThat(response.getContentAsString()).contains(
            "Objects", "Learn about objects"
        );
    }

}
