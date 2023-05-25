package com.network_hw;

class Review {

    public Review(Integer id, Integer userId, Status status, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.title = title;
        this.body = body;
    }

    enum Status {
        NEW, APPROVED, BY_MODERATOR;
    }

    protected Integer id;
    protected Integer userId;
    protected Status status;
    protected String title;
    protected String body;

    ReviewFullView getFullView() {
        return new ReviewFullView(id, userId, title, body);
    }

    ReviewPreview getPreview() {
        return new ReviewPreview(id, userId, title);
    }


}

class ReviewFullView {
    protected Integer id;
    protected Integer userId;
    protected String title;
    protected String body;

    public ReviewFullView() {

    }

    public ReviewFullView(Integer id, Integer userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

class ReviewPreview {
    protected Integer id;
    protected Integer userId;
    protected String title;

    public ReviewPreview() {

    }

    public ReviewPreview(Integer id, Integer userId, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}