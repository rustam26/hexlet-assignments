# Введение в Spring

## Ссылки

* [Гайд по созданию простого приложения на Spring boot](https://spring.io/guides/gs/spring-boot/)
* [Пример оформления контроллера](https://www.baeldung.com/spring-controller-vs-restcontroller)
* [Пример работы с параметром, переданным в query string](https://www.baeldung.com/spring-request-param)
* [Аннотации в Spring boot для привязки запросов к контроллерам и обработчикам, а также для привязки параметров запроса к аргументам метода](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html)
* [Postman — инструмент для тестирования API](https://www.postman.com/)

## src/main/java/exercise/WelcomeController.java

## Задачи

* Создайте контроллер `WelcomeController`

* Реализуйте обработчик, который будет обрабатывать GET-запросы к корневой странице приложения по пути */*. Запрос на эту страницу должен вернуть строку "Welcome to Spring".

* Реализуйте обработчик, который обрабатывает GET-запросы по пути */hello*. Этот маршрут принимает параметр *name*, переданный в query string, и возвращает строку в формате "Hello, name!". Например, для маршрута */hello?name=John*, должна вернуться строка "Hello, John!" Если параметр в query string не передан, по умолчанию он равен "World". То есть, GET-запрос на адрес */hello* должен вернуть строку `Hello, World!`

## Самостоятельная работа

* В этом задании вы сделали своё первое рабочее приложение на Spring boot. Сделайте это приложение доступным в интернете, задеплойте его. Для этого нужно будет применить знания, полученные в уроке "Деплой".
