# Spring ORM

В предыдущем домашнем задании мы вручную формировали запросы в базу данных при помощи JDBC. В этом домашнем задании мы будем использовать ORM

## Ссылки

* [Интерфейс CrudRepository – обеспечивает основные операции по поиску, сохранению и удалению данных](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)
* [Аннотация @Entity – отмечает, что класс является моделью](https://docs.oracle.com/javaee/7/api/javax/persistence/Entity.html)
* [Аннотации @Getter и @Setter для автоматической генерации геттеров и сеттеров](https://projectlombok.org/features/GetterSetter)
* [Аннотации для привязки запросов к методам контроллера](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html)
* [Аннотация @RequestBody – привязывает параметр метода к телу запроса](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestBody.html)
* [Аннотация @PathVariable – привязывает параметр метода к значению плейсхолдера](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PathVariable.html)

## src/main/java/exercise/model/Person.java

## Задачи

* Допишите модель `Person`. Добавьте в модель приватные поля `firstName` и `lastName` с типом `String`, а также геттеры и сеттеры для всех полей класса. Не забудьте поставить нужную аннотацию, чтобы класс расценивался как модель.

## build.gradle

В прошлом упражнении мы писали файл миграции руками. Liquibase умеет автоматически генерировать файл миграции на основании моделей, для этого нужно подключить зависимости и настроить Liquibase.

## Задачи

* Изучите файл *build.gradle*, обратите внимание на комментарии

* При помощи команды `make generate-migrations` сгенерируйте файл миграции. Изучите получившийся файл

## src/main/java/exercise/repository/PersonRepository.java

## Задачи

* Изучите класс репозитория. Обратите внимание, что он наследуется от класса `CrudRepository`, благодаря чему в нём уже есть различные методы для работы с данными

## src/main/java/exercise/controller/PeopleController.java

* Часть методов для работы с сущностью уже написаны в контроллере. Изучите их код, посмотрите, как происходит работа с репозиторием. Данные в теле запроса приходят в виде JSON следующего вида:

```json
{
  "firstName": "Jain",
  "lastName": "Doe"
}
```

## Задачи

* Допишите метод, который обрабатывает POST-запросы по пути */people* и добавляет новую сущность в базу данных. Чтобы привязать параметр метода к телу запроса, используйте аннотацию `@RequestBody`

* Допишите метод, который обрабатывает DELETE-запросы по пути */people/{id}* и удаляет сущность из базы. Чтобы получить id сущности из пути, используйте аннотацию `@PathVariable`

* Допишите метод, который обрабатывает PATCH-запросы по пути */people/{id}* и обновляет данные сущности.

## Подсказки

* Обратите внимание, что в классе модели импортирована библиотека Lombok. Вы можете использовать её аннотации, чтобы не писать геттеры и сеттеры вручную

* Метод [save()](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#save-S-) не только добавляет новую сущность в базу, но и обновляет данные уже существующей

* Чтобы обновить сущность при помощи метода `save()`, потребуется установить её id. Для этого вам понадобится написанный сеттер
