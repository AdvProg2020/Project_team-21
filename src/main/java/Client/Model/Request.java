package Client.Model;

public class Request {
    String requestId;
    String provider;
    String type;

    public Request(String requestId, String provider, String type) {
        this.requestId = requestId;
        this.provider = provider;
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getProvider() {
        return provider;
    }

    public String getType() {
        return type;
    }
}
