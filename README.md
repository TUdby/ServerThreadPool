# Overview
A server sets up a thread pool and manager that take in sockets user consumer producer 
model, the manager increases or decreases the thread amount based on traffic. 

## To Run
On the comman line use the commands

$ javac * .java

$ java CapitalizeServer

The server will start running. Now to test it, open a seperate command line and use

$ java ParallelClient

This will start throwing many clients at the server in parallel and printing the responses. 
The server will print messages from the thread pool indicating how many threads it has 
running every time it increased or decreased the amount. This is to show the thread manager in action.

## The Code
The following are the classes and what they do.

### CapitalizeServer
This creates the thread manager, asks it for the queue, then sets up the server and immediatley places every socket into the queue.

### ThreadManager
This creates the thread pool and when started, will poll the amount of threads and jobs to decide whether or not to increase or decrease the amount. The manager
will double the amount of threads when the amount of jobs is over twice the thread count, and will halve the number of threads when the number of jobs is
less than half the thread count. It will not go above 40 threads or below 5 threads. When asked to return the queue it gets the queue from the thread pool.

### ThreadPool
Creates the queue and an array of threads. The array will initially be given 5 threads, and the pool will have functions for increasing or decreasing that will
be called by the manager.

### SocketQueue
A threadsafe queue that holds "Jobs". The Server will drop in sockets and the worker threads will grab them when available (producer and consumer model).

### Job
A class to hold the sockets and client numbers to be placed in the queue.

### ParallelClient
Sets up many clients to call the server in parallel with messages to be capitalized.

