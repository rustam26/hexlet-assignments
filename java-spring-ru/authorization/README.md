# Авторизация

Авторизация – это процесс подтверждения прав на совершение каких-либо действий. Авторизация происходит после того, как пользователь подтвердил свою личность.

## Ссылки

* [Пример настройки авторизации](https://www.baeldung.com/spring-security-expressions#2-hasauthority-hasanyauthority)
* [Методы hasAuthority() и hasAnyAuthority() для настройки авторизации](https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/access/expression/SecurityExpressionOperations.html#hasAnyAuthority-java.lang.String...-)

## src/main/java/exercise/model/User.java

## Задачи

* Добавьте в модель пользователя поле `role`, в котором будет храниться роль пользователя. Перечисление ролей, который могут быть у пользователя, находится в файле *UserRole.java*

## src/main/java/exercise/repository/UserRepository.java

В этом задании мы сделаем так, чтобы аутентификация пользователя проходила не по имени пользователя, а по его email.

## Задачи

* Создайте в репозитории метод, который позволит найти в базе данных пользователя по его email.

## src/main/java/exercise/UserDetailsServiceImpl.java

## Задачи

* Допишите содержимое метода `loadUserByUsername()` так, чтобы он получал пользователя по его email. Метод должен вернуть запись пользователя – объект класса `org.springframework.security.core.userdetails.User`.

## src/main/java/exercise/WebSecurityConfig.java

Для настройки авторизации, так же как и при настройке аутентификации, требуется переопределить метод `configure()`

## Задачи

* Допишите содержимое метода `protected void configure()`. Настройте авторизацию следующим образом:
  * Просматривать корневую страницу */* и пройти регистрацию могут все не аутентифицированные пользователи
  * Просматривать список пользователей и информацию о конкретном пользователе могут все аутентифицированные пользователи (пользователи с ролью "USER") и администраторы (роль "ADMIN")
  * Удалить пользователя может только администратор

* Запустите приложение и попробуйте выполнять различные действия о имени пользователя и администратора. Email пользователей и пароли можно посмотреть в файле *scr/main/resources/import.sql*

## Деплой

## Задачи

* Выполните деплой получившегося приложения.

* Откройте задеплоенное приложение в браузере и убедитесь, что оно работает

## Подсказки

* Для дополнительной информации по работе приложения изучите файл с тестами.

* При создании объекта класса `org.springframework.security.core.userdetails.User` понадобится передать в конструктор список полномочий [SimpleGrantedAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/authority/SimpleGrantedAuthority.html), которыми будет наделён пользователь. Используйте роль пользователя для создания списка полномочий.
