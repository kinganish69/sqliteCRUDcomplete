package code.anish.sqlitecrudx;

public class Model {

    String id, image, name, age, phone, addTimeStamp, updateTimeStamp;

    public Model (String id, String image, String name, String age, String phone, String addTimeStamp, String updateTimeStamp) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.addTimeStamp = addTimeStamp;
        this.updateTimeStamp = updateTimeStamp;

    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddTimeStamp() {
        return addTimeStamp;
    }
    public void setAddTimeStamp(String addTimeStamp) {
        this.addTimeStamp = addTimeStamp;
    }
    public String getUpdateTimeStamp() {
        return updateTimeStamp;
    }
    public void setUpdateTimeStamp(String updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }



}
