package main

import (
	"context"
	"fmt"
	"grpc-p2p-chat/source/grpcChat"
	"log"
	"net"
	"os"
	"strconv"

	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

func runClient(username string, dst string, port int) {
	opts := []grpc.DialOption{
		grpc.WithTransportCredentials(insecure.NewCredentials()),
	}

	conn, err := grpc.Dial(fmt.Sprintf("%s:%d", dst, port), opts...)
	if err != nil {
		log.Fatalf("fail to dial: %v", err)
	}
	defer conn.Close()
	client := grpcChat.NewChatClient(conn)
	stream, err := client.Connect(context.Background())

	if err != nil {
		log.Fatalf("cannot connect to server")
		return
	}

	go grpcChat.ReceiveRoutine(stream)

	grpcChat.SendRoutine(stream, username)
}

func runServer(username string, src string, port int) {
	lis, err := net.Listen("tcp", fmt.Sprintf("%s:%d", src, port))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	grpcServer := grpc.NewServer()
	chatService := &grpcChat.Chat{Username: username}
	grpcChat.RegisterChatServer(grpcServer, chatService)
	grpcServer.Serve(lis)
}

func main() {
	if len(os.Args) == 4 {
		port, err := strconv.Atoi(os.Args[3])
		if err != nil {
			log.Fatalf("port must be covertible to int")
			return
		}
		runClient(os.Args[1], os.Args[2], port)
	} else if len(os.Args) == 3 {
		port, err := strconv.Atoi(os.Args[2])
		if err != nil {
			log.Fatalf("port must be covertible to int")
			return
		}
		runServer(os.Args[1], "localhost", port)
	} else {
		log.Fatalf("usage: char $nikname [$host] $port")
		return
	}
}
