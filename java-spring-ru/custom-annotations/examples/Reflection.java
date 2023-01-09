// // // Отметим класс аннотацией
// @CustomAnnotation(value = "Hexlet")
// public class Example {
//     public void foo() {

//     }
// }

// При помощи рефлексии можно получить доступ к аннотации и её параметрам
// Example example = new Example();

// // Проверка наличия аннотации у класса
// Example.class.isAnnotationPresent(CustomAnnotation.class);

// // Получение аннотации
// example.getClass().getAnnotation(CustomAnnotation.class);

// // Получение значения параметра, переданного в аннотацию
// example
//     // Получаем класс объекта
//     .getClass()
//     // Получаем аннотацию класса CustomAnnotation
//     .getAnnotation(CustomAnnotation.class)
//     // Вызываем метод аннотации для получения значения параметра
//     .value(); // "Hexlet"

// // Вызов метода

// example.getClass().getMethod("foo").invoke(example)


