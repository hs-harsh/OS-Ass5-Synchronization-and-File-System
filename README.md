# Synchronization and File System

(1) Implement multiple Readers-Writers problem discussed in the class using
semaphores. Your program should ensure bounded waiting for reader/writer.
Assume that there is a file where all readers and writers want to read/write.


(2) Implement the dynamic (insertion/deletion/update) data structure Red Black/AVL
Tree in concurrent environment. Here the data structure is shared among
multiple threads responsible for each of the operations: insertion, deletion,
update, in-order traversal. Compare the behaviour of this implementation
with the traditional one.


(3) Write a directory search routine (like a simplified version of “find”). Your
program should take a name, and look for a file / directory / etc. by that
name recursively within the current directory. If the named file/directory
is found, the path to the file/directory should be printed along with the
filetype (file / directory / link / etc.). Otherwise, a suitable error message
should be displayed.
