# Spring lifecycle

## Ссылки

* [Класс LocalDateTime – класс, представляющий дату и время](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html)
* [Аннотация @Component – отмечает класс, как компонент](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html)
* [Аннотация @Configuration – отмечает, что класс объявляет бины](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html)
* [Аннотация @Bean – отмечает, что метод создаёт бин](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)
* [Интерфейс BeanPostProcessor – позволяет модифицировать бины в процессе создания](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html)

## src/main/java/exercise/Meal.java

## Задача

* Добавьте необходимые импорты.

* Аннотируйте класс `Meal` так, чтобы при старте приложения создавался бин этого класса c именем "meal" и добавлялся в контекст приложения.

## src/main/java/exercise/MyApplicationConfig.java

* Добавьте нужные импорты

* Используйте необходимые аннотации и сконфигурируйте приложение так, чтобы в зависимости от времени суток в контекст добавлялся только определённый бин, который соответствует текущему времени суток:

  * Если сейчас утро (с 6 часов включительно до 12 часов), должен добавиться бин класса `Morning`
  * Если сейчас день (с 12 часов включительно до 18 часов), должен добавиться бин класса `Day`
  * Если сейчас вечер (с 18 часов включительно до 23 часов), должен добавиться бин класса `Evening`
  * Если сейчас ночь (с 23 часов включительно до 6 часов утра), должен добавиться бин класса `Night`

  Имя бина выберите по своему усмотрению

## src/main/java/exercise/WelcomeController.java

* Добавьте необходимые импорты

* Получите из контекста приложения объект текущего времени суток и объект класса `Meal`

* Добавьте в контроллер метод, который обрабатывает GET запросы на адрес */daytime*. Метод должен пожелать приятного аппетита в зависимости от времени суток. Например, если сейчас утро, запрос должен вернуть строку "It is morning now. Enjoy your breakfast". При составлении фразы используйте методы полученных из контекста объектов.

## Самостоятельная работа

При необходимости мы можем управлять жизненным циклом бина в приложении. Для этого используется интерфейс `BeanPostProcessor`. Он позволяет модифицировать бины, созданные фабрикой `BeanFactory`. Интерфейс содержит всего два метода: `postProcessBeforeInitialization()` и `postProcessAfterInitialization()`. Первый метод вызывается перед тем, как бин будет инициализирован, второй – после. В этих методах вы можете разместить ту логику, которая вам нужна. Обычно, метод `postProcessAfterInitialization()` используется, чтобы обернуть объект в прокси. В самостоятельной работе мы проследим за жизненным циклом.

* В файле *src/main/java/exercise/CustomBeanPostProcessor.java* создайте класс `CustomBeanPostProcessor`, который реализует интерфейс `BeanPostProcessor`. Не забудьте отметить его нужной аннотацией, чтобы при старте этот бин добавился в контекст приложения.

* В классе создайте два метода: `postProcessBeforeInitialization()` и `postProcessAfterInitialization()`. Сигнатуру методов можно посмотреть в документации на интерфейс. Добавьте в методы вывод в консоль, чтобы можно было отследить порядок вызова этих методов. Например:

  ```java
  System.out.println(System.out.println("Called postProcessBeforeInitialization for bean: " + beanName));
  ```

  Сами бины мы не будем никак модифицировать, только отследим процесс. Оба метода должны возвращать сам бин без изменений.

* В файле *src/main/java/exercise/Meal.java* создайте метод `init()` и отметьте его аннотацией `@PostConstruct`. Этот метод будет вызван при инициализации бина. В методе сделайте вывод в консоль, чтобы отследить процесс вызова:

```java
System.out.println("Init bean meal");
```

* Запустите приложение и изучите вывод в консоли. Посмотрите, какие бины создаются приложения и в каком порядке происходит вызов методов. Найдите в выводе наш бин `meal`. Вывод будет выглядеть примерно так:

```bash
Called postProcessBeforeInitialization for bean: meal
Init bean Meal
Called postProcessAfterInitialization for bean: meal
```

## Подсказки

* Для определения текущего времени можно воспользоваться классом `LocalDateTime`. Изучите методы этого класса и найдите подходящие.
