package backend.academy.fractall_flame.config;

public record Rect(double x, double y, double width, double height) {

    public boolean contains(Point p) {
//        log.info("sdkdksdks");
        return p.x() >= x && p.x() <= x + width && p.y() >= y && p.y() <= y + height;
    }
}
