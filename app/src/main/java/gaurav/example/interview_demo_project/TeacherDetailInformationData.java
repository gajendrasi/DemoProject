package gaurav.example.interview_demo_project;

public class TeacherDetailInformationData {

    String teacherName,teacherSp,docImage;;
    String teacherMobile;

    public TeacherDetailInformationData(){}

    public TeacherDetailInformationData(String name,String mobile,String specialization)
    {
        this.teacherName=name;
        this.teacherMobile=mobile;
        this.teacherSp=specialization;
    }

    public String getTeacherName() {

        return teacherName;
    }

    public void setTeacherName(String teacherName) {

        this.teacherName = teacherName;
    }

    public String getTeacherSp() {

        return teacherSp;
    }

    public void setTeacherSp(String teacherSp) {

        this.teacherSp = teacherSp;
    }

    public String getDocImage() {

        return docImage;
    }

    public void setDocImage(String docImage) {

        this.docImage = docImage;
    }

    public String getTeacherMobile() {

        return teacherMobile;
    }

    public void setTeacherMobile(String teacherMobile) {

        this.teacherMobile = teacherMobile;
    }
}
