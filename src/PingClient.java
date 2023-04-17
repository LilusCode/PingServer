import java.io.IOException;
import java.net.*;
import java.util.Scanner;

//clients job is to connect to the solver, send user input text data, and print the returned data.
public class PingClient {
    public static void main(String[] args) throws IOException {

        //creates socket connection to server
        DatagramSocket clientside = new DatagramSocket(2015);
        byte[] outbuffer = new byte[1024];
        byte[] inbuffer = new byte[1024];
        String input = new String();

        //prompts user for text to capitalize
        System.out.println("Please input text to be capitalized:");
        Scanner sc = new Scanner(System.in);
        input = sc.next();
        //convert packet to bytes
        outbuffer = input.getBytes();
        //pack bytes into packet
        DatagramPacket outPacket = new DatagramPacket(outbuffer,outbuffer.length, InetAddress.getByName("localhost"),2014);
        //send packet


        //loop to send packet 10 times, waiting one second per reply
        double sentTime = 0;
        for(int i = 0; i<10; i++){
            clientside.send(outPacket);
            sentTime = System.nanoTime();
            clientside.setSoTimeout(1000);
            System.out.println("Ping attempt number " + (i+1) + ". Message sent at " + sentTime);
            while(true){
                try{
                    DatagramPacket receivePacket = new DatagramPacket(inbuffer, inbuffer.length);
                    clientside.receive(receivePacket);
                    String cap = new String(receivePacket.getData(),0, receivePacket.getLength());
                    System.out.println("Reply from server: " + cap + ". Elapsed time: " + ((System.nanoTime()-sentTime)/1000) + " milliseconds.");
                    break;
                }
                catch (SocketTimeoutException e) {
                    System.out.println("Request timed out.");
                    break;
                }

            }
        }
        }
    }
