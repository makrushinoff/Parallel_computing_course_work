# Інвертований Індекс

Інструкція зі зборки проекту.

1. Інсталювати JDK 17 на комп’ютер;
2. клонувати репозиторій із вихідним кодом;
3. компіляція і запуск за допомогою Maven framework:
    1) інсталювати Maven на комп’ютер;
    2) вікрити термінал/консоль у папці із склонованим проектом;
    3) виконати команду: mvn install;
    4) виконати команду java -jar {папка проекту}/server/target/server-1.0-SNAPSHOT-run.jar;
    5) вікрити ще один термінал/консоль у папці із склонованим проектом;
    6) виконати команду java -jar {папка проекту}/client/target/client-1.0-SNAPSHOT-run.jar.
 
4) компіляція і запуск за допомогою Intellij Idea:
    1) інсталювати і запустити Intellij Idea;
    2) відкрити у Intellij Idea склонований проект;
    3) знайти файл ParallelServerMain.java;
    4) біля назви класу з’виться зелений трикутник – натиснути на нього;
    5) знайти файл ParallelClientMain.java;
    6) біля назви класу з’виться зелений трикутник – натиснути на нього.
