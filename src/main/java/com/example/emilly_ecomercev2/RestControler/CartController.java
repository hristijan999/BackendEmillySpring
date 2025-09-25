package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Model.Korpa;
import com.example.emilly_ecomercev2.Model.Roba;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CartController {

    @PostMapping("/add")
    public ResponseEntity<?> addItem(HttpSession session, @RequestBody Roba item) {
        Korpa cart = (Korpa) session.getAttribute("CART");
        if (cart == null) {
            cart = new Korpa();
            session.setAttribute("CART", cart);
        }
        cart.addItem(item);
        System.out.println("item added to cart "+item);
        return ResponseEntity.ok(cart.getItems());
    }

    @GetMapping
    public ResponseEntity<?> getCart(HttpSession session) {
        Korpa cart = (Korpa) session.getAttribute("CART");
        if (cart == null) {
            cart = new Korpa();
            session.setAttribute("CART", cart);
        }

        for(Roba item:cart.getItems())
            System.out.println(cart.getItems());

        return ResponseEntity.ok(cart.getItems());
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clearCart(HttpSession session) {
        Korpa cart = (Korpa) session.getAttribute("CART");
        if (cart != null) {
            cart.clear();
        }

        return ResponseEntity.ok("Cart cleared");
    }
    @PostMapping("/remove")
    public ResponseEntity<?> removeItem(HttpSession session,@RequestBody Roba item) {
        Korpa cart = (Korpa) session.getAttribute("CART");
        if (cart != null) {
            cart.removeItemById(item.getId());
        }
        System.out.println("item removed from cart "+item);
        assert cart != null;
        return ResponseEntity.ok(cart.getItems());
    }
}
