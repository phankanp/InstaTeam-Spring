package phan.spring.hibernate.instateam.web;

public enum Status {
    ACTIVE("Active", "active"),
    ARCHIVED("Archived", "archived"),
    WAITING("Waiting", "waiting");

    private final String name;
    private final String status;

    Status(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
