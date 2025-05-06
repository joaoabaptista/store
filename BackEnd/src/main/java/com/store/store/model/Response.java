package com.store.store.model;

public class Response {
    private Item item;
    private String message;

    public Response(Item item, String message) {
        this.item = item;
        this.message = message;
    }

    public Item getItem() {
        return item;
    }

    public String getMessage() {
        return message;
    }
}
