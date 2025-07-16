Локальное тетсирование 

Добавление 
![img.png](img.png)

показать всё

![img_1.png](img_1.png)

удаление 
![img_2.png](img_2.png)

попытка изменить удаленную запись 

![img_3.png](img_3.png)

Ловим ошибку в логах
```shell
SEVERE: Servlet.service() for servlet [app] in context with path [] threw exception [Request processing failed; nested exception is ru.netology.exception.NotFoundException] with root cause
ru.netology.exception.NotFoundException
	at java.base/java.util.Optional.orElseThrow(Optional.java:408)
```

Второй записи нет 
