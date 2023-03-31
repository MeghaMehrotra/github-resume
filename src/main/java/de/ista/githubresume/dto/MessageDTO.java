package de.ista.githubresume.dto;

/**
 * @author mmehrotra
 */
public class MessageDTO<T extends Object> {

    /**
     *
     */
    private String responseMessage;
    private Boolean status;
    private T body;

    /**
     *
     */
    public MessageDTO() {
        super();
    }

    public MessageDTO(String responseMessage, boolean status) {
        super();
        this.responseMessage = responseMessage;
        this.status = status;
    }

    public MessageDTO(String responseMessage, T body, boolean status) {
        super();
        this.responseMessage = responseMessage;
        this.body = body;
        this.status = status;
    }

    /**
     * @param responseMessage
     * @param body
     */
    public MessageDTO(String responseMessage, T body) {
        super();
        this.responseMessage = responseMessage;
        this.body = body;
    }

    /**
     * @param responseMessage
     */
    public MessageDTO(String responseMessage) {
        super();
        this.responseMessage = responseMessage;
    }

    /**
     * @return the responseMessage
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * @param responseMessage
     *            the responseMessage to set
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    /**
     * @return the body
     */
    public T getBody() {
        return body;
    }

    /**
     * @param body
     *            the body to set
     */
    public void setBody(T body) {
        this.body = body;
    }

    /**
     * @return the status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

}
