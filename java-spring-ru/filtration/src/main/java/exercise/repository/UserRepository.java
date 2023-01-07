package exercise.repository;

import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import exercise.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import exercise.model.QUser;


import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends
        CrudRepository<User, Long>,
        QuerydslPredicateExecutor<User>,
        QuerydslBinderCustomizer<QUser> {

    @Override
    default void customize(QuerydslBindings bindings, QUser user) {
        // Дополнительная задача

        // BEGIN
        bindings.bind(user.firstName)
                .first(StringExpression::containsIgnoreCase);
        bindings.bind(user.lastName)
                .first(StringExpression::containsIgnoreCase);
        bindings.bind(user.email)
                .first(StringExpression::containsIgnoreCase);
        bindings.bind(user.profession)
                .first(StringExpression::containsIgnoreCase);
        bindings.bind(user.gender)
                .first(SimpleExpression::eq);
        // END
    }

}
