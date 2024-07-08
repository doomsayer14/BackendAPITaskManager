Backend API "Task Manager"

Огляд

Створення API для керування задачами, яке надає основні функції створення, видалення,
оновлення статусу та зміни полів задачі, а також отримання списку задач.

Технології

Мова програмування: Java 17+

Фреймворк: Java Spring

Функціональні вимоги

1. Створення задачі:
Метод: POST
Вхідні дані: JSON об'єкт з даними нової задачі
Вихідні дані: ID створеної задачі або відповідне повідомлення про помилку
2. Видалення задачі:
Метод: DELETE
Вхідні дані: ID задачі, яку потрібно видалити
Вихідні дані: Підтвердження успішного видалення або відповідне повідомлення про
помилку
3. Оновлення статусу задачі:
Метод: PUT
Вхідні дані: ID задачі та новий статус
Вихідні дані: Підтвердження успішного оновлення статусу або відповідне
повідомлення про помилку
4. Зміна полів задачі:
Метод: PATCH
Вхідні дані: ID задачі та змінені поля задачі
Вихідні дані: Підтвердження успішної зміни полів або відповідне повідомлення про
помилку
5. Отримання списку завдань:
Метод: GET
Вхідні дані: Немає
Вихідні дані: Список усіх задач у вигляді JSON об'єкту або порожній список, якщо
немає задач

Зберігання даних

Основна база даних: H2 Database

Резервна база даних: PostgreSQL

Механізм журналювання подій та помилок: logback або log4j

Додаткові обов’язкові вимоги

● Проект повинен мати список усіх REST-команд із коротким описом вхідних та вихідних
даних. Використання OpenAPI специфікації бажане.

● Має бути можливість автоматичного підключення до резервної бази даних у разі
відмови основної.

● Покриття юніт-тестами повинно становити не менше 75% усього проекту.

● Проведення валідації вхідних даних для створення та зміни задач.

● У проекті мають бути написані тести, які демонструють усі типи запитів та сценарії.

● Додайте логіку бізнес-правил до системи, наприклад, перевірку на дублювання задач,
обмеження на кількість створених задач тощо.

За можливості

Надсилати створені задачі до однієї системи на вибір:

● менеджерів черг (Kafka або RabbitMQ)

● до чату (Google Chat або Telegram)

● до RSS стрічки.

Під час перевірки оцінюється

● Працездатність рішення

● Зручність та надійність API

● Журнал роботи

● Код рішення

● Обробка помилок

● Javadoc, коментарі

● Тести
