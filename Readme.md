## Запуск

#### Подключение к PostgreSQL
По шаблону `application-production.properties.example` создать файл `application-production.properties` и заполнить его данными для подключения к PostgreSQL.

#### Запуск приложения

    ./gradlew -Dspring.profiles.active=production -Dserver.port=8000 bootRun

или

    export server_port=8000
    ./gradlew -Dspring.profiles.active=production bootRun

#### Запуск тестов (Используется БД H2)

    ./gradlew test


## API

### Регистрация клиента
`{ POST | PUT } /register HTTP/1.1`

Тело запроса
```json
{
  "login": "tiny-winy-aircrafts",
  "token": "aebeimaisife3ioMah4naeb3",
  "balance": 500
}
```

Пример ответа
```
HTTP/1.1 201 CREATED
Content-Length: 0
```

* login - название клиента, строка до 20 символов, может включать
только URL-совместимые символы `a-z A-Z 0-9 ~ _ -`;
* token - токен (пароль), строка от 10 до 60 символов;
* balance - необязательный параметр, начальный баланс, целое
неотрицательное число, по умолчанию ноль;


### Метод полученея баланса клиента
```
GET /client/{login}/balance HTTP/1.1
X-Client-Token: {token}
```

Пример ответа
```
HTTP/1.1 200 OK
Content-Length: 15

{"balance":500}
```

* {login} - название клиента
* {token} - токен (пароль)