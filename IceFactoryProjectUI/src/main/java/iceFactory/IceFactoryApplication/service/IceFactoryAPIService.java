package iceFactory.IceFactoryApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import iceFactory.IceFactoryApplication.model.Owner;
import iceFactory.IceFactoryApplication.model.Staff;


@Service
public class IceFactoryAPIService {

    @Autowired
    private RestTemplate restTemplate ;

    //OWNER---------------------------------------------------------------------
    public List<Owner> getOwnerAll() {
        String url = "http://localhost:8090/owner";
        ResponseEntity<Owner[]> response = restTemplate.getForEntity(url, Owner[].class);
        Owner[] owner = response.getBody();
        return Arrays.asList(owner);
    }
    public Owner getOwnerByUsername(String username) {
        String url = "http://localhost:8090/owner/" + username;
        ResponseEntity<Owner> response = restTemplate.getForEntity(url, Owner.class);
        return response.getBody();
    }

    public void updateOwner(Owner owner) {
        String url = "http://localhost:8090/owner/" + owner.getUsername();
        restTemplate.put(url, owner, Owner.class);
    }

    //Staff---------------------------------------------------------------------
    public List<Staff> getStaffAll() {
        String url = "http://localhost:8090/staff";
        ResponseEntity<Staff[]> response = restTemplate.getForEntity(url, Staff[].class);
        Staff[] staffs = response.getBody();
        return Arrays.asList(staffs);
    }

    public Staff getStaffByUsername(String username) {
        String url = "http://localhost:8090/staff/" + username;
        ResponseEntity<Staff> response = restTemplate.getForEntity(url, Staff.class);
        Staff staff = response.getBody();
        return staff;
    }

    public void addStaff(Staff staff) {
        String url = "http://localhost:8090/staff";
        restTemplate.postForObject(url, staff, Staff.class);
    }

    public void updateStaff(Staff staff) {
        String url = "http://localhost:8090/staff/" + staff.getUsername();
        restTemplate.put(url, staff, Staff.class);
    }

    public void deleteStaff(String username) {
        String url = "http://localhost:8090/staff/" + username;
        restTemplate.delete(url);
    }



}


