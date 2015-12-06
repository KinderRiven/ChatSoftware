#include "header.h"
#include "log.h"
/*
    Miku Server
    2015/11/29 10:50
*/
#define DEBUG 1
int  get_socket = 0;
int  server_socket;
struct sockaddr_in addr_in_server;
struct sockaddr_in addr_in_client;

bool  islive[CLIENT_MAX_NUM];
int   sock_client[CLIENT_MAX_NUM];
void newProcess(){
    char send_buffer[BUFFER_SIZE];
    char recv_buffer[BUFFER_SIZE];
    if(get_socket < 0){
        LOG::OUT("[Error] Get message error!");
        exit(0);
    }
    sprintf(send_buffer, "[Start] Client [%s][%d] has connected!\n", inet_ntoa(addr_in_client.sin_addr),addr_in_client.sin_port);

    if(DEBUG)
        printf("%s", send_buffer);

    write(get_socket, send_buffer, strlen(send_buffer));
    LOG::OUT(send_buffer);
    while(1){
        int len  = read(get_socket, recv_buffer, sizeof(recv_buffer));
        recv_buffer[len] = '\0';
        if(DEBUG)
            LOG::OUT("Get a data sizeof [%d]", len);
        if(len > 0){
            printf("%s", recv_buffer);
            /*
                sendMessage
            */
            sprintf(send_buffer, "[Client %d]:%s", get_socket, recv_buffer);
            for(int i = 0; i < CLIENT_MAX_NUM; i++){
                if(islive[i]){
                    write(sock_client[i], send_buffer, strlen(send_buffer));
                }
            }
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
}
void addClient(int id){
    for(int i = 0; i < CLIENT_MAX_NUM; i++){
        if(!islive[i]){
            islive[i] = 1;
            sock_client[i] = id;
            break;
        }
    }
}
void debug(){
    printf("Now living client is:");
    for(int i = 0; i < CLIENT_MAX_NUM; i++){
        if(islive[i])
            printf("%d ",sock_client[i]);
    }
    puts("");
}
int main(){
    //init
    memset(islive, 0, sizeof(islive));

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
    while(1){
        LOG::OUT("[Hold] Waiting accpet...");
        /*
            Wait a connect for client.
        */
        get_socket = accept(server_socket, (struct sockaddr *)&addr_in_client, &sock_len);

        addClient(get_socket);

        pid_t pid = fork();

        if(pid == -1){
            LOG::OUT("[Error] Fork error!");
        }
        if(pid == 0){
            newProcess();
            exit(0);
        }
        else{
            debug();
        }
    }
    LOG::OUT("[End] Miku Server is end.");
    return 0;
}

