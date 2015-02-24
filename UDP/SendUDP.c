#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>
#include <assert.h>

/* utiliser .sin_addr.s_addr .sin_port=HTONS(#Port) */
int main(int argc, char *argv[]){
  /* if (argc != 3) {
      fprintf(stderr, "%s <dotted-address> <port> <message>\n", argv[0]);
      exit(EXIT_FAILURE);
   }*/
   struct sockaddr_in to;
   int soc;
   int i;
   
   soc = socket(AF_INET, SOCK_DGRAM, 0); /* création de la socket */

   to.sin_family = AF_INET;
   to.sin_port = htons(atoi(argv[2]));
   to.sin_addr.s_addr = inet_addr(argv[1]);
   for(i=0;i<8; i++){
	to.sin_zero[i] = 0;
   }
   sendto(soc, argv[3], strlen(argv[3]), 0, (struct sockaddr*)&to, sizeof(to)); /* envoie le message */
   exit(EXIT_SUCCESS); 

}
