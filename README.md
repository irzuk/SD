# CLI
![alt text](Arch.png)


## Взаимосвязь классов-команд
Есть интерфейс **Command**, наследуемый от интерфеса Runnable, от которого наследуется все доступные команды в интерпретаторе. 
Каждая команда при наследовании имеет метод run() для передачи в поток.
Также каждый класс-наследник при необходимости имеет PipeInputStream и/или PipeOutputStream,
через которые класс взаимодействует с данными.

Вызовом внешних программ занимается команда **Exec**.

## Структура workflow

Основной класс **Interpretator**, внутри которого хранится **Lexer**, **Parser** и **CmdManager**. 
- **Interpretator** 
	- Связующий элемент для всех остальных классов 
	- Отвечает за получение данных на вход 
	- Считанные данные передает в **Lexer**. 
	- Затем передачу полученных из **Lexer** данных в **Parser** 
	- Полученный из **Parser** список передает в **CmdManager**
	- Выведит итоговый результат в консоль. 
- **Lexer** 
	- Разбивает строку на токены
	- Сообщает **Interpretator** нужно ли считывать дальше (Например, в случае незакрытого '/").
- **Parser**  
	- Преобразует токены в список команд (список соотвествует командам через pipe)
	- Сохраняет переменные окружения и занимается их подстановкой
	- Детектирует команду exit
- **CmdManager**
	- Отвечает за запуск полученных из **Parser** команд в ThreadPool
	- Связывает **Commands** между собой с помощью Pipe\*Stream в требуемом порядке
	- В **Interpretator** возвращает выходной поток для последней команды
	- Обрабатывает окончание работы всех потоков
 

## 1 фаза разработки
Реализация основных классов-команд, которые наследуется от **Command** и основной логики приложения, 
без Thread Pool в **CmdManager** и HashMap в **Parser**.


## 2 фаза разработки
Добавление Thread Pool в **CmdManager** для поддержки pipe и HashMap в **Parser** для поддержки переменных окружения. 
На схеме выделены красным.

Для добавления новой поддерживаемой команды необходимо отнаследоваться от **Command**, реализовать все методы интерфейса. Реализовать конструктор от String[] и добавить название команды и реалиованный класс в классе **CLI** в метод registerAllCommands.