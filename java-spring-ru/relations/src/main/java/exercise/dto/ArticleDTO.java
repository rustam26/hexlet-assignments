package exercise.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArticleDTO {

    private String name;

    private String body;

    private CategoryDTO category;

}
