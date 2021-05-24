# httpBankAPI

### API Для работника
1. GET api/employee/users - показать всех юзеров
2. POST api/employee/users - добавление нового юзера
3. POST api/employee/users/accounts - доабвление нового счета для юзера
4. POST api/employee/contractor/accounts - доабвление нового счета для контрагента
5. PUT api/employee/cards - потверждение выпуска новой карты
6. PUT api/employee/transactions - потверждение транзакции


### API Для пользователя
1. POST api/users/cards - выпуск новой карты по счету
2. GET api/users/cards?id=1 - просмотр всех карт юзера
3. POST api/users/cards/refill - внесение средств
4. GET api/users/accounts?id=1 - проверка баланса
5. POST api/users/contractors - добавление контрагента юзеру
6. GET api/users/contractors?id=1 - список всех контрагентов юзера
7. POST api/users/cards/transаfer - перевод средств контрагенту

