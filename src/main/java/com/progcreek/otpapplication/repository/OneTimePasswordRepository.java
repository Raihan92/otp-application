package com.progcreek.otpapplication.repository;

import com.progcreek.otpapplication.models.OneTimePassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneTimePasswordRepository extends CrudRepository<OneTimePassword, Long> {
}
