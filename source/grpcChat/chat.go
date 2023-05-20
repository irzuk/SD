package grpcChat

import (
	"bufio"
	"errors"
	"fmt"
	"io"
	"os"
	"time"
)

type Sender interface {
	Send(*ChatMessage) error
}

type Recver interface {
	Recv() (*ChatMessage, error)
}

type SendRecver interface {
	Send(*ChatMessage) error
	Recv() (*ChatMessage, error)
}

func ReceiveRoutine(stream Recver) {
	for {
		req, err := stream.Recv()
		if errors.Is(err, io.EOF) {
			fmt.Printf("[connection closed]")
			return
		}

		if err != nil {
			fmt.Printf("[stream.Recv() error: %s]", err.Error())
			return
		}

		fmt.Printf("\n@%s [%s]: %s\n> ", req.Author, req.Time, req.Text)
	}
}

func SendRoutine(stream Sender, username string) error {
	reader := bufio.NewReader(os.Stdin)
	for {
		os.Stdout.Write([]byte("> "))
		text, _, err := reader.ReadLine()
		if err == io.EOF {
			fmt.Printf("[close connection]\n")
			return nil
		}

		if err != nil {
			fmt.Printf("[error: %s]\n", err.Error())
			return err
		}

		stream.Send(&ChatMessage{Author: username, Text: string(text), Time: time.Now().Format(time.TimeOnly)})
	}
}

type Chat struct {
	Username string
}

func (c *Chat) Connect(stream Chat_ConnectServer) error {
	go ReceiveRoutine(stream)

	return SendRoutine(stream, c.Username)
}
