#include "header.h"
#include "log.h"
/*
    Miku Server
    2015/11/29 10:50
*/
#define DEBUG 1
int  server_socket;
struct sockaddr_in addr_in_server;
struct sockaddr_in addr_in_client;

pthread_t arrThread[CLIENT_MAX_NUM];

bool isLive[CLIENT_MAX_NUM];
int  SocketClient[CLIENT_MAX_NUM];
int  nowSocket;
void sendMessage(char *s){
    for(int i = 0; i < CLIENT_MAX_NUM; i++)
        if(isLive[i]){
            write(SocketClient[i], s, strlen(s));
        }
}
void newProcess(int get_socket){
    char send_buffer[BUFFER_SIZE];
    char recv_buffer[BUFFER_SIZE];
    /*
        Say hello to Client
    */
    sprintf(send_buffer, "Welcome Client [%d]!\n",get_socket);
    write(get_socket, send_buffer, strlen(send_buffer));
    printf("Welcome Client [%d]!\n", get_socket);
    /*
        Sow message to Client
    */
    sprintf(send_buffer, "[Start] Client[%s][%d] has connected!\n", inet_ntoa(addr_in_client.sin_addr),addr_in_client.sin_port);
    write(get_socket, send_buffer, strlen(send_buffer));
    LOG::OUT(send_buffer);
    /*
        Wait
    */
    while(1){
        int len  = read(get_socket, recv_buffer, sizeof(recv_buffer));
        recv_buffer[len] = '\0';
        if(DEBUG)
            LOG::OUT("Get a data sizeof [%d]", len);
        if(len > 0){

            //printf("%s", recv_buffer);
            /*
                sendMessage
            */
            sprintf(send_buffer, "[Client %d]:%s", get_socket, recv_buffer);
            sendMessage(send_buffer);
            /*
                sprintf(send_buffer, "Get a data sizeof [%d]\n", len);
                write(get_socket, send_buffer, strlen(send_buffer));
            */
        }
        else{
            LOG::OUT("[End] Connection is broken!");
            break;
        }
    }
    for(int i = 0; i < CLIENT_MAX_NUM; i++)
        if(SocketClient[i] == get_socket){
            printf("Say good bye to Client [%d]!\n", get_socket);
            isLive[i] = false;
            break;
        }
}
void *startThread(void *arg0){
    newProcess(nowSocket);
    return NULL;
}
/*
    add Client
*/
void debug(){

}
int main(){
    //init
    memset(isLive, 0, sizeof(isLive));
    server_socket = socket(AF_INET, SOCK_STREAM, 0);
    if(server_socket < 0){
        LOG::OUT("[Error] Create socket error!");
        exit(0);
    }
    socklen_t sock_len = sizeof(addr_in_client);

    bzero(&addr_in_server, sizeof(addr_in_server));

    addr_in_server.sin_family = AF_INET;
    /*
        host to network short
    */
    addr_in_server.sin_port = htons(SERVER_PORT);
    /*
        host to network long long
    */
    addr_in_server.sin_addr.s_addr = htonl(INADDR_ANY);

    if(bind(server_socket, (struct sockaddr *)&addr_in_server, sizeof(addr_in_server)) < 0){
        LOG::OUT("[Error] Bind error!");
        exit(0);
    }
    if(listen(server_socket, 5)){
        LOG::OUT("[Error] Listen error!");
        exit(0);
    }


    LOG::OUT("[Start] Server is running.");
    int get_socket = 0;
    printf("Server Start Running!\n");
    while(1){
        LOG::OUT("[Hold] Waiting accpet...");
        /*
            Wait a connect for client.
        */
        get_socket = accept(server_socket, (struct sockaddr *)&addr_in_client, &sock_len);

        if(get_socket < 0){
            LOG::OUT("[Error] Get message error!");
            continue;
        }
        /*
            Start a new thread
        */
        for(int i = 0; i < CLIENT_MAX_NUM; i++)
            if(!isLive[i]){
                isLive[i] = true;
                SocketClient[i] = get_socket;
                nowSocket = get_socket;
                pthread_create(arrThread + i, NULL, startThread, NULL);
                break;
            }
    }
    LOG::OUT("[End] Miku Server is end.");
    return 0;
}


