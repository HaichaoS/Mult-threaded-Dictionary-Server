# Mult-threaded-Dictionary-Server

In this project, a multi-threaded server, that allows multiple clients to search the meaning(s) of a word, add a new word, and remove an existing word concurrently, is designed and implemented using client-server architecture. Sockets and threads are the lowest level of abstraction for network communication and concurrency. The system will follow a client-server architecture in which multiple clients can connect to a (single) multi-threaded server and perform operations concurrently. All communication will take place via sockets. These sockets use TCP protocol and JSON to exchange information. All communication between components is reliable and errors are properly managed.

## Running

Server

```
java –jar DictionaryServer.jar <port> <dictionary-file>
```

Client

```
java –jar DictionaryClient.jar <server-address> <server-port>
```

## Built With
* [JOrtho](http://jortho.sourceforge.net/) - Used to spell checking
* [Json-simple](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm) - Used to manipulate JSON files

## Authors

* **Haichao Song** - *University of Melbourne*
