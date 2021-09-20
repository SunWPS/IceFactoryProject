package iceFactory.iceFactoryAPI.repository;

import iceFactory.iceFactoryAPI.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends
        JpaRepository<Owner,String> {
}

