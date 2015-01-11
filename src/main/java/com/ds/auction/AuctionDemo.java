package com.ds.auction;


import com.ds.auction.model.Bid;
import com.ds.auction.model.Product;
import com.ds.auction.model.User;
import com.ds.auction.service.NotificationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class AuctionDemo {
    public static void main(String[] args) {
        User[] u = {
                new User("user1", "user2@ff.com", true),
                new User("user2", "user2@ff.com", true),
                new User("user3", "user3@ff.com", true),
        };

        List<Product> p = new LinkedList<>();
        p.add( new Product("Mac book pro 13", 1, LocalDateTime.now().plusHours(1), BigDecimal.valueOf(100), BigDecimal.valueOf(1000)));
        p.add( new Product("Skii boots Head", 3, LocalDateTime.now().plusHours(5), BigDecimal.valueOf(100), BigDecimal.valueOf(350)));
        p.add( new Product("Snowboard", 1, LocalDateTime.now().plusHours(2), BigDecimal.valueOf(100), BigDecimal.valueOf(400)));


        AuctionEngine auction = new AuctionEngineImpl(new NotificationService());
        Random rand = new Random();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int prodNumber = rand.nextInt(p.size());
                int userNumber = rand.nextInt(u.length);
                Product product = p.get(prodNumber);
                Bid bid = new Bid(product,
                                    BigDecimal.valueOf(
                                        rand.nextInt(
                                                product.getReservedPrice().intValue()
                                                - product.getMinimalPrice().intValue() + 1
                                                    ) + product.getMinimalPrice().intValue()),
                                    1,
                                    u[userNumber]);
                auction.placeBid(bid);
                if (auction.auctionIsClosed(product)) {
                    p.remove(product);
                }
                if (p.size() == 0) {
                    timer.cancel();
                    timer.purge();
                }
            }
        };


        timer.schedule(timerTask, 1000, 20);
    }
}
