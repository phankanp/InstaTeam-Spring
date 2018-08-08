package phan.spring.hibernate.instateam.service;

public class AssignedException extends RuntimeException {
    private String message;

    public AssignedException(String there) {
        message = there;
    }

    public String getExceptionMessage() {
        return message;
    }
}
