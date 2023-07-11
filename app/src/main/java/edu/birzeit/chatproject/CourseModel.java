package edu.birzeit.chatproject;

public class CourseModel {
    String couresName;
    String courseRef;

    public String getCouresName() {
        return couresName;
    }

    public void setCouresName(String couresName) {
        this.couresName = couresName;
    }

    public CourseModel(String couresName, String courseRef) {
        this.couresName = couresName;
        this.courseRef = courseRef;
    }

    public String getCourseRef() {
        return courseRef;
    }

    public void setCourseRef(String courseRef) {
        this.courseRef = courseRef;
    }

    @Override
    public String toString() {
        return "CourseModel{" +
                "couresName='" + couresName + '\'' +
                '}';
    }
}
