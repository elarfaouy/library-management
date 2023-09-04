public enum BookStatus {
    AVAILABLE("available"), ON_LOAN("on loan"), LOST("lost");

    private final String status;

    BookStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
