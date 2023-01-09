// // Proxy – это особый объект,
// // с помощью которого можно управлять доступом к свойствам практически любых объектов.
// // Прокси может менять поведение исходного объекта
// // Proxy оборачивает исходный объект и перехватывает запросы к нему
// // Прикладной код не должен знать, что он работает не с исходным объектом, а с Proxy.

// // Обернём в прокси
// // Метод newProxyInstance() создаёт новый экземпляр прокси
// // На вход принимает getClassLoader для класса, массив реализуемых интерфейсов и обработчик вызова
// MyInterface proxyInstance = Proxy.newProxyInstance(
//     MyClass.class.getClassLoader(),
//     MyClass.class.getInterfaces(),
//     // Лямбда - обработчик вызова
//     // В качестве аргумента принимает сам объект прокси, метод, к которому происходит обращение
//     // и массив аргументов, переданных при вызове
//     (proxy, method, args) -> {
//         // При помощи рефлексии получаем имя метода
//         if (method.getName().equals("identity")) {
//             return 10;
//         } else {
//             throw new UnsupportedOperationException(
//                 "Unsupported method: " + method.getName()
//             );
//         }
//     }
// );

// proxyInstance.identity(5); // 10
// proxyInstance.getValue(); // Exception
