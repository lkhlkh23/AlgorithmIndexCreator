package domain;

public class FileComponent {
    private String url;
    private String qustionName;
    private String reviewed;
    private String qustionNo;
    private boolean isComplete;

    public FileComponent(String url, String qustionName, String reviewed, boolean isComplete, String questionNo) {
        this.url = url;
        this.qustionName = qustionName;
        this.reviewed = reviewed;
        this.isComplete = isComplete;
        this.qustionNo = questionNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQustionName() {
        return qustionName;
    }

    public void setQustionName(String qustionName) {
        this.qustionName = qustionName;
    }

    public String isReview() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        reviewed = reviewed;
    }

    public String getQustionNo() {
        return qustionNo;
    }

    public void setQustionNo(String qustionNo) {
        this.qustionNo = qustionNo;
    }

    public String getReviewed() {
        return reviewed;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
