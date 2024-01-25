### Task - SpringMVC

* Проект разработан на основе Spring Framework. В нем используются конфигурационные классы ApplicationConfig.java,
* DatabaseConfig.java, DispatcherInitializer.java, HibernateConfig.java, LiquibaseConfig.java и
* WebMvcConfig.java для настройки контекста приложения. В проекте предусмотрена MVC архитектура. Для пояснения функциональность
* оформлены javadoc.
* Согласно условиям задания создана сущность User, которая может:
* 1) регистрировать пользователя (метод save в UserService);
* 2) входить по логину и паролю (метод login в UserService);
* 3) просматривать всех пользователей (метод findAll в UserService);
* 4) менять пароль (метод changingPassword в UserService);

* Реализована структура проекта согласно условиям задания.
* В проекте используется конфигурационный файл типа .yml. Создан 1 файла application-dev.yml. Но впроекте предусмотрено
* обеспечить 2 типа конфигурации dev и prod например. Выбор осуществляется указанием -Dspring.profiles.active=dev в
* Edit Configurations -> VM Options.
* * Добавлены тесты для сервиса. Покрытие не на 100%, но исправлюсь. Также нужно подправить создание тестовых данных.