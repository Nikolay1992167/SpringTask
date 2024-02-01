### Task - SpringMVC

* Проект разработан на основе проекта Spring Framework, путем удаления папки config с конфигурациями и создания 
* класса Application. Данный класс выполняет роль конфигурационного и запуск приложения осуществляется с его помощью.

### Технологии применённые в проекте

* Java 17
* Gradle 8.1.1
* Springframework.boot' version '3.2.1'
* Spring-boot-starter-data-jpa
* Spring-boot-starter-web
* spring-boot-starter-validation
* Liquibase 4.25.1
* Jackson-databind 2.14.2
* Mapstruct 1.5.3.Final
* Postgresql 42.6.0
* Spring-boot-starter-test
* Assertj-core 3.24.2
* Mockito-junit-jupiter 5.8.0
* Springdoc-openapi-starter-webmvc-ui 2.3.0

### Инструкция по запуску приложения локально

1. У вас должно быть установлено [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html),
   [Intellij IDEA Ultimate](https://www.jetbrains.com/idea/download/), [Tomcat 10.1](https://tomcat.apache.org/download-10.cgi)
   and [Postgresql](https://www.postgresql.org/download/).
2. В Postgresql вы должны создать базу данных houses.
3. В зависимости от используемого профиля вы вводите в application.yml укажите тип профиля spring: profiles: active:-.
   Введите свои username и password в соответсвующий файл в строках №5, №6
4. Скрипты по созданию таблиц и загрузке данных выполняются автоматически посредствам Liquibase.
5. Приложение готово к работе.

### Тесты

1. Тесты были написаны со 100% охватом Service слоя.

```
./gradlew test
```

### Функциональные возможности

#### UserController

* **GET findById | Находит одного User в базе данных по uuid**
* http://localhost:8080/users/0699cfd2-9fb7-4483-bcdf-194a2c6b7fe6
* Response example:
````json
{
   "uuid": "0699cfd2-9fb7-4483-bcdf-194a2c6b7fe6",
   "name": "Виктор",
   "surname": "Строганов",
   "phone": 375297684569,
   "createDate": "2024-01-24T12:00:00:000",
   "userType": "ADMIN"
}
````
* Bad Request example:
````json
{
   "errorMessage": "User with 0699cfd2-9fb7-4483-bcdf-194a2c6b7fe4 not found!",
   "errorCode": "404 NOT_FOUND"
}
````

* **GET findAll | Находит всеx Users в базе данных**
* http://localhost:8080/users
* Response example:
````json
[
   {
      "uuid": "0699cfd2-9fb7-4483-bcdf-194a2c6b7fe6",
      "name": "Виктор",
      "surname": "Строганов",
      "phone": 375297684569,
      "createDate": "2024-01-24T12:00:00:000",
      "userType": "ADMIN"
   },
   {
      "uuid": "9724b9b8-216d-4ab9-92eb-e6e06029580d",
      "name": "Олег",
      "surname": "Кашева",
      "phone": 375297684568,
      "createDate": "2024-01-24T12:00:00:000",
      "userType": "USER"
   },
   {
      "uuid": "d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14",
      "name": "Женя",
      "surname": "Полоса",
      "phone": 375297684566,
      "createDate": "2024-01-24T12:00:00:000",
      "userType": "USER"
   },
   {
      "uuid": "e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15",
      "name": "Катя",
      "surname": "Мотуга",
      "phone": 375297684565,
      "createDate": "2024-01-24T12:00:00:000",
      "userType": "USER"
   },
   {
      "uuid": "ec6d8ad6-c8cc-4f92-9908-baea6f667ca5",
      "name": "Николай",
      "surname": "Карамзин",
      "phone": 375297894561,
      "createDate": "2024-01-27T22:50:35:144",
      "userType": "USER"
   },
   {
      "uuid": "c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13",
      "name": "Валя",
      "surname": "Луна",
      "phone": 375297894578,
      "createDate": "2024-01-24T12:00:00:000",
      "userType": "ADMIN"
   }
]
````

* **POST save | Сохраняет одного User  в базу данных**
* http://localhost:8080/users
* Request example:
````json
{
   "name": "Вася",
   "surname": "Пупкин",
   "login": "vasja",
   "password": "pupkin",
   "phone": 375297894777,
   "userType":"USER"
}
````
* Response example:
````json
{
   "uuid": "93c33f84-4f70-447b-9369-410c7e8fad3b",
   "name": "Вася",
   "surname": "Пупкин",
   "phone": 375297894777,
   "createDate": "2024-01-28T00:36:03:972",
   "userType": "USER"
}
````
* Bad Request example:
````json
{
    "errorMessage": "Логин или пароль уже используются!",
    "errorCode": "409 CONFLICT"
}
````

* **PUT update | Обновляет одного User в базе данных по uuid**
* http://localhost:8080/users/c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13
* Request example:
````json
{
   "name": "Валя",
   "surname": "Луна",
   "login": "valja",
   "password": "luna",
   "phone": 375297894578,
   "userType":"ADMIN"
}
````
* Response example:
````json
{
   "uuid": "c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13",
   "name": "Валя",
   "surname": "Луна",
   "phone": 375297894578,
   "createDate": "2024-01-24T12:00:00:000",
   "userType": "ADMIN"
}
````
* Bad Request example:
````json
{
   "errorMessage": "User with c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a10 not found!",
   "errorCode": "404 NOT_FOUND"
}
````

* **DELETE delete | Удаляет одного User в базе данных по uuid**
* http://localhost:8080/users/d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14

* **POST authorization | Проверяет вход по логину и паролю**
* http://localhost:8080/users/authorization
* Request example:
````json
{
   "login":"oleg",
   "password":"kasheva"
}
````
* Response example:
   "Авторизация прошла успешно!"

* Bad Request example:
````json
{
   "errorMessage": "Данные введены не верно или пользователь не существует!",
   "errorCode": "401 UNAUTHORIZED"
}
````

* **PATCH changingPassword | Выполняет обновление пароля User по введённым данным**
* http://localhost:8080/users
* Request example:
````json
{
   "login":"oleg",
   "oldpassword":"kasheva",
   "newpassword":"gruzin"
}
````
* Response example:
````json
{
   "uuid": "9724b9b8-216d-4ab9-92eb-e6e06029580d",
   "name": "Олег",
   "surname": "Кашева",
   "phone": 375297684568,
   "createDate": "2024-01-24T12:00:00:000",
   "userType": "USER"
}
````
* Bad Request example:
````json
{
   "errorMessage": "Пароли совпадают, придумайте новый пароль!",
   "errorCode": "400 BAD_REQUEST"
}
````