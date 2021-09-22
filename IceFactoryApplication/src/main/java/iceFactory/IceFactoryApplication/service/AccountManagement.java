package iceFactory.IceFactoryApplication.service;



import iceFactory.IceFactoryApplication.model.Owner;
import iceFactory.IceFactoryApplication.model.Staff;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AccountManagement {
    private HashMap<String,Owner> ownerMap = new HashMap<>();
    private HashMap<String,Staff> staffMap = new HashMap<>();

    private Owner currentOwner ;
    private Staff currentStaff ;

    public AccountManagement() {
        currentOwner=null;
        currentStaff=null;

    }

    public void checkOwnerAccount(String username, String password){
            for(Owner owner : ownerMap.values()){
                try{
                    if(owner.entryCheck(username,password)){
                    currentOwner = owner;
                    return; }
                }
                catch (IllegalArgumentException e){
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
            if(currentOwner==null) throw new IllegalArgumentException("We don't have this username in the database.");
    }

    public void checkStaffAccount(String username,String password){
        for(Staff staff : staffMap.values()){
            try{
                if(staff.entryCheck(username,password)){
                currentStaff = staff;
                return;
                }
            }
            catch (IllegalArgumentException e){
                throw new IllegalArgumentException(e.getMessage()); }
        }
        if(currentStaff==null) throw new IllegalArgumentException("We don't have this username in the database.");
    }

    private HashMap<String,Staff> staffListToMap(List<Staff> list){
        staffMap.clear();
        for(Staff s : list){
            staffMap.put(s.getUsername(),s);
        }
        return staffMap;
    }

    private HashMap<String,Owner> ownerListToMap(List<Owner> list){
        ownerMap.clear();
        for(Owner o : list){
            ownerMap.put(o.getUsername(),o);
        }
        return ownerMap;
    }

    public HashMap<String, Owner> getOwnerMap() {
        return ownerMap;
    }

    public void setOwnerMap(HashMap<String, Owner> ownerMap) {
        this.ownerMap = ownerMap;
    }

    public HashMap<String, Staff> getStaffMap() {
        return staffMap;
    }

    public void setStaffMap(HashMap<String, Staff> staffMap) {
        this.staffMap = staffMap;
    }

    public void setOwnerMapFromList(List<Owner> ownerList) {
        this.ownerMap = ownerListToMap(ownerList);
    }



    public void setStaffMapFromList(List<Staff> staffList) {
        this.staffMap = staffListToMap(staffList); }

    public Owner getCurrentOwner() { return currentOwner; }

    public void setCurrentOwner(Owner currentOwner) { this.currentOwner = currentOwner; }

    public Staff getCurrentStaff() { return currentStaff; }

    public void setCurrentStaff(Staff currentStaff) { this.currentStaff = currentStaff; }
}
