/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.controller;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
/**
 *
 * @author Administrator
 */
@ServerEndpoint("/u-websocket")
public class SessionWebsocket {

    // Set chứa tất cả các phiên kết nối (clients)
    private static Set<Session> clients = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
        System.out.println("New connection opened: " + session.getId());
        System.out.println("Total clients connected: " + clients.size());
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        System.out.println("Connection closed: " + session.getId());
        System.out.println("Total clients connected: " + clients.size());
    }

    // Lấy ngẫu nhiên một client trong danh sách
    private static Session getRandomClient() {
        List<Session> clientList = new ArrayList<>(clients);
        Random rand = new Random();
        Session randomClient = clientList.get(rand.nextInt(clientList.size()));
        System.out.println("Random client selected: " + randomClient.getId());
        return randomClient;
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("Message received from client " + session.getId() + ": " + message);

        // Chọn một người nhận ngẫu nhiên và gửi tin nhắn
        Session randomClient = getRandomClient();
        try {
            randomClient.getBasicRemote().sendText(message);
            System.out.println("Message sent to client " + randomClient.getId() + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to client " + randomClient.getId());
        }
    }
}

