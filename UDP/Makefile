#Plockyn Théo & Dubrulle Thomas
CC = gcc
CFLAGS =  -Wall -pedantic
CFLAGS += -g

ReceiveUDP: ReceiveUDP.java
	javac $<

SendUDP: SendUDP.c
	$(CC) $(CFLAGS) -o $@ $<

clear:
	rm *~ mdu valid_name 
