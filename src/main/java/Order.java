public class Order {
    private Status status;
    private String visitorName;

    public Order(Status status, String visitorName) {
        this.status = status;
        this.visitorName = visitorName;
    }

    public Status getStatus() {
        return status;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
