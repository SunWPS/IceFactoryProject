package IceFactoryAPI.repository;


import IceFactoryAPI.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends
        JpaRepository<Staff,String> {
}
