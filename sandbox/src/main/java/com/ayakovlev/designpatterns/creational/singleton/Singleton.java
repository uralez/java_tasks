package com.ayakovlev.designpatterns.creational.singleton;

import java.util.Date;

/*
The class guarantees that it has only one instance and it gives global access point.
* */
public class Singleton {
    // ленивое создание (lazy initialization)
    /*
    volatile гарантирует видимость и порядок операций. Без него объект может быть виден другим потокам до того, как конструктор полностью завершил инициализацию - что приведёт к "полуинициализированному" объекту.
    Пример без volatile может вызвать "instruction reordering":
    память выделена
    ссылка присвоена instance
    конструктор ещё не завершён, но другой поток уже получил доступ.

    Volatile guarantees visibility and order of operations. Without it, an object could be visible to other threads before the constructor has fully initialized, resulting in a "half-initialized" object.
    The example without volatile may cause "instruction reordering":
    Memory allocated
    Reference assigned to instance
    The constructor has not yet completed, but another thread has already gained access.
    * */
    private static volatile Singleton instance;
    /*
    Можно сделать "eager initialization" - сразу создать instance = new Singleton(); при загрузке класса, тогда не нужен synchronized.
    One can make "eager initialization" - create immediately instance = new Singleton(); on class loading, then synchronization is not needed.
    You can do “eager initialization” — immediately create instance = new Singleton(); when loading the class, then synchronized is not needed.
    * */

    //Приватный конструктор не даёт создать экземпляр извне
    // Private constructor doesn't give to create an instance outside
    // Private constructor prevents an instance from being created from outside.
    Date myBirthDay;
    String parentThread;
    private Singleton(){
        // Возможно какая-то инициализация
        // Maybe some initialisation
        myBirthDay = new Date();
        parentThread = Thread.currentThread().getName();
        try{
            // Искусственная задержка - имитация "тяжёлой" инициализации
            // Artificial delay - imitation of "heavy" initialization
            Thread.sleep(100);
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        // Для демонстрации
        // For demonstration
        System.out.println(Thread.currentThread().getName() + " Singleton instance created, parentThread: " + parentThread);
    }
    /*
    Double check locking:
    Навешивание синхронизации на весь метод getInstance() - ведёт к потере производительности.
    Поэтому для повышения эффективности желательно синхронизировать только блок создания экземпляра.
    Поэтому сначала проверяем, что экземпляр не инициализирован, только в этом случае мы делаем синхронизацию,
    а потом проверяем опять на null, потому что во время синхронизации экземпляр синглетона мог инициализировать другой поток.

    Applying synchronisation to the entire method getInstance() leads to a loss of performance.
    Therefore, for increased efficiency, it's preferable to synchronize only the instance creation block.
    Therefore, we first check that the instance isn't initialized, and only then do we synchronize,
    and then check again for null, since another thread could have initialized the singleton instance during synchronisation.
    * */
    public static Singleton getInstance(){
        if(instance == null){// Первая проверка (без блокировки) // First check (without blocking)
            System.out.println(Thread.currentThread().getName() + ": instance == null => synchronise.");
            synchronized(Singleton.class){
                System.out.println(Thread.currentThread().getName() + ": instance == null, synchronisation performed, go to the second check.");
                if(instance == null){// Вторая проверка (внутри блокировки) // Second check (inside the lock)
                    System.out.println(Thread.currentThread().getName() + ": instance == null, synchronisation performed, second check is passed.");
                    instance = new Singleton();
                }else{
                    System.out.println(Thread.currentThread().getName() + ": instance == null, synchronisation performed, but the second check is NOT passed.");
                }
            }
        }else{
            System.out.println(Thread.currentThread().getName() + ": instance is yet created.");
        }
        return instance;
    }
    /*
    public static synchronized Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
    */
    public void doSomething(){
        System.out.println(Thread.currentThread().getName() + " I am doing something. My birthday is " + myBirthDay + "; parentThread is " + parentThread);
    }

}
