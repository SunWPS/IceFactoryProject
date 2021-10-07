package iceFactory.IceFactoryApplication.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Owner extends Account{


    //editStock
    //edit
    private Set<Staff> staffs = new HashSet<>();


    public Owner changePassword(String oldPass, String newPass){
        if(getPassword().equals(oldPass)){
            this.setPassword(newPass);
            return this;
        }
        else throw new IllegalArgumentException("Old password didn't match!!");
    }

    public Staff createStaff(HashMap<String,Staff> staffMap, String username, String password, String fName, String lName, String phone, String address){
        if(!staffMap.containsKey(username))
        {Staff staff = new Staff();
            staff.setUsername(username);
            staff.setPassword(password);
            staff.setFirstName(fName);
            staff.setLastName(lName);
            staff.setPhoneNumber(phone);
            staff.setAddress(address);
            staff.setOwner(this);
            staffMap.put(staff.getUsername(),staff);
            return staff;}
        else throw new IllegalArgumentException("Already have this account!!");
    }

    public Staff deleteStaff(HashMap<String,Staff> staffMap, String staffUsername){
        return(staffMap.remove(staffUsername));
    }

    public Staff editStaff (HashMap<String,Staff> staffMap, Staff staff){
        Staff editStaff = staffMap.get(staff.getUsername());
        if(staff.getFirstName()!=null)
            editStaff.setFirstName(staff.getFirstName());
        else if(staff.getLastName()!=null)
            editStaff.setLastName(staff.getLastName());
        else if(staff.getPhoneNumber()!=null)
            editStaff.setPhoneNumber(staff.getPhoneNumber());
        else if(staff.getAddress()!=null)
            editStaff.setAddress(staff.getAddress());
        else throw new IllegalArgumentException("Argument is null!!");

        return editStaff;

    }

    public Set<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(Set<Staff> staffs) {
        this.staffs = staffs;
    }
}
