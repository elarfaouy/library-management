package domain.enums;

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

    public static BookStatus fromString(String status) {
        for (BookStatus bookStatus : values()) {
            if (bookStatus.status.equalsIgnoreCase(status)) {
                return bookStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant with status: " + status);
    }
}
