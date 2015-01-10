package com.ds.auction.service;

import com.ds.auction.model.User;

public class NotificationService {
    public NotificationService() {
    }

    public void sendNotification(User user, String msg) {
        System.out.println("[Email to: " + user + "] :" + msg);
    }
}