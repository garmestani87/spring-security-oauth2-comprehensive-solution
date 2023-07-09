package com.garm.security.repository.mongo;

public interface UserRepositoryBase {

    boolean changePassword(String oldPassword, String newPassword, String username);

}
