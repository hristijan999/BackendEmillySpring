package com.example.emilly_ecomercev2.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Korpa {
    private List<Roba> items = new ArrayList<>();

    public void addItem(Roba item) {
        items.add(item);
    }

    public void clear() {
        items.clear();
    }
    public void removeItemById(Long id) {
        items.removeIf(item -> item.getId().equals(id));
    }
    public List<Roba> getItems()
    {
        return items;
    }

}
