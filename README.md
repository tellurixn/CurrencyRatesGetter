# Задание
Напишите простое spring boot приложение, позволяющее получать курсы валют на определенный день. Предполагается, что в нем будет один http-метод, принимающих запросы из стороннего клиента (Postman, wget, curl и т.д.), какого либо пользовательского web-интерфейса (UI на html и js) в приложении не требуется.

Данные по курсам валют относительно рубля необходимо получать с ресурса Центрального Банка: https://www.cbr.ru/development/SXML/
Необходима реализация фонового Job, который раз в сутки будет получать актуальные курсы валют и сохранять их в локальной базе данных. Для этого нужно создать структуру таблицы в БД. 

При обращении по http к приложению будут передаваться параметры (GET): 
+ Код валюты в буквенном формате согласно ISO 4217
+ Дата на которую нужно получить курс в формате yyyy-MM-dd
В ответе должен быть json объект с полями: код валюты, дата, значение в паре с рублем. 

Приложение при обращении должно проверить наличие данных в локальной базе, а при их отсутствии (в том случае если фоновый Job еще не работал, или не успел отработать за текущий день) осуществить обращение на сервис ЦБ, сохранить данные и вернуть их клиенту. В случае отсутствия данных локально и в ЦБ вернуть ошибку. 


## Для выполнения задания использованы: 

+ Java 21
+ Spring boot 3.2.6
+ Postgesql 12

Таблица currency для хранения информации о валютах 

![image](https://github.com/tellurixn/CurrencyRatesGetter/assets/90182791/7e546491-88d8-44ac-ace1-d60775c3f688)

# Демонтрация работы

Запустим приложение и попробуем получить курс валюты на определенный день

### Данные сохранены локально

![image](https://github.com/tellurixn/CurrencyRatesGetter/assets/90182791/77e1e9a1-e9db-4b99-b1b0-c221c26d489c)

Запросим данные из записи с id 121

![image](https://github.com/tellurixn/CurrencyRatesGetter/assets/90182791/6a985622-664c-4abe-855f-ccf7e94ad88b)


### Данные отсутсвуют локально, имеются в ЦБ

В базе данных пока нет данных о курсах валют на 1 мая 2024 года:

![image](https://github.com/tellurixn/CurrencyRatesGetter/assets/90182791/4cd660dd-253d-4ebc-befa-46294b00843b)

Выполним запрос на эту дату:

![image](https://github.com/tellurixn/CurrencyRatesGetter/assets/90182791/d5157b46-5bfd-4117-a702-6351c9d3fdb8)

Перед ответом приложение запрсило данные с ЦБ, сохранило их локально и выдало ответ:

![image](https://github.com/tellurixn/CurrencyRatesGetter/assets/90182791/30ab1570-05a7-49ad-8b1a-226752a201b7)


### Данные отсутсвуют локально и в ЦБ

Для проверки вводится несуществующий код валюты "XXXXX"

![image](https://github.com/tellurixn/CurrencyRatesGetter/assets/90182791/b3466903-7237-4f69-ad10-0c39f6e7903f)

